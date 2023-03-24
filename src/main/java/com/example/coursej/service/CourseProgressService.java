package com.example.coursej.service;

import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.repository.CourseProgressRepository;
import com.example.coursej.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CourseProgressService {

    private final CourseProgressRepository courseProgressRepository;

    @Autowired
    public CourseProgressService(CourseProgressRepository courseProgressRepository) {
        this.courseProgressRepository = courseProgressRepository;

    }

    public CourseProgress addCourseProgress(CourseProgress courseProgress) {
        return courseProgressRepository.save(courseProgress);
    }

    public CourseProgress updateCourseProgress(CourseProgress courseProgress) {
        return courseProgressRepository.save(courseProgress);
    }

    public CourseProgress getCourseProgressById(Long id) {
        return courseProgressRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("CourseProgress with id " + id + " does not exist"));
    }

    public void deleteCourseProgress(Long id) {
        courseProgressRepository.deleteById(id);
    }
}
