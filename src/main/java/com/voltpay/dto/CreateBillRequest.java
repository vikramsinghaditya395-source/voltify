package com.voltpay.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateBillRequest(
    @NotBlank String customerCode,
    @NotBlank String billingPeriod,
    @Min(0) long previousReading,
    @Min(0) long currentReading,
    @NotNull LocalDate dueDate
) {}
