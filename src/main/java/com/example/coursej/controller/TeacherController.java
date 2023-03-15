package com.example.coursej.controller;

import com.example.coursej.model.user.Teacher;
import com.example.coursej.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @Autowired
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @GetMapping
    public ResponseEntity<CollectionModel<Teacher>> getAllTeachers() {

        List<Teacher> teachers = teacherService.findAllTeachers();

        teachers.forEach(teacher -> teacher.add(linkTo(methodOn(TeacherController.class).getTeacherById(teacher.getId())).withSelfRel()));
        Link link = linkTo(TeacherController.class).withSelfRel();

        CollectionModel<Teacher> result = CollectionModel.of(teachers, link);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{teacherId}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("teacherId") Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        Link link = linkTo(methodOn(TeacherController.class).getTeacherById(teacher.getId())).withSelfRel();
        Link link2 = linkTo(TeacherController.class).withRel(link.getRel());
        Link link3 = linkTo(methodOn(CourseController.class).getCoursesByTeacherId(teacher.getId())).withRel("courses");

        teacher.add(link, link2, link3);
        return ResponseEntity.ok(teacher);
    }

    @PostMapping
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        Teacher newTeacher = teacherService.addTeacher(teacher);
        return new ResponseEntity<>(newTeacher, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher) {
        Teacher updateTeacher = teacherService.addTeacher(teacher);
        return new ResponseEntity<>(updateTeacher, HttpStatus.CREATED);
    }
}
