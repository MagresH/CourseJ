package com.example.coursej.service;

import com.example.coursej.model.progress.CourseProgress;
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
    private final CourseProgressService courseProgressService;

    @Autowired
    public LessonProgressService(LessonProgressRepository lessonProgressRepository, CourseProgressService courseProgressService) {
        this.lessonProgressRepository = lessonProgressRepository;
        this.courseProgressService = courseProgressService;
    }
    public List<LessonProgress> getLessonsProgressessByCourseProgressId(Long courseProgressId){
        List<LessonProgress> lessonProgresses= lessonProgressRepository.getLessonProgressesByCourseProgressId(courseProgressId);
        return lessonProgresses;
    }
    public LessonProgress addLessonProgress(LessonProgress lessonProgress, Long courseId, Long lessonId) {
        lessonProgressRepository.save(lessonProgress);
        return lessonProgress;
    }

    public LessonProgress getLessonProgressById(Long id) {
        return lessonProgressRepository.findById(id).get();
    }

    public void deleteLessonProgress(Long id) {
        lessonProgressRepository.deleteById(id);
    }

    public LessonProgress updateLessonProgress(Long id, LessonProgress updatedLessonProgress) {
        LessonProgress lessonProgress = getLessonProgressById(id);
      //  lessonProgress.set(updatedLessonProgress.getProgress());
        lessonProgressRepository.save(lessonProgress);
        return lessonProgress;
    }
}
