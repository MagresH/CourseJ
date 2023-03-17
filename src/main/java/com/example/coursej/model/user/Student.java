package com.example.coursej.model.user;


import com.example.coursej.model.Enrollment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;


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



    @OneToMany(mappedBy = "student",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Enrollment> enrollments;

    public Student(String username, String password, String email, String firstName, String lastName, String phone_number, List<Enrollment> enrollments) {
        super(username, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone_number = phone_number;
        this.enrollments = enrollments;
    }
}
