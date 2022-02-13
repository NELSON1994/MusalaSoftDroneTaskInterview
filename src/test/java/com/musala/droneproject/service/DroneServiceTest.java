package com.musala.droneproject.service;

import com.musala.droneproject.controller.DispatchController;
import com.musala.droneproject.dao.DroneDao;
import com.musala.droneproject.enums.Model;
import com.musala.droneproject.enums.State;
import com.musala.droneproject.model.Drone;
import com.musala.droneproject.repository.DroneRepository;
import com.musala.droneproject.wrapper.DroneBatteryLevelWrapper;
import com.musala.droneproject.wrapper.GeneralResponseWrapper;
import org.hibernate.annotations.NotFound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DroneServiceTest {
    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DroneService droneService;

    Drone drone = new Drone(1L,"DRONE1234567890MUSALA", Model.Cruiserweight.name(), 280, 69, State.IDLE.name());


    @Test
    void registerDrone() {
        when(droneRepository.save(any(Drone.class))).thenReturn(drone);
        DroneDao droneDao=new DroneDao();
        droneDao.setSerialNumber(drone.getSerialNumber());
        droneDao.setState(drone.getState());
        droneDao.setBatteryCapacity(drone.getBatteryCapacity());
        droneDao.setWeight(drone.getWeight());
        GeneralResponseWrapper generalResponseWrapper=droneService.registerDrone(droneDao);
        Drone d= (Drone) generalResponseWrapper.getData();
        assertNotNull(generalResponseWrapper);
        assertEquals(HttpStatus.CREATED.value(),generalResponseWrapper.getResponseCode());
        assertNotNull(((Drone) generalResponseWrapper.getData()).getModel());
        assertEquals(d.getModel(),Model.Cruiserweight.name());
        assertEquals(generalResponseWrapper.getMessage(),"Drone Registered Successfully");
    }

    @Test
    void listOfDronesForLoading() {
        List<Drone> drones=new ArrayList<>();
        drones.add(drone);
        when(droneRepository.findByBatteryCapacityGreaterThanEqualAndState(25,State.IDLE.name())).thenReturn(drones);
        GeneralResponseWrapper generalResponseWrapper=droneService.listOfDronesForLoading();
        List<Drone> list= (List<Drone>) generalResponseWrapper.getData();
        assertEquals(list.size(),1);
        assertNotNull(list);
        assertEquals(HttpStatus.OK.value(),generalResponseWrapper.getResponseCode());
        assertEquals(generalResponseWrapper.getMessage(),"Drones fetched Successfully");
    }

    @Test
    void findDroneBatteryLevel() {
        when(droneRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(drone));
        GeneralResponseWrapper generalResponseWrapper=droneService.findDroneBatteryLevel(1L);
        assertEquals(HttpStatus.OK.value(),generalResponseWrapper.getResponseCode());
        assertEquals(generalResponseWrapper.getMessage(),"Drone Battery Level fetched Successfully");
        DroneBatteryLevelWrapper batteryLevelWrapper= (DroneBatteryLevelWrapper) generalResponseWrapper.getData();
        assertNotNull(batteryLevelWrapper);
        assertEquals(batteryLevelWrapper.getBatteryLevel(),drone.getBatteryCapacity()+"%");
    }

    @Test
    void findDroneByID() {
        when(droneRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(drone));
        Drone drone=droneService.findDroneByID(1L);
        assertEquals(drone.getSerialNumber(),"DRONE1234567890MUSALA");
        assertNotNull(drone);

    }

//    @Test
//    void findByDroneIDThatDoesNotExist(){
//        when(droneRepository.findById(2L)).thenReturn();
//        GeneralResponseWrapper generalResponseWrapper=droneService.findDroneBatteryLevel(2L);
//        assertEquals(HttpStatus.NOT_FOUND.value(),generalResponseWrapper.getResponseCode());
//        assertNull(generalResponseWrapper.getData());
//    }

    @Test
    void findByDroneIDThatDoesNotExistButThrowsError500(){
        when(droneRepository.findById(2L)).thenThrow(NullPointerException.class);
        GeneralResponseWrapper generalResponseWrapper=droneService.findDroneBatteryLevel(2L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),generalResponseWrapper.getResponseCode());
        assertNull(generalResponseWrapper.getData());
    }

}