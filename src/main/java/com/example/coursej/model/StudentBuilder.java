package com.example.coursej.model;

public class StudentBuilder {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phone_number;

    public StudentBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public StudentBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public StudentBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public StudentBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public StudentBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public StudentBuilder setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public Student createStudent() {
        return new Student(username, password, email, firstName, lastName, phone_number);
    }
}