package com.myl.electronicsignatureservice.electronicsignature.dto;

import org.springframework.web.multipart.MultipartFile;

public record UploadRequest(
        MultipartFile file,
        String mail){
}
