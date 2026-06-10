package com.wanhe.apartment.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanhe.apartment.config.AiConfig;
import com.wanhe.apartment.entity.MaintenanceOrder;
import com.wanhe.apartment.entity.SysUser;
import com.wanhe.apartment.mapper.MaintenanceOrderMapper;
import com.wanhe.apartment.mapper.SysUserMapper;
import com.wanhe.apartment.service.IAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiServiceImpl implements IAiService {

    private final AiConfig aiConfig;
    private final MaintenanceOrderMapper maintenanceOrderMapper;
    private final SysUserMapper sysUserMapper;
    private final ObjectMapper objectMapper;

    private WebClient createWebClient() {
        return WebClient.builder()
                .baseUrl(aiConfig.getDeepseek().getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("Authorization", "Bearer " + aiConfig.getDeepseek().getApiKey())
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();
    }

    @Override
    public String chat(String message) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", aiConfig.getDeepseek().getModel());
            requestBody.put("messages", List.of(Map.of("role", "user", "content", message)));
            requestBody.put("temperature", 0.7);

            String response = createWebClient()
                    .post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(java.time.Duration.ofMillis(aiConfig.getDeepseek().getTimeout()))
                    .onErrorResume(e -> {
                        log.error("AI request failed", e);
                        return Mono.just("{\"error\": \"AI服务暂时不可用\"}");
                    })
                    .block();

            JsonNode jsonNode = objectMapper.readTree(response);
            if (jsonNode.has("choices")) {
                return jsonNode.get("choices").get(0).get("message").get("content").asText();
            }
            return response;
        } catch (Exception e) {
            log.error("AI chat error", e);
            return "抱歉，AI服务暂时不可用";
        }
    }

    @Override
    public String recommendWorker(Long workOrderId) {
        try {
            MaintenanceOrder order = maintenanceOrderMapper.selectById(workOrderId);
            if (order == null) {
                return "{\"error\": \"工单不存在\"}";
            }

            List<SysUser> workers = sysUserMapper.selectList(null);
            StringBuilder workersStr = new StringBuilder();
            for (SysUser worker : workers) {
                workersStr.append("- ID: ").append(worker.getId())
                        .append(", 姓名: ").append(worker.getUsername())
                        .append("\n");
            }

            String prompt = aiConfig.getPrompt().getWorkOrderAssign()
                    .replace("{workOrderType}", order.getCategory())
                    .replace("{description}", order.getDescription())
                    .replace("{priority}", String.valueOf(order.getUrgencyLevel()))
                    .replace("{workers}", workersStr.toString());

            String response = chat(prompt);
            return parseJsonResponse(response);
        } catch (Exception e) {
            log.error("Worker recommendation error", e);
            return "{\"recommendedWorkerIds\": [], \"reason\": \"推荐失败\"}";
        }
    }

    @Override
    public String summarizeContract(Long contractId) {
        try {
            return chat("请对合同ID " + contractId + " 进行摘要分析");
        } catch (Exception e) {
            log.error("Contract summary error", e);
            return "{\"error\": \"摘要生成失败\"}";
        }
    }

    @Override
    public String answerTenantQuestion(Long tenantId, String question) {
        try {
            String prompt = aiConfig.getPrompt().getCustomerService()
                    .replace("{question}", question)
                    .replace("{currentTime}", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    .replace("{userId}", String.valueOf(tenantId));

            return chat(prompt);
        } catch (Exception e) {
            log.error("Tenant question answer error", e);
            return "抱歉，暂时无法回答您的问题";
        }
    }

    private String parseJsonResponse(String response) {
        try {
            objectMapper.readTree(response);
            return response;
        } catch (Exception e) {
            return "{\"recommendedWorkerIds\": [], \"reason\": \"" + response + "\"}";
        }
    }
}