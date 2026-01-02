package com.capstoneproject.smartattendance.entity;

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

    private String name;
    private String collegeName;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Academic> academicDatas;
}
