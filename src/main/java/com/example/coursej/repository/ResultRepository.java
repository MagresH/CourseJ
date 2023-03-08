package com.example.coursej.repository;

import com.example.coursej.model.quiz.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ResultRepository extends JpaRepository<Result, Long> {
}
