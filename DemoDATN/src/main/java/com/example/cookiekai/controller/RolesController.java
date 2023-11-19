package com.example.cookiekai.controller;

import com.example.cookiekai.entity.Roles;
import com.example.cookiekai.service.impl.RolesServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/roles")
public class RolesController {
    @Autowired
    private RolesServiceImpl rolesService;

    @GetMapping("")
    public String viewRole(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo, Model model) {
        Page<Roles> rolesPage = rolesService.pageRoles(pageNo);
        model.addAttribute("roles", rolesPage);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", rolesPage.getTotalPages());
        return "dashboard/vai-tro/trang-chu-vai-tro";
    }

    @GetMapping("/new")
    public String newRole(Model model) {
        model.addAttribute("roles", new Roles());
        return "dashboard/vai-tro/view-add-vai-tro";
    }

    @PostMapping("/save")
    public String saveRole(@Valid @ModelAttribute("roles") Roles roles, BindingResult result) {
        if (result.hasErrors()) {
            return "dashboard/vai-tro/view-add-vai-tro";
        }
        rolesService.addRoles(roles);
        return "redirect:/admin/roles";
    }
}
