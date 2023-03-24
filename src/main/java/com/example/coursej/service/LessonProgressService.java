package com.example.coursej.service;

import com.example.coursej.model.progress.LessonProgress;
import com.example.coursej.repository.LessonProgressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LessonProgressService {

    private final LessonProgressRepository lessonProgressRepository;

    @Autowired
    public LessonProgressService(LessonProgressRepository lessonProgressRepository) {
        this.lessonProgressRepository = lessonProgressRepository;
    }
    public List<LessonProgress> getLessonsProgressesByCourseProgressId(Long courseProgressId){
        return lessonProgressRepository.getLessonProgressesByCourseProgressId(courseProgressId);
    }
    public LessonProgress addLessonProgress(LessonProgress lessonProgress) {
        lessonProgressRepository.save(lessonProgress);
        return lessonProgress;
    }

    public LessonProgress getLessonProgressById(Long id) {
        return lessonProgressRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("LessonProgress with id " + id + " does not exist"));
    }

    public void deleteLessonProgressById(Long id) {
        lessonProgressRepository.deleteById(id);
    }

    public LessonProgress updateLessonProgress(LessonProgress lessonProgress) {
        return lessonProgressRepository.save(lessonProgress);
    }
}
