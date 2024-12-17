package com.myl.electronicsignatureservice.electronicSignature.controller;

import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadRequest;
import com.myl.electronicsignatureservice.electronicSignature.dto.PayloadResponse;
import com.myl.electronicsignatureservice.electronicSignature.properties.AppProperties;
import com.myl.electronicsignatureservice.electronicSignature.service.SignatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/signature")
@RequiredArgsConstructor
public class ElectronicSignatureController {

    private final SignatureService pdfBoxSignatureService;
    private final SignatureService iTextSignatureService;

    @PostMapping("/sign/pdfBox")
    public ResponseEntity<PayloadResponse> signByPDFBox(@RequestBody PayloadRequest request) throws Exception {
        PayloadResponse response = pdfBoxSignatureService.getSignedDocument(request);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/sign/iText")
    public ResponseEntity<PayloadResponse> signByIText(@RequestBody PayloadRequest request) throws Exception {
        PayloadResponse response = iTextSignatureService.getSignedDocument(request);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
