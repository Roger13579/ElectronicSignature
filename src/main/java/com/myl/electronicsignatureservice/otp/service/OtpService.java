package com.myl.electronicsignatureservice.otp.service;

public interface OtpService {

    String generateOtp();

    boolean verifyOtp(String otp);
}
