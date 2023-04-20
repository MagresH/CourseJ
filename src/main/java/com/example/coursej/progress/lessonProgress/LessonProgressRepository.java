package com.example.coursej.progress.lessonProgress;

import com.example.coursej.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    Page<LessonProgress> getLessonProgressesByCourseProgressId(Long id, Pageable pageable);

}
