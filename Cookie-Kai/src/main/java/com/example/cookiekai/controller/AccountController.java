package com.example.cookiekai.controller;

import com.example.cookiekai.entity.Users;
import com.example.cookiekai.security.UsersDetail;
import com.example.cookiekai.service.impl.UsersServiceImpl;
import com.example.cookiekai.util.FileUploadUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AccountController {
    @Autowired
    private UsersServiceImpl usersService;

    @GetMapping("/account")
    public String viewDetailAcc(@AuthenticationPrincipal UsersDetail loggedUser, Model model) {
        String email = loggedUser.getUsername();
        Users users = usersService.getByEmail(email);
        model.addAttribute("user", users);
        System.out.println("User authenticate with fullname: " + users.getFullname());
        System.out.println("User authenticate with photo: " + users.getPhotos());
        return "dashboard/nhan-vien/view-update-account";
    }

    @PostMapping("/account/update")
    public String updateDetailAcc(@Valid @ModelAttribute("user") Users users, BindingResult result, Model model, @RequestParam("image") MultipartFile file, HttpSession session) throws IOException {
        if (!file.isEmpty()) {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            users.setPhotos(fileName);
            Users savedUser = usersService.updateAccount(users);
            String uploadDirectory = "user-photos/" + savedUser.getId();
            FileUploadUtil.cleanDirectory(uploadDirectory);
            FileUploadUtil.saveFile(uploadDirectory, fileName, file);
        } else {
            usersService.updateAccount(users);
        }
        System.out.println("User form with fullname: " + users.getFullname());
        System.out.println("User form with photo: " + users.getPhotos());
        return "redirect:/admin/account";
    }
}
