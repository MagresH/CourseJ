package com.example.coursej.helpers;

import com.example.coursej.builder.*;
import com.example.coursej.model.*;
import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.model.progress.LessonProgress;
import com.example.coursej.model.User;
import com.example.coursej.service.*;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitData {
    private static final int MOCKS = 10;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final CourseProgressService courseProgressService;
    private final LessonProgressService lessonProgressService;
    private final LessonService lessonService;

    private final UserService userService;

    public InitData(EnrollmentService enrollmentService, CourseService courseService, CourseProgressService courseProgressService, LessonProgressService lessonProgressService, LessonService lessonService, UserService userService) {
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.courseProgressService = courseProgressService;
        this.lessonProgressService = lessonProgressService;
        this.lessonService = lessonService;
        this.userService = userService;
        mockData();
    }

    public void mockData() {
        for (int i = 0; i < MOCKS; i++) {
            User student = new UserBuilder()
                    .setUsername("username" + i)
                    .setPassword("password" + i)
                    .setEmail("email" + i + "@example.com")
                    .setFirstName("First" + i)
                    .setLastName("Last" + i)
                    .setPhoneNumber("123-456-789" + i)
                    .setRole(UserRole.STUDENT)
                    .createUser();
            userService.addUser(student);

            User teacher = new UserBuilder()
                    .setUsername("username" + i*i + 1 )
                    .setPassword("password" + i)
                    .setEmail("email" + i*i + 1 + "@example.com")
                    .setFirstName("First" + i)
                    .setLastName("Last" + i)
                    .setPhoneNumber("123-456-789" + i + 1)
                    .setRole(UserRole.TEACHER)
                    .createUser();
            userService.addUser(teacher);

            Course course = new CourseBuilder()
                    .setDescription("Description" + i)
                    .setTeacher(teacher)
                    .setTitle("title" + i)
                    .createCourse();
            courseService.addCourse(course);
            List<Lesson> lessons = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Lesson lesson = new LessonBuilder()
                        .setCourse(course)
                        .setDescription("description" + i * j + 1)
                        .setContentUrl("data.com" + i * j + 1)
                        .setTitle("title" + i * j + 1)
                        .createLesson();
                lessonService.addLesson(lesson);
                lessons.add(lesson);
            }
            course.setLessons(lessons);
            courseService.updateCourse(course);

            Enrollment enrollment = new EnrollmentBuilder()
                    .setCourse(course)
                    .setStudent(student)
                    .createEnrollment();
            enrollmentService.addEnrollment(enrollment);
            CourseProgress courseProgress = new CourseProgressBuilder()
                    .setCompleted(false)
                    .setEnrollment(enrollment)
                    .createCourseProgress();
            courseProgressService.addCourseProgress(courseProgress);

            enrollment.setCourseProgress(courseProgress);
            enrollmentService.updateEnrollment(enrollment);

            List<LessonProgress> lessonProgresses = new ArrayList<>();
            for (int k = 0; k < 5; k++) {
                LessonProgress lessonProgress = new LessonProgressBuilder()
                        .setCourseProgress(courseProgress)
                        .setLesson(lessons.get(k))
                        .setCompleted(false)
                        .createLessonProgress();
                lessonProgresses.add(lessonProgress);
            }
            courseProgress.setLessonProgresses(lessonProgresses);
            courseProgressService.updateCourseProgress(courseProgress);

        }
    }
}
