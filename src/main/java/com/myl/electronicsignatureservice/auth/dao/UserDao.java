package com.myl.electronicsignatureservice.auth.dao;

import com.myl.electronicsignatureservice.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User,Integer> {
    User findByUserId(Integer userId);
    Optional<User> findByEmail(String email);
}
