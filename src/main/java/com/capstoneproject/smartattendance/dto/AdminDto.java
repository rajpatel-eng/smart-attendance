package com.capstoneproject.smartattendance.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto extends UserDto{

    private String name;
    private String email;
    private String collegeName;
    private String otp;
    private String confirmPassword;
    
}
