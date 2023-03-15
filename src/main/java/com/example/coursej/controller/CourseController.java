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

        courses.forEach(course -> course.add(linkTo(methodOn(CourseController.class).getCourseById(course.getId())).withSelfRel()));
        Link link = linkTo(CourseController.class).withSelfRel();
        CollectionModel<Course> result = CollectionModel.of(courses, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        Link link = linkTo(methodOn(CourseController.class).getCourseById(course.get().getId())).withSelfRel();
        Link link2 = linkTo(CourseController.class).withRel(link.getRel());
        course.get().add(link, link2);
        return new ResponseEntity<>(course.get(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course newCourse = courseService.addCourse(course);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(course);
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(params = "teacherId")
    public ResponseEntity<List<Course>> getCoursesByTeacherId(@RequestParam Long teacherId) {
        List<Course> courses = courseService.getCourseByTeacherId(teacherId);
        return ResponseEntity.ok(courses);
    }
}
