package com.example.cookiekai.service;

import com.example.cookiekai.dto.RolesDTO;
import com.example.cookiekai.entity.Roles;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RolesService {
    void addRoles(Roles roles);

    void deleteRoles(Integer id);

    void updateRoles(Roles roles);

    List<Roles> listRoles();

    Page<Roles> pageRoles(Integer pageNo);

    Roles getOne(Integer id);
}
