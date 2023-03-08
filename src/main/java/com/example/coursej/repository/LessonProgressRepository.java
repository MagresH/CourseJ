package com.example.coursej.repository;

import com.example.coursej.model.progress.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
}
