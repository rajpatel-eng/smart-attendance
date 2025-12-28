package com.capstoneproject.smartattendance.service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import com.capstoneproject.smartattendance.dto.AdminDto;
import com.capstoneproject.smartattendance.dto.Role;
import com.capstoneproject.smartattendance.dto.UserDto;
import com.capstoneproject.smartattendance.entity.Admin;
import com.capstoneproject.smartattendance.entity.User;
import com.capstoneproject.smartattendance.repository.AdminRepository;
import com.capstoneproject.smartattendance.repository.UserRepository;
import com.capstoneproject.smartattendance.security.JwtService;

@Service
public class AuthService {

    
    @Autowired
    OtpService otpService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;
    

    //admin register service
    public ResponseEntity<?> adminRegister(AdminDto adminDto){

            String userId = adminDto.getUserId();
            String email = adminDto.getEmail().toLowerCase();
            String name = adminDto.getName();
            String collegeName = adminDto.getCollegeName();
            String otp = adminDto.getOtp();
            String password =  adminDto.getPassword();
            String confirmPassword = adminDto.getConfirmPassword();
            adminDto.setRole(Role.ADMIN);
        
            
            if (userId == null || userId.isBlank() || email == null || email.isBlank() || name == null || name.isBlank()||
                collegeName == null || collegeName.isBlank()|| password == null || password.isBlank() ||otp==null || otp.isBlank()
            ){
                return ResponseEntity.badRequest().body(Map.of("error","ALL_FIELD_REQUIRED"));
            }
            if(!password.equals(confirmPassword)){
                return ResponseEntity.badRequest().body(Map.of("error","BOTH_PASSWORD_SHOULD_BE_SAME"));
            }
            if(adminRepository.findById(userId).isPresent()){
                return ResponseEntity.badRequest().body(Map.of("error","USERID_NOT_AVAILABLE"));
            }
            if(adminRepository.findByEmail(email).isPresent()){
                return ResponseEntity.badRequest().body(Map.of("error","EMAIL_NOT_AVAILABLE"));
            }

            ResponseEntity<?> otpResponse = otpService.verifyOtp(email, otp);
            if (!otpResponse.getStatusCode().is2xxSuccessful()) {
                return otpResponse;
            }
            
            adminDto.setPassword(passwordEncoder.encode(password));
            Admin admin = modelMapper.map(adminDto, Admin.class);
            adminRepository.save(admin);

            return ResponseEntity.ok(Map.of("message", "REGISTER_SUCCESSFULLY"));
    }

    // otp send service
    public ResponseEntity<?> sendOtpService(String email){
        try {
                if(email==null || email.isBlank()){
                    return ResponseEntity.badRequest().body(Map.of("error", "EMAIL_NOT_FOUND"));
                }
                return otpService.createOtp(email.toLowerCase());
            }catch(Exception e){
                return ResponseEntity.internalServerError().body(Map.of("error","An unexpected error occurred: " + e.getMessage()));
            }  
    }

    // all user login service
    public ResponseEntity<?> loginService(UserDto userDto,HttpServletResponse response) {
            
            String userId = userDto.getUserId();
            String password = userDto.getPassword();
            Role role = userDto.getRole();
            
            if(userId==null || userId.isBlank()||password== null || password.isBlank()){
                return ResponseEntity.badRequest().body(Map.of("error","ALL_FIELD_REQUIRED"));
            }

            Optional<User> optionalUser = userRepository.findByUserId(userId);
            
            if(!optionalUser.isPresent()){
                return ResponseEntity.badRequest().body(Map.of("error","USER_NOT_FOUND"));
            }

            User user = optionalUser.get();

            if (user.getRole()!=role) {
                return ResponseEntity.badRequest().body(Map.of("error","USER_NOT_FOUND"));
            }
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return ResponseEntity.badRequest().body(Map.of("error", "PASSWORD_NOT_MATCH"));
            }

            String token = jwtService.generateToken(user.getUserId(),user.getRole());

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", token);
            responseBody.put("username", user.getUserId());
            responseBody.put("role", user.getRole());
            

            Cookie jwtCookie = new Cookie("JWT", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60);
            jwtCookie.setSecure(false);
            response.addCookie(jwtCookie);

            return ResponseEntity.ok(responseBody);
    }

    // all user logout service
    public ResponseEntity<?> logoutService(HttpServletResponse response) {

            Cookie cookie = new Cookie("JWT", "");
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            cookie.setSecure(false);

            response.addCookie(cookie);

            return ResponseEntity.ok(Map.of("message", "LOGGED_OUT"));
    }

    // admin forgot password service
    public ResponseEntity<?> forgotPasswordService(AdminDto adminDto) {

            String userId = adminDto.getUserId();
            String email = adminDto.getEmail().toLowerCase();
            String otp = adminDto.getOtp();
            String password =  adminDto.getPassword();
            String confirmPassword = adminDto.getConfirmPassword();

            if (userId == null || userId.isBlank() || email == null || email.isBlank() 
                || password == null || password.isBlank()||otp==null || otp.isBlank()
            ){
                return ResponseEntity.badRequest().body(Map.of("error","ALL_FIELD_REQUIRED"));
            }
            if(!password.equals(confirmPassword)){
                return ResponseEntity.badRequest().body(Map.of("error","BOTH_PASSWORD_SHOULD_BE_SAME"));
            }
            
            Optional<Admin> optionalAdmin = adminRepository.findById(userId);

            if(!optionalAdmin.isPresent()){
                return ResponseEntity.badRequest().body(Map.of("error","USER_NOT_FOUND"));
            }
            
            Admin admin = optionalAdmin.get();

            if(!admin.getEmail().equals(adminDto.getEmail())){
                return ResponseEntity.badRequest().body(Map.of("error","EMAIL_NOT_MATCH"));
            }

            ResponseEntity<?> otpResponse = otpService.verifyOtp(email, otp);
            if (!otpResponse.getStatusCode().is2xxSuccessful()) {
                return otpResponse;
            }
            
            admin.setPassword(passwordEncoder.encode(password));
            adminRepository.save(admin);

            return ResponseEntity.ok(Map.of("message", "PASSWORD_CHANGE_SUCCESSFULLY"));
    }

}