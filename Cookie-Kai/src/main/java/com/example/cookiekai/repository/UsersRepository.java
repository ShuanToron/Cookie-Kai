package com.example.cookiekai.repository;

import com.example.cookiekai.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
//    @Query(value = "select * from users where email=?1 or fullname=?1 or phoneNumber=?1", nativeQuery = true)
//    Page<Users> searchListUser(String text);

    @Query(value = "select * from users where email=?1 or phoneNumber=?1", nativeQuery = true)
    Users searchDuplicate(String email);

    @Query(value = "select * from users where email=?1", nativeQuery = true)
    Users getbyEmail(String email);
}
