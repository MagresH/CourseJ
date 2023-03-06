package com.example.coursej.service;

import com.example.coursej.model.Teacher;
import com.example.coursej.repository.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository studentRepository) {
        this.teacherRepository = studentRepository;
    }

    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher addStudent(Teacher teacher){
        return teacherRepository.save(teacher);
    }

}
