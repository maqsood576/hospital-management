package com.hospital.management.service;

import com.hospital.management.models.Patient;
import com.hospital.management.repositories.PatientRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private static final Logger logger =
            LogManager.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    // GET ALL PATIENTS
    public List<Patient> getAllPatients() {
        logger.info("Fetching all patients");
        List<Patient> patients = patientRepository.findAll();
        logger.info("Total patients found: {}", patients.size());
        return patients;
    }

    // GET PATIENT BY ID
    public Patient getPatientById(Long id) {
        logger.info("Fetching patient with id: {}", id);
        Optional<Patient> patient = patientRepository.findById(id);

        if (patient.isPresent()) {
            logger.info("Patient found with id: {}", id);
            return patient.get();
        } else {
            logger.warn("Patient not found with id: {}", id);
            return null;
        }
    }

    // CREATE PATIENT
    public Patient createPatient(Patient patient) {
        logger.info("Creating new patient with name: {}", patient.getName());
        Patient savedPatient = patientRepository.save(patient);
        logger.info("Patient created successfully with id: {}", savedPatient.getId());
        return savedPatient;
    }

    // UPDATE PATIENT
    public Patient updatePatient(Long id, Patient patient) {
        logger.info("Updating patient with id: {}", id);

        Optional<Patient> existingOpt = patientRepository.findById(id);

        if (existingOpt.isPresent()) {
            Patient existing = existingOpt.get();
            existing.setName(patient.getName());
            existing.setAge(patient.getAge());
            existing.setGender(patient.getGender());

            Patient updated = patientRepository.save(existing);
            logger.info("Patient updated successfully with id: {}", id);
            return updated;
        } else {
            logger.warn("Cannot update. Patient not found with id: {}", id);
            return null;
        }
    }

    // DELETE PATIENT
    public void deletePatient(Long id) {
        logger.info("Deleting patient with id: {}", id);

        if (patientRepository.existsById(id)) {
            patientRepository.deleteById(id);
            logger.info("Patient deleted successfully with id: {}", id);
        } else {
            logger.warn("Cannot delete. Patient not found with id: {}", id);
        }
    }
}
