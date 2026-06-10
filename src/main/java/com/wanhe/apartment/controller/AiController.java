package com.wanhe.apartment.controller;

import com.wanhe.apartment.service.IAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@Tag(name = "AI服务", description = "AI大模型接口")
public class AiController {

    private final IAiService aiService;

    @PostMapping("/chat")
    @Operation(summary = "AI对话", description = "与AI进行对话")
    public ResponseEntity<Map<String, Object>> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        String response = aiService.chat(message);
        Map<String, Object> result = new HashMap<>();
        result.put("response", response);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/recommend-worker/{workOrderId}")
    @Operation(summary = "智能派单推荐", description = "根据工单信息推荐合适的维修人员")
    public ResponseEntity<String> recommendWorker(@PathVariable Long workOrderId) {
        String response = aiService.recommendWorker(workOrderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/summarize-contract/{contractId}")
    @Operation(summary = "合同摘要", description = "生成合同摘要")
    public ResponseEntity<Map<String, Object>> summarizeContract(@PathVariable Long contractId) {
        String response = aiService.summarizeContract(contractId);
        Map<String, Object> result = new HashMap<>();
        result.put("summary", response);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/tenant-question")
    @Operation(summary = "租户问答", description = "回答租户问题")
    public ResponseEntity<Map<String, Object>> answerTenantQuestion(@RequestBody Map<String, Object> request) {
        Long tenantId = ((Number) request.get("tenantId")).longValue();
        String question = (String) request.get("question");
        String response = aiService.answerTenantQuestion(tenantId, question);
        Map<String, Object> result = new HashMap<>();
        result.put("answer", response);
        return ResponseEntity.ok(result);
    }
}