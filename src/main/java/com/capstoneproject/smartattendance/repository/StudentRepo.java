package com.capstoneproject.smartattendance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstoneproject.smartattendance.entity.Student;

public interface StudentRepo extends JpaRepository<Student,String> {
    
}
