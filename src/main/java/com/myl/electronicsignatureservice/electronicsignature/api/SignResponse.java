package com.myl.electronicsignatureservice.electronicsignature.api;

/**
 * Data Transfer Object for signature responses
 * This class is part of the public API of the electronicsignature module
 */
public record SignResponse(byte[] signedDocument) {
}