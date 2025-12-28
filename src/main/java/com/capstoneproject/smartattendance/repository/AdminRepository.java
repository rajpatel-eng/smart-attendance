package com.capstoneproject.smartattendance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capstoneproject.smartattendance.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin,String> {

        Optional<Admin> findByEmail(String email); 
       
} 