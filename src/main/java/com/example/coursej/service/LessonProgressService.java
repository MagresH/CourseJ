package com.example.coursej.service;

import com.example.coursej.model.progress.CourseProgress;
import com.example.coursej.model.progress.LessonProgress;
import com.example.coursej.repository.LessonProgressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public LessonProgress addLessonProgress(LessonProgress lessonProgress, Long courseId, Long lessonId) {
        CourseProgress courseProgress = courseProgressService.getCourseProgressById(courseId);
        if (courseProgress == null) {
            //throw new ResourceNotFoundException("Course progress not found for course with id " + courseId);
        }
        lessonProgress.setCourseProgress(courseProgress);
      //  lessonProgress.setLessonId(lessonId);
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
