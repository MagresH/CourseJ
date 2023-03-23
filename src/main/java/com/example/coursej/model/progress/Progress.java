package com.example.coursej.model.progress;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class Progress extends RepresentationModel<Progress> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private Boolean completed;

}
