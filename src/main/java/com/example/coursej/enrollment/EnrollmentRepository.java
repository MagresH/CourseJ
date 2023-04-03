package com.example.coursej.enrollment;

import com.example.coursej.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> getEnrollmentsByUserId(Long id);
}
