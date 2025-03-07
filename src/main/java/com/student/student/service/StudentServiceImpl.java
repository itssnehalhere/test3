package com.student.student.service;

import com.student.student.dto.StudentDto;
import com.student.student.entity.StudentEntity;
import com.student.student.repository.StudentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
   StudentEntityRepository ser;
    @Autowired
    JWTService service;

    @Override
    public StudentDto addStudent(StudentDto dto) {
        StudentEntity entity=dtoToEntity(dto);
        StudentEntity en = ser.save(entity);
        StudentDto dt=entityToDto(en);
        return dt;


    }
    StudentEntity dtoToEntity(StudentDto dto){
        StudentEntity entity=new StudentEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setEmailId(dto.getEmailId());
        entity.setMobile(dto.getMobile());
        entity.setPassword(dto.getPassword());
        String password = BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt(10));
        entity.setPassword(password);
        return entity;
    }
    StudentDto entityToDto(StudentEntity entity){
        StudentDto dto=new StudentDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmailId(entity.getEmailId());
        dto.setMobile(entity.getMobile());
        dto.setPassword(entity.getPassword());
        dto.setUsername(entity.getUsername());
        return dto;
    }

    @Override
    public List<StudentDto> getStudents() {
        List<StudentEntity> all = ser.findAll();
        List<StudentDto> collect = all.stream().map(p -> entityToDto(p)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public String verifyLogin(StudentDto dto) {
        Optional<StudentEntity> opUser = ser.findByUsername(dto.getUsername());
        if(opUser.isPresent()){
            StudentEntity studentEntity = opUser.get();
            System.out.println("Entered Plain Password: " + dto.getPassword());
            System.out.println("Stored Hashed Password: " + studentEntity.getPassword());

            if(BCrypt.checkpw(dto.getPassword(),studentEntity.getPassword() )){
                String token = service.generateToken(studentEntity);
                return token;
            }
        }

        return null;
    }
}
