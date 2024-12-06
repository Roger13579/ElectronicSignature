package com.myl.electronicsignatureservice.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPlainText(List<String> receivers, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(String.valueOf(receivers));
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
