package com.example.coursej.progress.courseProgress;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private Long enrollmentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> lessonProgressesIds;

    private Boolean isCompleted;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime completeTimestamp;
}
