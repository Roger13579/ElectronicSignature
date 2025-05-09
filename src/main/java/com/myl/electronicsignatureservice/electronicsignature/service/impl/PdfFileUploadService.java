package com.myl.electronicsignatureservice.electronicsignature.service.impl;

import com.myl.electronicsignatureservice.electronicsignature.api.UploadFileEvent;
import com.myl.electronicsignatureservice.electronicsignature.model.FileData;
import com.myl.electronicsignatureservice.electronicsignature.repository.SignatureRepository;
import com.myl.electronicsignatureservice.electronicsignature.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PdfFileUploadService implements FileUploadService {

    private final SignatureRepository signatureRepository;

    @Override
    public void fileUpload(UploadFileEvent event) {
        if (event.getFile().isEmpty() || !Objects.equals(event.getFile().getContentType(), "application/pdf")) {
            throw new IllegalArgumentException("只能上傳 PDF 檔案");
        }
        FileData fileData;
        try {
            byte[] fileBytes = event.getFile().getBytes();
            fileData = FileData.builder()
                    .pdfFile(fileBytes)
                    .fileHash(computeSha256(fileBytes))
                    .email(event.getMail())
                    .build();
            fileData = signatureRepository.save(fileData);
            log.info("Uploaded file with id: {}", fileData.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        event.getIsCompleted().set(true);
        event.getFileId().set(fileData.getId().toString());
    }

    private String computeSha256(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);

            // 轉換成 16 進位字串表示
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 演算法不存在", e);
        }
    }
}
