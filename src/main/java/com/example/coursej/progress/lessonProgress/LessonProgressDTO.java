package com.example.coursej.progress.lessonProgress;

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
    private Long id;
    private Long lessonId;
    private Long courseProgressId;
    private Boolean isCompleted;
    private LocalDateTime completeTimestamp;
}

