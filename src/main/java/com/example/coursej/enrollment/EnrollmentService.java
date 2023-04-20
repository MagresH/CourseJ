package com.example.coursej.enrollment;

import com.example.coursej.helpers.SortOrderUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;


    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;

    }

    public Page<Enrollment> getSortedEnrollments(int page, int size, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, "id"));
        return enrollmentRepository.getAllEnrollments(pageRequest);
    }

    public Page<Enrollment> getSortedEnrollmentsByUserId(Long userId, int page, int size, String sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, "id"));
        return enrollmentRepository.getEnrollmentsByUserId(userId, pageRequest);
    }
    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Enrollment with id " + id + " does not exist"));
    }

    public Enrollment addEnrollment(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment updateEnrollment(Enrollment enrollment) {

        return enrollmentRepository.save(enrollment);
    }

    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
}
