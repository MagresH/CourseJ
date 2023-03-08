package com.example.coursej.repository;

import com.example.coursej.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
