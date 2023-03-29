package com.example.coursej.controller;

import com.example.coursej.config.SecurityUtils;
import com.example.coursej.model.Course;
import com.example.coursej.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "CourseController", description = "APIs for managing course resources")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/courses")
    @Operation(summary = "Get all courses", description = "Get all courses", tags = {"courses"})
    public ResponseEntity<CollectionModel<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();

        courses.forEach(course -> {
            Link lessonsLink = linkTo(methodOn(LessonController.class).getLessonsByCourseId(course.getId())).withRel("lessons");
            Link userLink = linkTo(methodOn(UserController.class).getUserById(course.getUser().getId())).withRel("teacher");
            Link selfLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(course.getId())).withSelfRel();

            course.add(lessonsLink, userLink, selfLink);
        });

        Link selfAllLink = linkTo(CourseController.class).withSelfRel();

        CollectionModel<Course> result = CollectionModel.of(courses, selfAllLink);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/courses/{id}")
    @Operation(summary = "Get course by id", description = "Get course by id", tags = {"courses"})
    public ResponseEntity<Course> getCourseByCourseId(@PathVariable("id") Long id) {
        Course course = courseService.getCourseById(id);

        Link lessonsLink = linkTo(methodOn(LessonController.class).getLessonsByCourseId(id)).withRel("lessons");
        Link userLink = linkTo(methodOn(UserController.class).getUserById(course.getUser().getId())).withRel("teacher");
        Link selfLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(course.getId())).withSelfRel();
        Link selfAllLink = linkTo(CourseController.class).withSelfRel();

        course.add(lessonsLink, userLink, selfLink, selfAllLink);

        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("users/{userId}/courses")
    @Operation(summary = "Get courses by user id", description = "Get courses by user id", tags = {"courses"})
    public ResponseEntity<CollectionModel<Course>> getCoursesByUserId(@PathVariable("userId") Long userId) {
        if (!SecurityUtils.isCurrentUserOrAdmin(userId) || !SecurityUtils.isAdmin(userId))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else {
            List<Course> courses = courseService.getCourseByTeacherId(userId);

            courses.forEach(course -> {
                Link selfLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(course.getId())).withSelfRel();
                Link userLink = linkTo(methodOn(UserController.class).getUserById(course.getUser().getId())).withRel("teacher");

                course.add(userLink, selfLink);
            });

            Link selfAllLink = linkTo(CourseController.class).withSelfRel();

            CollectionModel<Course> result = CollectionModel.of(courses, selfAllLink);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/courses")
    @Operation(summary = "Add course", description = "Add course", tags = {"courses"})
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course newCourse = courseService.addCourse(course);

        return new ResponseEntity<>(newCourse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/courses/{courseId}")
    @Operation(summary = "Update course", description = "Update course", tags = {"courses"})
    public ResponseEntity<Course> updateCourse(@PathVariable("courseId") Long courseId, @RequestBody Course course) {

        if (!SecurityUtils.isCurrentUserOrAdmin(course.getUser().getId()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else if (!courseId.equals(course.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else {
            Course updatedCourse = courseService.updateCourse(course);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/courses/{courseId}")
    @Operation(summary = "Delete course", description = "Delete course", tags = {"courses"})
    public ResponseEntity<Course> deleteCourse(@PathVariable("courseId") Long courseId) {

        if (!SecurityUtils.isCurrentUserOrAdmin(courseService.getCourseById(courseId).getUser().getId()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else {
            courseService.deleteCourseById(courseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
}
