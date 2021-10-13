package com.example.controller;

import com.example.model.Account;
import com.example.model.User;
import com.example.service.ConnectionService;
import com.example.service.TransactionService;
import com.example.service.UserService;
import com.example.service.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TransferController {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ConnectionService connectionService;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/transfer")
    public String getTransferPage(Model model) {
        User userConnected = userService.findByEmail(getAuthentication().getName());
        model.addAttribute("transactions", transactionService.findAllTransactionByUser(userConnected));
        model.addAttribute("user", userConnected);
        return "transfer";
    }

    @GetMapping("/pay-contact")
    public String getPayContactPage(Model model) {
        model.addAttribute("transaction", new TransactionDto());
        User userConnected = userService.findByEmail(getAuthentication().getName());
        model.addAttribute("connections", userService.findAllConnectionsByUser(userConnected));
        return "pay-contact";
    }

    @PostMapping("/pay-contact")
    public String payContact(@ModelAttribute("pay-contact") TransactionDto transactionDto) {
        User userConnected = userService.findByEmail(getAuthentication().getName());
        String param = transactionService.addTransaction(transactionDto, userConnected);
        System.out.println(param);
        return "redirect:/transfer?" + param;
    }

    @GetMapping("/add-money")
    public String getAddMoneyPage(Model model) {
        model.addAttribute("account", new AddMoneyDto());
        return "add-money";
    }

    @PostMapping("/add-money")
    public String addMoney(@ModelAttribute("add-money") AddMoneyDto addMoneyDto) {
        User userConnected = userService.findByEmail(getAuthentication().getName());
        String param = userService.addMoney(addMoneyDto, userConnected);
        return "redirect:/transfer?" + param;
    }
}
