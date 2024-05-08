package com.sideproject.shoescream.member.service;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class KaKaoLoginPageController {

    private static final String client_id = "cb87d198bac8bdd63f6684692e3d827c";

    private static final String redirect_uri = "http://localhost:8080/login/oauth2/code/kakao";

    @GetMapping("/login")
    public String loginPage(Model model) {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
        model.addAttribute("location", location);

        return "login";
    }
}
