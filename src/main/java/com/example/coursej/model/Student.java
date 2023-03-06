package com.example.coursej.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotNull;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Student extends User {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Column(unique = true)
    private String phone_number;

//    @ManyToMany
//    @ToString.Exclude
//    private Set<Course> courses;

    public Student(String username, String password, String email, String firstName, String lastName, String phone_number) {
        super(username, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone_number = phone_number;
    }


}
