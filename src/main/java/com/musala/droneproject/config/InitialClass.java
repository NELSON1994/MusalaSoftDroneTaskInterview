package com.musala.droneproject.config;

import com.musala.droneproject.enums.Model;
import com.musala.droneproject.enums.State;
import com.musala.droneproject.model.Drone;
import com.musala.droneproject.model.Medication;
import com.musala.droneproject.model.Models;
import com.musala.droneproject.model.States;
import com.musala.droneproject.repository.DroneRepository;
import com.musala.droneproject.repository.MedicationRepository;
import com.musala.droneproject.repository.ModelsRepository;
import com.musala.droneproject.repository.StatesRepository;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class InitialClass {

    private ModelsRepository modelsRepository;
    private StatesRepository statesRepository;
    private DroneRepository droneRepository;
    private MedicationRepository medicationRepository;

    @PostConstruct
    public void saveModelsAndStatesToDb() {
        modelsRepository.deleteAll();
        statesRepository.deleteAll();
//        droneRepository.deleteAll();
//        medicationRepository.deleteAll();

        List<Models> models = Stream.of(
                new Models(Model.Lightweight.name(), 150),
                new Models(Model.Middleweight.name(), 250),
                new Models(Model.Cruiserweight.name(), 400),
                new Models(Model.Heavyweight.name(), 500)
        ).collect(Collectors.toList());
        modelsRepository.saveAll(models);

        List<States> states = Stream.of(
                new States(State.IDLE.name()),
                new States(State.RETURNING.name()),
                new States(State.DELIVERING.name()),
                new States(State.LOADED.name()),
                new States(State.DELIVERED.name()),
                new States(State.LOADED.name())
        ).collect(Collectors.toList());
        statesRepository.saveAll(states);

        List<Drone> droneList = Stream.of(
                new Drone("DRONE12345-MUSALA1", Model.Lightweight.name(), 135, 99, State.IDLE.name()),
                new Drone("DRONE12345-MUSALA2", Model.Cruiserweight.name(), 380, 65, State.LOADED.name()),
                new Drone("DRONE12345-MUSALA3", Model.Middleweight.name(), 240, 45, State.LOADING.name()),
                new Drone("DRONE12345-MUSALA4", Model.Heavyweight.name(), 405, 30, State.RETURNING.name()),
                new Drone("DRONE12345-MUSALA5", Model.Heavyweight.name(), 490, 60, State.DELIVERING.name()),
                new Drone("DRONE12345-MUSALA6", Model.Middleweight.name(), 190, 80, State.IDLE.name()),
                new Drone("DRONE12345-MUSALA7", Model.Cruiserweight.name(), 270, 38, State.IDLE.name()),
                new Drone("DRONE12345-MUSALA8", Model.Lightweight.name(), 140, 78, State.LOADING.name()),
                new Drone("DRONE12345-MUSALA9", Model.Middleweight.name(), 190, 22, State.IDLE.name()),
                new Drone("DRONE12345-MUSALA10", Model.Cruiserweight.name(), 310, 90, State.DELIVERING.name()),
                new Drone("DRONE12345-MUSALA11", Model.Middleweight.name(), 220, 45, State.IDLE.name())
        ).collect(Collectors.toList());

        droneRepository.saveAll(droneList);

        Drone drone = droneRepository.findDroneBySerialNumber("DRONE12345-MUSALA1");
        Drone drone1 = droneRepository.findDroneBySerialNumber("DRONE12345-MUSALA5");
        List<Medication> medicationList = Stream.of(
                new Medication("MED111111", 120, "DRONE12MED115644", null, drone),
                new Medication("MED222222", 410, "DRONE12MED226780", null, drone1),
                new Medication("MED333333", 490, "DRONE12MED335090", null, drone1),
                new Medication("MED444444", 400, "DRONE12MED444211", null, drone1),
                new Medication("MED555555", 470, "DRONE12MED550909", null, drone1),
                new Medication("MED666666", 148, "DRONE12MED661464", null, drone),
                new Medication("MED777777", 78, "DRONE12MED777349", null, drone),
                new Medication("MED888888", 390, "DRONE12MED886745", null, drone1),
                new Medication("MED999999", 460, "DRONE12MED993456", null, drone1),
                new Medication("MED000000", 411, "DRONE12MED009000", null, drone1)
        ).collect(Collectors.toList());

        medicationRepository.saveAll(medicationList);
    }

}
