package com.voltpay.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCustomerRequest(
    @NotBlank String customerCode,
    @NotBlank String name,
    @Pattern(regexp = "\\d{10}", message = "Mobile must be exactly 10 digits") String mobile,
    String address,
    @NotBlank String meterNumber,
    @NotBlank String category
) {}
