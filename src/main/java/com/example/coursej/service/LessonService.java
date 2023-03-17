package com.example.coursej.service;

import com.example.coursej.model.Lesson;
import com.example.coursej.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public Lesson addLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    public List<Lesson> getLessonsByCourseId(Long courseId) {
        return lessonRepository.getLessonsByCourseId(courseId);
    }
}
