package com.student.student.repository;

import com.student.student.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentEntityRepository extends JpaRepository<StudentEntity, Long> {



    Optional<StudentEntity> findByUsername(String user);
}