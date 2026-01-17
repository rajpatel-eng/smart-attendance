package com.capstoneproject.smartattendance.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Value("${file.upload-dir}")
    private String uploadDir;

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
        return ResponseEntity.ok(Map.of("response", response));
    }

    // change image req
    public ResponseEntity<?> changeMyImageReqService(MultipartFile image, String studentId) throws IOException {
        if (image.isEmpty()) {
            throw new CustomeException(ErrorCode.ALL_FIELD_REQUIRED);
        }
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new CustomeException(ErrorCode.USER_NOT_FOUND));

        if (image.getSize() > 5 * 1024 * 1024) {
            throw new CustomeException(ErrorCode.FILE_SIZE_EXCEEDED);
        }

        String contentType = image.getContentType();
        if (contentType == null || !contentType.equals("image/png")) {
            throw new CustomeException(ErrorCode.INVALID_FILE_TYPE);
        }

        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new CustomeException(ErrorCode.INVALID_FILE_NAME);
        }
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        String prevFile = student.getNewImage();
        if (prevFile != null) {
            Path prevPath = uploadPath.resolve(prevFile).normalize();
            Files.deleteIfExists(prevPath);
        }
        // new image
        String fileName = UUID.randomUUID() + "_" + studentId + extension;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        student.setNewImage(fileName);
        studentRepo.save(student);

        return ResponseEntity.ok(Map.of("message", "IMAGE_CHANGE_REQUEST_SEND_TO_ADMIN"));
    }

    public ResponseEntity<?> deleteMyImageReqService(String studentId) throws IOException{
        
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new CustomeException(ErrorCode.USER_NOT_FOUND));

        if(student.getNewImage()==null){
            throw new CustomeException(ErrorCode.NO_REQUEST_FOUND);
        }

        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        String prevFile = student.getNewImage();
        Path prevPath = uploadPath.resolve(prevFile).normalize();
        Files.deleteIfExists(prevPath);

        return ResponseEntity.ok(Map.of("message", "IMAGE_CHANGE_REQUEST_DELETED"));
    }
    // scan qr code
    // match face

    // get all attendance
    public ResponseEntity<?> getMyAllAttendanceService(String studentId) {
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

        return ResponseEntity.ok(Map.of("response", response));
    }

}
