package com.student.student.controller;

import com.student.student.dto.JWTdto;
import com.student.student.service.StudentService;
import com.student.student.dto.StudentDto;
import com.student.student.entity.StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
public class StudentController {
    @Autowired
    StudentService ss;


    //https://localhost:8080/api/v1/student
    @PostMapping
    public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto dto){
        StudentDto studentDto = ss.addStudent(dto);
        return new ResponseEntity<>(studentDto, HttpStatus.CREATED);

    }
    @PostMapping("/verifyLogin")
    public ResponseEntity<?> verifyLogin(@RequestBody StudentDto dto){
        String token = ss.verifyLogin(dto);
        if(token!=null){
            JWTdto jwtdto=new JWTdto();
            jwtdto.setToken(token);
            jwtdto.setTokenType("jwt token");
            return new ResponseEntity<>(jwtdto, HttpStatus.OK);

        }
        return new ResponseEntity<>("invalid token",HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getStudents(){
        List<StudentDto> student = ss.getStudents();
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
    @PostMapping("/msg")
    public String getMessage(){
        return "hello";
    }


}
