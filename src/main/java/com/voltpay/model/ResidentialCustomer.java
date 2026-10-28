package com.voltpay.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RESIDENTIAL")
public class ResidentialCustomer extends Customer {
    protected ResidentialCustomer() {}
    public ResidentialCustomer(String code, String name, String mobile, String address, String meter) {
        super(code, name, mobile, address, meter);
    }
    @Override public String getCategory() { return "Residential"; }
    @Override public double getRatePerUnit() { return 6.50; }
    @Override public double getFixedCharge() { return 100.00; }
}
