package com.myl.electronicsignatureservice.otp.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for OTP verification request
 * Contains the OTP to validate and the associated mail
 */
@Data
public class OtpVerificationRequest {
    @NotNull
    private String otp;

    @NotNull
    @Email(message = "Invalid email format")
    private String mail;
}