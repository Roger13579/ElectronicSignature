package com.myl.electronicsignatureservice.electronicsignature.service;

public interface SignatureService {

    byte[] getSignedDocument(byte[] unsignFile) throws Exception;
    byte[] getUnsignFile(String fileId) throws Exception;
}
