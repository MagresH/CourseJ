package com.example.coursej.controller;

import com.example.coursej.model.user.Teacher;
import com.example.coursej.service.TeacherService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Teacher>> getAllTeachers(){
        List<Teacher> teachers = teacherService.findAllTeachers();
        return new ResponseEntity<>(teachers,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher){
        Teacher newTeacher = teacherService.addTeacher(teacher);
        return new ResponseEntity<>(newTeacher,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher){
        Teacher updateTeacher = teacherService.addTeacher(teacher);
        return new ResponseEntity<>(updateTeacher,HttpStatus.CREATED);
    }
}
