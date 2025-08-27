// src/main/java/com/example/patient/service/impl/PatientServiceImpl.java
package com.example.patientmicroservice.service.impl;

import com.example.patientmicroservice.model.Patient;
import com.example.patientmicroservice.repository.PatientRepository;
import com.example.patientmicroservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(String id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient addPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Optional<Patient> updatePatient(String id, Patient updatedPatient) {
        return patientRepository.findById(id).map(existing -> {
            existing.setFirstName(updatedPatient.getFirstName());
            existing.setLastName(updatedPatient.getLastName());
            existing.setDateOfBirth(updatedPatient.getDateOfBirth());
            existing.setContactNumber(updatedPatient.getContactNumber());
            existing.setEmail(updatedPatient.getEmail());
            existing.setGender(updatedPatient.getGender());
            return patientRepository.save(existing);
        });
    }

    @Override
    public boolean deletePatient(String id) {
        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
