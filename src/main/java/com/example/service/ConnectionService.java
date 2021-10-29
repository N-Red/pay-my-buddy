package com.example.service;

import com.example.model.Connection;
import com.example.model.User;
import com.example.repository.ConnectionRepository;
import com.example.repository.UserRepository;
import com.example.service.form.AddContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
