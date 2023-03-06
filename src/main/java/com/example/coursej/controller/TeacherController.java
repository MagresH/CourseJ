package com.example.coursej.controller;

import com.example.coursej.model.Teacher;
import com.example.coursej.service.TeacherService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @PostMapping("/add")
//    public ResponseEntity<Student> addStudent(@RequestBody Student student){
//        Student newStudent = studentService.addStudent(student);
//        return new ResponseEntity<>(newStudent,HttpStatus.CREATED);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
//        Student updateStudent = studentService.addStudent(student);
//        return new ResponseEntity<>(updateStudent,HttpStatus.CREATED);
//    }
}
