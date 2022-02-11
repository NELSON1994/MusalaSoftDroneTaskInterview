package com.musala.droneproject.config;

import com.musala.droneproject.model.Drone;
import com.musala.droneproject.repository.DroneRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class DroneBatteryLevelTracker {

    private DroneRepository droneRepository;

    @Scheduled(cron = "${droneBatteryLevelTiming}")
    public void droneBatteryLevelTracking() throws IOException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String fileName = "MS-" + simpleDateFormat.format(date) + "-logs.txt";
        Path path = Paths.get("logs/" + fileName);
        FileWriter fileWriter = new FileWriter(String.valueOf(Files.createFile(path)));
        fileWriter.append("   SERIAL NUMBER              BATTERY-LEVEL            STATE");
        fileWriter.append("\n");
        Stream<Drone> stream = droneRepository.findAll().stream();
        stream.forEach(

                (e) -> {
                    try {
                        fileWriter.append("   " + e.getSerialNumber() + "           " + e.getBatteryCapacity() + "                      " + e.getState());
                        fileWriter.append("\n");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

        );
        fileWriter.close();
    }
}
