package com.example.service;

import com.example.model.Connection;
import com.example.model.User;
import com.example.repository.ConnectionRepository;
import com.example.repository.UserRepository;
import com.example.service.form.AddContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConnectionService {
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Connection> findConnectionByUser(User user) {
        List<Connection> connectionList = connectionRepository.findAll()
                .stream().filter(connection -> connection.getUser1().equals(user)).collect(Collectors.toList());
        return connectionList;
    }

    public List<Connection> findConnectionByUserWithFilters(User user, String email, String firstName, String lastName) {
        List<Connection> connectionList = connectionRepository.findAll().stream()
                .filter(connection -> connection.getUser1().equals(user))
                .collect(Collectors.toList());
        System.out.println("Email :" + email);
        System.out.println("First Name :" + firstName);
        System.out.println("Last Name :" + lastName);
        System.out.println(lastName);
        if (email != null) {
            System.out.println(email);
            connectionList = connectionRepository.findAll().stream()
                    .filter(connection -> connection.getUser1().equals(user) && connection.getUser2().getEmail().equals(email))
                    .collect(Collectors.toList());
        }
        if (firstName != null) {
            System.out.println(firstName);
            connectionList = connectionRepository.findAll().stream()
                    .filter(connection -> connection.getUser1().equals(user) && connection.getUser2().getFirstName().equals(firstName))
                    .collect(Collectors.toList());
        }
        if (lastName != null) {
            System.out.println(lastName);
            connectionList = connectionRepository.findAll().stream()
                    .filter(connection -> connection.getUser1().equals(user) && connection.getUser2().getLastName().equals(lastName))
                    .collect(Collectors.toList());
        }
        return connectionList;
    }
}
