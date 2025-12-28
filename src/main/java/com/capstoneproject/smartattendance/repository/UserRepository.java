package com.capstoneproject.smartattendance.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.capstoneproject.smartattendance.entity.User;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUserId(String userId);

} 