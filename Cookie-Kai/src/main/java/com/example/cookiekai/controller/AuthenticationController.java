package com.example.cookiekai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/cookie-kai/login")
    public String viewLogin() {
        return "dashboard/authentication/login";
    }

    @GetMapping("/cookie-kai/forgot-password")
    public String viewRegister() {
        return "dashboard/authentication/forgot-password";
    }

    @GetMapping("/cookie-kai/reset-password")
    public String loginAction() {
        return "dashboard/authentication/reset-password";
    }
}
