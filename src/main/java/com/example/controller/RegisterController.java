package com.example.controller;

import com.example.service.form.RegisterForm;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getRegisterPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("registerForm") RegisterForm registerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            userService.createUser(registerForm);
            return "login";
        }
    }

}
