package com.example.service;

import com.example.model.Connection;
import com.example.model.User;
import com.example.repository.ConnectionRepository;
import com.example.repository.UserRepository;
import com.example.service.dto.ConnectionForm;
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

    public String addConnection(ConnectionForm connectionForm, User userConnected) {
        String param = checkConnectionBeforeAdd(userConnected, connectionForm);
        if (param == "connectionSuccess") {
            Connection connection = new Connection();
            connection.setUser1(userConnected);
            connection.setUser2(userRepository.findByEmail(connectionForm.getEmail()));
            connectionRepository.save(connection);
        }
        return param;
    }

    private List<Connection> findAllConnectionsByUser(User userConnected) {
        return connectionRepository.findAll()
                .stream().filter(connection -> connection.getUser1().equals(userConnected)).collect(Collectors.toList());
    }

    private String checkConnectionBeforeAdd(User userConnected, ConnectionForm connectionForm) {
        User userSelected = userRepository.findByEmail(connectionForm.getEmail());
        List<Connection> connectionList = this.findAllConnectionsByUser(userConnected);
        if (userSelected != null) {
            for (Connection connection : connectionList) {
                if (connection.getUser2().getEmail().equals(userSelected.getEmail())) {
                    return "userAlreadyInYourConnection";
                }
            }
        }
        if (connectionForm.getEmail().isEmpty()) {
            return "noEmailInForm";
        } else if (userSelected == null) {
            return "userSelectedNotInDB";
        } else if (userSelected.getEmail().equals(userConnected.getEmail())) {
            return "userSelectedSameUserConnected";
        }
        return "connectionSuccess";
    }
}
