package com.example.demo.dao;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName ProductRepository
 * @auther haoshidi
 * @date 2022/4/8 8:45
 * @Description
 * @Version 1.0
 */
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
