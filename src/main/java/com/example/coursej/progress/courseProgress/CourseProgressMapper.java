package com.example.coursej.progress.courseProgress;


import com.example.coursej.enrollment.Enrollment;
import com.example.coursej.enrollment.EnrollmentService;
import com.example.coursej.mapper.BaseMapper;
import com.example.coursej.progress.lessonProgress.LessonProgress;
import com.example.coursej.progress.lessonProgress.LessonProgressService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class CourseProgressMapper implements BaseMapper<CourseProgress, CourseProgressDTO> {

    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private LessonProgressService lessonProgressService;

    @Mapping(source = "enrollment", target = "enrollmentId", qualifiedByName = "toEnrollmentId")
    @Mapping(source = "lessonProgresses", target = "lessonProgressesIds", qualifiedByName = "toLessonProgressesIdList")
    public abstract CourseProgressDTO toDTO(CourseProgress courseProgress);

    @Mapping(source = "enrollmentId", target = "enrollment", qualifiedByName = "toEnrollment")
    @Mapping(source = "lessonProgressesIds", target = "lessonProgresses", qualifiedByName = "toLessonProgressesList")
    public abstract CourseProgress toEntity(CourseProgressDTO courseProgressDTO);

    @IterableMapping(qualifiedByName = "toDTO")
    public List<CourseProgressDTO> toDTOList(List<CourseProgress> courseProgressList) {
        return courseProgressList.stream()
                .map(this::toDTO)
                .toList();
    }
    @IterableMapping(qualifiedByName = "toEntity")
    public List<CourseProgress> toEntityList(List<CourseProgressDTO> courseProgressDTOList) {
        return courseProgressDTOList.stream()
                .map(this::toEntity)
                .toList();
    }
    @Named("toEnrollmentId")
    Long toEnrollmentId(Enrollment enrollment) {
        return enrollment.getId();
    }
    @Named("toLessonProgressesIdList")
    List<Long> toLessonProgressesIdList(List<LessonProgress> lessonProgresses) {
        return lessonProgresses.stream()
                .map(LessonProgress::getId)
                .toList();
    }
    @Named("toEnrollment")
    Enrollment toEnrollment(Long enrollmentId){
        return enrollmentService.getEnrollmentById(enrollmentId);
    }

    @Named("toLessonProgressesList")
    List<LessonProgress> toLessonProgressesList(List<Long> lessonProgressesIds) {
        return lessonProgressesIds.stream()
                .map(lessonProgressService::getLessonProgressById)
                .toList();
    }
}
