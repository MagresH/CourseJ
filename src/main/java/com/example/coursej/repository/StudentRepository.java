package com.example.coursej.repository;

import com.example.coursej.model.user.Student;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends UserRepository<Student> {
    Student getStudentById(Long id);

}
