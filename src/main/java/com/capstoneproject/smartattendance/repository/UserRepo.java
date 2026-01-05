package com.capstoneproject.smartattendance.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstoneproject.smartattendance.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,String> {

    Optional<User> findByUserId(String userId);

} 