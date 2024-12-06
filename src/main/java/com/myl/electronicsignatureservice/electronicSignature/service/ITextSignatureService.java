package com.myl.electronicsignatureservice.electronicSignature.service;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.StampingProperties;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.signatures.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;

public class ITextSignatureService {
    public static void digitalSignature(String sourceFile, String signatureFieldName, String outputFile, Certificate[] certificateChain, PrivateKey privateKey, String digestAlgorithm,
                                        String bouncyCastleProvider, PdfSigner.CryptoStandard cryptoStandardSubFilter)
            throws GeneralSecurityException, IOException {
        addId(sourceFile, "temp.pdf");
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

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
        Security.addProvider(bouncyCastleProvider);

        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream("./MS190611.p12"), "1234".toCharArray());
        String alias = ks.aliases().nextElement();
        PrivateKey privateKey = (PrivateKey) ks.getKey(alias, "1234".toCharArray());
        Certificate[] certificateChain = ks.getCertificateChain(alias);
        digitalSignature("c4-Vafw86Lq-230704094733.pdf", "Signature Field Name", "signed.pdf", certificateChain, privateKey,
                DigestAlgorithms.SHA256, bouncyCastleProvider.getName(), PdfSigner.CryptoStandard.CMS);
    }
//    public static void main(String[] args) {
//        try {
//            // Create a MessageDigest instance for SHA-512
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] uuid = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
//            // Apply SHA-512 to the input string
//            byte[] encodedHash = digest.digest(uuid);
//            String s = Base64.getEncoder().encodeToString(encodedHash);
//            String s1 = Base64.getEncoder().encodeToString(encodedHash) + "." + "YWJiZjYwYmEtMGEzMy00ZDJiLThmMjctYjRlYjM5ODJhNjU4";
//            // Convert byte array into a readable string format (e.g., Base64)
//            System.out.println(s1);
//            System.out.println(s.length());
//            System.out.println(s1.length());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException("Error while hashing with SHA-512", e);
//        }
//    }
    private static void addId(String src, String dest){
        try {
            // Initialize PDF reader and writer
            PdfReader reader = new PdfReader(src);
            PdfWriter writer = new PdfWriter(dest);

            // Initialize PDF document
            PdfDocument pdfDoc = new PdfDocument(reader, writer);

            // Get the number of pages in the document
            int numberOfPages = pdfDoc.getNumberOfPages();

            // Get the last page's dimensions
            Rectangle pageSize = pdfDoc.getPage(numberOfPages).getPageSize();

            // Define the text to be added
            String text = "This is FIDO ID";

            // Font settings
            PdfFont font = PdfFontFactory.createFont();
            float fontSize = 12;

            // Calculate the position for the right-bottom corner
            float x = pageSize.getWidth() - 10;  // 10 units from the right edge
            float y = 10;  // 10 units from the bottom edge

            // Create a PdfCanvas to draw the text on the last page
            PdfCanvas pdfCanvas = new PdfCanvas(pdfDoc.getLastPage());

            // Create a Canvas to add the text
            Canvas canvas = new Canvas(pdfCanvas, pageSize);
            canvas.setFont(font);
            canvas.setFontSize(fontSize);
            canvas.showTextAligned(text, x, y, TextAlignment.RIGHT);
            canvas.close();

            // Close the document
            pdfDoc.close();

            System.out.println("Text added to the last page successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
