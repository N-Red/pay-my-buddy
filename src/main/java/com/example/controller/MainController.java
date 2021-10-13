package com.example.controller;

import com.example.service.dto.ContactUsDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "", "/index"})
    public String homePage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/contact-us")
    public String contactPage(Model model) {
        model.addAttribute("user", new ContactUsDto());
        return "contact-us";
    }

}
