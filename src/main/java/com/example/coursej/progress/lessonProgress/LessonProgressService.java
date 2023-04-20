package com.example.coursej.progress.lessonProgress;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LessonProgressService {

    private final LessonProgressRepository lessonProgressRepository;

    @Autowired
    public LessonProgressService(LessonProgressRepository lessonProgressRepository) {
        this.lessonProgressRepository = lessonProgressRepository;
    }
    public Page<LessonProgress> getLessonsProgressesByCourseProgressId(Long courseProgressId, int page, int size , String sortDirection){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "id"));

        return lessonProgressRepository.getLessonProgressesByCourseProgressId(courseProgressId, pageable);
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

    public void completeLesson(Long id) {

        var lessonProgress = getLessonProgressById(id);

        lessonProgress.setCompleted(true);
        lessonProgress.setCompleteTimestamp(LocalDateTime.now());

        lessonProgressRepository.save(lessonProgress);
    }
}
