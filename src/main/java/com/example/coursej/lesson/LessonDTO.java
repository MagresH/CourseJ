package com.example.coursej.lesson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO extends RepresentationModel<Lesson> implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String title;

    private String description;

    private String contentUrl;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long courseId;
}
