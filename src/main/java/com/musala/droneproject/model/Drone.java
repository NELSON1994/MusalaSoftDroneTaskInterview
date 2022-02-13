package com.musala.droneproject.model;

import com.musala.droneproject.enums.Model;
import com.musala.droneproject.enums.State;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Drone implements Serializable {
    private static final long serialVersionUID = 2842598520185366295L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min=5, max=100)
    private String serialNumber;

    private String model;

    @Max(500)
    private int weight;

    @Max(100)
    private int batteryCapacity;

    private String state;

    public Drone(String serialNumber, String model, int weight, int batteryCapacity, String state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weight = weight;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }
}
