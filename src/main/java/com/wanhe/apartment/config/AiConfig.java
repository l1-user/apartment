package com.wanhe.apartment.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "ai")
public class AiConfig {
    private DeepseekConfig deepseek = new DeepseekConfig();
    private PromptConfig prompt = new PromptConfig();

    @Data
    public static class DeepseekConfig {
        private String apiKey;
        private String baseUrl;
        private String model;
        private int timeout;
    }

    @Data
    public static class PromptConfig {
        private String workOrderAssign;
        private String contractSummary;
        private String customerService;
    }
}