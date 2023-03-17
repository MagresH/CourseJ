package com.example.coursej.controller;

import com.example.coursej.model.progress.LessonProgress;
import com.example.coursej.service.LessonProgressService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/enrollments/{enrollmentId}/course-progress/{courseProgressId}/lessons-progresses")
public class LessonProgressController {
    private final LessonProgressService lessonProgressService;

    public LessonProgressController(LessonProgressService lessonProgressService) {
        this.lessonProgressService = lessonProgressService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<LessonProgress>> getLessonsProgressesByCourseProgressId(@PathVariable Long enrollmentId, @PathVariable Long courseProgressId) {
        List<LessonProgress> lessonProgresses = lessonProgressService.getLessonsProgressesByCourseProgressId(courseProgressId);

        lessonProgresses.forEach(
                lessonProgress -> {
                    Link lessonLink = linkTo(methodOn(LessonController.class).getLessonById(courseProgressId, lessonProgress.getLesson().getId())).withRel("lesson");
                    Link courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withRel("courseProgress");
                    Link selfLink = linkTo(methodOn(LessonProgressController.class).getLessonProgressById(enrollmentId,courseProgressId,lessonProgress.getId())).withSelfRel();

                    lessonProgress.add(lessonLink,courseProgressLink, selfLink);
                });

        CollectionModel<LessonProgress> collectionModel = CollectionModel.of(lessonProgresses,
                linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withRel("courseProgress"),
                linkTo(methodOn(LessonProgressController.class).getLessonsProgressesByCourseProgressId(enrollmentId,courseProgressId)).withRel("lessonsProgresses")
                );


        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonProgress> getLessonProgressById(@PathVariable Long enrollmentId,
                                                                @PathVariable Long courseProgressId,
                                                                @PathVariable("id") Long id) {
        LessonProgress lessonProgress = lessonProgressService.getLessonProgressById(id);

        Link selfLink = linkTo(methodOn(LessonProgressController.class).getLessonProgressById(enrollmentId,courseProgressId,id)).withSelfRel();
        Link selfAllLink = linkTo(methodOn(LessonProgressController.class).getLessonsProgressesByCourseProgressId(enrollmentId,courseProgressId)).withSelfRel();
        Link courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withRel("courseProgress");

        lessonProgress.add(selfLink,courseProgressLink,selfAllLink);

        return ResponseEntity.ok(lessonProgress);
    }
}
