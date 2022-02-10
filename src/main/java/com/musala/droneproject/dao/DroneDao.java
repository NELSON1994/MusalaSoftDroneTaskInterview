package com.musala.droneproject.dao;

import com.musala.droneproject.enums.State;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
public class DroneDao {
    @Size(min=5, max=100)
    private String serialNumber;

    @Max(500)
    private int weight;

    @Max(100)
    private int batteryCapacity;

    private String state;
}
