package com.example.coursej.builder;

import com.example.coursej.model.Course;
import com.example.coursej.model.Enrollment;
import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.model.user.Student;

public class EnrollmentBuilder {
    private Course course;
    private Student student;
    private CourseProgress courseProgress;

    public EnrollmentBuilder setCourse(Course course) {
        this.course = course;
        return this;
    }

    public EnrollmentBuilder setStudent(Student student) {
        this.student = student;
        return this;
    }

    public EnrollmentBuilder setCourseProgress(CourseProgress courseProgress) {
        this.courseProgress = courseProgress;
        return this;
    }

    public Enrollment createEnrollment() {
        return new Enrollment(course, student, courseProgress);
    }
}
