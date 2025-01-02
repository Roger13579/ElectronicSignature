package com.myl.electronicsignatureservice.electronicsignature.controller;

import com.myl.electronicsignatureservice.electronicsignature.dto.PayloadRequest;
import com.myl.electronicsignatureservice.electronicsignature.dto.PayloadResponse;
import com.myl.electronicsignatureservice.electronicsignature.service.SignatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "電子簽章")
public class ElectronicSignatureController {

    private final SignatureService pdfBoxSignatureService;
    private final SignatureService iTextSignatureService;

    @PostMapping("/sign/pdfBox")
    @Operation(summary = "使用PDFBox套件")
    public ResponseEntity<PayloadResponse> signByPDFBox(@RequestBody PayloadRequest request) throws Exception {
        PayloadResponse response = pdfBoxSignatureService.getSignedDocument(request);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/sign/iText")
    @Operation(summary = "使用iText套件")
    public ResponseEntity<PayloadResponse> signByIText(@RequestBody PayloadRequest request) throws Exception {
        PayloadResponse response = iTextSignatureService.getSignedDocument(request);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
