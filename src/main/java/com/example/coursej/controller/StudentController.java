package com.example.coursej.controller;

import com.example.coursej.model.user.Student;
import com.example.coursej.service.StudentService;
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
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Student>> getAllStudents() {
        List<Student> students = studentService.findAllStudents();

        students.forEach(student -> {
            Link selfLink = linkTo(methodOn(StudentController.class).getStudentByID(student.getId())).withSelfRel();
            student.add(selfLink);
            Link enrollmentsLink = linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(student.getId())).withRel("enrollments");
            student.add(enrollmentsLink);
        });
        Link link = linkTo(StudentController.class).withSelfRel();
        CollectionModel<Student> result = CollectionModel.of(students, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentByID(@PathVariable("id") Long id) {
        Optional<Student> studentOptional = studentService.getStudentById(id);

        Link link = linkTo(methodOn(StudentController.class).getStudentByID(studentOptional.get().getId())).withSelfRel();
        Link link2 = linkTo(StudentController.class).withRel(link.getRel());
        Link link3 = linkTo(methodOn(EnrollmentController.class).getEnrollmentsByUserId(studentOptional.get().getId())).withRel("enrollments");

        studentOptional.get().add(link, link2,link3);

        return new ResponseEntity<>(studentOptional.get(), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student newStudent = studentService.addStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(updateStudent, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") Long id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
