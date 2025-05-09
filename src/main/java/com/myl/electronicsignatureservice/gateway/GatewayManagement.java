package com.myl.electronicsignatureservice.gateway;

import com.myl.electronicsignatureservice.electronicsignature.api.UploadRequest;
import com.myl.electronicsignatureservice.electronicsignature.api.SignRequest;
import com.myl.electronicsignatureservice.electronicsignature.api.SignResponse;
import com.myl.electronicsignatureservice.electronicsignature.api.SignatureRequestEvent;
import com.myl.electronicsignatureservice.electronicsignature.api.UploadFileEvent;
import com.myl.electronicsignatureservice.otp.api.OtpVerificationEvent;
import com.myl.electronicsignatureservice.otp.api.OtpVerificationRequest;
import com.myl.electronicsignatureservice.otp.api.SendOtpEvent;
import com.myl.electronicsignatureservice.otp.api.SendOtpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.modulith.ApplicationModule;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.concurrent.TimeUnit;

@ApplicationModule
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Gateway", description = "統一 API 接口")
public class GatewayManagement {

    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/sign")
    @Operation(summary = "Sign a document", description = "Signs a document with the specified signature type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Document signed successfully",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = SignResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<SignResponse> sign(
            @Parameter(description = "Sign request containing file ID, email, and signature type")
            @RequestBody @Valid SignRequest request) throws Exception {
        SignatureRequestEvent event = new SignatureRequestEvent(
                request.fileId(), request.mail(), request.signatureType());
        eventPublisher.publishEvent(event);
        byte[] bytes = event.getResultFuture().get(10, TimeUnit.SECONDS);
        SignResponse response = new SignResponse(bytes);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping("/upload")
    @Operation(summary = "Upload a file", description = "Uploads a file and returns a file ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File uploaded successfully",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "Upload request containing file and email")
            @ModelAttribute @Valid UploadRequest request) {
        UploadFileEvent uploadFileEvent = new UploadFileEvent(request.file(), request.mail());
        eventPublisher.publishEvent(uploadFileEvent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(uploadFileEvent.getFileId().get());
    }

    @PostMapping("/otp/send")
    @Operation(summary = "Send OTP", description = "Sends a one-time password to the specified email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OTP sent successfully",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input",
                content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content)
    })
    public ResponseEntity<String> otpSend(
            @Parameter(description = "Request containing email to send OTP to")
            @RequestBody @Valid SendOtpRequest request) {
        eventPublisher.publishEvent(new SendOtpEvent(request.getMail()));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("Otp send success");
    }

    @PostMapping("/otp/verify")
    @Operation(summary = "Verify OTP", description = "Verifies a one-time password for the specified email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OTP verified successfully",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "Invalid OTP",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<String> otpVerify(
            @Parameter(description = "Request containing OTP and email to verify")
            @RequestBody @Valid OtpVerificationRequest request) throws Exception {
        OtpVerificationEvent event = new OtpVerificationEvent(request.getOtp(), request.getMail());
        eventPublisher.publishEvent(event);
        if (event.getResultFuture().get(10, TimeUnit.SECONDS)){
            log.info("Otp validate success");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Otp validate success");
        }else {
            log.info("Wrong Otp");
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("Wrong Otp");
        }
    }

    /**
     * Redirect root URL to the index.html page
     * @return redirect to index.html
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/index.html";
    }
}
