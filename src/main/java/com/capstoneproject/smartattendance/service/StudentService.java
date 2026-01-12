package com.capstoneproject.smartattendance.service;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capstoneproject.smartattendance.dto.StudentAttendanceResponseDto;
import com.capstoneproject.smartattendance.dto.StudentResponseDto;
import com.capstoneproject.smartattendance.entity.Academic;
import com.capstoneproject.smartattendance.entity.Attendance;
import com.capstoneproject.smartattendance.entity.AttendanceRecord;
import com.capstoneproject.smartattendance.entity.Student;
import com.capstoneproject.smartattendance.exception.CustomeException;
import com.capstoneproject.smartattendance.exception.ErrorCode;
import com.capstoneproject.smartattendance.repository.AttendanceRecordRepo;
import com.capstoneproject.smartattendance.repository.StudentRepo;

@Service
public class StudentService {
    // get my details
    @Autowired
    StudentRepo studentRepo;

    @Autowired 
    AttendanceRecordRepo attendanceRecordRepo;

    @Autowired
    ModelMapper modelMapper;
    
    public ResponseEntity<?> getMyDetailsService(String studentId) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new CustomeException(ErrorCode.USER_NOT_FOUND));
        StudentResponseDto response = modelMapper.map(student, StudentResponseDto.class);
        Academic academic = student.getAcademic();
        response.setYear(academic.getYear());
        response.setBranch(academic.getBranch());
        response.setSemester(academic.getSemester());
        response.setClassName(response.getClassName());
        response.setBatch(response.getBatch());
        return ResponseEntity.ok(Map.of("response",response));
    }
    // change image req

    // scan qr code
    // match face
    
    // get all attendance
    public ResponseEntity<?> getMyAllAttendanceService(String studentId){
        List<AttendanceRecord> attendanceRecords = attendanceRecordRepo.findByStudent_UserId(studentId);

        List<StudentAttendanceResponseDto> response = attendanceRecords
                                    .stream()
                                    .map(ar -> {
                                        Attendance attendance = ar.getAttendance();
                                        StudentAttendanceResponseDto sard = new StudentAttendanceResponseDto(); 
                                        sard.setAttendanceId(attendance.getAttendanceId());
                                        sard.setAttendanceDate(attendance.getAttendanceDate());
                                        sard.setAttendanceTime(attendance.getAttendanceTime());
                                        sard.setSubjectName(attendance.getSubjectName());
                                        sard.setStatus(ar.getStatus());
                                        return sard;
                                    })
                                    .toList();

        return ResponseEntity.ok(Map.of("response",response));
    }
    
}
