package com.capstoneproject.smartattendance.service;



import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;



@Service
public class MailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;
        
    public ResponseEntity<?> sendOtpOnMail(String email, String otp) {
            try {
                if (mailSender == null) {
                    return ResponseEntity.internalServerError().body(Map.of("error","OTP_NOT_SEND"));
                }

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject("Your One-Time Password (OTP) for Account Verification");
                message.setText(
                        "Dear User,\n\n" +
                        "Your One-Time Password (OTP) for account verification is: " + otp + "\n\n" +
                        "This OTP is valid for the next 2 minute.\n" +
                        "If you did not request this, please ignore this email.\n\n" +
                        "Best regards,\n" +
                        "Smart Attendance Team"
                );

                mailSender.send(message);
                return ResponseEntity.ok(Map.of("message", "OTP_SENT"));
                
            } catch (Exception e) {
                return ResponseEntity.internalServerError().body(Map.of("error","OTP_NOT_SEND" + e.getMessage()));
            }
        }
}
