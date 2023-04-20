package com.example.coursej.enrollment;

import com.example.coursej.config.SecurityUtils;
import com.example.coursej.course.CourseController;
import com.example.coursej.progress.courseProgress.CourseProgressController;
import com.example.coursej.user.UserController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/")
@Tag(name = "EnrollmentController", description = "APIs for managing enrollment resources")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final EnrollmentMapper enrollmentMapper;
    private final PagedResourcesAssembler<EnrollmentDTO> pagedResourcesAssembler;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/enrollments")
    @Operation(summary = "Get all enrollments", description = "Get all enrollments")
    public ResponseEntity<PagedModel<EntityModel<EnrollmentDTO>>> getAllEnrollments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ) {

        Page<Enrollment> enrollments = enrollmentService.getSortedEnrollments(page, size, sortDirection);
        Page<EnrollmentDTO> enrollmentDTOs = enrollments.map(enrollmentMapper::toDTO);

        enrollmentDTOs.forEach(enrollment -> {
            Link enrollmentSelfLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollment.getId())).withSelfRel();
            Link courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollment.getId(), enrollment.getCourseProgressId())).withRel("courseProgress");
            Link userLink = linkTo(methodOn(UserController.class).getUserById(enrollment.getUserId())).withRel("student");
            Link courseLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(enrollment.getCourseId())).withRel("course");
            enrollment.add(enrollmentSelfLink, userLink, courseLink, courseProgressLink);
        });

        Link selfLink = linkTo(methodOn(EnrollmentController.class).getAllEnrollments(0,30,"ASC")).withSelfRel();

        PagedModel<EntityModel<EnrollmentDTO>> pagedModel = pagedResourcesAssembler.toModel(enrollmentDTOs, selfLink);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/enrollments/{id}")
    @Operation(summary = "Get enrollment by ID", description = "Returns an enrollment by ID.")
    public ResponseEntity<EnrollmentDTO> getEnrollmentById(@PathVariable Long id) {

        Enrollment enrollment = enrollmentService.getEnrollmentById(id);
        EnrollmentDTO enrollmentDTO = enrollmentMapper.toDTO(enrollment);

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
    public ResponseEntity<PagedModel<EntityModel<EnrollmentDTO>>> getEnrollmentsByUserId(@PathVariable("userId") Long userId,
                                                                                         @RequestParam(defaultValue = "0") int page,
                                                                                         @RequestParam(defaultValue = "30") int size,
                                                                                         @RequestParam(defaultValue = "ASC") String sortDirection) {


        Page<Enrollment> enrollments = enrollmentService.getSortedEnrollmentsByUserId(userId, page, size, sortDirection);
        Page<EnrollmentDTO> enrollmentDTOs = enrollments.map(enrollmentMapper::toDTO);


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

                    Link link1 = linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(userId,0,30,"ASC")).withSelfRel();
                    Link link2 = linkTo(EnrollmentController.class).withSelfRel();
                    Link link3 = linkTo(methodOn(UserController.class).getAllUsers(null, null, null, 0, 30, List.of(), DESC)).withRel("students");

            PagedModel<EntityModel<EnrollmentDTO>> collectionModel = pagedResourcesAssembler.toModel(enrollmentDTOs, enrollmentDTO -> EntityModel.of(enrollmentDTO, link1, link2, link3));
            return ResponseEntity.ok(collectionModel);

        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/enrollments")
    @Operation(summary = "Add enrollment", description = "Adds a new enrollment.")
    public ResponseEntity<EnrollmentDTO> addEnrollment(@RequestBody EnrollmentDTO enrollmentDTO) {

        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentDTO);
        EnrollmentDTO createdEnrollmentDTO = enrollmentMapper.toDTO(enrollmentService.addEnrollment(enrollment));

        return new ResponseEntity<>(createdEnrollmentDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/enrollments/{id}")
    @Operation(summary = "Update enrollment", description = "Updates an existing enrollment.")
    public ResponseEntity<EnrollmentDTO> updateEnrollment(@PathVariable Long id, @RequestBody EnrollmentDTO enrollmentDTO) {

        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentDTO);
        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(enrollment);

        if (updatedEnrollment == null) {
            return ResponseEntity.notFound().build();
        }

        EnrollmentDTO updatedEnrollmentDTO = enrollmentMapper.toDTO(updatedEnrollment);

        return ResponseEntity.ok(updatedEnrollmentDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/enrollments/{id}")
    @Operation(summary = "Delete enrollment", description = "Deletes an enrollment by its ID.")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable("id") Long enrollmentId) {
        enrollmentService.deleteEnrollment(enrollmentId);

        return ResponseEntity.noContent().build();
    }
}
