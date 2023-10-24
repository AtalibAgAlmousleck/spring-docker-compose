package com.atalibdev.controller;

import com.atalibdev.model.Student;
import com.atalibdev.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentRestApi {
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> students() {
        List<Student> studentList = studentService.students();
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> student(@PathVariable("id") Long id) {
        Student student = studentService.student(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Student> register(
            @Valid @RequestBody Student student) {
        Student registerStudent = studentService.registerStudent(student);
        return new ResponseEntity<>(registerStudent, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
      studentService.deleteStudent(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable("id") Long id,
            @RequestBody Student student) {
        Student updateStudent = studentService.updateStudent(id, student);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }
}
