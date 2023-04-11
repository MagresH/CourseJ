package com.example.coursej.progress.courseProgress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseProgressDTO extends RepresentationModel<CourseProgressDTO> implements Serializable {
    private Long id;
    private Long enrollmentId;
    private List<Long> lessonProgressesIds;
    private Boolean isCompleted;
    private LocalDateTime completeTimestamp;
}
