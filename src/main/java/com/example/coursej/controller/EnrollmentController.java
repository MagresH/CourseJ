package com.example.coursej.controller;

import com.example.coursej.model.Enrollment;
import com.example.coursej.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Enrollment>> getAllEnrollments() {
        Link selfLink = linkTo(EnrollmentController.class).withSelfRel();
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        enrollments.forEach(enrollment -> {
            Link enrollmentSelfLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollment.getId())).withSelfRel();
            Link courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollment.getId(), enrollment.getCourseProgress().getId())).withRel("courseProgress");
            Link userLink = linkTo(methodOn(UserController.class).getUserById(enrollment.getUser().getId())).withRel("student");
            Link courseLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(enrollment.getCourse().getId())).withRel("course");
            enrollment.add(enrollmentSelfLink, userLink, courseLink, courseProgressLink);
        });
        CollectionModel<Enrollment> result = CollectionModel.of(enrollments, selfLink);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {

        Enrollment enrollment = enrollmentService.getEnrollmentById(id);

        Link selfLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(id)).withSelfRel();
        Link selfAllLink = linkTo(EnrollmentController.class).withSelfRel();
        Link userLink = linkTo(methodOn(UserController.class).getUserById(enrollment.getUser().getId())).withRel("student");
        Link courseLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(enrollment.getCourse().getId())).withRel("course");
        Link courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollment.getId(), enrollment.getCourseProgress().getId())).withRel("courseProgress");
        enrollment.add(userLink, courseLink, courseProgressLink, selfLink, selfAllLink);
        return new ResponseEntity<>(enrollment, HttpStatus.OK);
    }

    @GetMapping(params = "userId")
    public ResponseEntity<CollectionModel<Enrollment>> getEnrollmentsByUserId(@RequestParam("userId") Long userId) {
        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByUserId(userId);
        enrollments.forEach(
                enrollment -> {
                    Link selfLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentById(enrollment.getId())).withSelfRel();
                    Link courseLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(enrollment.getCourse().getId())).withRel("course");
                    Link courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollment.getId(), enrollment.getCourseProgress().getId())).withRel("courseProgress");
                    Link userLink = linkTo(methodOn(UserController.class).getUserById(enrollment.getUser().getId())).withRel("student");
                    enrollment.add(courseLink, userLink, courseProgressLink, selfLink);
                });

        CollectionModel<Enrollment> collectionModel = CollectionModel.of(enrollments,
                linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(userId)).withSelfRel(),
                linkTo(EnrollmentController.class).withSelfRel(),
                linkTo(methodOn(UserController.class).getAllUsers()).withRel("students") //TODO
        );

        return ResponseEntity.ok(collectionModel);
    }


    @PostMapping
    public ResponseEntity<Enrollment> addEnrollment(@RequestBody Enrollment enrollment) {
        Enrollment newEnrollment = enrollmentService.addEnrollment(enrollment);
        return ResponseEntity.created(URI.create("/enrollments/" + newEnrollment.getId())).body(newEnrollment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enrollment> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        Enrollment updatedEnrollment = enrollmentService.updateEnrollment(enrollment);
        if (updatedEnrollment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEnrollment);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable("id") Long enrollmentId) {
        enrollmentService.deleteEnrollment(enrollmentId);
        return ResponseEntity.noContent().build();
    }
}
