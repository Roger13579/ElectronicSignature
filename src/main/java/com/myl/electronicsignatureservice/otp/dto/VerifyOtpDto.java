package com.myl.electronicsignatureservice.otp.dto;

import lombok.Data;

/**
 * Data Transfer Object for OTP validation
 * Contains the OTP to validate and the associated mail
 */
@Data
public class VerifyOtpDto {
    /**
     * The OTP to validate
     */
    private String otp;

    /**
     * The mail associated with the OTP
     */
    private String email;
}
