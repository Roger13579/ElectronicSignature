package com.myl.electronicsignatureservice.electronicSignature.controller;

import com.myl.electronicsignatureservice.electronicSignature.service.PDFBoxSignatureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.*;
import java.util.Base64;
import java.util.Calendar;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/signature")
public class ElectronicSignatureController {

//    @PostMapping(value = "/upload")
//    public ResponseEntity<PDFUploadPayloadResponse> pdfUpload(@RequestBody PDFUploadPayloadRequest request){
//        log.info("[PDFUploadPayloadRequest]: {}", request.toString());
//        final String signTxId = UUID.randomUUID().toString();
//        log.info("[gen.signTxId]: {}", signTxId);
//        ElectronicSignatureLog log = ElectronicSignatureLog.builder()
//                .memberId(request.getUsername())
//                .signTxId(signTxId)
//                .build();
//        try {
//            electronicSignatureService.save(log);
//            PDFUploadPayloadResponse response = PDFUploadPayloadResponse.builder()
//                    .fileHash(log.getFileHash())
//                    .signHash(log.getSignHash())
//                    .memberId(log.getMemberId())
//                    .signTxId(log.getSignTxId())
//                    .build();
//            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
//        } catch (ResourceAlreadyExistException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @PostMapping("/sign")
//    public ResponseEntity<PDFSignPayloadResponse> signPDF(@RequestParam("pdfFile") MultipartFile pdfFile,
//                                                          @RequestParam("fileHash") String fileHash,
//                                                          @RequestParam("signTxId") String signTxId,
//                                                          @RequestParam("assertion") String assertion){
//        KeyStore keystore;
//        PDFSignPayloadResponse response;
//        try {
//            keystore = KeyStore.getInstance("PKCS12");
//            keystore.load(new FileInputStream("./cert.p12"), "1234".toCharArray());
//            String alias = keystore.aliases().nextElement();
//            // Create signature instance
//            PDFBoxSignatureService signatureUtils = new PDFBoxSignatureService(keystore, alias, "1234", appProperty.getTsaUrl());
//            // Get Assertion Base64 String
//            String signAssertionData = Base64.getEncoder().encodeToString(assertion.getBytes());
//            log.info("signAssertionData length: {}", signAssertionData.length());
//            // Update ElectronicSignatureLog
//            ElectronicSignatureLog signatureLog = electronicSignatureService.findBySignTxId(signTxId);
//            signatureLog.setFileHash(fileHash);
//            signatureLog.setSignHash(signAssertionData);
//            electronicSignatureService.update(signatureLog);
//
//            // Load the PDF document
//            PDDocument document = PDDocument.load(pdfFile.getBytes());
//            // Create a new signature field
//            PDSignature pdSignature = new PDSignature();
//            pdSignature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
//            pdSignature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
//            pdSignature.setName("Example Signer");
//            pdSignature.setReason(signAssertionData);
//            pdSignature.setSignDate(Calendar.getInstance());
//
//            // Add the signature field
//            document.addSignature(pdSignature, signatureUtils);
//
//            // Save the signed document
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            document.saveIncremental(baos);
//            document.close();
//
//            response = PDFSignPayloadResponse.builder().signedFile(baos.toByteArray()).build();
//            log.info("PDF signed successfully, response: {}", response.getSignedFile().length);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(response);
//    }
}
