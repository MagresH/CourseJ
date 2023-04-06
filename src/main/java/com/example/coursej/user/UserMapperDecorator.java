package com.example.coursej.user;

import com.example.coursej.course.Course;
import com.example.coursej.course.CourseService;
import com.example.coursej.enrollment.Enrollment;
import com.example.coursej.enrollment.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.mapstruct.Named;
import org.mapstruct.Qualifier;

import java.util.List;

public abstract class UserMapperDecorator implements UserMapper{

    private UserMapper delegate;
    private EnrollmentService enrollmentService;
    private CourseService courseService;

    @Override
    public User toEntity(UserDTO userDTO) {
        User user = delegate.toEntity(userDTO);
        user.setCourses(null);
        user.setEnrollments(null);
        return user;
    }
    @Named("toEnrollmentsList")
    public List<Enrollment> toEnrollmentsList(List<Long> enrollmentsIds) {
        return enrollmentsIds.stream()
                .map(enrollmentService::getEnrollmentById)
                .toList();
    }

    @Named("toCoursesList")
    List<Course> toCoursesList(List<Long> coursesIds) {
        return coursesIds.stream()
                .map(courseService::getCourseById)
                .toList();
    }
}
