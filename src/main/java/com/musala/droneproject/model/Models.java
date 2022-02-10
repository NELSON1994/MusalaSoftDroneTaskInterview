package com.musala.droneproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Models {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 2, max = 100)
    private String modelName;

    @Max(500)
    private int maximumWeight;

    public Models(String modelName, int maximumWeight) {
        this.modelName = modelName;
        this.maximumWeight = maximumWeight;
    }
}
