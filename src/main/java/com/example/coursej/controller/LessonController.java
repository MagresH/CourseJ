package com.example.coursej.controller;

import com.example.coursej.model.Lesson;
import com.example.coursej.model.progress.LessonProgress;
import com.example.coursej.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/lessons")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable("courseId") Long courseId, @PathVariable("lessonId") Long lessonId) {
        Lesson lesson = lessonService.getLessonById(lessonId).get();

        Link selfLink = linkTo(methodOn(LessonController.class).getLessonById(courseId, lesson.getId())).withSelfRel();
        Link selfAllLink = linkTo(methodOn(LessonController.class).getLessonsByCourseId(courseId)).withSelfRel();
        Link courseLink = linkTo(methodOn(CourseController.class).getCourseById(courseId)).withRel("course");

        lesson.add(courseLink,selfLink, selfAllLink);

        return ResponseEntity.ok(lesson);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<Lesson>> getLessonsByCourseId(@PathVariable("courseId") Long courseId) {
        List<Lesson> lessons = lessonService.getLessonsByCourseId(courseId);

        lessons.forEach(
                lesson -> {
                    Link selfLink = linkTo(methodOn(LessonController.class).getLessonById(courseId, lesson.getId())).withSelfRel();
                    lesson.add(selfLink);
                });

        CollectionModel<Lesson> collectionModel = CollectionModel.of(lessons,
                linkTo(methodOn(CourseController.class).getCourseById(courseId)).withRel("course"),
                linkTo(methodOn(LessonController.class).getLessonsByCourseId(courseId)).withSelfRel()
        );
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    public ResponseEntity<Lesson> addLesson(@RequestBody Lesson lesson) {
        Lesson addedLesson = lessonService.addLesson(lesson);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedLesson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        Lesson updatedLesson = null; // TODO lessonService.updateLesson(id, lesson);
        return ResponseEntity.ok(updatedLesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}
