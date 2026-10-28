package com.voltpay.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("COMMERCIAL")
public class CommercialCustomer extends Customer {
    protected CommercialCustomer() {}
    public CommercialCustomer(String code, String name, String mobile, String address, String meter) {
        super(code, name, mobile, address, meter);
    }
    @Override public String getCategory() { return "Commercial"; }
    @Override public double getRatePerUnit() { return 9.50; }
    @Override public double getFixedCharge() { return 250.00; }
}
