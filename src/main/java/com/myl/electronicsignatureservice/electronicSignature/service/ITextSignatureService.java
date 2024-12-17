package com.myl.electronicsignatureservice.electronicSignature.service;

import com.itextpdf.signatures.DigestAlgorithms;
import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadRequest;
import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadResponse;
import com.myl.electronicsignatureservice.electronicSignature.properties.AppProperties;
import com.myl.electronicsignatureservice.electronicSignature.utils.ITextSignatureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Slf4j
@Service(value = "iTextSignatureService")
@RequiredArgsConstructor
public class ITextSignatureService implements SignatureService {

    private final AppProperties appProperties;

    @Override
    public PayloadResponse getSignedDocument(PayloadRequest request) throws Exception {
        ITextSignatureUtil iTextSignatureUtil1 = new ITextSignatureUtil("test", DigestAlgorithms.SHA256, appProperties.getKeystorePath(), appProperties.getCertPsd().toCharArray());
        byte[] fileByteArray = iTextSignatureUtil1.sign(request.getPdfFile().getInputStream());
        return PayloadResponse.builder().signedFile(fileByteArray).build();
    }
}

