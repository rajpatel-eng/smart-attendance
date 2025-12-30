package com.capstoneproject.smartattendance.service;

import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstoneproject.smartattendance.dto.Role;
import com.capstoneproject.smartattendance.dto.StudentDto;
import com.capstoneproject.smartattendance.entity.Student;
import com.capstoneproject.smartattendance.exception.AuthException;
import com.capstoneproject.smartattendance.exception.ErrorCode;
import com.capstoneproject.smartattendance.repository.StudentRepository;
import com.capstoneproject.smartattendance.service.mail.AdminMailService;

@Service
public class AdminService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AdminMailService adminMailService;

    public ResponseEntity<?> addStudentService(StudentDto studentDto, String adminName) {
        String userId = studentDto.getUserId();
        String password = studentDto.getPassword();

        if (studentRepository.findById(userId).isPresent()) {
            throw new AuthException(ErrorCode.USERID_NOT_AVAILABLE);
        }

        Student student = modelMapper.map(studentDto, Student.class);
        student.setAttendance(0);
        student.setRole(Role.STUDENT);
        student.setManagedBy(adminName);
        student.setPassword(passwordEncoder.encode(password));

        adminMailService.sendStudentDetailsMail(studentDto, adminName,"created");
        studentRepository.save(student);
        return ResponseEntity.ok(Map.of("message", "STUDENT_ID_CREATED_SUCCESSFULLY"));
    }

    public ResponseEntity<?> updateStudentService(StudentDto studentDto,String adminName){
        String userId = studentDto.getUserId();
        String password = studentDto.getPassword();

        Student prevStudent = studentRepository.findById(userId).orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
        
        if(!prevStudent.getManagedBy().equals(adminName)){
            throw new AuthException(ErrorCode.YOU_CANT_UPDATE_THIS_ACCOUNT);
        }

        Student student = modelMapper.map(studentDto, Student.class);
        student.setAttendance(prevStudent.getAttendance());
        student.setRole(Role.STUDENT);
        student.setManagedBy(adminName);
        student.setPassword(passwordEncoder.encode(password));

        adminMailService.sendStudentDetailsMail(studentDto, adminName,"updated");
        studentRepository.save(student);
        return ResponseEntity.ok(Map.of("message", "STUDENT_ID_UPDATED_SUCCESSFULLY"));
    }




}
