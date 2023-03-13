package com.example.coursej.model.user;

import com.example.coursej.model.Course;
import com.example.coursej.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Teacher extends User {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Column(unique = true)
    private String phone_number;

    @OneToMany
    private List<Course> courses;

    public Teacher(String username, String password, String email, String firstName, String lastName, String phone_number, List<Course> courses) {
        super(username, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone_number = phone_number;
        this.courses = courses;
    }
}

