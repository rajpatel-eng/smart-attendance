package com.capstoneproject.smartattendance.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcademicDto {
    private String branch;   
    private String semester; 
    private String className; 
    private String batch;
}
