package com.example.coursej.course;

import com.example.coursej.lesson.Lesson;
import com.example.coursej.lesson.LessonService;
import com.example.coursej.mapper.BaseMapper;
import com.example.coursej.user.User;
import com.example.coursej.user.UserService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CourseMapper implements BaseMapper<Course, CourseDTO> {
    @Autowired
    private LessonService lessonService;
    @Autowired
    private UserService userService;


    @Mapping(source = "user", target = "userId", qualifiedByName = "toUserId")
    @Mapping(source = "lessons", target = "lessonsIds", qualifiedByName = "toLessonIds")
    public abstract CourseDTO toDTO(Course course);

    @Mapping(source = "userId", target = "user", qualifiedByName = "toUser")
    @Mapping(source = "lessonsIds", target = "lessons", qualifiedByName = "toLessonList", ignore = true)
    public abstract Course toEntity(CourseDTO courseDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    public List<CourseDTO> toDTOList(List<Course> courses) {
        return courses.stream()
                .map(this::toDTO)
                .toList();
    }

    @IterableMapping(qualifiedByName = "toEntity")
    public List<Course> toEntityList(List<CourseDTO> courseDTOs) {
        return courseDTOs.stream()
                .map(this::toEntity)
                .toList();
    }

    @Named("toLessonIds")
    List<Long> toLessonIds(List<Lesson> lessons) {
        return lessons.stream()
                .map(Lesson::getId)
                .toList();
    }

    @Named("toLessonList")
    List<Lesson> toLessonList(List<Long> lessonIds) {
        return lessonIds.stream()
                .map(lessonService::getLessonById)
                .toList();
    }

    @Named("toUserId")
    Long toUserId(User user) {
        return user.getId();
    }

    @Named("toUser")
    User toUser(Long userId) {
        return userService.getUserById(userId);
    }
}
