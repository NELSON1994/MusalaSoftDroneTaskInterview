package com.musala.droneproject.dao;

import com.musala.droneproject.enums.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneDao {
    @Size(min=5, max=100)
    private String serialNumber;

    @Max(500)
    private int weight;

    @Max(100)
    private int batteryCapacity;

    private String state;
}
