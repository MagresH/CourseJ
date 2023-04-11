package com.example.coursej.progress.lessonProgress;

import com.example.coursej.config.SecurityUtils;
import com.example.coursej.lesson.LessonController;
import com.example.coursej.progress.courseProgress.CourseProgressController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
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

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get all lessons progresses by course progress id", description = "Get all lessons progresses by course progress id")
    public ResponseEntity<CollectionModel<LessonProgressDTO>> getLessonsProgressesByCourseProgressId(@PathVariable Long enrollmentId,
                                                                                                     @PathVariable Long courseProgressId) {
        if (!SecurityUtils.isCurrentUserOrAdmin(lessonProgressService
                .getLessonsProgressesByCourseProgressId(courseProgressId)
                .get(0)
                .getCourseProgress()
                .getEnrollment()
                .getUser()
                .getId())
        ) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        var lessonProgresses = lessonProgressService.getLessonsProgressesByCourseProgressId(courseProgressId);
        var lessonProgressDTOs = lessonProgressMapper.toDTOList(lessonProgresses)   ;

        lessonProgressDTOs.forEach(
                lessonProgressDTO -> {
                    var lessonLink = linkTo(methodOn(LessonController.class).getLessonById(courseProgressId, lessonProgressDTO.getLessonId())).withRel("lesson");
                    var courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withRel("courseProgress");
                    var selfLink = linkTo(methodOn(LessonProgressController.class).getLessonProgressById(enrollmentId,courseProgressId, lessonProgressDTO.getLessonId())).withSelfRel();

                    lessonProgressDTO.add(lessonLink,courseProgressLink, selfLink);
                });

    CollectionModel<LessonProgressDTO> collectionModel = CollectionModel.of(lessonProgressDTOs,
                linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withRel("courseProgress"),
                linkTo(methodOn(LessonProgressController.class).getLessonsProgressesByCourseProgressId(enrollmentId,courseProgressId)).withRel("lessonsProgresses")
                );


        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get lesson progress by id", description = "Get lesson progress by id")
    public ResponseEntity<LessonProgressDTO> getLessonProgressById(@PathVariable Long enrollmentId,
                                                                @PathVariable Long courseProgressId,
                                                                @PathVariable("id") Long id) {
        if (!SecurityUtils.isCurrentUserOrAdmin(lessonProgressService
                .getLessonProgressById(id)
                .getCourseProgress()
                .getEnrollment()
                .getUser()
                .getId())
        ) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        var lessonProgress = lessonProgressService.getLessonProgressById(id);
        var lessonProgressDTO = lessonProgressMapper.toDTO(lessonProgress);

        var selfLink = linkTo(methodOn(LessonProgressController.class).getLessonProgressById(enrollmentId,courseProgressId,id)).withSelfRel();
        var selfAllLink = linkTo(methodOn(LessonProgressController.class).getLessonsProgressesByCourseProgressId(enrollmentId,courseProgressId)).withSelfRel();
        var courseProgressLink = linkTo(methodOn(CourseProgressController.class).getCourseProgressById(enrollmentId, courseProgressId)).withRel("courseProgress");

        lessonProgressDTO.add(selfLink,courseProgressLink,selfAllLink);

        return ResponseEntity.ok(lessonProgressDTO);
    }
    @PostMapping("/{id}/complete")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Complete lesson", description = "Complete lesson")
    public ResponseEntity<Void> completeLesson(@PathVariable Long enrollmentId,
                                               @PathVariable Long courseProgressId,
                                               @PathVariable("id") Long id) {
        if (!SecurityUtils.isCurrentUserOrAdmin(lessonProgressService
                .getLessonProgressById(id)
                .getCourseProgress()
                .getEnrollment()
                .getUser()
                .getId())
        ) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        lessonProgressService.completeLesson(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
