package com.example.coursej.enrollment;

import com.example.coursej.config.SecurityUtils;
import com.example.coursej.course.CourseController;
import com.example.coursej.progress.courseProgress.CourseProgressController;
import com.example.coursej.user.UserController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/")
@Tag(name = "EnrollmentController", description = "APIs for managing enrollment resources")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/enrollments")
    @Operation(summary = "Get all enrollments", description = "Get all enrollments", tags = {"enrollments"}, operationId = "getAllEnrollments")
    public ResponseEntity<CollectionModel<EnrollmentDTO>> getAllEnrollments() {

        Link selfLink = linkTo(methodOn(EnrollmentController.class).getAllEnrollments()).withSelfRel();

        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        var enrollmentDTOs = EnrollmentMapper.INSTANCE.toDTOList(enrollments);

        enrollmentDTOs.forEach(enrollment -> {
            Link enrollmentSelfLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollment.getId())).withSelfRel();
            Link courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollment.getId(), enrollment.getCourseProgressId())).withRel("courseProgress");
            Link userLink = linkTo(methodOn(UserController.class).getUserById(enrollment.getUserId())).withRel("student");
            Link courseLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(enrollment.getCourseId())).withRel("course");
            enrollment.add(enrollmentSelfLink, userLink, courseLink, courseProgressLink);
        });

        CollectionModel<EnrollmentDTO> result = CollectionModel.of(enrollmentDTOs, selfLink);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/enrollments/{id}")
    @Operation(summary = "Get enrollment by ID", description = "Returns an enrollment by ID.")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {
        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        var enrollmentDTO = EnrollmentMapper.INSTANCE.toDTO(enrollment);
        if (!SecurityUtils.isCurrentUserOrAdmin(enrollment.getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            Link selfLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(id)).withSelfRel();
            Link selfAllLink = linkTo(EnrollmentController.class).withSelfRel();
            Link userLink = linkTo(methodOn(UserController.class).getUserById(enrollment.getUser().getId())).withRel("student");
            Link courseLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(enrollment.getCourse().getId())).withRel("course");
            Link courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollment.getId(), enrollment.getCourseProgress().getId())).withRel("courseProgress");

            enrollmentDTO.add(userLink, courseLink, courseProgressLink, selfLink, selfAllLink);
            return new ResponseEntity<>(enrollmentDTO, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users/{userId}/enrollments")
    @Operation(summary = "Get all enrollments by user ID", description = "Returns a list of all enrollments by user ID.")
    public ResponseEntity<CollectionModel<EnrollmentDTO>> getEnrollmentsByUserId(@PathVariable("userId") Long userId) {

        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
        var enrollmentDTOs = EnrollmentMapper.INSTANCE.toDTOList(enrollments);
        if (!SecurityUtils.isCurrentUserOrAdmin(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else {
            enrollmentDTOs.forEach(
                    enrollment -> {
                        Link selfLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollment.getId())).withSelfRel();
                        Link courseLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(enrollment.getCourseId())).withRel("course");
                        Link courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollment.getCourseProgressId(), enrollment.getCourseProgressId())).withRel("courseProgress");
                        Link userLink = linkTo(methodOn(UserController.class).getUserById(enrollment.getUserId())).withRel("student");
                        enrollment.add(courseLink, userLink, courseProgressLink, selfLink);
                    });

            CollectionModel<EnrollmentDTO> collectionModel = CollectionModel.of(enrollmentDTOs,
                    linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(userId)).withSelfRel(),
                    linkTo(EnrollmentController.class).withSelfRel(),
                    linkTo(methodOn(UserController.class).getAllUsers()).withRel("students")
            );

            return ResponseEntity.ok(collectionModel);

        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enrollments")
    @Operation(summary = "Add enrollment", description = "Adds a new enrollment.")
    public ResponseEntity<Enrollment> addEnrollment(@RequestBody Enrollment enrollment) {
        Enrollment newEnrollment = enrollmentService.addEnrollment(enrollment);
        return ResponseEntity.created(URI.create("/enrollments/" + newEnrollment.getId())).body(newEnrollment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/enrollments/{id}")
    @Operation(summary = "Update enrollment", description = "Updates an existing enrollment.")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(enrollment);
        if (updatedEnrollment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEnrollment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/enrollments/{id}")
    @Operation(summary = "Delete enrollment", description = "Deletes an enrollment by its ID.")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable("id") Long enrollmentId) {
        enrollmentService.deleteEnrollment(enrollmentId);
        return ResponseEntity.noContent().build();
    }
}