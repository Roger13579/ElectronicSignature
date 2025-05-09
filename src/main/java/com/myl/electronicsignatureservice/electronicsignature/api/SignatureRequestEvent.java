package com.myl.electronicsignatureservice.electronicsignature.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

/**
 * Event for requesting a signature operation
 * This class is part of the public API of the electronicsignature module
 */
@Getter
public class SignatureRequestEvent {
    @NotNull
    private final String fileId;
    @NotNull
    @Email
    private final String email;
    @NotNull
    private final String signatureType;
    private final CompletableFuture<byte[]> resultFuture = new CompletableFuture<>();

    public SignatureRequestEvent(String fileId, String email, String signatureType) {
        this.fileId = fileId;
        this.email = email;
        this.signatureType = signatureType;
    }
}