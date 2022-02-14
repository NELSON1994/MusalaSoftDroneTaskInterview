package com.musala.droneproject.controller;

import com.musala.droneproject.dao.DroneDao;
import com.musala.droneproject.enums.Model;
import com.musala.droneproject.enums.State;
import com.musala.droneproject.model.Drone;
import com.musala.droneproject.service.DroneService;
import com.musala.droneproject.service.MedicationService;
import com.musala.droneproject.wrapper.GeneralResponseWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DispatchController.class)
class DispatchControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DroneService droneService;

    @MockBean
    private MedicationService medicationService;

    Drone drone = new Drone(1L, "DRONE1234567890MUSALA", Model.Cruiserweight.name(), 280, 69, State.IDLE.name());
    private final String rootUrl = "/musala";

    @Test
    public void registerDrone() throws Exception {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        DroneDao droneDao = new DroneDao(drone.getSerialNumber(), drone.getWeight(), drone.getBatteryCapacity(), drone.getState());
        generalResponseWrapper.setResponseCode(201);
        generalResponseWrapper.setMessage("Drone Registered Successfully");
        generalResponseWrapper.setData(drone);
        given(droneService.registerDrone(droneDao)).willReturn(generalResponseWrapper);
        mockMvc.perform(post(rootUrl + "/drone")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(droneDao)))
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$.responseCode")).value(201))
                .andExpect(jsonPath(("$.message")).value("Drone Registered Successfully"))
                .andExpect(jsonPath(("$.data")).isNotEmpty())
                .andExpect(jsonPath(("$.data.serialNumber")).value("DRONE1234567890MUSALA"));

    }

    @Test
    public void getDronesForLoading() throws Exception {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        List<Drone> droneList = Arrays.asList(drone);
        generalResponseWrapper.setResponseCode(200);
        generalResponseWrapper.setMessage("Drones fetched Successfully");
        generalResponseWrapper.setData(droneList);
        given(droneService.listOfDronesForLoading()).willReturn(generalResponseWrapper);
        mockMvc.perform(get(rootUrl + "/drone/loading")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath(("$.responseCode")).value(200))
                .andExpect(jsonPath(("$.message")).value("Drones fetched Successfully"))
                .andExpect(jsonPath(("$.data")).isNotEmpty())
                .andExpect(jsonPath(("$.data")).isArray())
                .andExpect(jsonPath(("$.data[0]")).isNotEmpty())
                .andExpect(jsonPath(("$.data[0].serialNumber")).value("DRONE1234567890MUSALA"));
    }
}