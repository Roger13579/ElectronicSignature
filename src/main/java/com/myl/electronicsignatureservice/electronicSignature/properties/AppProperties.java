package com.myl.electronicsignatureservice.electronicSignature.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String tsaUrl;
    private String certPsd;
    private String keystorePath;
}
