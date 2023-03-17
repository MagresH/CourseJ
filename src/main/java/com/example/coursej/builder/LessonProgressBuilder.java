package com.example.coursej.builder;

import com.example.coursej.model.Lesson;
import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.model.progress.LessonProgress;


public class LessonProgressBuilder {
    private Boolean completed;
    private Lesson lesson;
    private CourseProgress courseProgress;

    public LessonProgressBuilder setCompleted(Boolean completed) {
        this.completed = completed;
        return this;
    }

    public LessonProgressBuilder setLesson(Lesson lesson) {
        this.lesson = lesson;
        return this;
    }

    public LessonProgressBuilder setCourseProgress(CourseProgress courseProgress) {
        this.courseProgress = courseProgress;
        return this;
    }


    public LessonProgress createLessonProgress() {
        return new LessonProgress(completed, lesson, courseProgress);
    }
}
