package com.example.cookiekai.service;

import com.example.cookiekai.entity.Users;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsersService {
    Page<Users> pageUser(Integer pageNo);

    Users addOrUpdateUser(Users users);

    void deleteUser(Integer usersId);

    Users updateAccount(Users users);

    List<Users> listUser();

    Users getOne(Integer id);

//    Page<Users> searchListUser(String text);

    Boolean isUnique(String email);

    Users getByEmail(String email);
}
