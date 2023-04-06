package com.example.coursej.course;

import com.example.coursej.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link Course} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Long userId;
    private List<Long> lessonsIds;
    private List<Long> usersIds;
}
