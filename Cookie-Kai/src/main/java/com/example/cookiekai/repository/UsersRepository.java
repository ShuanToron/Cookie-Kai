package com.example.cookiekai.repository;

import com.example.cookiekai.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("UPDATE Users u SET u.enabled=?1 WHERE u.id=?2")
    @Modifying
    void updateEnableUser(Boolean status, Integer id);

    @Query("SELECT u FROM Users u WHERE u.fullname LIKE %?1% OR u.email LIKE %?1% OR u.phoneNumber LIKE %?1%")
    Page<Users> find(String keyWord, Pageable pageable);
}
