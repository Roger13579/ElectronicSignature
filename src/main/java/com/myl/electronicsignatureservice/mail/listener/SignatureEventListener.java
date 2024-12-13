package com.myl.electronicsignatureservice.mail.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SignatureEventListener {

    @EventListener
    public void receivedAndSendEmail(){

    }
}
