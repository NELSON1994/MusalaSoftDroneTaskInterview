package com.musala.droneproject.repository;

import com.musala.droneproject.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findByBatteryCapacityGreaterThanEqualAndState(int batteryCapacity,String state);
    Drone findDroneBySerialNumber(String serialNumber);
}
