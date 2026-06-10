package com.wanhe.apartment.service;

import java.util.Map;

public interface IAiService {
    String chat(String message);
    String recommendWorker(Long workOrderId);
    String summarizeContract(Long contractId);
    String answerTenantQuestion(Long tenantId, String question);
}