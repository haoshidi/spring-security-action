package com.example.demo.dao;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @ClassName UserRepository
 * @auther haoshidi
 * @date 2022/4/8 8:06
 * @Description
 * @Version 1.0
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findUserByUsername(String u);
}
