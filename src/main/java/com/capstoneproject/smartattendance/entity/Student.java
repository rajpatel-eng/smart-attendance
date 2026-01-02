package com.capstoneproject.smartattendance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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
   @NotNull
   private String name;

   @NotNull
   private String collegeName;

   @NotNull
   private String departmentName;

   @NotNull
   private String enrollmentNo;

   @NotNull
   private String sem;

   @NotNull
   private String email;

   @NotNull
   private String className;

   @NotNull
   private String batchName;

   @NotNull
   private float attendance;

   @NotNull
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "admin_user_id", nullable = false)
   private Admin admin;
}
