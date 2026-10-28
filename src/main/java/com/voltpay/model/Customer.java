package com.voltpay.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "customers")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "customer_kind")
public abstract class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false) @NotBlank
    private String customerCode;
    @NotBlank private String name;
    @Column(nullable = false, length = 10) private String mobile;
    private String address;
    @Column(unique = true, nullable = false) private String meterNumber;

    protected Customer() {}
    protected Customer(String customerCode, String name, String mobile, String address, String meterNumber) {
        this.customerCode = customerCode; this.name = name; this.mobile = mobile;
        this.address = address; this.meterNumber = meterNumber;
    }
    public abstract String getCategory();
    public abstract double getRatePerUnit();
    public abstract double getFixedCharge();

    public Long getId() { return id; }
    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getMeterNumber() { return meterNumber; }
    public void setMeterNumber(String meterNumber) { this.meterNumber = meterNumber; }
}
