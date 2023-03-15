package com.example.coursej.controller;

import com.example.coursej.model.Enrollment;
import com.example.coursej.model.progress.LessonProgress;
import com.example.coursej.service.LessonProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments/{enrollmentId}/course-progress/{courseProgressId}/lesson-progresses")
public class LessonProgressController {
    private final LessonProgressService lessonProgressService;

    public LessonProgressController(LessonProgressService lessonProgressService) {
        this.lessonProgressService = lessonProgressService;
    }

    @GetMapping
    public ResponseEntity<List<LessonProgress>> getAllLessonProgressesByCourseProgressId(@PathVariable Long courseProgressId){
        List<LessonProgress> lessonProgresses = lessonProgressService
                .getAllLessonProgressessByCourseProgressId(courseProgressId);
        return ResponseEntity.ok(lessonProgresses);
    }
}
