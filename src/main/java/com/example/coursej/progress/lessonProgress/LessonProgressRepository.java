package com.example.coursej.progress.lessonProgress;

import com.example.coursej.progress.lessonProgress.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    List<LessonProgress> getLessonProgressesByCourseProgressId(Long id);
}
