package com.myl.electronicsignatureservice.otp.api;

import jakarta.validation.constraints.Email;
import lombok.Data;

/**
 * Data Transfer Object for OTP verification request
 * Contains the OTP to validate and the associated mail
 */
@Data
public class SendOtpRequest {
    /**
     * The mail associated with the OTP
     */
    @Email(message = "Invalid email format")
    private String mail;
}