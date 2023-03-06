package com.example.coursej.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotNull;
import java.util.Set;


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

    private String firstAddressLine;

    private String lastAddressLine;

    @OneToMany
    private Set<Enrollment> enrollments;

    public Student(String username, String password, String email, String firstName, String lastName, String phone_number) {
        super(username, password, email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone_number = phone_number;
    }


}
