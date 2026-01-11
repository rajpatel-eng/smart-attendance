package com.capstoneproject.smartattendance.controller;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstoneproject.smartattendance.dto.UserDto;
import com.capstoneproject.smartattendance.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("/changepassword")
    public ResponseEntity<?> changePassword(UserDto userDto,Authentication authentication){
        String adminId = authentication.getName();
        return userService.changePasswordService(userDto,adminId);
    }
    
}



