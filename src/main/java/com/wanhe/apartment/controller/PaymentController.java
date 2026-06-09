package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wanhe.apartment.entity.Bill;
import com.wanhe.apartment.entity.PaymentRecord;
import com.wanhe.apartment.entity.Tenant;
import com.wanhe.apartment.entity.SysUser;
import com.wanhe.apartment.service.IBillService;
import com.wanhe.apartment.service.IPaymentRecordService;
import com.wanhe.apartment.service.ITenantService;
import com.wanhe.apartment.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@RestController
@RequestMapping("/api/payment")
@Tag(name = "支付接口")
public class PaymentController {

    @Autowired
    private IBillService billService;

    @Autowired
    private IPaymentRecordService paymentRecordService;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("/pay")
    @Operation(summary = "在线缴费", description = "租客在线支付账单")
    public ResponseEntity<?> pay(@RequestBody Map<String, Object> request) {
        Object userIdObj = request.get("userId");
        Long userId = null;
        if (userIdObj instanceof Long) {
            userId = (Long) userIdObj;
        } else if (userIdObj instanceof Integer) {
            userId = ((Integer) userIdObj).longValue();
        } else if (userIdObj instanceof String) {
            userId = Long.parseLong((String) userIdObj);
        }
        
        if (userId == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户ID无效");
            return ResponseEntity.ok(result);
        }
        String billNo = (String) request.get("billNo");
        String paymentMethod = (String) request.get("paymentMethod");
        BigDecimal amount = new BigDecimal(request.get("amount").toString());

        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户不存在");
            return ResponseEntity.ok(result);
        }

        LambdaQueryWrapper<Bill> billWrapper = new LambdaQueryWrapper<>();
        billWrapper.eq(Bill::getBillNo, billNo);
        billWrapper.eq(Bill::getIsDeleted, 0);
        Bill bill = billService.getOne(billWrapper);

        if (bill == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "账单不存在");
            return ResponseEntity.ok(result);
        }

        if (bill.getBillStatus() == 3) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "该账单已支付");
            return ResponseEntity.ok(result);
        }

        BigDecimal finalAmount = bill.getFinalAmount();
        if (finalAmount == null) {
            finalAmount = bill.getAmount();
        }
        
        if (finalAmount == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "账单金额为空，无法缴费");
            return ResponseEntity.ok(result);
        }
        BigDecimal paidAmount = bill.getPaidAmount() != null ? bill.getPaidAmount() : BigDecimal.ZERO;
        BigDecimal remainingAmount = finalAmount.subtract(paidAmount);
        
        if (amount.compareTo(remainingAmount) > 0) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "支付金额不能超过待支付金额");
            return ResponseEntity.ok(result);
        }

        String paymentNo = "PY" + System.currentTimeMillis();
        String transactionId = "TX" + System.currentTimeMillis();

        PaymentRecord paymentRecord = new PaymentRecord();
        paymentRecord.setPaymentNo(paymentNo);
        paymentRecord.setBillId(bill.getId());
        paymentRecord.setContractId(bill.getContractId());
        paymentRecord.setTenantId(bill.getTenantId());
        paymentRecord.setAmount(amount);
        paymentRecord.setPaymentMethod(paymentMethod);
        paymentRecord.setPaymentChannel(paymentMethod);
        paymentRecord.setTransactionId(transactionId);
        paymentRecord.setPaymentStatus((byte) 2);
        paymentRecord.setPaymentTime(LocalDateTime.now());
        paymentRecord.setCreatedBy(user.getUsername());
        paymentRecord.setCreatedTime(LocalDateTime.now());
        paymentRecord.setIsDeleted((byte) 0);

        boolean paymentSuccess = paymentRecordService.save(paymentRecord);

        if (!paymentSuccess) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "支付记录保存失败");
            return ResponseEntity.ok(result);
        }

        BigDecimal newPaidAmount = paidAmount.add(amount);
        bill.setPaidAmount(newPaidAmount);
        bill.setPaymentMethod(paymentMethod);
        bill.setPaymentTime(LocalDateTime.now());
        bill.setPaymentTransactionId(transactionId);

        if (newPaidAmount.compareTo(finalAmount) >= 0) {
            bill.setBillStatus((byte) 3);
        } else {
            bill.setBillStatus((byte) 2);
        }

        billService.updateById(bill);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "缴费成功");
        result.put("paymentNo", paymentNo);
        result.put("transactionId", transactionId);
        result.put("paidAmount", amount);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/records/{userId}")
    @Operation(summary = "获取用户缴费记录", description = "获取当前用户的所有缴费记录")
    public ResponseEntity<?> getPaymentRecords(@PathVariable Long userId) {
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户不存在");
            return ResponseEntity.ok(result);
        }

        Long tenantId = null;
        LambdaQueryWrapper<Tenant> tenantWrapper = new LambdaQueryWrapper<>();
        tenantWrapper.eq(Tenant::getPhone, user.getPhone());
        tenantWrapper.eq(Tenant::getIsDeleted, 0);
        Tenant tenant = tenantService.getOne(tenantWrapper);
        if (tenant != null) {
            tenantId = tenant.getId();
        }

        LambdaQueryWrapper<PaymentRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaymentRecord::getTenantId, tenantId);
        wrapper.eq(PaymentRecord::getIsDeleted, 0);
        wrapper.orderByDesc(PaymentRecord::getPaymentTime);
        List<PaymentRecord> records = paymentRecordService.list(wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "获取成功");
        result.put("data", records);
        return ResponseEntity.ok(result);
    }
}