package com.myl.electronicsignatureservice.otp.service;

public interface OtpService {

    public String generateOtp();

    public boolean verifyOtp(String otp);
}
