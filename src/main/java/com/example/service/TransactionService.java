package com.example.service;

import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import com.example.service.dto.TransactionDto;
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

    public String addTransaction(TransactionDto transactionDto, User userConnected) {
        String param = checkBeforeAddTransaction(transactionDto, userConnected);
        if (param == "successAddTransaction") {
            Transaction transaction = new Transaction();
            transaction.setFrom(userConnected);
            transaction.setDescription(transactionDto.getDescription());
            transaction.setDate(LocalDateTime.now());
            transaction.setAmountBeforeFee(transactionDto.getAmount());
            transaction.setAmountAfterFee(transaction.getAmountBeforeFee() * 0.95);

            User userSelected = userRepository.findByEmail(transactionDto.getEmail());
            transaction.setTo(userSelected);
            userSelected.getAccount().plus(transactionDto.getAmount());
            userConnected.getAccount().minus(transactionDto.getAmount());
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

    private String checkBeforeAddTransaction(TransactionDto transactionDto, User userConnected) {
        if (userConnected.getAccount().getBalance() < transactionDto.getAmount()) {
            return "noMoneyInBalance";
        } else if (transactionDto.getAmount() <= 0) {
            return "errorAmountInferiorZero";
        } else if (transactionDto.getAmount().isNaN()) {
            return "errorNan";
        } else if (transactionDto.getAmount() == null) {
            return "errorNoAmount";
        } else if (transactionDto.getEmail().isEmpty()) {
            return "errorEmailEmpty";
        } else {
            return "successAddTransaction";
        }
    }
}
