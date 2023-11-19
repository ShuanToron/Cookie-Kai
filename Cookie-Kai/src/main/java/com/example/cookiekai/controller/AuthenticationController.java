package com.example.cookiekai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/cookie-kai/login")
    public String viewLogin() {
        return "dashboard/authentication/login";
    }
}
