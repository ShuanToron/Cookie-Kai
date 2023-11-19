package com.example.cookiekai.service.impl;

import com.example.cookiekai.entity.Users;
import com.example.cookiekai.repository.UsersRepository;
import com.example.cookiekai.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Page<Users> pageUser(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return usersRepository.findAll(pageable);
    }

    @Override
    public Users addUser(Users users) {
        String encodePassword = encoder.encode(users.getPassword());
        users.setPassword(encodePassword);
        return usersRepository.save(users);
    }

    @Override
    public void deleteUser(Integer usersId) {
        if (usersId == null) {

        }
        Users users = getOne(usersId);
        usersRepository.delete(users);
    }

    @Override
    public Users updateUser(Users users) {
        String encodePassword = encoder.encode(users.getPassword());
        users.setPassword(encodePassword);
        return usersRepository.save(users);
    }

    @Override
    public List<Users> listUser() {
        return usersRepository.findAll();
    }

    @Override
    public Users getOne(Integer id) {
        return usersRepository.findById(id).get();
    }

//    @Override
//    public Page<Users> searchListUser(String text) {
//        return usersRepository.searchListUser(text);
//    }

    @Override
    public Boolean isUnique(String email) {
        Users users = usersRepository.searchDuplicate(email);
        return users == null;
    }
}
