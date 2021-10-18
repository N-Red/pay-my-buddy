package com.example.controller;

import com.example.model.User;
import com.example.service.ConnectionService;
import com.example.service.TransactionService;
import com.example.service.UserService;
import com.example.service.form.*;
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
        model.addAttribute("transaction", new TransactionForm());
        User userConnected = userService.findByEmail(getAuthentication().getName());
        model.addAttribute("connections", userService.findAllConnectionsByUser(userConnected));
        return "pay-contact";
    }

    @PostMapping("/pay-contact")
    public String createTransaction(@ModelAttribute("pay-contact") TransactionForm transactionForm) {
        User userConnected = userService.findByEmail(getAuthentication().getName());
        String param = transactionService.createTransaction(transactionForm, userConnected);
        return "redirect:/transfer?" + param;
    }

    @GetMapping("/add-money")
    public String getAddMoneyPage(Model model) {
        model.addAttribute("account", new AddMoneyForm());
        return "add-money";
    }

    @PostMapping("/add-money")
    public String addMoney(@ModelAttribute("add-money") AddMoneyForm addMoneyForm) {
        User userConnected = userService.findByEmail(getAuthentication().getName());
        String param = userService.addMoney(addMoneyForm, userConnected);
        return "redirect:/transfer?" + param;
    }

}
