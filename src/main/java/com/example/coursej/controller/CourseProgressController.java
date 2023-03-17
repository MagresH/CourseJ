package com.example.coursej.controller;

import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.service.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<CourseProgress> getCourseProgressById(@PathVariable("enrollmentId") Long enrollmentId, @PathVariable("courseProgressId") Long courseProgressId) {

        CourseProgress courseProgress = courseProgressService.getCourseProgressById(courseProgressId);

        Link enrollmentLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollmentId)).withRel("enrollment");
        Link lessonsProgressesLink = linkTo(methodOn(LessonProgressController.class).getLessonsProgressesByCourseProgressId(enrollmentId, courseProgressId)).withRel("lessonsProgresses");
        Link selfLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withSelfRel();

        courseProgress.add(selfLink, lessonsProgressesLink, enrollmentLink);

        return ResponseEntity.ok(courseProgress);
    }


    @PostMapping
    public ResponseEntity<CourseProgress> addCourseProgress(@PathVariable String courseProgressId, @RequestBody CourseProgress courseProgress) {
        CourseProgress addedCourseProgress = courseProgressService.addCourseProgress(courseProgress);
        return new ResponseEntity<>(addedCourseProgress, HttpStatus.CREATED);
    }

    //TODO missing put mapping
    @DeleteMapping
    public ResponseEntity<Void> deleteCourseProgress(@PathVariable Long enrollmentId, @PathVariable Long id) {
        courseProgressService.deleteCourseProgress(id);
        return ResponseEntity.noContent().build();
    }
}
