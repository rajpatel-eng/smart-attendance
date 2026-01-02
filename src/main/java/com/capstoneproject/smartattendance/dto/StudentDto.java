package com.capstoneproject.smartattendance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto extends UserDto {
   
   @NotNull
   @NotBlank(message = "ALL_FIELD_REQUIRED")
   private String name;

   @NotNull
   @NotBlank(message = "ALL_FIELD_REQUIRED")
   private String collegeName;

   @NotNull
   @NotBlank(message = "ALL_FIELD_REQUIRED")
   private String departmentName;

   @NotNull
   @NotBlank(message = "ALL_FIELD_REQUIRED")
   private String enrollmentNo;

   @NotNull
   @NotBlank(message = "ALL_FIELD_REQUIRED")
   private String sem;

   @NotNull
   @Email(message = "ALL_FIELD_REQUIRED")
   @NotBlank(message = "ALL_FIELD_REQUIRED")
   private String email;

   @NotNull
   @NotBlank(message = "ALL_FIELD_REQUIRED")
   private String className;

   @NotNull
   @NotBlank(message = "ALL_FIELD_REQUIRED")
   private String batchName;

   private float attendance;
}
