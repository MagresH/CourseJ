package com.example.coursej.repository;

import com.example.coursej.model.user.Student;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends UserRepository<Student> {
    Optional<Student> getStudentById(Long id);

}
