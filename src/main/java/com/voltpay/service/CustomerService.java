package com.voltpay.service;

import com.voltpay.dto.CreateCustomerRequest;
import com.voltpay.exception.ResourceNotFoundException;
import com.voltpay.model.*;
import com.voltpay.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    public CustomerService(CustomerRepository repository) { this.repository = repository; }

    public Customer create(CreateCustomerRequest request) {
        String category = request.category().trim().toLowerCase();
        Customer customer;
        // POLYMORPHISM: each subclass supplies its own category/rate/fixed charge.
        if (category.equals("residential")) {
            customer = new ResidentialCustomer(request.customerCode(), request.name(), request.mobile(), request.address(), request.meterNumber());
        } else if (category.equals("commercial")) {
            customer = new CommercialCustomer(request.customerCode(), request.name(), request.mobile(), request.address(), request.meterNumber());
        } else throw new IllegalArgumentException("Category must be Residential or Commercial.");
        if (repository.existsByCustomerCode(request.customerCode())) throw new IllegalArgumentException("Customer code already exists.");
        return repository.save(customer);
    }
    public List<Customer> all() { return repository.findAll(); }
    public Customer get(String code) {
        return repository.findByCustomerCode(code).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + code));
    }
}
