package com.atalibdev.service;

import com.atalibdev.exception.BadRequestException;
import com.atalibdev.exception.StudentNotFoundException;
import com.atalibdev.model.Student;
import com.atalibdev.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<Student> students() {
        return studentRepository.findAll();
    }

    @Override
    public Student student(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with the given id: [%s] not found."
                        .formatted(id)));
    }

    @Override
    public Student registerStudent(Student student) {
        Boolean isStudentPresent =
                studentRepository.selectExistsEmail(student.getEmail());
        if (isStudentPresent) {
            throw new BadRequestException("Student with the given email: [%s] taken"
                    .formatted(student.getEmail()));
        }
       return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id))
            throw new StudentNotFoundException("Student with the given id: [%s] not found."
                    .formatted(id));
        studentRepository.deleteById(id);
    }

    @Override
    public Student updateStudent(Long id, Student update) {
        Student existingStudent = studentRepository
                .findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with the given id: [%s] not found."
                        .formatted(id)));
        // Copy properties from the partialStudent to the existingStudent, excluding null and specified fields.
        BeanUtils.copyProperties(update, existingStudent, getNullPropertiesName(update));
        return studentRepository.save(existingStudent);
    }

    // Utility method to get null property names from an object
    private String[] getNullPropertiesName(Object source) {
        final BeanWrapper wrapper = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] propertyDescriptors =
                wrapper.getPropertyDescriptors();
        return Arrays.stream(propertyDescriptors)
                .filter(pd -> wrapper.getPropertyValue(pd.getName()) == null)
                .map(java.beans.PropertyDescriptor::getName)
                .toArray(String[]::new);
    }
}
