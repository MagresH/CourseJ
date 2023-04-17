package com.example.coursej.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Page<Course> getAllCourses(String titleFilter, int page, int size, List<String> sortList, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortDirection)));

        return courseRepository.findCoursesByTitleLikeIgnoreCase(titleFilter, pageable);
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Course with id " + id + " does not exist"));
    }

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    public Page<Course> getCourseByTeacherId(Long teacherId, String titleFilter, int page, int size, List<String> sortList, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(createSortOrder(sortList, sortDirection)));
        return courseRepository.getCoursesByUserIdAndTitleLikeIgnoreCase(teacherId, titleFilter, pageable);
    }
    private List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {

        List<Sort.Order> sorts = new ArrayList<>();

        Sort.Direction direction;

        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.ASC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }
}
