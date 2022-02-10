package com.musala.droneproject.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class MedicationDao {
    @NotNull
    private String medicationName;
    @Max(500)
    private int weight;
}
