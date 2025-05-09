package com.myl.electronicsignatureservice.otp.api;

import lombok.Getter;

import java.util.concurrent.CompletableFuture;

/**
 * Event for OTP verification
 * This class is part of the public API of the otp module
 */
@Getter
public class OtpVerificationEvent {
    /**
     * The OTP to verify
     */
    private final String otp;
    
    /**
     * The mail associated with the OTP
     */
    private final String email;
    
    /**
     * The future that will be completed with the verification result
     */
    private final CompletableFuture<Boolean> resultFuture = new CompletableFuture<>();
    
    public OtpVerificationEvent(String otp, String email) {
        this.otp = otp;
        this.email = email;
    }
}