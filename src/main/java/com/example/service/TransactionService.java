package com.example.service;

import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import com.example.service.form.PayContactForm;
import com.example.service.form.SearchTransactionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Transaction> findAllTransactionByUser(User user) {
        List<Transaction> transactionList = transactionRepository.findAllByUser(user);
        return transactionList;
    }

    public List<Transaction> findTransactionByUserWithFilters(User user,
                                                              Double amountMin,
                                                              Double amountMax,
                                                              String startDate,
                                                              String endDate,
                                                              String email,
                                                              String description) {

        List<Transaction> transactionList = this.findAllTransactionByUser(user);

        if (amountMin != null && amountMax != null) {
            transactionList = transactionList.stream()
                    .filter(transaction -> transaction.getAmountAfterFee() >= amountMin
                            && transaction.getAmountAfterFee() <= amountMax).collect(Collectors.toList());
        } else {
            if (amountMin != null) {
                transactionList = transactionList.stream()
                        .filter(transaction -> transaction.getAmountAfterFee() >= amountMin).collect(Collectors.toList());
            }
            if (amountMax != null) {
                transactionList = transactionList.stream()
                        .filter(transaction -> transaction.getAmountAfterFee() <= amountMax).collect(Collectors.toList());
            }
        }
        if (email != "") {
            transactionList = transactionList.stream()
                    .filter(transaction -> transaction.getTo().getEmail().toLowerCase().contains(email.toLowerCase())).collect(Collectors.toList());
        }
        if (description != "") {
            transactionList = transactionList.stream()
                    .filter(transaction -> transaction.getDescription().toLowerCase().contains(description.toLowerCase())).collect(Collectors.toList());
        }
//        if (startDate != null || endDate != null) {
//            if (endDate != null && startDate != null) {
//                // TODO: add filter with dates
//            }
//            if (startDate != null) {
//            }
//            if (endDate != null) {
//            }
//        }
        return transactionList;
    }

    private String formatDate(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTimeFormatter.format(localDateTime);
    }
}
