package com.myl.electronicsignatureservice.electronicSignature.service;

import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadRequest;
import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface SignatureService {

    PayloadResponse getSignedDocument(PayloadRequest request) throws Exception;

}
