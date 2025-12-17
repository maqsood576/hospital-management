package com.hospital.management.service;

import com.hospital.management.models.Appointment;
import com.hospital.management.repositories.AppointmentRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private static final Logger logger =
            LogManager.getLogger(AppointmentService.class);

    @Autowired
    private AppointmentRepository appointmentRepository;

    // GET ALL APPOINTMENTS
    public List<Appointment> getAllAppointments() {
        logger.info("Fetching all appointments");
        List<Appointment> appointments = appointmentRepository.findAll();
        logger.info("Total appointments found: {}", appointments.size());
        return appointments;
    }

    // GET APPOINTMENT BY ID
    public Appointment getAppointmentById(Long id) {
        logger.info("Fetching appointment with id: {}", id);
        Optional<Appointment> appointment = appointmentRepository.findById(id);

        if (appointment.isPresent()) {
            logger.info("Appointment found with id: {}", id);
            return appointment.get();
        } else {
            logger.warn("Appointment not found with id: {}", id);
            return null;
        }
    }

    // CREATE APPOINTMENT
    public Appointment createAppointment(Appointment appointment) {
        logger.info(
            "Creating appointment for patient id: {}, doctor id: {}",
            appointment.getPatient() != null ? appointment.getPatient().getId() : "N/A",
            appointment.getDoctor() != null ? appointment.getDoctor().getId() : "N/A"
        );

        Appointment savedAppointment = appointmentRepository.save(appointment);
        logger.info("Appointment created successfully with id: {}", savedAppointment.getId());
        return savedAppointment;
    }

    // UPDATE APPOINTMENT
    public Appointment updateAppointment(Long id, Appointment appointment) {
        logger.info("Updating appointment with id: {}", id);

        Optional<Appointment> existingOpt = appointmentRepository.findById(id);

        if (existingOpt.isPresent()) {
            Appointment existing = existingOpt.get();
            existing.setAppointmentDate(appointment.getAppointmentDate());
            existing.setAppointmentTime(appointment.getAppointmentTime());
            existing.setStatus(appointment.getStatus());
            existing.setDoctor(appointment.getDoctor());
            existing.setPatient(appointment.getPatient());

            Appointment updatedAppointment = appointmentRepository.save(existing);
            logger.info("Appointment updated successfully with id: {}", id);
            return updatedAppointment;
        } else {
            logger.warn("Cannot update. Appointment not found with id: {}", id);
            return null;
        }
    }

    // DELETE APPOINTMENT
    public void deleteAppointment(Long id) {
        logger.info("Deleting appointment with id: {}", id);

        if (appointmentRepository.existsById(id)) {
            appointmentRepository.deleteById(id);
            logger.info("Appointment deleted successfully with id: {}", id);
        } else {
            logger.warn("Cannot delete. Appointment not found with id: {}", id);
        }
    }
}
