package com.example.coursej.lesson;

import com.example.coursej.config.SecurityUtils;
import com.example.coursej.course.CourseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final LessonMapper lessonMapper;

    @GetMapping("/{lessonId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get lesson by id", description = "Get lesson by id")
    public ResponseEntity<LessonDTO> getLessonById(@PathVariable("courseId") Long courseId, @PathVariable("lessonId") Long lessonId) {

        var lesson = lessonService.getLessonById(lessonId);
        var lessonDTO = lessonMapper.toDTO(lesson);

        Link selfLink = linkTo(methodOn(LessonController.class).getLessonById(courseId, lesson.getId())).withSelfRel();
        Link selfAllLink = linkTo(methodOn(LessonController.class).getLessonsByCourseId(courseId)).withSelfRel();
        Link courseLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(courseId)).withRel("course");

        lessonDTO.add(courseLink, selfLink, selfAllLink);

        return ResponseEntity.ok(lessonDTO);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get all lessons by course id", description = "Get all lessons by course id")
    public ResponseEntity<CollectionModel<LessonDTO>> getLessonsByCourseId(@PathVariable("courseId") Long courseId) {

        var lessons = lessonService.getLessonsByCourseId(courseId);
        var lessonDTOs = lessonMapper.toDTOList(lessons);

        lessonDTOs.forEach(
                lesson -> {
                    Link selfLink = linkTo(methodOn(LessonController.class).getLessonById(courseId, lesson.getId())).withSelfRel();
                    lesson.add(selfLink);
                });

        CollectionModel<LessonDTO> collectionModel = CollectionModel.of(lessonDTOs,
                linkTo(methodOn(CourseController.class).getCourseByCourseId(courseId)).withRel("course"),
                linkTo(methodOn(LessonController.class).getLessonsByCourseId(courseId)).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Add lesson", description = "Add lesson")
    public ResponseEntity<LessonDTO> addLesson(@RequestBody LessonDTO lessonDTO) {

        if (!SecurityUtils.isCurrentUserOrAdmin(
                lessonService.getLessonById(lessonDTO.getId()).getCourse().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        var lesson = lessonMapper.toEntity(lessonDTO);
        lessonService.addLesson(lesson);

        return ResponseEntity.status(HttpStatus.CREATED).body(lessonDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Update lesson by id", description = "Update lesson by id")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable Long id, @RequestBody LessonDTO lessonDTO) {

        if (!SecurityUtils.isCurrentUserOrAdmin(
                lessonService.getLessonById(lessonDTO.getId()).getCourse().getUser().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else if (lessonService.getLessonById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            var lesson = lessonMapper.toEntity(lessonDTO);
            lessonService.updateLesson(lesson);

            return ResponseEntity.ok(lessonDTO);
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Delete lesson by id", description = "Delete lesson by id")
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
