package com.example.coursej.progress.courseProgress;

import com.example.coursej.config.SecurityUtils;
import com.example.coursej.enrollment.EnrollmentController;
import com.example.coursej.progress.lessonProgress.LessonProgressController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/enrollments/{enrollmentId}/course-progress/{courseProgressId}")
@Tag(name = "CourseProgressController", description = "APIs for managing course progress resources")
public class CourseProgressController {

    private final CourseProgressService courseProgressService;

    @Autowired
    public CourseProgressController(CourseProgressService courseProgressService) {
        this.courseProgressService = courseProgressService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get course progress by id", description = "Get course progress by id", tags = {"course-progress"})
    public ResponseEntity<CourseProgress> getCourseProgressById(@PathVariable("enrollmentId") Long enrollmentId,
                                                                @PathVariable("courseProgressId") Long courseProgressId) {
        if (!SecurityUtils.isCurrentUserOrAdmin(courseProgressService.getCourseProgressById(courseProgressId).getEnrollment().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            CourseProgress courseProgress = courseProgressService.getCourseProgressById(courseProgressId);
            Link enrollmentLink = WebMvcLinkBuilder.linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollmentId)).withRel("enrollment");
            Link lessonsProgressesLink = WebMvcLinkBuilder.linkTo(methodOn(LessonProgressController.class).getLessonsProgressesByCourseProgressId(enrollmentId, courseProgressId)).withRel("lessonsProgresses");
            Link selfLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withSelfRel();
            courseProgress.add(selfLink, lessonsProgressesLink, enrollmentLink);
            return ResponseEntity.ok(courseProgress);
        }

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add course progress", description = "Add course progress", tags = {"course-progress"})
    public ResponseEntity<CourseProgress> addCourseProgress(@RequestBody CourseProgress courseProgress) {
        if (!SecurityUtils.isCurrentUserOrAdmin(courseProgress.getEnrollment().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            CourseProgress addedCourseProgress = courseProgressService.addCourseProgress(courseProgress);
            return new ResponseEntity<>(addedCourseProgress, HttpStatus.CREATED);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update course progress", description = "Update course progress", tags = {"course-progress"})
    public ResponseEntity<CourseProgress> updateCourseProgress(@PathVariable Long courseProgressId,
                                                               @RequestBody CourseProgress courseProgress) {
        if (!SecurityUtils.isCurrentUserOrAdmin(courseProgress.getEnrollment().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (!courseProgressId.equals(courseProgress.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            CourseProgress updatedCourseProgress = courseProgressService.updateCourseProgress(courseProgress);
            return new ResponseEntity<>(updatedCourseProgress, HttpStatus.OK);
        }

    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete course progress", description = "Delete course progress", tags = {"course-progress"})
    public ResponseEntity<Void> deleteCourseProgress(@PathVariable Long courseProgressId) {
        if (!SecurityUtils.isCurrentUserOrAdmin(courseProgressService.getCourseProgressById(courseProgressId).getEnrollment().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            courseProgressService.deleteCourseProgressById(courseProgressId);
            return ResponseEntity.noContent().build();
        }
    }
}
