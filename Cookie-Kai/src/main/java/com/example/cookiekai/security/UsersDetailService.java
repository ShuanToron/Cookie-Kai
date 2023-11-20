package com.example.cookiekai.security;

import com.example.cookiekai.entity.Users;
import com.example.cookiekai.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class UsersDetailService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.getbyEmail(email);
        if (users != null) {
            return new UsersDetail(users);
        }
        throw new UsernameNotFoundException("Couldn't find user with email " + email);
    }
}
