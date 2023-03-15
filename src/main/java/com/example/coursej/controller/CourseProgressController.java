package com.example.coursej.controller;

import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.service.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/enrollments/{enrollmentId}/course-progress/{courseProgressId}")
public class CourseProgressController {

    private final CourseProgressService courseProgressService;

    @Autowired
    public CourseProgressController(CourseProgressService courseProgressService) {
        this.courseProgressService = courseProgressService;
    }

    @GetMapping
    public ResponseEntity<CourseProgress> getCourseProgressById(@PathVariable Long courseProgressId) {
        CourseProgress courseProgress = courseProgressService.getCourseProgressById(courseProgressId);
        return ResponseEntity.ok(courseProgress);
    }

    @PostMapping
    public ResponseEntity<CourseProgress> addCourseProgress(@PathVariable Long enrollmentId, @RequestBody CourseProgress courseProgress) {
        CourseProgress addedCourseProgress = courseProgressService.addCourseProgress(courseProgress, enrollmentId);
        return new ResponseEntity<>(addedCourseProgress, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseProgress(@PathVariable Long enrollmentId, @PathVariable Long id) {
        courseProgressService.deleteCourseProgress(id);
        return ResponseEntity.noContent().build();
    }
}
