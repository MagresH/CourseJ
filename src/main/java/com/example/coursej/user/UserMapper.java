package com.example.coursej.user;

import com.example.coursej.course.Course;
import com.example.coursej.course.CourseService;
import com.example.coursej.enrollment.Enrollment;
import com.example.coursej.enrollment.EnrollmentService;
import com.example.coursej.mapper.BaseMapper;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper implements BaseMapper<User, UserDTO> {

    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private CourseService courseService;


    @Mapping(source = "enrollments", target = "enrollmentsIds", qualifiedByName = "toEnrollmentsIdList")
    @Mapping(source = "courses", target = "coursesIds", qualifiedByName = "toCoursesIdList")
    public abstract UserDTO toDTO(User user);


    @Mapping(source = "enrollmentsIds", target = "enrollments", qualifiedByName = "toEnrollmentsList")
    @Mapping(source = "coursesIds", target = "courses", qualifiedByName = "toCoursesList")
    public abstract User toEntity(UserDTO userDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    public List<UserDTO> toDTOList(List<User> userList) {
        return userList.stream()
                .map(this::toDTO)
                .toList();
    }

    @IterableMapping(qualifiedByName = "toEntity")
    public List<User> toEntityList(List<UserDTO> userDTOList) {
        return userDTOList.stream()
                .map(this::toEntity)
                .toList();
    }

    @Named("toEnrollmentsIdList")
    List<Long> toEnrollmentsIdList(List<Enrollment> enrollments) {
        return enrollments.stream()
                .map(Enrollment::getId)
                .toList();
    }

    @Named("toCoursesIdList")
    List<Long> toCoursesIdList(List<Course> courses) {
        return courses.stream()
                .map(Course::getId)
                .toList();
    }

    @Named("toEnrollmentsList")
    List<Enrollment> toEnrollmentsList(List<Long> enrollmentsIds) {
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


