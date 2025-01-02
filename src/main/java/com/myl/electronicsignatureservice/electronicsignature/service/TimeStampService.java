package com.myl.electronicsignatureservice.electronicsignature.service;

import org.bouncycastle.tsp.TimeStampToken;

public interface TimeStampService {

    TimeStampToken getTimestamp(byte[] signatureBytes, String tsaUrl) throws Exception;
}
