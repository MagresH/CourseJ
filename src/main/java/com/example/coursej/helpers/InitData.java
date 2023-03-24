package com.example.coursej.helpers;

import com.example.coursej.model.*;
import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.model.progress.LessonProgress;
import com.example.coursej.model.User;
import com.example.coursej.service.*;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class InitData {
    private static final int MOCKS = 10;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final CourseProgressService courseProgressService;
    private final LessonProgressService lessonProgressService;
    private final LessonService lessonService;
    private final UserService userService;

    @Bean
    public void mockData() {
        for (int i = 0; i < MOCKS; i++) {
            User student = User.builder()
                    .username("username" + i)
                    .password(new BCryptPasswordEncoder().encode("password" + i))
                    .email("email" + i*i + 5311 + "@example.com")
                    .password("email" + i + "@example.com")
                    .firstName("First" + i)
                    .lastName("Last" + i)
                    .phoneNumber("123-456-789" + i)
                    .role(UserRole.STUDENT)
                    .build();
            userService.addUser(student);

            User teacher = User.builder()
                    .username("username" + i*i + 1 )
                    .password(new BCryptPasswordEncoder().encode("password" + i))
                    .email("email" + i*i + 11 + "@example.com")
                    .firstName("First" + i)
                    .lastName("Last" + i)
                    .phoneNumber("123-456-789" + i + 1)
                    .role(UserRole.TEACHER)
                    .build();
            userService.addUser(teacher);


            Course course = Course.builder()
                    .description("Description" + i)
                    .user(teacher)
                    .title("title" + i)
                    .build();
            courseService.addCourse(course);
            List<Lesson> lessons = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Lesson lesson = Lesson.builder()
                        .course(course)
                        .description("description" + i * j + 1)
                        .contentUrl("data.com" + i * j + 1)
                        .title("title" + i * j + 1)
                        .build();
                lessonService.addLesson(lesson);
                lessons.add(lesson);
            }
            course.setLessons(lessons);
            courseService.updateCourse(course);

            Enrollment enrollment = Enrollment.builder()
                    .course(course)
                    .user(student)
                    .build();
            enrollmentService.addEnrollment(enrollment);
            CourseProgress courseProgress = CourseProgress.builder()
                    .enrollment(enrollment)
                    .build();
            courseProgressService.addCourseProgress(courseProgress);

            enrollment.setCourseProgress(courseProgress);
            enrollmentService.updateEnrollment(enrollment);

            List<LessonProgress> lessonProgresses = new ArrayList<>();
            for (int k = 0; k < 5; k++) {
                LessonProgress lessonProgress = LessonProgress.builder()
                        .courseProgress(courseProgress)
                        .lesson(lessons.get(k))
                        .build();
                lessonProgresses.add(lessonProgress);
                lessonProgressService.addLessonProgress(lessonProgress);
            }
            courseProgress.setLessonProgresses(lessonProgresses);
            courseProgressService.updateCourseProgress(courseProgress);

        }
    }
}
