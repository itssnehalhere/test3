package com.student.student.service;

import com.student.student.dto.StudentDto;
import com.student.student.entity.StudentEntity;

import java.util.List;

public interface StudentService {
    StudentDto addStudent(StudentDto dto);


    List<StudentDto> getStudents();

    String  verifyLogin(StudentDto dto);
}
