package com.myl.electronicsignatureservice.electronicSignature.utils;

import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;

@Slf4j
@RequiredArgsConstructor
public class ITextSignatureUtil {
    private String signatureFieldName;
    private final Certificate[] certificateChain;
    private final PrivateKey privateKey;
    private final String digestAlgorithm;
    private final String bouncyCastleProvider;
    private final PdfSigner.CryptoStandard cryptoStandardSubFilter;


    public ITextSignatureUtil(String signatureFieldName, String digestAlgorithm, String keystorePath, char[] keystorePsd) throws Exception {
        BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
        Security.addProvider(bouncyCastleProvider);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(keystorePath), keystorePsd);
        String alias = keyStore.aliases().nextElement();
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, keystorePsd);
        Certificate[] certificateChain = keyStore.getCertificateChain(alias);

        this.signatureFieldName = signatureFieldName;
        this.privateKey = privateKey;
        this.digestAlgorithm = digestAlgorithm;
        this.bouncyCastleProvider = bouncyCastleProvider.getName();
        this.certificateChain = certificateChain;
        this.cryptoStandardSubFilter = PdfSigner.CryptoStandard.CMS;
    }

    public byte[] sign(InputStream inputStream)
            throws GeneralSecurityException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfReader pdfReader = new PdfReader(inputStream);
        PdfSigner pdfSigner = new PdfSigner(pdfReader, outputStream, new StampingProperties());

        // This name corresponds to the name of the field that already exists in the document.
        pdfSigner.setFieldName(signatureFieldName);

        IExternalSignature iExternalSignature = new PrivateKeySignature(privateKey, digestAlgorithm, bouncyCastleProvider);
        IExternalDigest iExternalDigest = new BouncyCastleDigest();

        // Sign the document using the detached mode, CMS, or CAdES equivalent.
        pdfSigner.signDetached(iExternalDigest, iExternalSignature, certificateChain, null, null, null, 0, cryptoStandardSubFilter);
        return outputStream.toByteArray();
    }
}
