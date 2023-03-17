package com.example.coursej.repository;

import com.example.coursej.model.Enrollment;
import com.example.coursej.model.user.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> getEnrollmentsByStudentId(Long id);
}
