package com.example.demo.controller;

import com.example.demo.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName MainPageController
 * @auther haoshidi
 * @date 2022/4/8 8:47
 * @Description
 * @Version 1.0
 */
@Controller
@Slf4j
public class MainPageController {
    @Autowired
    private ProductService productService;

    @GetMapping("/main")
    public String main(Authentication a, Model model){
        model.addAttribute("username",a.getName());
        model.addAttribute("products",productService.findAll());
        return "main.html";
    }
}
