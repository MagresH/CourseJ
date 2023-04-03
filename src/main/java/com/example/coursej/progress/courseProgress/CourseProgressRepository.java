package com.example.coursej.progress.courseProgress;

import com.example.coursej.progress.courseProgress.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {
}
