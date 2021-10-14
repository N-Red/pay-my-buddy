package com.example.service;

import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import com.example.service.dto.TransactionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;

    public String addTransaction(TransactionForm transactionForm, User userConnected) {
        String param = checkBeforeAddTransaction(transactionForm, userConnected);
        if (param == "successAddTransaction") {
            Transaction transaction = new Transaction();
            transaction.setFrom(userConnected);
            transaction.setDescription(transactionForm.getDescription());
            transaction.setDate(LocalDateTime.now());
            transaction.setAmountBeforeFee(transactionForm.getAmount());
            transaction.setAmountAfterFee(transaction.getAmountBeforeFee() * 0.95);

            User userSelected = userRepository.findByEmail(transactionForm.getEmail());
            transaction.setTo(userSelected);
            userSelected.getAccount().plus(transactionForm.getAmount());
            userConnected.getAccount().minus(transactionForm.getAmount());
            transactionRepository.save(transaction);
        }
        return param;
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> findAllTransactionByUser(User user) {
        List<Transaction> transactionList = transactionRepository.findAllByUser(user);
        return transactionList;
    }

    private String checkBeforeAddTransaction(TransactionForm transactionForm, User userConnected) {
        if (userConnected.getAccount().getBalance() < transactionForm.getAmount()) {
            return "noMoneyInBalance";
        } else if (transactionForm.getAmount() <= 0) {
            return "errorAmountInferiorZero";
        } else if (transactionForm.getAmount().isNaN()) {
            return "errorNan";
        } else if (transactionForm.getAmount() == null) {
            return "errorNoAmount";
        } else if (transactionForm.getEmail().isEmpty()) {
            return "errorEmailEmpty";
        } else {
            return "successAddTransaction";
        }
    }

}
