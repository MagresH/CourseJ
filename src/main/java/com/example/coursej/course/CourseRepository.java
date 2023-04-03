package com.example.coursej.course;

import com.example.coursej.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> getCoursesByUserId(Long userId);
}
