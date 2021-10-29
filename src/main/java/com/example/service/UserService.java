package com.example.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.example.model.*;
import com.example.repository.ConnectionRepository;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import com.example.service.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User with email not found"));
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public String createUser(RegisterForm registerForm) {
        User user = new User(registerForm.getFirstName(),
                registerForm.getLastName(),
                registerForm.getEmail(),
                passwordEncoder.encode(registerForm.getPassword()),
                Arrays.asList(new Role("ROLE_USER"))
        );
        Account account = new Account();
        account.setBalance(0.00);
        user.setAccount(account);
        userRepository.save(user);
        return "success";
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public long countUsers() {
        return userRepository.count();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email not found"));
    }

    public String addMoney(AddMoneyForm addMoneyForm, User userConnected) {
        User user = userRepository.findByEmail(userConnected.getEmail())
                .orElseThrow(() -> new RuntimeException("User with email not found"));
        user.setAccount(user.getAccount().plus(addMoneyForm.getAmount()));
        setTransactionWithAddMoneyForm(user, addMoneyForm);
        userRepository.save(user);
        return "success";
    }

    public String updateProfile(User user, EditProfileForm editProfileForm) {
        //Check if the form is empty, if yes return error
        if (editProfileForm.getFirstName().isEmpty() && editProfileForm.getLastName().isEmpty()
                && editProfileForm.getPassword().isEmpty()) {
            return "error";
        }
        //Update data
        if (!editProfileForm.getFirstName().isEmpty()) {
            user.setFirstName(editProfileForm.getFirstName());
        }
        if (!editProfileForm.getLastName().isEmpty()) {
            user.setLastName(editProfileForm.getLastName());
        }
        if (!editProfileForm.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(editProfileForm.getPassword()));
        }
        userRepository.save(user);
        return "success";
    }

    public void addContact(AddContactForm addContactForm, User user) {
        if (addContactForm.getEmail() != null
                && addContactForm.getEmail() != user.getEmail()) {
            User user2 = userRepository.findByEmail(addContactForm.getEmail())
                    .orElseThrow(() -> new RuntimeException("User with email not found"));
            if (user2 != null) {
                Connection connection = new Connection();
                connection.setUser1(user);
                connection.setUser2(user2);
                connectionRepository.save(connection);
            }
        }
    }

    public void payAContact(PayContactForm payContactForm, User user) {
        if (!payContactForm.getEmail().equals(user.getEmail())
                && user.getAccount().getBalance() > payContactForm.getAmount()) {
            User receiver = userRepository.findByEmail(payContactForm.getEmail())
                    .orElseThrow(() -> new RuntimeException("User with email not found"));
            Transaction transaction = setTransactionWithPayContactForm(user, receiver, payContactForm);
            user.getAccount().minus(transaction.getAmountBeforeFee());
            userRepository.save(user);
            Transaction transactionReceiver = setTransactionWithPayContactForm(receiver, user, payContactForm);
            receiver.getAccount().plus(transactionReceiver.getAmountAfterFee());
            userRepository.save(receiver);
        }
    }

    private Transaction setTransactionWithPayContactForm(User sender, User receiver, PayContactForm form) {
        Transaction transaction = new Transaction();
        transaction.setFrom(sender);
        transaction.setTo(receiver);
        transaction.setDescription(form.getDescription());
        transaction.setDate(LocalDateTime.now());
        transaction.setAmountBeforeFee(form.getAmount());
        transaction.setAmountAfterFee(form.getAmount() * 0.95);
        transactionRepository.save(transaction);
        return transaction;
    }

    private Transaction setTransactionWithAddMoneyForm(User sender, AddMoneyForm form) {
        Transaction transaction = new Transaction();
        transaction.setFrom(sender);
        transaction.setTo(sender);
        transaction.setDescription("From Bank Account");
        transaction.setDate(LocalDateTime.now());
        transaction.setAmountBeforeFee(form.getAmount());
        transaction.setAmountAfterFee(form.getAmount());
        transactionRepository.save(transaction);
        return transaction;
    }


}
