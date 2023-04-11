package com.example.coursej.lesson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO extends RepresentationModel<Lesson> implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String contentUrl;
    private Long courseId;
}
