package com.myl.electronicsignatureservice.otp.listener;

import com.myl.electronicsignatureservice.otp.api.SendMailEvent;
import com.myl.electronicsignatureservice.otp.api.SendOtpEvent;
import com.myl.electronicsignatureservice.otp.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SendOtpListener {

    private final OtpService otpService;
    private final ApplicationEventPublisher eventPublisher;

    @EventListener
    public void receivedAndSendOtp(SendOtpEvent sendOtpEvent) throws Exception {
        String otp = otpService.generateOtp(sendOtpEvent.mail());
        log.info("OTP generated result: {}", otp);
        eventPublisher.publishEvent(new SendMailEvent(sendOtpEvent.mail(), otp));
        log.info("mail event sent successfully");
    }
}
