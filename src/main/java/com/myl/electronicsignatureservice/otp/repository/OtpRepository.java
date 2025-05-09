package com.myl.electronicsignatureservice.otp.repository;

import com.myl.electronicsignatureservice.otp.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,Long> {
    Optional<Otp> findTopByEmailOrderByCreatedTimestampDesc(String mail);
}
