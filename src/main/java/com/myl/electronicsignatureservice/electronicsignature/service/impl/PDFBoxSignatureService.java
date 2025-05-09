package com.myl.electronicsignatureservice.electronicsignature.service.impl;

import com.myl.electronicsignatureservice.electronicsignature.model.FileData;
import com.myl.electronicsignatureservice.electronicsignature.properties.AppProperties;
import com.myl.electronicsignatureservice.electronicsignature.repository.SignatureRepository;
import com.myl.electronicsignatureservice.electronicsignature.service.SignatureService;
import com.myl.electronicsignatureservice.electronicsignature.service.TimeStampService;
import com.myl.electronicsignatureservice.electronicsignature.utils.PDFBoxSignatureUtil;
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
    private final TimeStampService timeStampService;
    private final SignatureRepository signatureRepository;

    @Override
    public byte[] getSignedDocument(byte[] unsignFile) {

        char[] keystorePsd = appProperties.getCertPsd().toCharArray();
        try (FileInputStream keystoreStream = new FileInputStream(appProperties.getKeystorePath());
             PDDocument document = PDDocument.load(unsignFile)) {

            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(keystoreStream, keystorePsd);
            String alias = keystore.aliases().nextElement();

            PDFBoxSignatureUtil signatureUtils = new PDFBoxSignatureUtil(keystore, alias, appProperties.getCertPsd(), appProperties.getTsaUrl(), timeStampService);

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

            return fileByteArray;
        } catch (Exception e) {
            log.error("Error signing the PDF document", e);
            throw new RuntimeException("PDF signing failed", e);
        } finally {
            Arrays.fill(keystorePsd, '\0');
        }
    }

    @Override
    public byte[] getUnsignFile(String fileId) throws Exception {
        FileData file = signatureRepository.findById(Long.parseLong(fileId)).orElseThrow(Exception::new);
        return file.getPdfFile();
    }
}
