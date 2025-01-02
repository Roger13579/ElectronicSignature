package com.myl.electronicsignatureservice.electronicsignature.service.impl;

import com.myl.electronicsignatureservice.electronicsignature.service.TimeStampService;
import org.bouncycastle.tsp.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

@Service
public class TimeStampServiceImpl implements TimeStampService {

    @Override
    public TimeStampToken getTimestamp(byte[] signatureBytes, String tsaUrl) throws Exception {
        // Timestamp logic here (connect to the TSA and request a timestamp token)
        TimeStampRequestGenerator tsRequestGen = new TimeStampRequestGenerator();
        tsRequestGen.setCertReq(true);
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(signatureBytes);
        TimeStampRequest tsRequest = tsRequestGen.generate(TSPAlgorithms.SHA256, digest);
        TimeStampResponse tsResponse = getTimeStampResponse(tsRequest, tsaUrl);
        tsResponse.validate(tsRequest);

        // Return the timestamp token
        return tsResponse.getTimeStampToken();
    }

    public TimeStampResponse getTimeStampResponse(TimeStampRequest tsRequest, String tsaUrl) throws IOException, TSPException {
        byte[] requestBytes = tsRequest.getEncoded();

        // Send the request to the TSA
        URL url = new URL(tsaUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/timestamp-query");
        OutputStream out = connection.getOutputStream();
        out.write(requestBytes);
        out.close();

        // Get the response
        InputStream in = connection.getInputStream();
        return new TimeStampResponse(in);
    }
}