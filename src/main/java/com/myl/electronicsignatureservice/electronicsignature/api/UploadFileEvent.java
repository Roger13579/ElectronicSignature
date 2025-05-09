package com.myl.electronicsignatureservice.electronicsignature.api;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Event for file upload operations
 * This class is part of the public API of the electronicsignature module
 */
@Data
public class UploadFileEvent {
    private MultipartFile file;
    private String mail;
    private final AtomicReference<String> fileId = new AtomicReference<>();
    private final AtomicBoolean isCompleted = new AtomicBoolean(false);

    public UploadFileEvent(MultipartFile file, String mail) {
        this.file = file;
        this.mail = mail;
    }
}