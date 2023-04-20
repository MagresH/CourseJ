package com.example.coursej.enrollment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("SELECT e FROM Enrollment e WHERE e.user.id = ?1")
    Page<Enrollment> getEnrollmentsByUserId(Long id, Pageable pageable);
    @Query("SELECT e FROM Enrollment e")
    Page<Enrollment> getAllEnrollments(Pageable pageable);
   }


