package com.example.coursej.helpers;

import com.example.coursej.course.Course;
import com.example.coursej.course.CourseService;
import com.example.coursej.enrollment.Enrollment;
import com.example.coursej.enrollment.EnrollmentService;
import com.example.coursej.lesson.Lesson;
import com.example.coursej.lesson.LessonService;
import com.example.coursej.progress.courseProgress.CourseProgress;
import com.example.coursej.progress.courseProgress.CourseProgressService;
import com.example.coursej.progress.lessonProgress.LessonProgress;
import com.example.coursej.progress.lessonProgress.LessonProgressService;
import com.example.coursej.user.User;
import com.example.coursej.user.UserRole;
import com.example.coursej.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
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
        User admin = User.builder()
                .username("admin")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .email("admin@sample.com")
                .firstName("Admin")
                .lastName("Admin")
                .phoneNumber("123-456-789")
                .role(UserRole.ADMIN)
                .build();
        userService.addUser(admin);
        for (int i = 0; i < MOCKS; i++) {
            User student = User.builder()
                    .username("username" + i)
                    .password(new BCryptPasswordEncoder().encode("password" + i))
                    .email("email" + i * i + 5311 + "@example.com")
                    .firstName("First" + i)
                    .lastName("Last" + i)
                    .phoneNumber("123-456-789" + i)
                    .role(UserRole.USER)
                    .build();
            userService.addUser(student);

            User teacher = User.builder()
                    .username("username" + i * i + 1)
                    .password(new BCryptPasswordEncoder().encode("password" + i))
                    .email("email" + i * i + 11 + "@example.com")
                    .firstName("First" + i)
                    .lastName("Last" + i)
                    .phoneNumber("123-456-789" + i + 1)
                    .role(UserRole.USER)
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
