package com.myl.electronicsignatureservice.electronicSignature.service;

import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadRequest;
import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadResponse;
import com.myl.electronicsignatureservice.electronicSignature.properties.AppProperties;
import com.myl.electronicsignatureservice.electronicSignature.utils.PDFBoxSignatureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Calendar;

@Slf4j
@Service(value = "pdfBoxSignatureService")
@RequiredArgsConstructor
public class PDFBoxSignatureService implements SignatureService {

    private final AppProperties appProperties;

    @Override
    public PayloadResponse getSignedDocument(PayloadRequest request) {

        char[] keystorePsd = appProperties.getCertPsd().toCharArray();
        try (FileInputStream keystoreStream = new FileInputStream(appProperties.getKeystorePath());
             PDDocument document = PDDocument.load(request.getPdfFile().getBytes())) {

            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(keystoreStream, keystorePsd);
            String alias = keystore.aliases().nextElement();

            PDFBoxSignatureUtil signatureUtils = new PDFBoxSignatureUtil(keystore, alias, appProperties.getCertPsd(), appProperties.getTsaUrl());

            PDSignature pdSignature = new PDSignature();
            pdSignature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
            pdSignature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
            pdSignature.setName("Example Signer");
            pdSignature.setSignDate(Calendar.getInstance());

            document.addSignature(pdSignature, signatureUtils);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            document.saveIncremental(baos);

            byte[] fileByteArray = baos.toByteArray();
            log.info("PDF signed successfully with size: {} bytes", fileByteArray.length);

            return PayloadResponse.builder().signedFile(fileByteArray).build();
        } catch (Exception e) {
            log.error("Error signing the PDF document", e);
            throw new RuntimeException("PDF signing failed", e);
        } finally {
            Arrays.fill(keystorePsd, '\0');
        }
    }
}
