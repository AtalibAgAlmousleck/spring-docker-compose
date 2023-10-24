package com.atalibdev.service;

import com.atalibdev.model.Student;

import java.util.List;

public interface StudentService {
    List<Student> students();
    Student student(Long id);
    Student registerStudent(Student student);
    void deleteStudent(Long id);
    Student updateStudent(Long id, Student update);
}
