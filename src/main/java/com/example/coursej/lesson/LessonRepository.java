package com.example.coursej.lesson;

import com.example.coursej.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> getLessonsByCourseId(Long id);
}
