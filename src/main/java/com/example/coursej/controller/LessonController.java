package com.example.coursej.controller;

import com.example.coursej.config.SecurityUtils;
import com.example.coursej.model.Lesson;
import com.example.coursej.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/lessons")
@Tag(name = "LessonController", description = "APIs for managing lesson resources")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/{lessonId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get lesson by id", description = "Get lesson by id", tags = {"lessons"})
    public ResponseEntity<Lesson> getLessonById(@PathVariable("courseId") Long courseId, @PathVariable("lessonId") Long lessonId) {

        Lesson lesson = lessonService.getLessonById(lessonId);

        Link selfLink = linkTo(methodOn(LessonController.class).getLessonById(courseId, lesson.getId())).withSelfRel();
        Link selfAllLink = linkTo(methodOn(LessonController.class).getLessonsByCourseId(courseId)).withSelfRel();
        Link courseLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(courseId)).withRel("course");

        lesson.add(courseLink, selfLink, selfAllLink);

        return ResponseEntity.ok(lesson);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get all lessons by course id", description = "Get all lessons by course id", tags = {"lessons"})
    public ResponseEntity<CollectionModel<Lesson>> getLessonsByCourseId(@PathVariable("courseId") Long courseId) {
        List<Lesson> lessons = lessonService.getLessonsByCourseId(courseId);
        lessons.forEach(
                lesson -> {
                    Link selfLink = linkTo(methodOn(LessonController.class).getLessonById(courseId, lesson.getId())).withSelfRel();
                    lesson.add(selfLink);
                });

        CollectionModel<Lesson> collectionModel = CollectionModel.of(lessons,
                linkTo(methodOn(CourseController.class).getCourseByCourseId(courseId)).withRel("course"),
                linkTo(methodOn(LessonController.class).getLessonsByCourseId(courseId)).withSelfRel()
        );
        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Add lesson", description = "Add lesson", tags = {"lessons"})
    public ResponseEntity<Lesson> addLesson(@RequestBody Lesson lesson) {
        if (!SecurityUtils.isCurrentUserOrAdmin(lesson.getCourse().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Lesson addedLesson = lessonService.addLesson(lesson);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedLesson);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Update lesson by id", description = "Update lesson by id", tags = {"lessons"})
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long id, @RequestBody Lesson lesson) {
        if (!SecurityUtils.isCurrentUserOrAdmin(lesson.getCourse().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (lessonService.getLessonById(id) == null) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(lessonService.updateLesson(lesson));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Delete lesson by id", description = "Delete lesson by id", tags = {"lessons"})
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        if (!SecurityUtils.isCurrentUserOrAdmin(lessonService.getLessonById(id).getCourse().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } else if (lessonService.getLessonById(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            lessonService.deleteLesson(id);
            return ResponseEntity.noContent().build();
        }
    }
}
