package com.myl.electronicsignatureservice.electronicsignature.service;

import com.myl.electronicsignatureservice.electronicsignature.dto.PayloadRequest;
import com.myl.electronicsignatureservice.electronicsignature.dto.PayloadResponse;

public interface SignatureService {

    PayloadResponse getSignedDocument(PayloadRequest request) throws Exception;

}
