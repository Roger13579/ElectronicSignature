package com.myl.electronicsignatureservice.electronicsignature.service;


import com.myl.electronicsignatureservice.electronicsignature.api.UploadFileEvent;

public interface FileUploadService {

    void fileUpload(UploadFileEvent uploadFileEvent);
}
