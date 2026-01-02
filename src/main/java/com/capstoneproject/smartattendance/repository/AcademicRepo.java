package com.capstoneproject.smartattendance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstoneproject.smartattendance.entity.Academic;

import jakarta.transaction.Transactional;

public interface AcademicRepo extends JpaRepository<Academic, Long> {

    @Transactional
    void deleteByAdminUserId(String adminUserId);
    List<Academic> findByAdminUserId(String adminId);
}
