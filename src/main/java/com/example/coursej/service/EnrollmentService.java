package com.example.coursej.service;

import com.example.coursej.model.Course;
import com.example.coursej.model.Enrollment;
import com.example.coursej.model.user.Student;
import com.example.coursej.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentService studentService, CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public Optional<Enrollment> getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }



    public Enrollment addEnrollment(Enrollment enrollment) {
            return enrollmentRepository.save(enrollment);
        }

    public Enrollment updateEnrollment(Long id, Enrollment enrollment) { //TODO make all updates like this

        Optional<Enrollment> existingEnrollment = enrollmentRepository.findById(id);

        if (existingEnrollment.isPresent()) {
            Optional<Student> student = studentService.getStudentById(enrollment.getStudent().getId());
            Optional<Course> course = courseService.getCourseById(enrollment.getCourse().getId());

            if (student.isPresent() && course.isPresent()) {
                Enrollment updatedEnrollment = existingEnrollment.get();
                updatedEnrollment.setStudent(student.get());
                updatedEnrollment.setCourse(course.get());
                updatedEnrollment.setCourseProgress(enrollment.getCourseProgress());
                return enrollmentRepository.save(updatedEnrollment);
            }
        }
        return enrollment;
    }

    public void deleteEnrollment(Long id) {
        Optional<Enrollment> enrollment = enrollmentRepository.findById(id);

        if (enrollment.isPresent()) {
            enrollmentRepository.delete(enrollment.get());
        }
    }

    public List<Enrollment> getEnrollmentsByUserId(Long userId) {
        return enrollmentRepository.getEnrollmentsByStudentId(userId).get();
    }
}
