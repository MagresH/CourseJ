package com.example.coursej.helpers;

import com.example.coursej.builder.*;
import com.example.coursej.model.*;
import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.model.progress.LessonProgress;
import com.example.coursej.model.user.Student;
import com.example.coursej.model.user.Teacher;
import com.example.coursej.service.*;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitData {
    private final int MOCKS = 10;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;
    private final CourseProgressService courseProgressService;
    private final LessonProgressService lessonProgressService;
    private final TeacherService teacherService;
    private final LessonService lessonService;

    public InitData(StudentService studentService, EnrollmentService enrollmentService, CourseService courseService, CourseProgressService courseProgressService, LessonProgressService lessonProgressService, TeacherService teacherService, LessonService lessonService) {
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
        this.courseService = courseService;
        this.courseProgressService = courseProgressService;
        this.lessonProgressService = lessonProgressService;
        this.teacherService = teacherService;
        this.lessonService = lessonService;
        mockData();
    }


    public void mockData() {

        for (int i = 0; i < MOCKS; i++) {
//            Student student = new StudentBuilder()
//                    .setUsername("username" + i)
//                    .setPassword("password" + i)
//                    .setEmail("email" + i + "@example.com")
//                    .setFirstName("First" + i)
//                    .setLastName("Last" + i)
//                    .setPhone_number("123-456-789" + 1)
//                    .createStudent();
            Student student = new Student("username" + i, "password" + i, "email" + i + "@example.com",
                    "First" + i, "Last" + i, "123-456-789" + i);
            studentService.addStudent(student);

            Teacher teacher = new TeacherBuilder()
                    .setEmail("email" + i)
                    .setFirstName("firstName" + i)
                    .setLastName("lastName" + i)
                    .setUsername("username" + i)
                    .setPassword("password")
                    .setPhone_number("123-456-789" + i)
                    .createTeacher();
            teacherService.addTeacher(teacher);

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
                        .setDescription("description" + i * j +1)
                        .setContentUrl("data.com" + i * j + 1)
                        .setTitle("title" + i * j +1)
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
            enrollmentService.updateEnrollment(enrollment.getId(), enrollment);





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
