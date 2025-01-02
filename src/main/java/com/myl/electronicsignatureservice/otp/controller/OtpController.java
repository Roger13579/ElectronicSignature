package com.myl.electronicsignatureservice.otp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OtpController {

    @PostMapping("/otp/send")
    public String sendOtp(String email) {
        return "otp/send";
    }

    @PostMapping("/otp/validate")
    public String validateOtp(String email) {
        return "otp/validate";
    }
}
