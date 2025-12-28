package com.capstoneproject.smartattendance.dto;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String UserId;
    private String password;
    
    @Enumerated(EnumType.STRING) 
    private Role role;
    
}
