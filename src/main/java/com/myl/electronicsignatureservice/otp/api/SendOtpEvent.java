package com.myl.electronicsignatureservice.otp.api;

/**
 * Event for sending OTP
 * This class is part of the public API of the otp module
 */
public record SendOtpEvent(String mail) {
}