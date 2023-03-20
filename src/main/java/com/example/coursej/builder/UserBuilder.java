package com.example.coursej.builder;

import com.example.coursej.model.Course;
import com.example.coursej.model.Enrollment;
import com.example.coursej.model.User;
import com.example.coursej.model.UserRole;

import java.util.List;

public class UserBuilder {
    private String username;
    private String password;
    private String email;
    private UserRole role;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<Enrollment> enrollments;
    private List<Course> courses;



    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setPhone_number(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
        return this;
    }

    public UserBuilder setCourses(List<Course> courses) {
        this.courses = courses;
        return this;
    }


    public User createUser() {
        return new User(username, password, email, role, firstName, lastName, phoneNumber, enrollments, courses);
    }
}