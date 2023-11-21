package com.example.cookiekai.service.impl;

import com.example.cookiekai.entity.Categories;
import com.example.cookiekai.repository.CategoriesRepository;
import com.example.cookiekai.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Override
    public List<Categories> listCategory() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories saveOrUpdate(Categories categories) {
        return categoriesRepository.save(categories);
    }

    @Override
    public Categories getOne(Integer id) {
        return categoriesRepository.findById(id).get();
    }

    @Override
    public void deleteCategory(Categories categories) {
        categoriesRepository.delete(categories);
    }

    @Override
    public Page<Categories> pageCategory(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return categoriesRepository.findAll(pageable);
    }
}
