package com.capstoneproject.smartattendance.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstoneproject.smartattendance.entity.Teacher;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher,String> {

    Optional<Teacher> findByUserIdAndAdmin_UserId(String userId, String adminId);

    List<Teacher> findByAdminUserId(String adminId);

       
} 