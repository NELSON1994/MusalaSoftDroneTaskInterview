# MusalaSoftDroneTaskInterview
## Table of contents
* [General info](#general-info)
* [PreRequisites](#prerequisites)
* [Technologies](#technologies)
* [Setup](#setup)
* [Assumptions](#assumptions)

## General info
```
This project is for new technology that is destined to be a disruptive force in the field of transportation 
which is the drone.Just as the mobile phone allowed developing countries to leapfrog older technologies for 
personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.
Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult
access.This one will be used to transport medication.
The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.
```

## PreRequisites
```
Installation of Java Sdk preferbly Java 11 to run the project
Install Maven in your machine to use the Maven Commands to run the application
```
## Technologies
```
This Project is created with:
* Java 11
* Maven
* Tomcat Server
* SpringBoot Framework with Spring Data JPA
* MySQL Database
* REST API
```
	
## Setup
To run this project locally, clone it from GitHub

```
N/B : Have Maven Installed in your machine  to use the Maven commands.
$ cd to the project folder
$ create folder called logs at the root of the project if it is not already existing
$ set the database url, username and password on the /config/application-test.properties file
$ create a database called musaladrone as it is the database name used for this project

To run the Project
$ mvn spring-boot:run

To clean the project
$ mvn clean

To test the project tests scenarios
$ mvn test

To create JarFile /Build the project
$ mvn clean install   or mvn package

To run the project with Java Command
$ java -jar target/musala-drone:1.0.jar
```
## Assumptions
```
The following assumptions were made made during development
* The relationship between drone and medication is a one-to-many relationship
* The drones will have unique serial numbers.
* No state or model will be allowed that is not defined in the database when registering the drone
* The model of the drone is determined by it's weight.
          >> Lightweight: 0-150
          >> Middleweight:151-250
          >> Cruiserweight: 251-399
          >> Heavyweight: 400-500
* The Medication Code is System Generated. 
i.e Drone SerialNumber(First 4 strings)+Medication Name(First 4 Strings)+ 5 length Random Generated Numbers.
* The drone that will be Loaded is one with >= 25% battery Level and also is on IDLE State
* No drone will be loaded with medication that is greater than its maximum weight it can carry.
Maximum weight of the drone is detrmined by the model.
```

## Audit Event
```
The drones Battery Level and State will be tracked by the system after every set time duration which this is done 
through cron expression on the application.properties file.So any duration can be set for the tracking.The 
Result will be written in a log file under the logs folder in file naming format of MS-yyyyMMddhhmmss-logs.txt  
The data in the file will be recorded in the format below.
   SERIAL NUMBER              BATTERY-LEVEL            STATE
   DRONE12345-MUSALA1           99%                      IDLE
   DRONE12345-MUSALA2           65%                      LOADED
   DRONE12345-MUSALA3           45%                      LOADING
```
