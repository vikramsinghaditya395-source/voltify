package com.voltpay.controller;

import com.voltpay.dto.CreateCustomerRequest;
import com.voltpay.model.Customer;
import com.voltpay.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {
    private final CustomerService service;
    public CustomerController(CustomerService service) { this.service = service; }
    @PostMapping public Customer create(@Valid @RequestBody CreateCustomerRequest request) { return service.create(request); }
    @GetMapping public List<Customer> all() { return service.all(); }
    @GetMapping("/{code}") public Customer get(@PathVariable String code) { return service.get(code); }
}
