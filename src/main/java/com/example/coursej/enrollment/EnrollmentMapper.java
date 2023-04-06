package com.example.coursej.enrollment;

import com.example.coursej.course.Course;
import com.example.coursej.progress.courseProgress.CourseProgress;
import com.example.coursej.user.User;
import com.example.coursej.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {

    EnrollmentMapper INSTANCE = Mappers.getMapper(EnrollmentMapper.class);

    @Mappings({
            @Mapping(source = "course", target = "courseId", qualifiedByName = "toCourseId"),
            @Mapping(source = "user", target = "userId", qualifiedByName = "toUserId"),
            @Mapping(source = "courseProgress", target = "courseProgressId", qualifiedByName = "toCourseProgressId")
    })
    EnrollmentDTO toDTO(Enrollment enrollment);

    @Mappings({
            @Mapping(source = "courseId", target = "course",qualifiedByName = "toCourse"),
            @Mapping(source = "userId", target = "user", qualifiedByName = "toUser"),
            @Mapping(source = "courseProgressId", target = "courseProgress", qualifiedByName = "toCourseProgress")
    })
    Enrollment toEntity(EnrollmentDTO enrollmentDTO);

    @Named("toCourseId")
    default Long toCourseId(Course course) {
        return course.getId();
    }

    @Named("toCourse")
    default Course toCourse(Long courseId) {
        return Course.builder().id(courseId).build();
    }

    @Named("toUserId")
    default Long toUserId(User user) {
        return user.getId();
    }

    @Named("toUser")
    default User toUser(Long userId) {
        return User.builder().id(userId).build();
    }

    @Named("toCourseProgressId")
    default Long toCourseProgressId(CourseProgress courseProgress) {
        return courseProgress.getId();
    }

    @Named("toCourseProgress")
    default CourseProgress toCourseProgress(Long courseProgressId) {
        return CourseProgress.builder().id(courseProgressId).build();
    }

    default List<EnrollmentDTO> toDTOList(List<Enrollment> enrollmentList) {
        return enrollmentList.stream()
                .map(this::toDTO)
                .toList();
    }

    default List<Enrollment> toEntityList(List<EnrollmentDTO> enrollmentDTOList) {
        return enrollmentDTOList.stream()
                .map(this::toEntity)
                .toList();
    }
}
