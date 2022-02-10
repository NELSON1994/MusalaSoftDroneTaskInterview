package com.musala.droneproject.service;

import com.musala.droneproject.dao.MedicationDao;
import com.musala.droneproject.model.Medication;
import com.musala.droneproject.wrapper.GeneralResponseWrapper;
import org.springframework.web.multipart.MultipartFile;

public interface MedicationServiceImple {
    // load drone with medication
    GeneralResponseWrapper loadDrone(Long drone_id, String medicationName,int weightt , MultipartFile file);

    GeneralResponseWrapper listOfLoadedMedicationByDrone(Long droneId);
}
