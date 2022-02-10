package com.musala.droneproject.repository;

import com.musala.droneproject.model.Drone;
import com.musala.droneproject.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByDrone(Drone drone);
}
