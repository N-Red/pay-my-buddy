package com.example.controller;

import com.example.model.Connection;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.service.ConnectionService;
import com.example.service.TransactionService;
import com.example.service.UserService;
import com.example.service.form.AddContactForm;
import com.example.service.form.AddMoneyForm;
import com.example.service.form.PayContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;

@Controller
public class TransferController {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ConnectionService connectionService;

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping({"/transfer", "/transactions"})
    public String getTransferTransactionPage(Model model,
                                             @RequestParam(value = "amountMin", required = false) Double amountMin,
                                             @RequestParam(value = "amountMax", required = false) Double amountMax,
                                             @RequestParam(value = "startDate", required = false) String startDate,
                                             @RequestParam(value = "endDate", required = false) String endDate,
                                             @RequestParam(value = "email", required = false) String email,
                                             @RequestParam(value = "description", required = false) String description) {

        User user = userService.findByEmail(getAuthentication().getName());
        List<Transaction> transactionList = transactionService.findAllTransactionByUser(user);
        List<Connection> connectionList = connectionService.findConnectionByUser(user);

        if (amountMax != null || amountMin != null || startDate != null || endDate != null || email != null || description != null) {
            transactionList = transactionService.findTransactionByUserWithFilters(user, amountMin, amountMax, startDate, endDate, email, description);
        }

        model.addAttribute("user", user);
        model.addAttribute("transactions", transactionList);
        return "transfer";
    }

    @GetMapping("/contacts")
    public String getTransferContactsPage(Model model,
                                          @RequestParam(value = "email", required = false) String email,
                                          @RequestParam(value = "firstName", required = false) String firstName,
                                          @RequestParam(value = "lastName", required = false) String lastName) {
        User user = userService.findByEmail(getAuthentication().getName());
        List<Connection> connectionList = connectionService.findConnectionByUser(user);
        if (email != null || firstName != null || lastName != null) {
            System.out.println(" in filters controller");
            connectionList = connectionService.findConnectionByUserWithFilters(user, email, firstName, lastName);
        }
        model.addAttribute("user", user);
        model.addAttribute("connections", connectionList);
        return "contacts";
    }

    @GetMapping("/add-money")
    public String getAddMoneyPage(Model model) {
        model.addAttribute("addMoneyForm", new AddMoneyForm());
        return "transfer/add-money";
    }

    @PostMapping("/add-money")
    public String getAddMoneyPage(@ModelAttribute("addMoneyForm") AddMoneyForm addMoneyForm) {
        User user = userService.findByEmail(getAuthentication().getName());
        userService.addMoney(addMoneyForm, user);
        return "redirect:/transfer";
    }

    @GetMapping("/add-contact")
    public String getAddContactPage(Model model) {
        model.addAttribute("addContactForm", new AddContactForm());
        return "transfer/add-contact";
    }

    @PostMapping("/add-contact")
    public String getAddContactPage(@ModelAttribute("addContactForm") AddContactForm addContactForm) {
        User user = userService.findByEmail(getAuthentication().getName());
        userService.addContact(addContactForm, user);
        return "transfer/add-contact";
    }

    @GetMapping("/pay-contact")
    public ModelAndView getPayContactPage(ModelAndView model) {
        User user = userService.findByEmail(getAuthentication().getName());
        model.addObject("connections", connectionService.findConnectionByUser(user));
        model.addObject("payContactForm", new PayContactForm());
        model.setViewName("transfer/pay-contact");
        return model;
    }

    @PostMapping("/pay-contact")
    public ModelAndView getPayContactPage(ModelAndView model, @ModelAttribute("payContactForm") PayContactForm payContactForm) {
        User user = userService.findByEmail(getAuthentication().getName());
        userService.payAContact(payContactForm, user);
        model.addObject("connections", connectionService.findConnectionByUser(user));
        model.addObject("payContactForm", new PayContactForm());
        model.setViewName("transfer/pay-contact");
        return model;
    }

}