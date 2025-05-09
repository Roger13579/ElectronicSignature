package com.myl.electronicsignatureservice.electronicsignature.listener;

import com.myl.electronicsignatureservice.electronicsignature.api.UploadFileEvent;
import com.myl.electronicsignatureservice.electronicsignature.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUploadListener {

    private final FileUploadService pdfFileUploadService;

    @EventListener
    public void receivedAndUploadFile(UploadFileEvent uploadFileEvent){
        pdfFileUploadService.fileUpload(uploadFileEvent);
    }
}
