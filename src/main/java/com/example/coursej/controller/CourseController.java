package com.example.coursej.controller;

import com.example.coursej.model.Course;
import com.example.coursej.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();

        courses.forEach(course -> {
            Link lessonsLink = linkTo(methodOn(LessonController.class).getLessonsByCourseId(course.getId())).withRel("lessons");
            Link teacherLink = linkTo(methodOn(TeacherController.class).getTeacherById(course.getTeacher().getId())).withRel("teacher");
            Link selfLink = linkTo(methodOn(CourseController.class).getCourseById(course.getId())).withSelfRel();

            course.add(lessonsLink, teacherLink, selfLink);
        });

        Link selfAllLink = linkTo(CourseController.class).withSelfRel();

        CollectionModel<Course> result = CollectionModel.of(courses, selfAllLink);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Long id) {
        Optional<Course> course = courseService.getCourseById(id);

        Link lessonsLink = linkTo(methodOn(LessonController.class).getLessonsByCourseId(id)).withRel("lessons");
        Link teacherLink = linkTo(methodOn(TeacherController.class).getTeacherById(course.get().getTeacher().getId())).withRel("teacher");
        Link selfLink = linkTo(methodOn(CourseController.class).getCourseById(course.get().getId())).withSelfRel();
        Link selfAllLink = linkTo(CourseController.class).withSelfRel();

        course.get().add(lessonsLink, teacherLink, selfLink, selfAllLink);

        return new ResponseEntity<>(course.get(), HttpStatus.OK);
    }

    @GetMapping(params = "teacherId")
    public ResponseEntity<CollectionModel<Course>> getCoursesByTeacherId(@RequestParam Long teacherId) {
        List<Course> courses = courseService.getCourseByTeacherId(teacherId);

        courses.forEach(course -> {
            Link selfLink = linkTo(methodOn(CourseController.class).getCourseById(course.getId())).withSelfRel();
            Link teacherLink = linkTo(methodOn(TeacherController.class).getTeacherById(course.getTeacher().getId())).withRel("teacher");

            course.add(teacherLink, selfLink);
        });

        Link selfAllLink = linkTo(CourseController.class).withSelfRel();

        CollectionModel<Course> result = CollectionModel.of(courses, selfAllLink);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{courseId}")
    public ResponseEntity<Course> addCourse(@PathVariable("courseId") Long courseId, @RequestBody Course course) {
        Course newCourse = courseService.addCourse(course);

        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(@PathVariable("courseId") Long courseId, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(course);

        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
