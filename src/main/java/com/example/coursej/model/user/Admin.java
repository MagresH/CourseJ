package com.example.coursej.model.user;

import com.example.coursej.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "admin")
@Table(name = "admin")
public class Admin extends User {
    private String test;
}

