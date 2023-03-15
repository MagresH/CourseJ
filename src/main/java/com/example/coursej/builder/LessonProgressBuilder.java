package com.example.coursej.builder;

import com.example.coursej.model.Lesson;
import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.model.progress.LessonProgress;
import com.example.coursej.model.quiz.QuizProgress;

import java.util.List;

public class LessonProgressBuilder {
    private Boolean completed;
    private Lesson lesson;
    private CourseProgress courseProgress;
    private List<QuizProgress> quizProgresses;

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

    public LessonProgressBuilder setQuizProgresses(List<QuizProgress> quizProgresses) {
        this.quizProgresses = quizProgresses;
        return this;
    }

    public LessonProgress createLessonProgress() {
        return new LessonProgress(completed, lesson, courseProgress, quizProgresses);
    }
}
