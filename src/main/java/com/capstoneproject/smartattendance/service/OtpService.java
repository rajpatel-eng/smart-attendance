package com.capstoneproject.smartattendance.service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.capstoneproject.smartattendance.dto.OtpDto;

@Service
public class OtpService {

    @Autowired
    MailService mailService;


    private final Map<String, OtpDto> otpStore = new ConcurrentHashMap<>();
  
    public ResponseEntity<?> createOtp(String email) {
        
            long expiresTime = Instant.now().plusSeconds(2*60).toEpochMilli(); // 2 min
            int code = ThreadLocalRandom.current().nextInt(100000, 1_000_000);
            String otp = String.valueOf(code);

            try {
                ResponseEntity<?> response =  mailService.sendOtpOnMail(email, otp);
                otpStore.put(email, new OtpDto(otp, expiresTime));
                return response;

            } catch(Exception e){
                return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
            }
    }


    public  ResponseEntity<?> verifyOtp(String email, String otp) {
            OtpDto otpDto = otpStore.get(email);

            if(otpDto == null){
                return ResponseEntity.badRequest().body(Map.of("error","NO_OTP_RECORD"));
            }

            if (Instant.now().toEpochMilli() > otpDto.getExpireTime()) {
                otpStore.remove(email);
                return ResponseEntity.badRequest().body(Map.of("error", "OTP_EXPIRED"));
            }
            if (!otpDto.getOtp().equals(otp)) {
                return ResponseEntity.badRequest().body(Map.of("error", "INVALID_OTP"));
            }
            otpStore.remove(email);
            return ResponseEntity.ok(Map.of("message", "OTP_VERIFIED"));
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // every 5 minutes
    public void cleanExpiredOtps() {
        long now = System.currentTimeMillis();

        otpStore.entrySet().removeIf(entry ->
                entry.getValue().getExpireTime() < now
        );
}

    
}
