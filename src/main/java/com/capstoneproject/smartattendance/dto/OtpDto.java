package com.capstoneproject.smartattendance.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OtpDto {

    private String otp;
    private Long expireTime;
    
}
