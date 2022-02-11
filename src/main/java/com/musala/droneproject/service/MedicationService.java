package com.musala.droneproject.service;

import com.musala.droneproject.dao.MedicationDao;
import com.musala.droneproject.model.Drone;
import com.musala.droneproject.model.Medication;
import com.musala.droneproject.model.Models;
import com.musala.droneproject.repository.MedicationRepository;
import com.musala.droneproject.repository.ModelsRepository;
import com.musala.droneproject.wrapper.GeneralResponseWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@AllArgsConstructor
@Transactional
public class MedicationService implements MedicationServiceImple {

    private MedicationRepository medicationRepository;
    private DroneService droneService;
    private ModelsRepository modelsRepository;

    @Override
    public GeneralResponseWrapper loadDrone(Long drone_id, String medicationName, int medicationWeight, MultipartFile file) {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            Drone drone = droneService.findDroneByID(drone_id);
            if (nonNull(drone)) {
                Medication medication = new Medication();
                Models models = modelsRepository.findByModelName(drone.getModel());
                if (drone.getBatteryCapacity() >= 25) {
                    if (medicationWeight <= models.getMaximumWeight()) {
                        String code = generateCode(medicationName, drone.getSerialNumber());
                        medication.setDrone(drone);
                        medication.setMedicationName(medicationName);
                        medication.setWeight(medicationWeight);
                        medication.setPhoto(file.getBytes());
                        medication.setCode(code);
                        medicationRepository.save(medication);
                        generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
                        generalResponseWrapper.setMessage("Drone loaded Successfully");
                        generalResponseWrapper.setData(medication);
                    } else {
                        generalResponseWrapper.setResponseCode(HttpStatus.NOT_ACCEPTABLE.value());
                        generalResponseWrapper.setMessage("This Drone can be loaded with upto a maximum of : " + models.getMaximumWeight() + " gr");
                    }
                } else {
                    generalResponseWrapper.setResponseCode(HttpStatus.NOT_ACCEPTABLE.value());
                    generalResponseWrapper.setMessage("This Drone Battery Level is Low : " + drone.getBatteryCapacity() + "% Hence it can not be Loaded");
                }

            } else {
                generalResponseWrapper.setResponseCode(HttpStatus.NOT_FOUND.value());
                generalResponseWrapper.setMessage("Drone with ID : " + drone_id + "Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            generalResponseWrapper.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            generalResponseWrapper.setMessage(e.getMessage());
        }
        return generalResponseWrapper;
    }

    @Override
    public GeneralResponseWrapper listOfLoadedMedicationByDrone(Long droneId) {
        GeneralResponseWrapper generalResponseWrapper = new GeneralResponseWrapper();
        try {
            Drone drone = droneService.findDroneByID(droneId);
            if (drone != null) {
                List<Medication> medicationList = medicationRepository.findByDrone(drone);
                generalResponseWrapper.setResponseCode(HttpStatus.OK.value());
                generalResponseWrapper.setMessage("Medications fetched Successfully");
                generalResponseWrapper.setData(medicationList);
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

    private String generateCode(String medicationName, String droneSerialNumber) {
        int number = (int) (Math.random() * 10000);
        String generatedCode = droneSerialNumber.substring(0, 5) + medicationName.substring(0, 5) + number;
        return generatedCode.toUpperCase();
    }
}
