package com.example.coursej.progress.lessonProgress;

import com.example.coursej.lesson.Lesson;
import com.example.coursej.lesson.LessonService;
import com.example.coursej.mapper.BaseMapper;
import com.example.coursej.progress.courseProgress.CourseProgress;
import com.example.coursej.progress.courseProgress.CourseProgressService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)

public abstract class LessonProgressMapper implements BaseMapper<LessonProgress, LessonProgressDTO> {



    @Autowired
    private CourseProgressService courseProgressService;
    @Autowired
    private LessonService lessonService;


    @Mapping(source = "lesson", target = "lessonId", qualifiedByName = "toLessonId")
    @Mapping(source = "courseProgress", target = "courseProgressId", qualifiedByName = "toCourseProgressId")
    public abstract LessonProgressDTO toDTO(LessonProgress lessonProgress);

    @Mapping(source = "lessonId", target = "lesson", qualifiedByName = "toLesson")
    @Mapping(source = "courseProgressId", target = "courseProgress", qualifiedByName = "toCourseProgress")
    public abstract LessonProgress toEntity(LessonProgressDTO lessonProgressDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    public List<LessonProgressDTO> toDTOList(List<LessonProgress> lessonProgressList) {
        return lessonProgressList.stream()
                .map(this::toDTO)
                .toList();
    }
    @IterableMapping(qualifiedByName = "toEntity")
    public List<LessonProgress> toEntityList(List<LessonProgressDTO> lessonProgressDTOList) {
        return lessonProgressDTOList.stream()
                .map(this::toEntity)
                .toList();
    }

    @Named("toLessonId")
    Long toLessonId(Lesson lesson) {
        return lesson.getId();
    }
    @Named("toCourseProgressId")
    Long toCourseProgressId(CourseProgress courseProgress) {
        return courseProgress.getId();
    }
    @Named("toLesson")
    Lesson toLesson(Long lessonId) {
        return lessonService.getLessonById(lessonId);
    }
    @Named("toCourseProgress")
    CourseProgress toCourseProgress(Long courseProgressId) {
        return courseProgressService.getCourseProgressById(courseProgressId);
    }
}
