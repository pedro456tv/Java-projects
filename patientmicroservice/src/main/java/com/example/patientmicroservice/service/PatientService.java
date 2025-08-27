// src/main/java/com/example/patient/service/PatientService.java
package com.example.patientmicroservice.service;

import com.example.patientmicroservice.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<Patient> getAllPatients();
    Optional<Patient> getPatientById(String id);
    Patient addPatient(Patient patient);
    Optional<Patient> updatePatient(String id, Patient patient);
    boolean deletePatient(String id);
}
