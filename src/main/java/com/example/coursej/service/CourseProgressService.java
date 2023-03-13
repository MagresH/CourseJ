package com.example.coursej.service;

import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.repository.CourseProgressRepository;
import com.example.coursej.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CourseProgressService {

    private final CourseProgressRepository courseProgressRepository;
    private final EnrollmentService enrollmentService;

    @Autowired
    public CourseProgressService(CourseProgressRepository courseProgressRepository, EnrollmentService enrollmentService) {
        this.courseProgressRepository = courseProgressRepository;
        this.enrollmentService = enrollmentService;
    }

    public CourseProgress addCourseProgress(CourseProgress courseProgress, Long enrollmentId) {
        courseProgress.setEnrollment(enrollmentService.getEnrollmentById(enrollmentId).get());
        courseProgressRepository.save(courseProgress);
        return courseProgress;
    }

    public CourseProgress getCourseProgressById(Long id) {
        return courseProgressRepository.findById(id).get();
    }

//    public CourseProgress updateCourseProgress(CourseProgress courseProgress) {
//        return courseProgressRepository.findById(id).map(existingCourseProgress -> {
//            existingCourseProgress.setProgress(courseProgress.getProgress());
//            existingCourseProgress.setCourse(courseProgress.getCourse());
//            existingCourseProgress.setEnrollment(courseProgress.getEnrollment());
//            return courseProgressRepository.save(existingCourseProgress);
//        }).orElseThrow(() -> new ResourceNotFoundException("Course progress not found with id " + id));
//    }

    public void deleteCourseProgress(Long id) {
        courseProgressRepository.deleteById(id);
    }
}
