package com.myl.electronicsignatureservice.otp.service;

/**
 * Service for OTP operations
 * This interface defines the contract for OTP generation and verification
 */
public interface OtpService {

    /**
     * Generates a new OTP
     * @return the generated OTP
     */
    String generateOtp(String mail) throws Exception;

    /**
     * Verifies if an OTP is valid
     * @param otp the OTP to verify
     * @return true if the OTP is valid, false otherwise
     */
    boolean verifyOtp(String mail, String otp);
}
