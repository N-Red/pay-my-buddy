package com.example.service;

import com.example.model.User;
import com.example.service.form.UserForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    UserForm userForm = new UserForm("test", "test", "test@example.com", "password");

    @Test
    public void addUserTest() {
        User user = userService.addUser(userForm);

        Assertions.assertEquals("test@example.com", userService.findByEmail(user.getEmail()).getEmail());

        userService.deleteUser(user);
    }

    @Test
    public void countUsersTest() {
        User user = userService.addUser(userForm);

        long countUsers = userService.countUsers();

        Assertions.assertTrue(countUsers > 0);
        userService.deleteUser(user);
    }

    @Test
    public void deleteUserTest() {
        User user = userService.addUser(userForm);

        userService.deleteUser(user);

        Assertions.assertTrue(userService.findByEmail(user.getEmail()) == null);
    }

    @Test
    void loadUserByUsernameTest() {
    }

    @Test
    void findAllUsersTest() {
    }

    @Test
    void findByEmailTest() {
    }

    @Test
    void addMoneyTest() {
    }

    @Test
    void findAllConnectionsByUserTest() {
    }

}
