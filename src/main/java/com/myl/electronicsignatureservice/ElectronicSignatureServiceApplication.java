package com.myl.electronicsignatureservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulith;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Modulith
@SpringBootApplication
public class ElectronicSignatureServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicSignatureServiceApplication.class, args);
    }

}
