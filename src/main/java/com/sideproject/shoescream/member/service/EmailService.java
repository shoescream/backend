package com.sideproject.shoescream.member.service;


import com.sideproject.shoescream.global.exception.ErrorCode;
import com.sideproject.shoescream.member.constant.AuthType;
import com.sideproject.shoescream.member.dto.request.MemberFindMemberInfoRequest;
import com.sideproject.shoescream.member.entity.EmailAuth;
import com.sideproject.shoescream.member.entity.Member;
import com.sideproject.shoescream.member.exception.AlreadyExistEmailException;
import com.sideproject.shoescream.member.exception.MemberNotFoundException;
import com.sideproject.shoescream.member.repository.EmailAuthRepository;
import com.sideproject.shoescream.member.repository.MemberRepository;
import com.sideproject.shoescream.member.util.PasswordGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final EmailAuthRepository emailAuthRepository;
    private final BCryptPasswordEncoder encoder;

    private static final String SENDER_EMAIL = "junobee27@gmail.com";
    private static int authNumber;
    private static String randomPassword;

    public MimeMessage createAuthMail(String mail) {
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(SENDER_EMAIL);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + authNumber + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public MimeMessage createFindPasswordMail(String mail) {
        createRandomPassword();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(SENDER_EMAIL);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("비밀번호 찾기");
            String body = "";
            body += "<h3>" + "임시 비밀번호가 발급 되었습니다." + "</h3>";
            body += "<h1>" + randomPassword + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public static void createNumber() {
        authNumber = (int) (Math.random() * (90000)) + 100000;
    }

    public static void createRandomPassword() {
        randomPassword = PasswordGenerator.generateRandomPassword(10);
    }

    public int sendAuthMail(String mail) {
        checkEmail(mail);
        MimeMessage message = createAuthMail(mail);
        javaMailSender.send(message);
        emailAuthRepository.save(
                EmailAuth.builder()
                        .email(mail)
                        .authType(AuthType.EMAIL_AUTH)
                        .authNumber(authNumber)
                        .expired(false)
                        .build());
        return authNumber;
    }

    @Transactional
    public String sendRandomPasswordMail(MemberFindMemberInfoRequest memberFindMemberInfoRequest) {
        Member member = memberRepository.findByEmail(memberFindMemberInfoRequest.email())
                .orElseThrow(() ->
                        new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
        MimeMessage message = createFindPasswordMail(memberFindMemberInfoRequest.email());
        javaMailSender.send(message);
        member.setPassword(encoder.encode(randomPassword));
        return "비밀번호 발급 메일이 성공적으로 전송되었습니다.";
    }

    @Transactional
    public String checkValidAuthByEmail(String mail, Integer authNumber) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(mail, AuthType.EMAIL_AUTH, authNumber, LocalDateTime.now())
                .orElseThrow(RuntimeException::new);
        emailAuth.expired();
        return "이메일 인증 성공";
    }

    private void checkEmail(String mail) {
        if (memberRepository.existsByEmail(mail)) {
            throw new AlreadyExistEmailException(ErrorCode.ALREADY_EXIST_EMAIL);
        }
    }
}
