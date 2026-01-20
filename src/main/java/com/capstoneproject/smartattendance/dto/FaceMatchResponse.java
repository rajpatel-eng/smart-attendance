package com.capstoneproject.smartattendance.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FaceMatchResponse {

    private double similarity;

    @JsonProperty("same_person")
    private boolean same_person;
}

