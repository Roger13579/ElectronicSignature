package com.myl.electronicsignatureservice.otp.api;

/**
 * Event that is published when an OTP needs to be sent
 * This event is consumed by the mail module to send the OTP via mail
 */
public record SendMailEvent(String email, String otp) {

    /**
     * Creates a new SendOtpEvent
     * @param email the mail to send the OTP to
     * @param otp the OTP to send
     */
    public SendMailEvent {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (otp == null || otp.isEmpty()) {
            throw new IllegalArgumentException("OTP cannot be null or empty");
        }
    }
}