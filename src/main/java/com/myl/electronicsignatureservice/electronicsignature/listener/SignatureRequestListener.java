package com.myl.electronicsignatureservice.electronicsignature.listener;

import com.myl.electronicsignatureservice.electronicsignature.api.SignatureRequestEvent;
import com.myl.electronicsignatureservice.electronicsignature.service.SignatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SignatureRequestListener {

    private final Map<String, SignatureService> signatureServices;

    @EventListener
    public void handleSignatureRequest(SignatureRequestEvent event) {
        log.info("Signature request received for type: {}", event.getSignatureType());
        
        SignatureService signatureService = signatureServices.get(event.getSignatureType() + "SignatureService");
        if (signatureService == null) {
            String errorMessage = "Unsupported signature type: " + event.getSignatureType();
            log.error(errorMessage);
            event.getResultFuture().completeExceptionally(new IllegalArgumentException(errorMessage));
            return;
        }
        
        try {
            byte[] unsignFile = signatureService.getUnsignFile(event.getFileId());
            byte[] signedDocument = signatureService.getSignedDocument(unsignFile);
            event.getResultFuture().complete(signedDocument);
            log.info("Signature request processed successfully");
        } catch (Exception e) {
            log.error("Error signing the PDF document", e);
            event.getResultFuture().completeExceptionally(e);
        }
    }
}