package com.example.coursej.enrollment;


import com.example.coursej.course.Course;
import com.example.coursej.course.CourseService;
import com.example.coursej.mapper.BaseMapper;
import com.example.coursej.progress.courseProgress.CourseProgress;
import com.example.coursej.progress.courseProgress.CourseProgressService;
import com.example.coursej.user.User;
import com.example.coursej.user.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class EnrollmentMapper implements BaseMapper<Enrollment, EnrollmentDTO> {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseProgressService courseProgressService;


    @Mapping(source = "course", target = "courseId", qualifiedByName = "toCourseId")
    @Mapping(source = "user", target = "userId", qualifiedByName = "toUserId")
    @Mapping(source = "courseProgress", target = "courseProgressId", qualifiedByName = "toCourseProgressId")
    public abstract EnrollmentDTO toDTO(Enrollment enrollment);


    @Mapping(source = "courseId", target = "course", qualifiedByName = "toCourse")
    @Mapping(source = "userId", target = "user", qualifiedByName = "toUser")
    @Mapping(source = "courseProgressId", target = "courseProgress", qualifiedByName = "toCourseProgress")
    public abstract Enrollment toEntity(EnrollmentDTO enrollmentDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    public List<EnrollmentDTO> toDTOList(List<Enrollment> enrollmentList) {
        return enrollmentList.stream()
                .map(this::toDTO)
                .toList();
    }

    @IterableMapping(qualifiedByName = "toEntity")
    public List<Enrollment> toEntityList(List<EnrollmentDTO> enrollmentDTOList) {
        return enrollmentDTOList.stream()
                .map(this::toEntity)
                .toList();
    }

    @Named("toCourseId")
    Long toCourseId(Course course) {
        return course.getId();
    }

    @Named("toUserId")
    Long toUserId(User user) {
        return user.getId();
    }

    @Named("toCourseProgressId")
    Long toCourseProgressId(CourseProgress courseProgress) {
        return courseProgress.getId();
    }

    @Named("toCourse")
    Course toCourse(Long courseId) {
        return courseService.getCourseById(courseId);
    }

    @Named("toUser")
    User toUser(Long userId) {
        return userService.getUserById(userId);
    }

    @Named("toCourseProgress")
    CourseProgress toCourseProgress(Long courseProgressId) {
        return courseProgressService.getCourseProgressById(courseProgressId);
    }
}
