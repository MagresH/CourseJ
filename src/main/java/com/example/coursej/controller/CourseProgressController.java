package com.example.coursej.controller;

import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.service.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/enrollments/{enrollmentId}/course-progress/{courseProgressId}")
public class CourseProgressController {

    private final CourseProgressService courseProgressService;

    @Autowired
    public CourseProgressController(CourseProgressService courseProgressService) {
        this.courseProgressService = courseProgressService;
    }

    @GetMapping
    public ResponseEntity<CourseProgress> getCourseProgressById(@PathVariable("enrollmentId") Long enrollmentId,@PathVariable("courseProgressId") Long courseProgressId) {
        CourseProgress courseProgress = courseProgressService.getCourseProgressById(courseProgressId);
        if (courseProgress == null) {
            return ResponseEntity.notFound().build();
        }

        Link selfLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId,courseProgressId)).withSelfRel();
        Link courseLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollmentId)).withRel("enrollment");
        courseProgress.add(selfLink,courseLink);
        return ResponseEntity.ok(courseProgress);
    }


    @PostMapping
    public ResponseEntity<CourseProgress> addCourseProgress(@RequestBody CourseProgress courseProgress) {
        CourseProgress addedCourseProgress = courseProgressService.addCourseProgress(courseProgress);
        return new ResponseEntity<>(addedCourseProgress, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourseProgress(@PathVariable Long enrollmentId, @PathVariable Long id) {
        courseProgressService.deleteCourseProgress(id);
        return ResponseEntity.noContent().build();
    }
}
