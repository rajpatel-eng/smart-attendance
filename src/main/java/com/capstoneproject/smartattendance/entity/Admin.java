package com.capstoneproject.smartattendance.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends User {
    
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String collegeName;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Academic> academicDatas = new ArrayList<>();

    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Teacher> teachers = new ArrayList<>();
}
