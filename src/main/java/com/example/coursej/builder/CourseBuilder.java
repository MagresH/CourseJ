package com.example.coursej.builder;

import com.example.coursej.model.Course;
import com.example.coursej.model.Lesson;
import com.example.coursej.model.User;

import java.util.List;

public class CourseBuilder {
    private String title;
    private String description;
    private User teacher;
    private List<Lesson> lessons;

    public CourseBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public CourseBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CourseBuilder setTeacher(User teacher) {
        this.teacher = teacher;
        return this;
    }

    public CourseBuilder setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
        return this;
    }

    public Course createCourse() {
        return new Course(title, description, teacher, lessons);
    }
}