package com.myl.electronicsignatureservice.electronicsignature.dto;

import org.springframework.web.multipart.MultipartFile;

public record SignRequest(MultipartFile pdfFile,
                          String fileHash,
                          String email,
                          String signatureType) {}
