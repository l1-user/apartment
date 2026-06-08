package com.wanhe.apartment.controller;

import com.wanhe.apartment.entity.DepositRefund;
import com.wanhe.apartment.service.IDepositRefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 押金退还记录表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@RestController
@RequestMapping("/api/depositRefund")
@Tag(name = "押金退还管理")
public class DepositRefundController {

    @Autowired
    private IDepositRefundService depositRefundService;

    @GetMapping("/list")
    @Operation(summary = "获取押金退还列表")
    public List<DepositRefund> list() {
        return depositRefundService.list();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取押金退还记录")
    public DepositRefund getById(@PathVariable Long id) {
        return depositRefundService.getById(id);
    }

    @PostMapping
    @Operation(summary = "新增押金退还记录")
    public boolean save(@RequestBody DepositRefund depositRefund) {
        return depositRefundService.save(depositRefund);
    }

    @PutMapping
    @Operation(summary = "更新押金退还记录")
    public boolean updateById(@RequestBody DepositRefund depositRefund) {
        return depositRefundService.updateById(depositRefund);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除押金退还记录")
    public boolean removeById(@PathVariable Long id) {
        return depositRefundService.removeById(id);
    }
}
