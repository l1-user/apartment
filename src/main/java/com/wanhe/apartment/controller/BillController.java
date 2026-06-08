package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.Bill;
import com.wanhe.apartment.entity.Contract;
import com.wanhe.apartment.service.IBillService;
import com.wanhe.apartment.service.IContractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 账单表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@RestController
@RequestMapping("/api/bill")
@Tag(name = "账单管理接口")
public class BillController {

    @Autowired
    private IBillService billService;

    @Autowired
    private IContractService contractService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有账单信息")
    public List<Bill> showAll() {
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bill::getIsDeleted, 0);
        wrapper.orderByDesc(Bill::getDueDate);
        return billService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询账单信息")
    public Page<Bill> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "租户ID") @RequestParam(required = false) Long tenantId,
            @Parameter(description = "账单状态") @RequestParam(required = false) Integer billStatus,
            @Parameter(description = "账单类型") @RequestParam(required = false) Integer billType) {
        Page<Bill> page = new Page<>(current, size);
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bill::getIsDeleted, 0);
        if (tenantId != null) {
            wrapper.eq(Bill::getTenantId, tenantId);
        }
        if (billStatus != null) {
            wrapper.eq(Bill::getBillStatus, billStatus);
        }
        if (billType != null) {
            wrapper.eq(Bill::getBillType, billType);
        }
        wrapper.orderByDesc(Bill::getDueDate);
        return billService.page(page, wrapper);
    }

    @GetMapping("/listByTenant/{tenantId}")
    @Operation(summary = "根据租户ID查询账单列表")
    public List<Bill> listByTenant(@Parameter(description = "租户ID") @PathVariable Long tenantId) {
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bill::getTenantId, tenantId);
        wrapper.eq(Bill::getIsDeleted, 0);
        wrapper.orderByDesc(Bill::getDueDate);
        return billService.list(wrapper);
    }

    @GetMapping("/overdue")
    @Operation(summary = "查询逾期账单")
    public List<Bill> getOverdueBills() {
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bill::getBillStatus, 4);
        wrapper.eq(Bill::getIsDeleted, 0);
        wrapper.orderByDesc(Bill::getDueDate);
        return billService.list(wrapper);
    }

    @GetMapping("/unpaid")
    @Operation(summary = "查询未付账单")
    public List<Bill> getUnpaidBills() {
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Bill::getBillStatus, 1, 2);
        wrapper.eq(Bill::getIsDeleted, 0);
        wrapper.orderByAsc(Bill::getDueDate);
        return billService.list(wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询账单信息")
    public Bill showById(@Parameter(description = "账单ID") @PathVariable Long id) {
        return billService.getById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "新增账单")
    public boolean save(@RequestBody Bill bill) {
        // 验证contractId是否存在
        if (bill.getContractId() != null) {
            Contract contract = contractService.getById(bill.getContractId());
            if (contract == null) {
                throw new IllegalArgumentException("合同ID不存在: " + bill.getContractId());
            }
        } else {
            throw new IllegalArgumentException("合同ID不能为空");
        }
        bill.setIsDeleted((byte) 0);
        return billService.save(bill);
    }

    @PutMapping("/update")
    @Operation(summary = "更新账单信息")
    public boolean update(@RequestBody Bill bill) {
        return billService.updateById(bill);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除账单")
    public boolean delete(@Parameter(description = "账单ID") @PathVariable Long id) {
        Bill bill = new Bill();
        bill.setId(id);
        bill.setIsDeleted((byte) 1);
        return billService.updateById(bill);
    }

    @PostMapping("/pay/{id}")
    @Operation(summary = "账单支付")
    public boolean pay(
            @Parameter(description = "账单ID") @PathVariable Long id,
            @Parameter(description = "支付金额") @RequestParam BigDecimal paidAmount,
            @Parameter(description = "支付方式") @RequestParam String paymentMethod) {
        Bill bill = billService.getById(id);
        if (bill != null) {
            BigDecimal newPaidAmount = bill.getPaidAmount().add(paidAmount);
            bill.setPaidAmount(newPaidAmount);
            if (newPaidAmount.compareTo(bill.getFinalAmount()) >= 0) {
                bill.setBillStatus((byte) 3); // 已支付
            } else if (newPaidAmount.compareTo(BigDecimal.ZERO) > 0) {
                bill.setBillStatus((byte) 2); // 部分支付
            }
            bill.setPaymentMethod(paymentMethod);
            bill.setPaymentTime(LocalDate.now().atStartOfDay());
            return billService.updateById(bill);
        }
        return false;
    }
}
