package com.capstoneproject.smartattendance.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends User {
   private String name;
   private String collegeName;
   private String departmentName;
   private String enrollmentNo;
   private String sem;
   private String email;
   private String className;
   private String batchName;
   private float attendance;
   private String managedBy;
}
