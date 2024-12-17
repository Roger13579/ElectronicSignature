package com.myl.electronicsignatureservice.electronicSignature.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

@Data
@SuperBuilder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PayloadRequest {
    private MultipartFile pdfFile;
    private String fileHash;
    private String signTxId;
}
