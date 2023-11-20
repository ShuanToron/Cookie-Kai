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

    @GetMapping("/cookie-kai/register")
    public String viewRegister() {
        return "dashboard/authentication/register";
    }

    @PostMapping("/login")
    public String loginAction() {
        return "redirect:/admin/users";
    }
}
