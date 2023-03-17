package com.example.coursej.builder;

import com.example.coursej.model.Enrollment;
import com.example.coursej.model.user.Student;

import java.util.List;

public class StudentBuilder {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<Enrollment> enrollments;

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

    public StudentBuilder setPhone_number(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public StudentBuilder setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
        return this;
    }

    public Student createStudent() {
        return new Student(username, password, email, firstName, lastName, phoneNumber, enrollments);
    }
}