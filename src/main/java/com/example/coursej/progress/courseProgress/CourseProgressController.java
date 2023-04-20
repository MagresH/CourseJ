package com.example.coursej.progress.courseProgress;

import com.example.coursej.config.SecurityUtils;
import com.example.coursej.enrollment.EnrollmentController;
import com.example.coursej.progress.lessonProgress.LessonProgressController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/enrollments/{enrollmentId}/course-progress/{courseProgressId}")
@Tag(name = "CourseProgressController", description = "APIs for managing course progress resources")
@RequiredArgsConstructor
public class CourseProgressController {

    private final CourseProgressService courseProgressService;
    private final CourseProgressMapper courseProgressMapper;


    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get course progress by id", description = "Get course progress by id")
    public ResponseEntity<CourseProgressDTO> getCourseProgressById(@PathVariable("enrollmentId") Long enrollmentId,
                                                                   @PathVariable("courseProgressId") Long courseProgressId) {

        if (!SecurityUtils.isCurrentUserOrAdmin(courseProgressService.getCourseProgressById(courseProgressId).getEnrollment().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {

            var courseProgress = courseProgressService.getCourseProgressById(courseProgressId);
            var courseProgressDTO = courseProgressMapper.toDTO(courseProgress);

            var enrollmentLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollmentId)).withRel("enrollment");
            var lessonsProgressesLink = linkTo(methodOn(LessonProgressController.class).getLessonsProgressesByCourseProgressId(enrollmentId, courseProgressId,0,30, Sort.Direction.ASC)).withRel("lessonsProgresses");
            var selfLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withSelfRel();

            courseProgressDTO.add(selfLink, lessonsProgressesLink, enrollmentLink);

            return ResponseEntity.ok(courseProgressDTO);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Add course progress", description = "Add course progress")
    public ResponseEntity<CourseProgressDTO> addCourseProgress(@RequestBody CourseProgressDTO courseProgressDTO, @PathVariable Long courseProgressId) {
        if (!SecurityUtils.isCurrentUserOrAdmin(
                courseProgressService
                        .getCourseProgressById(courseProgressId)
                        .getEnrollment()
                        .getUser()
                        .getId()
        )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            var courseProgress = courseProgressMapper.toEntity(courseProgressDTO);
            CourseProgressDTO createdCourseProgressDTO = courseProgressMapper.toDTO(courseProgressService.addCourseProgress(courseProgress));
            return new ResponseEntity<>(createdCourseProgressDTO, HttpStatus.CREATED);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update course progress", description = "Update course progress")
    public ResponseEntity<CourseProgressDTO> updateCourseProgress(@PathVariable Long courseProgressId,
                                                                  @RequestBody CourseProgressDTO courseProgressDTO) {
        if (!SecurityUtils.isCurrentUserOrAdmin(
                courseProgressService
                        .getCourseProgressById(courseProgressId)
                        .getEnrollment()
                        .getUser()
                        .getId()
        )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        } else if (!courseProgressId.equals(courseProgressDTO.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } else {

            var courseProgress = courseProgressMapper.toEntity(courseProgressDTO);
            CourseProgressDTO updatedCourseProgressDTO = courseProgressMapper.toDTO(courseProgressService.updateCourseProgress(courseProgress));

            return new ResponseEntity<>(updatedCourseProgressDTO, HttpStatus.OK);
        }


    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete course progress", description = "Delete course progress")
    public ResponseEntity<Void> deleteCourseProgress(@PathVariable Long courseProgressId) {
        if (!SecurityUtils.isCurrentUserOrAdmin(courseProgressService.getCourseProgressById(courseProgressId).getEnrollment().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            courseProgressService.deleteCourseProgressById(courseProgressId);
            return ResponseEntity.noContent().build();
        }
    }
    @PostMapping("/complete")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Complete course progress", description = "Complete course progress")
    public ResponseEntity<Void> completeCourseProgress(@PathVariable Long courseProgressId) {
        if (!SecurityUtils.isCurrentUserOrAdmin(courseProgressService
                .getCourseProgressById(courseProgressId)
                .getEnrollment()
                .getUser()
                .getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            courseProgressService.completeCourseProgress(courseProgressId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
