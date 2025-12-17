package com.hospital.management.service;

import com.hospital.management.models.Doctor;
import com.hospital.management.repository.DoctorRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private static final Logger logger =
            LogManager.getLogger(DoctorService.class);

    @Autowired
    private DoctorRepository doctorRepository;

    // GET ALL DOCTORS
    public List<Doctor> getAllDoctors() {
        logger.info("Fetching all doctors");
        List<Doctor> doctors = doctorRepository.findAll();
        logger.info("Total doctors found: {}", doctors.size());
        return doctors;
    }

    // GET DOCTOR BY ID
    public Doctor getDoctorById(Long id) {
        logger.info("Fetching doctor with id: {}", id);
        Optional<Doctor> doctor = doctorRepository.findById(id);

        if (doctor.isPresent()) {
            logger.info("Doctor found with id: {}", id);
            return doctor.get();
        } else {
            logger.warn("Doctor not found with id: {}", id);
            return null;
        }
    }

    // CREATE DOCTOR
    public Doctor createDoctor(Doctor doctor) {
        logger.info("Creating doctor with name: {}", doctor.getName());
        Doctor savedDoctor = doctorRepository.save(doctor);
        logger.info("Doctor created successfully with id: {}", savedDoctor.getId());
        return savedDoctor;
    }

    // UPDATE DOCTOR
    public Doctor updateDoctor(Long id, Doctor doctor) {
        logger.info("Updating doctor with id: {}", id);

        Optional<Doctor> existingOpt = doctorRepository.findById(id);

        if (existingOpt.isPresent()) {
            Doctor existing = existingOpt.get();
            existing.setName(doctor.getName());
            existing.setSpecialty(doctor.getSpecialty());

            Doctor updatedDoctor = doctorRepository.save(existing);
            logger.info("Doctor updated successfully with id: {}", id);
            return updatedDoctor;
        } else {
            logger.warn("Cannot update. Doctor not found with id: {}", id);
            return null;
        }
    }

    // DELETE DOCTOR
    public void deleteDoctor(Long id) {
        logger.info("Deleting doctor with id: {}", id);

        if (doctorRepository.existsById(id)) {
            doctorRepository.deleteById(id);
            logger.info("Doctor deleted successfully with id: {}", id);
        } else {
            logger.warn("Cannot delete. Doctor not found with id: {}", id);
        }
    }
}
