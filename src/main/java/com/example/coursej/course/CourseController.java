package com.example.coursej.course;

import com.example.coursej.config.SecurityUtils;
import com.example.coursej.lesson.LessonController;
import com.example.coursej.user.UserController;
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

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "CourseController", description = "APIs for managing course resources")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/courses")
    @Operation(summary = "Get all courses", description = "Get all courses")
    public ResponseEntity<CollectionModel<CourseDTO>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        List<CourseDTO> courseDTOs = courseMapper.toDTOList(courses);

        courseDTOs.forEach(course -> {
            Link lessonsLink = WebMvcLinkBuilder.linkTo(methodOn(LessonController.class).getLessonsByCourseId(course.getId())).withRel("lessons");
            Link userLink = linkTo(methodOn(UserController.class).getUserById(course.getUserId())).withRel("teacher");
            Link selfLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(course.getId())).withSelfRel();

            course.add(lessonsLink, userLink, selfLink);
        });

        Link selfAllLink = linkTo(CourseController.class).withSelfRel();

        CollectionModel<CourseDTO> result = CollectionModel.of(courseDTOs, selfAllLink);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/courses/{id}")
    @Operation(summary = "Get course by id", description = "Get course by id")
    public ResponseEntity<CourseDTO> getCourseByCourseId(@PathVariable("id") Long id) {
        Course course = courseService.getCourseById(id);
        CourseDTO courseDTO = courseMapper.toDTO(course);

        Link lessonsLink = linkTo(methodOn(LessonController.class).getLessonsByCourseId(id)).withRel("lessons");
        Link userLink = linkTo(methodOn(UserController.class).getUserById(course.getUser().getId())).withRel("teacher");
        Link selfLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(course.getId())).withSelfRel();
        Link selfAllLink = linkTo(CourseController.class).withSelfRel();

        courseDTO.add(lessonsLink, userLink, selfLink, selfAllLink);

        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("users/{userId}/courses")
    @Operation(summary = "Get courses by user id", description = "Get courses by user id")
    public ResponseEntity<CollectionModel<CourseDTO>> getCoursesByUserId(@PathVariable("userId") Long userId) {
        if (!SecurityUtils.isCurrentUserOrAdmin(userId) || !SecurityUtils.isAdmin(userId))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else {
            List<Course> courses = courseService.getCourseByTeacherId(userId);
            List<CourseDTO> courseDTOs = courseMapper.toDTOList(courses);

            courseDTOs.forEach(course -> {
                Link selfLink = linkTo(methodOn(CourseController.class).getCourseByCourseId(course.getId())).withSelfRel();
                Link userLink = linkTo(methodOn(UserController.class).getUserById(course.getUserId())).withRel("teacher");

                course.add(userLink, selfLink);
            });

            Link selfAllLink = linkTo(CourseController.class).withSelfRel();

            CollectionModel<CourseDTO> result = CollectionModel.of(courseDTOs, selfAllLink);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/courses")
    @Operation(summary = "Add course", description = "Add course")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);
        courseService.addCourse(course);

        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/courses/{courseId}")
    @Operation(summary = "Update course", description = "Update course")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable("courseId") Long courseId, @RequestBody CourseDTO courseDTO) {

        if (!SecurityUtils.isCurrentUserOrAdmin(courseDTO.getUserId()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else if (!courseId.equals(courseDTO.getId()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else {
            Course course = courseMapper.toEntity(courseDTO);
            courseService.updateCourse(course);
            return new ResponseEntity<>(courseDTO, HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/courses/{courseId}")
    @Operation(summary = "Delete course", description = "Delete course")
    public ResponseEntity<CourseDTO> deleteCourse(@PathVariable("courseId") Long courseId) {

        if (!SecurityUtils.isCurrentUserOrAdmin(courseService.getCourseById(courseId).getUser().getId()))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        else {
            courseService.deleteCourseById(courseId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
}
