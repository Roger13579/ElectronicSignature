package com.myl.electronicsignatureservice.otp.service.impl;

import com.myl.electronicsignatureservice.otp.model.Otp;
import com.myl.electronicsignatureservice.otp.repository.OtpRepository;
import com.myl.electronicsignatureservice.otp.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the OTP service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {

    // In-memory storage for OTPs (in a real application, this would be a database or cache)
    private final SecureRandom random = new SecureRandom();
    private final OtpRepository otpRepository;

    // OTP expiration time in milliseconds (5 minutes)
    private static final long OTP_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(3);

    @Override
    public String generateOtp(String mail) throws Exception {
        try {
            // Generate a 6-digit OTP
            String otp = String.valueOf(100000 + random.nextInt(900000));
            Otp otpEntity = Otp.builder()
                    .email(mail)
                    .otp(otp)
                    .createdTimestamp(System.currentTimeMillis())
                    .build();
            // Store the OTP with its creation timestamp
            otpRepository.save(otpEntity);
            log.info("Generated OTP: {}", otp);
            return otp;
        }catch (Exception e){
            log.error("Error generating OTP", e);
            throw new Exception("Error generating OTP", e);
        }
    }

    @Override
    public boolean verifyOtp(String mail, String otp) {
        if (otp == null || otp.isEmpty()) {
            log.warn("Empty OTP provided for verification");
            return false;
        }

        Otp MostRecentOtp = otpRepository.findTopByEmailOrderByCreatedTimestampDesc(mail)
                .orElseThrow();

        // Check if the OTP has expired
        long currentTime = System.currentTimeMillis();
        if (currentTime - MostRecentOtp.getCreatedTimestamp() > OTP_EXPIRATION_TIME) {
            log.warn("Expired OTP: {}", otp);
            return false;
        }

        if (!MostRecentOtp.getOtp().equals(otp)) {
            log.warn("Invalid OTP: {}", otp);
            return false;
        }

        // OTP is valid, remove it to prevent reuse
        log.info("Valid OTP: {}", otp);
        return true;
    }
}
