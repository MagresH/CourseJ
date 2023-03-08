package com.example.coursej.repository;

import com.example.coursej.model.progress.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {
}
