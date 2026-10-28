package com.voltpay.service;

import com.voltpay.dto.CreateBillRequest;
import com.voltpay.exception.ResourceNotFoundException;
import com.voltpay.model.Bill;
import com.voltpay.model.Customer;
import com.voltpay.repository.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BillService {
    private final BillRepository bills;
    private final CustomerService customers;
    public BillService(BillRepository bills, CustomerService customers) { this.bills = bills; this.customers = customers; }

    @Transactional
    public Bill create(CreateBillRequest r) {
        if (r.currentReading() < r.previousReading())
            throw new IllegalArgumentException("Current reading cannot be less than previous reading.");
        if (bills.existsByCustomerCustomerCodeAndBillingPeriod(r.customerCode(), r.billingPeriod()))
            throw new IllegalArgumentException("A bill already exists for this customer and billing period.");
        Customer customer = customers.get(r.customerCode());
        // INHERITANCE + POLYMORPHISM: Customer reference resolves subclass rates at runtime.
        return bills.save(new Bill(customer, r.billingPeriod(), r.previousReading(), r.currentReading(), r.dueDate()));
    }
    public List<Bill> forCustomer(String code) {
        customers.get(code);
        return bills.findByCustomerCustomerCodeOrderByBillingPeriodDesc(code);
    }
    public Bill get(Long id) {
        return bills.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill not found: " + id));
    }
    @Transactional
    public Bill pay(Long id) { Bill bill = get(id); bill.markPaid(); return bill; }
}
