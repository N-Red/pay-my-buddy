package com.example.controller;

import com.example.service.TransactionService;
import com.example.service.UserService;
import com.example.service.form.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping({"/", "", "/index"})
    public String getHomePage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/contact")
    public String getContactPage(Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "contact";
    }

}
