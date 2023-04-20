package com.example.coursej.progress.lessonProgress;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonProgressDTO extends RepresentationModel<LessonProgressDTO> implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Long lessonId;

     private Long courseProgressId;

    private boolean completed;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime completeTimestamp;
}

