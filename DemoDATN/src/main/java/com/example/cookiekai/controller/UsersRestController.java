package com.example.cookiekai.controller;

import com.example.cookiekai.service.impl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersRestController {
    @Autowired
    private UsersServiceImpl usersService;

    @PostMapping("/users/check")
    public String checkDuplicate(@RequestParam("email") String email) {
        return usersService.isUnique(email) ? "OK" : "Duplicated";
    }
}
