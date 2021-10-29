package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.service.form.EditProfileForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        User user = userService.findByEmail(getAuthentication().getName());
        model.addAttribute("user", userService.findByEmail(user.getEmail()));
        model.addAttribute("editProfileForm", new EditProfileForm());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("editProfileForm") EditProfileForm editProfileForm) {
        User user = userService.findByEmail(getAuthentication().getName());
        userService.updateProfile(user, editProfileForm);
        return "redirect:transfer";
    }

}
