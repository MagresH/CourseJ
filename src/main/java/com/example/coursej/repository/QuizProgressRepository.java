package com.example.coursej.repository;

import com.example.coursej.model.progress.QuizProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface QuizProgressRepository extends JpaRepository<QuizProgress, Long> {
}
