package com.example.controller;

import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String getProfilePage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("countUsers", userService.countUsers());
        return "admin";
    }
}
