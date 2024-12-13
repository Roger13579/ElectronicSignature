package com.myl.electronicsignatureservice.mail.listener;

import com.myl.electronicsignatureservice.otp.event.SendOtpEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OtpEventListener {

    @EventListener
    public void receivedAndSendEmail(SendOtpEvent sendOtpEvent) {
        log.info("Otp event received: {}", sendOtpEvent);

    }
}
