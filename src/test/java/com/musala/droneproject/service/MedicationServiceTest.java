package com.musala.droneproject.service;

import com.musala.droneproject.enums.Model;
import com.musala.droneproject.enums.State;
import com.musala.droneproject.model.Drone;
import com.musala.droneproject.model.Medication;
import com.musala.droneproject.model.Models;
import com.musala.droneproject.repository.MedicationRepository;
import com.musala.droneproject.repository.ModelsRepository;
import com.musala.droneproject.wrapper.GeneralResponseWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicationServiceTest {
    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private MedicationService medicationService;

    @Mock
    private DroneService droneService;

    @Mock
    private ModelsRepository modelsRepository;

    Drone drone = new Drone(1L, "DRONE1234567890MUSALA", Model.Cruiserweight.name(), 280, 69, State.IDLE.name());
    Medication medication = new Medication(1, "MEDICATION4567", 290, "", null, drone);
    Models models=new Models(Model.Cruiserweight.name(),399);

    @Test
    void loadDrone() {
        String medicationCode = medicationService.generateCode(medication.getMedicationName(), drone.getSerialNumber());
        when(droneService.findDroneByID(any())).thenReturn(drone);
        when(modelsRepository.findByModelName(drone.getModel())).thenReturn(models);
        medication.setCode(medicationCode);
        medication.setDrone(drone);
        when(medicationRepository.save(any(Medication.class))).thenReturn(medication);
        GeneralResponseWrapper generalResponseWrapper = medicationService.loadDrone(1L, medication.getMedicationName(), medication.getWeight(), null);
        Medication expectedBody = (Medication) generalResponseWrapper.getData();
        assertNotNull(generalResponseWrapper);
        assertEquals(generalResponseWrapper.getResponseCode(), 201);
        assertNotNull(expectedBody);
        assertNotNull(expectedBody.getCode());
        assertEquals(generalResponseWrapper.getMessage(),"Drone loaded Successfully");
        assertEquals(expectedBody.getDrone().getSerialNumber(), "DRONE1234567890MUSALA");
        assertEquals(expectedBody.getMedicationName(), "MEDICATION4567");
    }

    @Test
    void listOfLoadedMedicationByDrone() {
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication);
        when(medicationRepository.findByDrone(drone)).thenReturn(medicationList);
        when(droneService.findDroneByID(any())).thenReturn(drone);
        GeneralResponseWrapper generalResponseWrapper = medicationService.listOfLoadedMedicationByDrone(1L);
        List<Medication> expectedlist = (List<Medication>) generalResponseWrapper.getData();
        assertNotNull(generalResponseWrapper);
        assertNotNull(expectedlist);
        assertEquals(generalResponseWrapper.getMessage(),"Medications fetched Successfully");
        assertEquals(expectedlist.size(), 1);
        assertEquals(generalResponseWrapper.getResponseCode(), 200);
        assertEquals(expectedlist.get(0).getDrone().getSerialNumber(), "DRONE1234567890MUSALA");

    }

    @Test
    void listOfLoadedMedicationByDroneNotFound() {
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(medication);
        GeneralResponseWrapper generalResponseWrapper = medicationService.listOfLoadedMedicationByDrone(1L);
        List<Medication> expectedlist = (List<Medication>) generalResponseWrapper.getData();
        assertNotNull(generalResponseWrapper);
        assertNull(expectedlist);
        assertEquals(generalResponseWrapper.getMessage(),"Drone with ID : 1 Not Found");
        assertEquals(generalResponseWrapper.getResponseCode(), 404);

    }
}