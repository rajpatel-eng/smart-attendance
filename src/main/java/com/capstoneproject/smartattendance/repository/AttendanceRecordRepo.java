package com.capstoneproject.smartattendance.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstoneproject.smartattendance.entity.AttendanceRecord;

@Repository
public interface AttendanceRecordRepo extends JpaRepository<AttendanceRecord,Long> {

    
}
