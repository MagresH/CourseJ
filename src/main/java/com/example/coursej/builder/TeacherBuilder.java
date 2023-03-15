package com.example.coursej.builder;

import com.example.coursej.model.Course;
import com.example.coursej.model.user.Teacher;

import java.util.List;

public class TeacherBuilder {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<Course> courses;

    public TeacherBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public TeacherBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public TeacherBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public TeacherBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public TeacherBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public TeacherBuilder setPhone_number(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public TeacherBuilder setCourses(List<Course> courses) {
        this.courses = courses;
        return this;
    }

    public Teacher createTeacher() {
        return new Teacher(username, password, email, firstName, lastName, phoneNumber, courses);
    }
}
