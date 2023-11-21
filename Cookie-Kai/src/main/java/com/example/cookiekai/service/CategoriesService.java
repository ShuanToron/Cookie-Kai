package com.example.cookiekai.service;

import com.example.cookiekai.entity.Categories;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoriesService {
    List<Categories> listCategory();

    Categories saveOrUpdate(Categories categories);

    Categories getOne(Integer id);

    void deleteCategory(Categories categories);

    Page<Categories> pageCategory(Integer pageNo);
}
