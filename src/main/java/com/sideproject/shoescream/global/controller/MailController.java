package com.sideproject.shoescream.global.controller;

import com.sideproject.shoescream.global.dto.response.Response;
import com.sideproject.shoescream.global.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail-auth")
public class MailController {

    private final MailService mailService;

    @PostMapping("/signup")
    public Response<String> mailSend(String mail) {
        return Response.success("" + mailService.sendMail(mail));
    }

    @GetMapping("/signup-check")
    public Response<String> mailCheck(String mail, Integer authNumber) {
        return Response.success(mailService.checkValidAuthByEmail(mail, authNumber));
    }
}
