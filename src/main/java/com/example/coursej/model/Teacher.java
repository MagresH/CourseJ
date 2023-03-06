package com.example.coursej.model;

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

}

