package com.example.controller;

import com.example.service.TransactionService;
import com.example.service.UserService;
import com.example.service.form.ContactForm;
import com.example.service.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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

    @PostMapping("/contact")
    public void getContactPage(@Valid @ModelAttribute("contactForm") ContactForm contactForm, BindingResult bindingResult) {
        System.out.println("Message send!");
        System.out.println(contactForm.getFirstName());
        System.out.println(contactForm.getLastName());
        System.out.println(contactForm.getEmail());
        System.out.println(contactForm.getMessage());
    }

}
