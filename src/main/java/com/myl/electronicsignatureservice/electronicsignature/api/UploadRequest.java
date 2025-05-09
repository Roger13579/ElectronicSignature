package com.myl.electronicsignatureservice.electronicsignature.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * Data Transfer Object for file upload requests
 * This class is part of the public API of the electronicsignature module
 */
public record UploadRequest(
        @NotNull
        MultipartFile file,
        @NotNull
        @Email(message = "Invalid email format")
        String mail) {
}