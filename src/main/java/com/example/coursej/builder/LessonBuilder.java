package com.example.coursej.builder;

import com.example.coursej.model.Course;
import com.example.coursej.model.Lesson;
import com.example.coursej.model.quiz.Quiz;

import java.util.List;

public class LessonBuilder {
    private String title;
    private String description;
    private String contentUrl;
    private Course course;
    private List<Quiz> quizzes;

    public LessonBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public LessonBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public LessonBuilder setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
        return this;
    }

    public LessonBuilder setCourse(Course course) {
        this.course = course;
        return this;
    }

    public LessonBuilder setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
        return this;
    }

    public Lesson createLesson() {
        return new Lesson(title, description, contentUrl, course, quizzes);
    }
}
