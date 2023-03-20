package com.example.coursej.builder;

import com.example.coursej.model.Course;
import com.example.coursej.model.Enrollment;
import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.model.User;

public class EnrollmentBuilder {
    private Course course;
    private User user;
    private CourseProgress courseProgress;

    public EnrollmentBuilder setCourse(Course course) {
        this.course = course;
        return this;
    }

    public EnrollmentBuilder setStudent(User user) {
        this.user = user;
        return this;
    }

    public EnrollmentBuilder setCourseProgress(CourseProgress courseProgress) {
        this.courseProgress = courseProgress;
        return this;
    }

    public Enrollment createEnrollment() {
        return new Enrollment(course, user, courseProgress);
    }
}