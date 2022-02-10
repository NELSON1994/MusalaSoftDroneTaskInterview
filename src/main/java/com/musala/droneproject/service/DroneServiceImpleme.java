package com.musala.droneproject.service;

import com.musala.droneproject.dao.DroneDao;
import com.musala.droneproject.wrapper.GeneralResponseWrapper;

public interface DroneServiceImpleme {
    GeneralResponseWrapper registerDrone(DroneDao droneDao);

    GeneralResponseWrapper listOfDronesForLoading();

    GeneralResponseWrapper findDroneBatteryLevel(Long droneId);
}
