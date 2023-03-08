package com.example.coursej.helpers;

import com.example.coursej.model.user.Student;
import com.example.coursej.builder.StudentBuilder;
import com.example.coursej.service.StudentService;

import org.springframework.stereotype.Component;

@Component
public class InitData {
    private final StudentService studentService;

    public InitData(StudentService studentService) {
        this.studentService = studentService;
        Student st = new StudentBuilder().setFirstName("John").setLastName("Doe").setUsername("JohnDoe").setPassword("Doe").setEmail("mail").createStudent();

        studentService.addStudent(st);
    }
}
