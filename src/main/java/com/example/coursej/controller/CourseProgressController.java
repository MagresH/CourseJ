package com.example.coursej.controller;

import com.example.coursej.config.SecurityUtil;
import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.service.CourseProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<CourseProgress> getCourseProgressById(@PathVariable("enrollmentId") Long enrollmentId,
                                                                @PathVariable("courseProgressId") Long courseProgressId) {
        if (SecurityUtil.isCurrentUser(courseProgressService.getCourseProgressById(courseProgressId).getEnrollment().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else {
            CourseProgress courseProgress = courseProgressService.getCourseProgressById(courseProgressId);
            Link enrollmentLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollmentId)).withRel("enrollment");
            Link lessonsProgressesLink = linkTo(methodOn(LessonProgressController.class).getLessonsProgressesByCourseProgressId(enrollmentId, courseProgressId)).withRel("lessonsProgresses");
            Link selfLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withSelfRel();
            courseProgress.add(selfLink, lessonsProgressesLink, enrollmentLink);
            return ResponseEntity.ok(courseProgress);
        }

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseProgress> addCourseProgress(@RequestBody CourseProgress courseProgress) {
        if (SecurityUtil.isCurrentUser(courseProgress.getEnrollment().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else {
            CourseProgress addedCourseProgress = courseProgressService.addCourseProgress(courseProgress);
            return new ResponseEntity<>(addedCourseProgress, HttpStatus.CREATED);
        }
    }
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseProgress> updateCourseProgress(@PathVariable Long courseProgressId,
                                                               @RequestBody CourseProgress courseProgress) {
        if (SecurityUtil.isCurrentUser(courseProgress.getEnrollment().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else if (!courseProgressId.equals(courseProgress.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else {
            CourseProgress updatedCourseProgress = courseProgressService.updateCourseProgress(courseProgress);
            return new ResponseEntity<>(updatedCourseProgress, HttpStatus.OK);
        }

    }
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourseProgress(@PathVariable Long courseProgressId) {
        courseProgressService.deleteCourseProgressById(courseProgressId);
        return ResponseEntity.noContent().build();
    }
}
