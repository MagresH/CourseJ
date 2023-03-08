package com.example.coursej.repository;

import com.example.coursej.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
