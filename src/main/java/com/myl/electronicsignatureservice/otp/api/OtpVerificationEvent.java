package com.myl.electronicsignatureservice.otp.api;

import lombok.Getter;

import java.util.concurrent.CompletableFuture;

/**
 * Event for OTP verification
 * This class is part of the public API of the otp module
 */
@Getter
public class OtpVerificationEvent {
    private final String otp;
    private final String email;
    private final CompletableFuture<Boolean> resultFuture = new CompletableFuture<>();
    
    public OtpVerificationEvent(String otp, String email) {
        this.otp = otp;
        this.email = email;
    }
}