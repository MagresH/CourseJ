package com.example.coursej.progress.lessonProgress;

import com.example.coursej.config.SecurityUtils;
import com.example.coursej.course.CourseDTO;
import com.example.coursej.lesson.LessonController;
import com.example.coursej.progress.courseProgress.CourseProgressController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/enrollments/{enrollmentId}/course-progress/{courseProgressId}/lessons-progresses")
@Tag(name = "LessonProgressController", description = "APIs for managing lesson progress resources")
@RequiredArgsConstructor
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;
    private final LessonProgressMapper lessonProgressMapper;
    private final PagedResourcesAssembler<LessonProgressDTO> pagedResourcesAssembler;

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get all lessons progresses by course progress id", description = "Get all lessons progresses by course progress id")
    public ResponseEntity<PagedModel<EntityModel<LessonProgressDTO>>> getLessonsProgressesByCourseProgressId(@PathVariable Long enrollmentId,
                                                                                                @PathVariable Long courseProgressId,
                                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                                @RequestParam(defaultValue = "10") int size,
                                                                                                @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder) {
        if (!SecurityUtils.isCurrentUserOrAdmin(lessonProgressService
                .getLessonsProgressesByCourseProgressId(courseProgressId,page,size,sortOrder.toString())
                .get()
                .findFirst()
                .get()
                .getCourseProgress()
                .getEnrollment()
                .getUser()
                .getId())
        ) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        Page<LessonProgress> lessonProgresses = lessonProgressService.getLessonsProgressesByCourseProgressId(courseProgressId, page, size, sortOrder.toString());
        Page<LessonProgressDTO> lessonProgressDTOs = lessonProgresses.map(lessonProgressMapper::toDTO);

        lessonProgressDTOs.forEach(
                lessonProgressDTO -> {
                    var lessonLink = linkTo(methodOn(LessonController.class).getLessonById(courseProgressId, lessonProgressDTO.getLessonId())).withRel("lesson");
                    var courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withRel("courseProgress");
                    var selfLink = linkTo(methodOn(LessonProgressController.class).getLessonProgressById(enrollmentId,courseProgressId, lessonProgressDTO.getLessonId())).withSelfRel();

                    lessonProgressDTO.add(lessonLink,courseProgressLink, selfLink);
                });

                Link courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withRel("courseProgress");
                Link lessonsProgressesLink = linkTo(methodOn(LessonProgressController.class).getLessonsProgressesByCourseProgressId(enrollmentId,courseProgressId, page,size,sortOrder)).withRel("lessonsProgresses");

        PagedModel<EntityModel<LessonProgressDTO>> result = pagedResourcesAssembler.toModel(lessonProgressDTOs, lessonProgressDTO -> EntityModel.of(lessonProgressDTO, courseProgressLink, lessonsProgressesLink));

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{lessonProgressId}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get lesson progress by id", description = "Get lesson progress by id")
    public ResponseEntity<LessonProgressDTO> getLessonProgressById(@PathVariable Long enrollmentId,
                                                                @PathVariable Long courseProgressId,
                                                                @PathVariable("lessonProgressId") Long lessonProgressId) {
        if (!SecurityUtils.isCurrentUserOrAdmin(lessonProgressService
                .getLessonProgressById(lessonProgressId)
                .getCourseProgress()
                .getEnrollment()
                .getUser()
                .getId())
        ) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        var lessonProgress = lessonProgressService.getLessonProgressById(lessonProgressId);
        var lessonProgressDTO = lessonProgressMapper.toDTO(lessonProgress);

        var selfLink = linkTo(methodOn(LessonProgressController.class).getLessonProgressById(enrollmentId,courseProgressId,lessonProgressId)).withSelfRel();
        var selfAllLink = linkTo(methodOn(LessonProgressController.class).getLessonsProgressesByCourseProgressId(enrollmentId,courseProgressId,0,30, Sort.Direction.ASC)).withSelfRel();
        var courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withRel("courseProgress");

        lessonProgressDTO.add(selfLink,courseProgressLink,selfAllLink);

        return ResponseEntity.ok(lessonProgressDTO);
    }
    @PostMapping("/{lessonProgressId}/complete")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Complete lesson", description = "Complete lesson")
    public ResponseEntity<Void> completeLesson(@PathVariable Long enrollmentId,
                                               @PathVariable Long courseProgressId,
                                               @PathVariable("lessonProgressId") Long lessonProgressId) {
        if (!SecurityUtils.isCurrentUserOrAdmin(lessonProgressService
                .getLessonProgressById(lessonProgressId)
                .getCourseProgress()
                .getEnrollment()
                .getUser()
                .getId())
        ) return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        if (lessonProgressService.getLessonProgressById(lessonProgressId).isCompleted()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        lessonProgressService.completeLesson(lessonProgressId);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
