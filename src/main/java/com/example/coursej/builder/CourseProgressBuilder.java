package com.example.coursej.builder;

import com.example.coursej.model.Enrollment;
import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.model.progress.LessonProgress;

import java.util.List;

public class CourseProgressBuilder {
    private Boolean completed;
    private Enrollment enrollment;
    private List<LessonProgress> lessonProgresses;

    public CourseProgressBuilder setCompleted(Boolean completed) {
        this.completed = completed;
        return this;
    }

    public CourseProgressBuilder setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
        return this;
    }

    public CourseProgressBuilder setLessonProgresses(List<LessonProgress> lessonProgresses) {
        this.lessonProgresses = lessonProgresses;
        return this;
    }

    public CourseProgress createCourseProgress() {
        return new CourseProgress(completed, enrollment, lessonProgresses);
    }
}
