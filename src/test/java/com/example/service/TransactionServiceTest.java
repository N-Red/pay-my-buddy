package com.example.service;

import com.example.model.Transaction;
import com.example.model.User;
import com.example.service.dto.TransactionForm;
import com.example.service.dto.UserForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    @Test
    void addTransactionTest() {
//        User userSendMoney = userService.addUser(new UserForm("sender", "sender", "sender@example.fr", "password"));
//        User userReceivedMoney =  userService.addUser(new UserForm("receiver", "receiver", "receiver@example.fr", "password"));
//        userSendMoney.getAccount().setBalance(50.0);
//
//        TransactionForm transactionForm = new TransactionForm(userReceivedMoney.getEmail(), 20.0, "description");
//        transactionService.addTransaction(transactionForm, userSendMoney);
//
//        Assertions.assertFalse(transactionService.findAll().isEmpty());
    }

    @Test
    void findAllTest() {
    }

    @Test
    void findAllTransactionByUserTest() {
    }
}