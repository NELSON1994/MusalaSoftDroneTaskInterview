package com.musala.droneproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Medication implements Serializable {
    private static final long serialVersionUID = 2842598520185366295L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "medicationName", nullable = false)
    @NotNull
    @Size(min = 5,max = 50)
    private String medicationName;

    @Column(name = "medicationWeight", nullable = false)
    private int weight;

    @Column(name = "medicationCode", nullable = false)
    private String code;

    @Lob
    @Column(name = "medicationPicture")
    private byte[] photo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "drone_id")
    private Drone drone;

    public Medication(String medicationName, int weight, String code, byte[] photo, Drone drone) {
        this.medicationName = medicationName;
        this.weight = weight;
        this.code = code;
        this.photo = photo;
        this.drone = drone;
    }
}
