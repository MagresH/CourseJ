package com.example.coursej.builder;

import com.example.coursej.model.Course;
import com.example.coursej.model.Lesson;

import java.util.List;

public class LessonBuilder {
    private String title;
    private String description;
    private String contentUrl;
    private Course course;


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


    public Lesson createLesson() {
        return new Lesson(title, description, contentUrl, course);
    }
}
