package com.example.coursej.lesson;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> getLessonsByCourseId(Long id);

    @Query("select l from Lesson l where l.course.id = ?1 and (upper(l.title) like CONCAT('%',UPPER(?2),'%') or upper(l.description) like CONCAT('%',UPPER(?3),'%'))")
    Page<Lesson> getLessonsByCourseIdAndAndTitleLikeAndDescriptionLikeIgnoreCase(Long courseId, String titleFilter, String descriptionFilter, Pageable pageable);

}
