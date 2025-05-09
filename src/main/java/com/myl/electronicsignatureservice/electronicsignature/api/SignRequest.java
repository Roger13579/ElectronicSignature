package com.myl.electronicsignatureservice.electronicsignature.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for signature requests
 * This class is part of the public API of the electronicsignature module
 */
public record SignRequest(
        @NotNull String fileId,
        @NotNull @Email String mail,
        @NotNull String signatureType) {
}