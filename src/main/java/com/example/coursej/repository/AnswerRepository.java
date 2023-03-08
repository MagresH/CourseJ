package com.example.coursej.repository;

import com.example.coursej.model.quiz.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
