package com.example.coursej.repository;

import com.example.coursej.model.user.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends UserRepository<Teacher> {
}
