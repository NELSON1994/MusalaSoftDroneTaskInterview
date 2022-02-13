package com.musala.droneproject.controller;

import com.musala.droneproject.dao.DroneDao;
import com.musala.droneproject.dao.MedicationDao;
import com.musala.droneproject.service.DroneServiceImpleme;
import com.musala.droneproject.service.MedicationServiceImple;
import com.musala.droneproject.wrapper.GeneralResponseWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/musala")
@AllArgsConstructor
public class DispatchController {

    private DroneServiceImpleme droneServiceImpleme;
    private MedicationServiceImple medicationServiceImple;

    @PostMapping("/drone")
    private ResponseEntity<GeneralResponseWrapper> registerDrone(@Validated @RequestBody DroneDao droneDao){
        GeneralResponseWrapper generalResponseWrapper=droneServiceImpleme.registerDrone(droneDao);
        return ResponseEntity.ok(generalResponseWrapper);
    }

    @PostMapping("/load-drone/{droneId}")
    private ResponseEntity<GeneralResponseWrapper> loadDroneWithMedication(@Valid @PathVariable Long droneId, @RequestParam String medicationName,@RequestParam int weight, @RequestParam MultipartFile file){
        GeneralResponseWrapper generalResponseWrapper=medicationServiceImple.loadDrone(droneId,medicationName,weight,file);
        return ResponseEntity.ok(generalResponseWrapper);
    }

    @GetMapping("/drone/medication/{droneId}")
    private ResponseEntity<GeneralResponseWrapper> getMedicationsLoadedForDrone(@Valid @PathVariable Long droneId){
        GeneralResponseWrapper generalResponseWrapper=medicationServiceImple.listOfLoadedMedicationByDrone(droneId);
        return ResponseEntity.ok(generalResponseWrapper);
    }

    @GetMapping("/drone/loading")
    private ResponseEntity<GeneralResponseWrapper> getDronesForLoading(){
        GeneralResponseWrapper generalResponseWrapper=droneServiceImpleme.listOfDronesForLoading();
        return ResponseEntity.ok(generalResponseWrapper);
    }

    @GetMapping("/drone/battery-level/{droneId}")
    private ResponseEntity<GeneralResponseWrapper> getDronesForLoading(@Valid @PathVariable Long droneId){
        GeneralResponseWrapper generalResponseWrapper=droneServiceImpleme.findDroneBatteryLevel(droneId);
        return ResponseEntity.ok(generalResponseWrapper);
    }

}
