package com.capstoneproject.smartattendance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstoneproject.smartattendance.entity.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin,String> {

        Optional<Admin> findByEmail(String email); 
       
} 