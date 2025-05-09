package com.myl.electronicsignatureservice.electronicsignature.service;

import org.springframework.web.multipart.MultipartFile;

public interface SignatureService {

    byte[] getSignedDocument(byte[] unsignFile) throws Exception;
    byte[] getUnsignFile(String fileId) throws Exception;
}
