package com.myl.electronicsignatureservice.otp.service.impl;

import com.myl.electronicsignatureservice.otp.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {

    @Override
    public String generateOtp() {
        return "";
    }

    @Override
    public boolean verifyOtp(String otp) {
        return false;
    }
}
