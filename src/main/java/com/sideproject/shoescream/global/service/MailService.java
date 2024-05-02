package com.sideproject.shoescream.global.service;


import com.sideproject.shoescream.global.constant.AuthType;
import com.sideproject.shoescream.global.entity.EmailAuth;
import com.sideproject.shoescream.global.repository.EmailAuthRepository;
import com.sideproject.shoescream.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@EnableAsync
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MemberRepository memberRepository;
    private final EmailAuthRepository emailAuthRepository;

    private static final String SENDER_EMAIL = "junobee27@gmail.com";
    private static int authNumber;

    public MimeMessage createMail(String mail) {
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

    public static void createNumber() {
        authNumber = (int) (Math.random() * (90000)) + 100000;
    }

    public int sendMail(String mail) {
        checkEmail(mail);
        MimeMessage message = createMail(mail);
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
    public String checkValidAuthByEmail(String mail, Integer authNumber) {
        EmailAuth emailAuth = emailAuthRepository.findValidAuthByEmail(mail, AuthType.EMAIL_AUTH, authNumber, LocalDateTime.now())
                .orElseThrow(RuntimeException::new);
        emailAuth.expired();
        return "이메일 인증 성공";
    }

    private void checkEmail(String mail) {
        if (memberRepository.existsByEmail(mail)) {
            throw new IllegalArgumentException("이메일 중복 입니다.");
        }
    }
}
