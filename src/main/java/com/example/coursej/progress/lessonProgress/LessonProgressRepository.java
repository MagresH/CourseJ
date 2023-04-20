package com.example.coursej.progress.lessonProgress;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    Page<LessonProgress> getLessonProgressesByCourseProgressId(Long id, Pageable pageable);

}
