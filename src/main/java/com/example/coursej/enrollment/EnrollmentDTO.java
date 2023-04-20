package com.example.coursej.enrollment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

/**
 * A DTO for the {@link Enrollment} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentDTO extends RepresentationModel<EnrollmentDTO> implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Long courseId;

    private Long userId;

    private Long courseProgressId;

}
