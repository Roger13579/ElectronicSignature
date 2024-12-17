package com.myl.electronicsignatureservice.electronicSignature.service;

import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadRequest;
import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadResponse;

public interface SignatureService {

    PayloadResponse getSignedDocument(PayloadRequest request) throws Exception;

}
