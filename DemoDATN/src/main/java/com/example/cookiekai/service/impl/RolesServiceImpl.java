package com.example.cookiekai.service.impl;

import com.example.cookiekai.entity.Roles;
import com.example.cookiekai.repository.RolesRepository;
import com.example.cookiekai.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public void addRoles(Roles roles) {
        rolesRepository.save(roles);
    }

    @Override
    public void deleteRoles(Integer id) {

    }

    @Override
    public void updateRoles(Roles roles) {
        rolesRepository.save(roles);
    }

    @Override
    public List<Roles> listRoles() {
        return rolesRepository.findAll();
    }

    @Override
    public Page<Roles> pageRoles(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return rolesRepository.findAll(pageable);
    }

    @Override
    public Roles getOne(Integer id) {
        return rolesRepository.findById(id).get();
    }

}
