package com.example.coursej.lesson;

import com.example.coursej.course.Course;
import com.example.coursej.course.CourseService;
import com.example.coursej.mapper.BaseMapper;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class LessonMapper implements BaseMapper<Lesson, LessonDTO> {

    @Autowired
    private CourseService courseService;

    @Mapping(source = "course", target = "courseId", qualifiedByName = "toCourseId")
    public abstract LessonDTO toDTO(Lesson lesson);

    @Mapping(source = "courseId", target = "course", qualifiedByName = "toCourse")
    public abstract Lesson toEntity(LessonDTO lessonDTO);

    @IterableMapping(qualifiedByName = "toEntity")
    public List<Lesson> toEntityList(List<LessonDTO> lessonDTOs) {
        return lessonDTOs.stream()
                .map(this::toEntity)
                .toList();
    }
    @IterableMapping(qualifiedByName = "toDTO")
    public List<LessonDTO> toDTOList(List<Lesson> lessons) {
        return lessons.stream()
                .map(this::toDTO)
                .toList();
    }

    @Named("toCourseId")
    Long toCourseId(Course course) {
        return course.getId();
    }
    @Named("toCourse")
    Course toCourse(Long courseId) {
        return courseService.getCourseById(courseId);
    }

}
