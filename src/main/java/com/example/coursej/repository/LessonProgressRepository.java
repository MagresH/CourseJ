package com.example.coursej.repository;

import com.example.coursej.model.Enrollment;
import com.example.coursej.model.progress.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    List<LessonProgress> getLessonProgressesByCourseProgressId(Long id);
}
