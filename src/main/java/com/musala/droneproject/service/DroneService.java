package com.musala.droneproject.service;

import com.musala.droneproject.dao.DroneDao;
import com.musala.droneproject.enums.Model;
import com.musala.droneproject.enums.State;
import com.musala.droneproject.model.Drone;
import com.musala.droneproject.model.Medication;
import com.musala.droneproject.repository.DroneRepository;
import com.musala.droneproject.wrapper.DroneBatteryLevelWrapper;
import com.musala.droneproject.wrapper.GeneralResponseWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class DroneService implements DroneServiceImpleme {

    private final DroneRepository droneRepository;

    @Override
    public GeneralResponseWrapper registerDrone(DroneDao droneDao) {
        GeneralResponseWrapper generalResponseWrapper=new GeneralResponseWrapper();
        try {
            Drone drone = new Drone();
            drone.setBatteryCapacity(droneDao.getBatteryCapacity());
            drone.setSerialNumber(droneDao.getSerialNumber());
            drone.setWeight(droneDao.getWeight());
            if (droneDao.getBatteryCapacity() < 25) {
                drone.setState(State.IDLE.name());
            } else {
                boolean validate = validateDroneState(droneDao.getState());
                if (validate) {
                    drone.setState(droneDao.getState());
                    Model model = validateModelAsToDroneWeight(droneDao.getWeight());
                    if (model == null) {
                        generalResponseWrapper.setResponseCode(HttpStatus.NOT_ACCEPTABLE.value());
                        generalResponseWrapper.setData(droneDao);
                        generalResponseWrapper.setMessage("INVALID MODEL PROVIDED:");
                    } else {
                        drone.setModel(model.name());
                        droneRepository.save(drone);
                        generalResponseWrapper.setResponseCode(HttpStatus.CREATED.value());
                        generalResponseWrapper.setData(drone);
                        generalResponseWrapper.setMessage("Drone Registered Successfully");
                    }

                } else {
                    generalResponseWrapper.setResponseCode(HttpStatus.NOT_ACCEPTABLE.value());
                    generalResponseWrapper.setData(droneDao);
                    generalResponseWrapper.setMessage("INVALID STATE PROVIDED: >" + droneDao.getState());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    @Override
    public GeneralResponseWrapper listOfDronesForLoading() {
        GeneralResponseWrapper generalResponseWrapper=new GeneralResponseWrapper();
        try {
            List<Drone> droneList = droneRepository.findByBatteryCapacityGreaterThanEqualAndState(25, State.IDLE.name());
            generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
            generalResponseWrapper.setMessage("Drones fetched Successfully");
            generalResponseWrapper.setData(droneList);

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    @Override
    public GeneralResponseWrapper findDroneBatteryLevel(Long droneId) {
        GeneralResponseWrapper generalResponseWrapper=new GeneralResponseWrapper();
        try {
            Optional<Drone> drone = droneRepository.findById(droneId);
            if (drone.isPresent()) {
                DroneBatteryLevelWrapper droneBatteryLevelWrapper = new DroneBatteryLevelWrapper();
                Drone drone1 = drone.get();
                String batteryLevel = drone1.getBatteryCapacity() + "%";
                droneBatteryLevelWrapper.setBatteryLevel(batteryLevel);
                generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
                generalResponseWrapper.setMessage("Drone Battery Level fetched Successfully");
                generalResponseWrapper.setData(droneBatteryLevelWrapper);
            } else {
                generalResponseWrapper.setResponseCode(HttpStatus.NOT_FOUND.value());
                generalResponseWrapper.setMessage("Drone with ID : " + droneId + "Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }


    public Drone findDroneByID(Long id) {
        Drone drone = null;
        Optional<Drone> droneOpt = droneRepository.findById(id);
        if (droneOpt.isPresent()) {
            drone = droneOpt.get();
        }
        return drone;
    }


    private Boolean validateDroneState(String state) {
        boolean res = false;
        if (state.equalsIgnoreCase(State.IDLE.name()) || state.equalsIgnoreCase(State.DELIVERED.name()) || state.equalsIgnoreCase(State.LOADED.name()) || state.equalsIgnoreCase(State.LOADING.name()) || state.equalsIgnoreCase(State.RETURNING.name()) || state.equalsIgnoreCase(State.DELIVERING.name())) {
            res = true;
        }
        return res;
    }

    private Model validateModelAsToDroneWeight(int weight) {
        Model model = null;
        if (weight >= 0 && weight < 150) {
            model = Model.Lightweight;
        } else if (weight >= 150 && weight < 250) {
            model = Model.Middleweight;
        } else if (weight >= 250 && weight < 400) {
            model = Model.Cruiserweight;
        } else if (weight >= 400 && weight <= 500) {
            model = Model.Heavyweight;
        } else {
        }
        return model;
    }

}
