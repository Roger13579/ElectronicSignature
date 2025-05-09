package com.myl.electronicsignatureservice.electronicsignature.utils;

import com.myl.electronicsignatureservice.electronicsignature.service.TimeStampService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.*;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.tsp.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@Slf4j
public class PDFBoxSignatureUtil implements SignatureInterface {
    private final PrivateKey privateKey;
    private final Certificate[] certificateChain;
    private final String tsaUrl;
    private final TimeStampService timeStampService;

    public PDFBoxSignatureUtil(KeyStore keystore, String alias, String keyPassword, String tsaUrl, TimeStampService timeStampService) throws Exception {
        this.tsaUrl = tsaUrl;
        this.timeStampService = timeStampService;
        privateKey = (PrivateKey) keystore.getKey(alias, keyPassword.toCharArray());
        certificateChain = keystore.getCertificateChain(alias);
    }
    @Override
    public byte[] sign(InputStream content) throws IOException {
        try {
            CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
            Security.addProvider(new BouncyCastleProvider());
            ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").setProvider("BC").build(privateKey);
            generator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
                    new JcaDigestCalculatorProviderBuilder().build())
                    .build(contentSigner, (X509Certificate) certificateChain[0]));
            generator.addCertificates(new JcaCertStore(Arrays.asList(certificateChain)));
            CMSTypedData cmsData = new CMSTypedDataInputStream(content);
            CMSSignedData signedData = generator.generate(cmsData, false);
            SignerInformation signerInformation = signedData.getSignerInfos().getSigners().iterator().next();
            byte[] signature = signerInformation.getSignature();
            log.info("Generated signature. Length: {}", signature.length);

            TimeStampToken tsToken = timeStampService.getTimestamp(signature, tsaUrl);
            if (tsToken == null) {
                log.info("TimeStampToken is null. This should not happen if the response was validated successfully.");
                throw new IOException("Failed to get TimeStampToken from TSA response");
            }

            ASN1EncodableVector v = new ASN1EncodableVector();
            v.add(new Attribute(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken, new DERSet(ASN1Primitive.fromByteArray(tsToken.getEncoded()))));
            signerInformation = SignerInformation.replaceUnsignedAttributes(signerInformation, new AttributeTable(v));

            SignerInformationStore signerStore = new SignerInformationStore(signerInformation);
            signedData = CMSSignedData.replaceSigners(signedData, signerStore);

            log.info("Signing process completed successfully.");
            return signedData.getEncoded();
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    // Helper class to make InputStream work with CMSTypedData
    static class CMSTypedDataInputStream implements CMSTypedData {
        private final InputStream in;

        public CMSTypedDataInputStream(InputStream is) {
            in = is;
        }

        @Override
        public ASN1ObjectIdentifier getContentType() {
            return new ASN1ObjectIdentifier("1.2.840.113549.1.7.1"); // OID for data
        }

        @Override
        public Object getContent() {
            return in;
        }

        @Override
        public void write(OutputStream out) throws IOException {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
        }
    }
}
