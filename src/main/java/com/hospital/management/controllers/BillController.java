package com.hospital.management.controllers;

import com.hospital.management.models.Bill;
import com.hospital.management.service.BillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService billService;

    // GET ALL BILLS
    @GetMapping
    public List<Bill> getAllBills() {
        return billService.getAllBills();
    }

    // GET BILL BY ID
    @GetMapping("/{id}")
    public Bill getBillById(@PathVariable Long id) {
        return billService.getBillById(id);
    }

    // CREATE BILL
    @PostMapping
    public Bill createBill(@RequestBody Bill bill) {
        return billService.createBill(bill);
    }

    // UPDATE BILL
    @PutMapping("/{id}")
    public Bill updateBill(
            @PathVariable Long id,
            @RequestBody Bill bill) {

        return billService.updateBill(id, bill);
    }

    // DELETE BILL
    @DeleteMapping("/{id}")
    public void deleteBill(@PathVariable Long id) {
        billService.deleteBill(id);
    }
}
