package com.example.cookiekai.controller;

import com.example.cookiekai.entity.Categories;
import com.example.cookiekai.service.impl.CategoriesServiceImpl;
import org.apache.commons.compress.harmony.unpack200.bytecode.forms.IincForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/dashboard/categories")
public class CategoriesController {
    @Autowired
    private CategoriesServiceImpl categoriesService;

    @GetMapping("")
    public String viewCategory(@RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo, Model model) {
        Page<Categories> page = categoriesService.pageCategory(pageNo);
        model.addAttribute("page", page);
        return "dashboard/category/trang-chu-category";
    }
}
