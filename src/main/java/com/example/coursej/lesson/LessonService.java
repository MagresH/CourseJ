package com.example.coursej.lesson;

import com.example.coursej.helpers.SortOrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Lesson with id " + id + " does not exist"));
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
    public Page<Lesson> getFilteredAndSortedLessonsByCourseId(Long courseId, String titleFilter, String descriptionFilter, int page, int size, List<String> sortList, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(SortOrderUtil.createSortOrder(sortList, sortDirection)));

        return lessonRepository.getLessonsByCourseIdAndAndTitleLikeAndDescriptionLikeIgnoreCase(
                courseId,
                titleFilter,
                descriptionFilter,
                pageable);
    }

    public Lesson updateLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }
}
