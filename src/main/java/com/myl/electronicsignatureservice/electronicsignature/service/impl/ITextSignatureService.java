package com.myl.electronicsignatureservice.electronicsignature.service.impl;

import com.itextpdf.signatures.DigestAlgorithms;
import com.myl.electronicsignatureservice.electronicsignature.model.FileData;
import com.myl.electronicsignatureservice.electronicsignature.properties.AppProperties;
import com.myl.electronicsignatureservice.electronicsignature.repository.SignatureRepository;
import com.myl.electronicsignatureservice.electronicsignature.service.SignatureService;
import com.myl.electronicsignatureservice.electronicsignature.utils.ITextSignatureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Slf4j
@Service(value = "iTextSignatureService")
@RequiredArgsConstructor
public class ITextSignatureService implements SignatureService {

    private final AppProperties appProperties;
    private final SignatureRepository signatureRepository;

    @Override
    public byte[] getSignedDocument(byte[] unsignFile) throws Exception {
        ITextSignatureUtil iTextSignatureUtil = new ITextSignatureUtil("test", DigestAlgorithms.SHA256, appProperties.getKeystorePath(), appProperties.getCertPsd().toCharArray());
        return iTextSignatureUtil.sign(new ByteArrayInputStream(unsignFile));
    }

    @Override
    public byte[] getUnsignFile(String fileId) throws Exception {
        FileData file = signatureRepository.findById(Long.parseLong(fileId)).orElseThrow(Exception::new);
        return file.getPdfFile();
    }
}

