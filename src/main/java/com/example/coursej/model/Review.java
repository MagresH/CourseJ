package com.example.coursej.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
public class Review extends RepresentationModel<Review> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @CreationTimestamp
    private LocalDateTime reviewCreationTimestamp;

    @UpdateTimestamp
    private LocalDateTime reviewUpdateTimestamp ;

}
