package com.myl.electronicsignatureservice.otp.listener;

import com.myl.electronicsignatureservice.otp.api.OtpVerificationEvent;
import com.myl.electronicsignatureservice.otp.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OtpVerificationListener {

    private final OtpService otpService;

    @EventListener
    public void handleOtpVerification(OtpVerificationEvent event) {
        log.info("Received OTP verification request for mail: {}", event.getEmail());
        boolean isValid = otpService.verifyOtp(event.getEmail(), event.getOtp());
        log.info("OTP verification result: {}", isValid);
        event.getResultFuture().complete(isValid);
        log.info("OTP verification result sent successfully");
    }
}