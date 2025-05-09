package com.myl.electronicsignatureservice.mail.listener;

import com.myl.electronicsignatureservice.mail.service.impl.MailServiceImpl;
import com.myl.electronicsignatureservice.otp.api.SendMailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OtpEventListener {

    private final MailServiceImpl mailService;

    @EventListener
    public void receivedAndSendOtp(SendMailEvent sendMailEvent) {
        log.info("Otp event received: {}", sendMailEvent);
        mailService.sendEmail(sendMailEvent.email(),
                "Your OTP is: " + sendMailEvent.otp());
        log.info("{} OTP sent successfully", sendMailEvent.email());
    }
}
