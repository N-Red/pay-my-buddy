package com.example.service.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterForm {
    @NotEmpty(message = "Please Enter First Name")
    private String firstName;
    @NotEmpty(message = "Please Enter Last Name")
    private String lastName;
    @NotEmpty(message = "Please Enter Email")
    private String email;
    @NotEmpty(message = "Please Enter Password")
    @Size(min = 6, message = "Password must contain 6 letters")
    private String password;
    private String confirmPassword;

    public RegisterForm() {
    }
    public RegisterForm(String firstName, String lastName, String email, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
