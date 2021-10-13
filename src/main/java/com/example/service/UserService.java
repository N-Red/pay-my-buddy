package com.example.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.model.Account;
import com.example.model.Connection;
import com.example.repository.ConnectionRepository;
import com.example.repository.UserRepository;
import com.example.service.dto.AddMoneyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.dto.UserRegistrationDto;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),
                Arrays.asList(new Role("ROLE_USER"))
        );
        Account account = new Account();
        account.setBalance(0.00);
        user.setAccount(account);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public long countUsers() {
        return userRepository.count();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String addMoney(AddMoneyDto addMoneyDto, User userConnected) {
        User user = userRepository.findByEmail(userConnected.getEmail());
        user.setAccount(user.getAccount().plus(addMoneyDto.getAmount()));
        //TODO Add payment by credit card
        userRepository.save(user);
        return "success";
    }

    public List<Connection> findAllConnectionsByUser(User userConnected) {
        return connectionRepository.findAll().stream()
                .filter(connection -> connection.getUser1().equals(userConnected)).collect(Collectors.toList());
    }
}
