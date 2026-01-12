package com.capstoneproject.smartattendance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstoneproject.smartattendance.service.StudentService;

@RestController
@RequestMapping("api/teacher")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping("/my")
    public ResponseEntity<?> getMyDetails(Authentication authentication){
        String studentId = authentication.getName();
        return studentService.getMyDetailsService(studentId);
    }

    @GetMapping("/allattendance")
    public ResponseEntity<?> getMyAllAttendance(Authentication authentication){
        String studentId = authentication.getName();
        return studentService.getMyAllAttendanceService(studentId);
    }


}
