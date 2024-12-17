package com.myl.electronicsignatureservice.electronicSignature.utils;

import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.signatures.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.Certificate;

@Slf4j
@RequiredArgsConstructor
public class ITextSignatureUtil {
    public void digitalSignature(String sourceFile, String signatureFieldName, String outputFile, Certificate[] certificateChain, PrivateKey privateKey, String digestAlgorithm,
                                 String bouncyCastleProvider, PdfSigner.CryptoStandard cryptoStandardSubFilter)
            throws GeneralSecurityException, IOException {
        PdfReader pdfReader = new PdfReader("temp.pdf");
        PdfSigner pdfSigner = new PdfSigner(pdfReader, new FileOutputStream(outputFile), new StampingProperties());

        // Create the signature appearance
        PdfSignatureAppearance pdfSignatureAppearance = pdfSigner.getSignatureAppearance();

        // This name corresponds to the name of the field that already exists in the document.
        pdfSigner.setFieldName(signatureFieldName);

        pdfSignatureAppearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);

        IExternalSignature iExternalSignature = new PrivateKeySignature(privateKey, digestAlgorithm, bouncyCastleProvider);
        IExternalDigest iExternalDigest = new BouncyCastleDigest();

        // Sign the document using the detached mode, CMS, or CAdES equivalent.
        pdfSigner.signDetached(iExternalDigest, iExternalSignature, certificateChain, null, null, null, 0, cryptoStandardSubFilter);
    }
}
