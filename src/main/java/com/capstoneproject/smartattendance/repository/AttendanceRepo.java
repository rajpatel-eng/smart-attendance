package com.capstoneproject.smartattendance.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstoneproject.smartattendance.entity.Attendance;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance,UUID> {

    
}