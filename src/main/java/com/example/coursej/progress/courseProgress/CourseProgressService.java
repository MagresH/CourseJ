package com.example.coursej.progress.courseProgress;

import com.example.coursej.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class CourseProgressService {

    private final CourseProgressRepository courseProgressRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseProgressService(CourseProgressRepository courseProgressRepository,
                                 UserRepository userRepository) {
        this.courseProgressRepository = courseProgressRepository;

        this.userRepository = userRepository;
    }

    public CourseProgress addCourseProgress(CourseProgress courseProgress) {
        return courseProgressRepository.save(courseProgress);
    }

    public CourseProgress updateCourseProgress(CourseProgress courseProgress) {
        return courseProgressRepository.save(courseProgress);
    }

    public CourseProgress getCourseProgressById(Long id) {
        return courseProgressRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("CourseProgress with id " + id + " does not exist"));
    }

    public void deleteCourseProgressById(Long id) {
        courseProgressRepository.deleteById(id);
    }

    public void completeCourseProgress(Long courseProgressId) {
        var courseProgress = getCourseProgressById(courseProgressId);
        courseProgress.setCompleted(true);
        courseProgress.setCompleteTimestamp(LocalDateTime.now());
        courseProgressRepository.save(courseProgress);
    }
}
