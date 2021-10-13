package com.example.controller;

import com.example.model.User;
import com.example.service.ConnectionService;
import com.example.service.UserService;
import com.example.service.dto.ConnectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactsController {

    @Autowired
    private UserService userService;
    @Autowired
    private ConnectionService connectionService;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/contacts")
    public String getContactsPage(Model model) {
        User userConnected = userService.findByEmail(getAuthentication().getName());
        model.addAttribute("connections", connectionService.findConnectionByUser(userConnected));
        model.addAttribute("user", userConnected);
        return "contacts";
    }

    @GetMapping("/add-contact")
    public String getAddContactPage(Model model) {
        model.addAttribute("connection", new ConnectionDto());
        return "add-contact";
    }

    @PostMapping("/add-contact")
    public String addContact(@ModelAttribute("add-contact") ConnectionDto connectionDto) {
        User userConnected = userService.findByEmail(getAuthentication().getName());
        String param = connectionService.addConnection(connectionDto, userConnected);
        return "redirect:/transfer?" + param;
    }

}