package com.myl.electronicsignatureservice.electronicSignature.service;

import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadRequest;
import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service(value = "iTextSignatureService")
@RequiredArgsConstructor
public class ITextSignatureService implements SignatureService {


    @Override
    public PayloadResponse getSignedDocument(PayloadRequest request) {
        return null;
    }
}
