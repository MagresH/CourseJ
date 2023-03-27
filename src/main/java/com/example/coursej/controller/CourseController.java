package com.example.coursej.controller;

import com.example.coursej.config.SecurityUtil;
import com.example.coursej.model.Course;
import com.example.coursej.service.CourseService;
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
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
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
    @GetMapping("/{id}")
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
    public ResponseEntity<CollectionModel<Course>> getCoursesByUserId(@PathVariable("userId") Long userId) {
        if (!SecurityUtil.isCurrentUser(userId))
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
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course newCourse = courseService.addCourse(course);

        return new ResponseEntity<>(newCourse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/courses/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable("courseId") Long courseId, @RequestBody Course course) {

        if (!SecurityUtil.isCurrentUser(course.getUser().getId()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else if (!courseId.equals(course.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else {
            Course updatedCourse = courseService.updateCourse(course);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("courseId") Long courseId) {

        if (!SecurityUtil.isCurrentUser(courseService.getCourseById(courseId).getUser().getId()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else {
            courseService.deleteCourseById(courseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
}
