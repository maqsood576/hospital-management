package com.hospital.management.service;

import com.hospital.management.models.Bill;
import com.hospital.management.repository.BillRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillService {

    private static final Logger logger =
            LogManager.getLogger(BillService.class);

    @Autowired
    private BillRepository billRepository;

    // GET ALL BILLS
    public List<Bill> getAllBills() {
        logger.info("Fetching all bills");
        List<Bill> bills = billRepository.findAll();
        logger.info("Total bills found: {}", bills.size());
        return bills;
    }

    // GET BILL BY ID
    public Bill getBillById(Long id) {
        logger.info("Fetching bill with id: {}", id);
        Optional<Bill> bill = billRepository.findById(id);

        if (bill.isPresent()) {
            logger.info("Bill found with id: {}", id);
            return bill.get();
        } else {
            logger.warn("Bill not found with id: {}", id);
            return null;
        }
    }

    // CREATE BILL
    public Bill createBill(Bill bill) {
        logger.info("Creating bill for appointment id: {}",
                bill.getAppointment() != null ? bill.getAppointment().getId() : "N/A");

        Bill savedBill = billRepository.save(bill);
        logger.info("Bill created successfully with id: {}", savedBill.getId());
        return savedBill;
    }

    // UPDATE BILL
    public Bill updateBill(Long id, Bill bill) {
        logger.info("Updating bill with id: {}", id);

        Optional<Bill> existingOpt = billRepository.findById(id);

        if (existingOpt.isPresent()) {
            Bill existing = existingOpt.get();
            existing.setAmount(bill.getAmount());
            existing.setStatus(bill.getStatus());
            existing.setAppointment(bill.getAppointment());

            Bill updatedBill = billRepository.save(existing);
            logger.info("Bill updated successfully with id: {}", id);
            return updatedBill;
        } else {
            logger.warn("Cannot update. Bill not found with id: {}", id);
            return null;
        }
    }

    // DELETE BILL
    public void deleteBill(Long id) {
        logger.info("Deleting bill with id: {}", id);

        if (billRepository.existsById(id)) {
            billRepository.deleteById(id);
            logger.info("Bill deleted successfully with id: {}", id);
        } else {
            logger.warn("Cannot delete. Bill not found with id: {}", id);
        }
    }
}
