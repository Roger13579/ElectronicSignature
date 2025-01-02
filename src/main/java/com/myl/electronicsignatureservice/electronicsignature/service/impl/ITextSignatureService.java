package com.myl.electronicsignatureservice.electronicsignature.service.impl;

import com.itextpdf.signatures.DigestAlgorithms;
import com.myl.electronicsignatureservice.electronicsignature.dto.PayloadRequest;
import com.myl.electronicsignatureservice.electronicsignature.dto.PayloadResponse;
import com.myl.electronicsignatureservice.electronicsignature.properties.AppProperties;
import com.myl.electronicsignatureservice.electronicsignature.service.SignatureService;
import com.myl.electronicsignatureservice.electronicsignature.utils.ITextSignatureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

