package com.capstoneproject.smartattendance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto extends UserDto {
   private String name;
   private String collegeName;
   private String departmentName;
   private String enrollmentNo;
   private String sem;
   private String email;
   private String className;
   private String batchName;
   private float attendance;
}
