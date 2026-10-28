package com.voltpay.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills", uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "billing_period"}))
public class Bill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false, fetch = FetchType.EAGER) private Customer customer;
    @Column(name = "billing_period", nullable = false) private String billingPeriod;
    private long previousReading;
    private long currentReading;
    private long units;
    private double rate;
    private double fixedCharge;
    private double energyCharge;
    private double totalAmount;
    private LocalDate dueDate;
    private String status = "UNPAID";
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime paidAt;

    protected Bill() {}
    public Bill(Customer customer, String period, long previous, long current, LocalDate due) {
        this.customer = customer; this.billingPeriod = period; this.previousReading = previous;
        this.currentReading = current; this.units = current - previous; this.dueDate = due;
        this.rate = customer.getRatePerUnit(); this.fixedCharge = customer.getFixedCharge();
        this.energyCharge = units * rate; this.totalAmount = energyCharge + fixedCharge;
    }
    public void markPaid() { if ("PAID".equals(status)) throw new IllegalStateException("Bill is already paid."); status = "PAID"; paidAt = LocalDateTime.now(); }
    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public String getBillingPeriod() { return billingPeriod; }
    public long getPreviousReading() { return previousReading; }
    public long getCurrentReading() { return currentReading; }
    public long getUnits() { return units; }
    public double getRate() { return rate; }
    public double getFixedCharge() { return fixedCharge; }
    public double getEnergyCharge() { return energyCharge; }
    public double getTotalAmount() { return totalAmount; }
    public LocalDate getDueDate() { return dueDate; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getPaidAt() { return paidAt; }
}
