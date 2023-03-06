package com.example.coursej.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Result extends RepresentationModel<Result> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Quiz quiz;

    private Integer score;
}
