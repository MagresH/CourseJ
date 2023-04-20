package com.example.coursej.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where c.id = ?1 and upper(c.title) like CONCAT('%',UPPER(?2),'%')")
    Page<Course> getCoursesByUserIdAndTitleLikeIgnoreCase(Long userId, String titleFilter, Pageable pageable);

    @Query("select c from Course c where upper(c.title) like CONCAT('%',UPPER(?1),'%')")
    Page<Course> findCoursesByTitleLikeIgnoreCase(String titleFilter, Pageable pageable);
}
