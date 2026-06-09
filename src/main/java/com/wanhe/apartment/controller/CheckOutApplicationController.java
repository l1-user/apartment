package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.CheckOutApplication;
import com.wanhe.apartment.service.ICheckOutApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/checkOutApplication")
@Tag(name = "退租申请管理接口")
public class CheckOutApplicationController {

    @Autowired
    private ICheckOutApplicationService checkOutApplicationService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有退租申请")
    public List<CheckOutApplication> showAll() {
        LambdaQueryWrapper<CheckOutApplication> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckOutApplication::getIsDeleted, 0);
        wrapper.orderByDesc(CheckOutApplication::getCreatedTime);
        return checkOutApplicationService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询退租申请")
    public Page<CheckOutApplication> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "租户ID") @RequestParam(required = false) Long tenantId,
            @Parameter(description = "房间ID") @RequestParam(required = false) Long roomId,
            @Parameter(description = "申请状态") @RequestParam(required = false) Integer applicationStatus) {
        Page<CheckOutApplication> page = new Page<>(current, size);
        LambdaQueryWrapper<CheckOutApplication> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckOutApplication::getIsDeleted, 0);
        if (tenantId != null) {
            wrapper.eq(CheckOutApplication::getTenantId, tenantId);
        }
        if (roomId != null) {
            wrapper.eq(CheckOutApplication::getRoomId, roomId);
        }
        if (applicationStatus != null) {
            wrapper.eq(CheckOutApplication::getApplicationStatus, applicationStatus.byteValue());
        }
        wrapper.orderByDesc(CheckOutApplication::getCreatedTime);
        return checkOutApplicationService.page(page, wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询退租申请")
    public CheckOutApplication showById(@Parameter(description = "申请ID") @PathVariable Long id) {
        return checkOutApplicationService.getById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "新增退租申请")
    public boolean save(@RequestBody CheckOutApplication application) {
        application.setIsDeleted((byte) 0);
        application.setCreatedTime(LocalDateTime.now());
        application.setApplicationDate(LocalDate.now());
        application.setApplicationStatus((byte) 1);
        application.setApplicationNo("CO" + System.currentTimeMillis());
        return checkOutApplicationService.save(application);
    }

    @PutMapping("/update")
    @Operation(summary = "更新退租申请")
    public boolean update(@RequestBody CheckOutApplication application) {
        application.setUpdatedTime(LocalDateTime.now());
        return checkOutApplicationService.updateById(application);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除退租申请")
    public boolean delete(@Parameter(description = "申请ID") @PathVariable Long id) {
        CheckOutApplication application = new CheckOutApplication();
        application.setId(id);
        application.setIsDeleted((byte) 1);
        return checkOutApplicationService.updateById(application);
    }

    @PatchMapping("/approve/{id}")
    @Operation(summary = "审核通过")
    public boolean approve(
            @Parameter(description = "申请ID") @PathVariable Long id,
            @Parameter(description = "审核人") @RequestParam(required = false) String approver,
            @Parameter(description = "审核备注") @RequestParam(required = false) String approveRemark) {
        CheckOutApplication application = new CheckOutApplication();
        application.setId(id);
        application.setApplicationStatus((byte) 2);
        application.setApprover(approver);
        application.setApproveRemark(approveRemark);
        application.setApproveTime(LocalDateTime.now());
        return checkOutApplicationService.updateById(application);
    }

    @PatchMapping("/reject/{id}")
    @Operation(summary = "审核拒绝")
    public boolean reject(
            @Parameter(description = "申请ID") @PathVariable Long id,
            @Parameter(description = "审核人") @RequestParam(required = false) String approver,
            @Parameter(description = "审核备注") @RequestParam(required = false) String approveRemark) {
        CheckOutApplication application = new CheckOutApplication();
        application.setId(id);
        application.setApplicationStatus((byte) 3);
        application.setApprover(approver);
        application.setApproveRemark(approveRemark);
        application.setApproveTime(LocalDateTime.now());
        return checkOutApplicationService.updateById(application);
    }

    @PatchMapping("/complete/{id}")
    @Operation(summary = "完成退房")
    public boolean complete(
            @Parameter(description = "申请ID") @PathVariable Long id,
            @Parameter(description = "实际退房日期") @RequestParam(required = false) String actualCheckOutDate) {
        CheckOutApplication application = new CheckOutApplication();
        application.setId(id);
        application.setApplicationStatus((byte) 4);
        if (actualCheckOutDate != null && !actualCheckOutDate.isEmpty()) {
            application.setActualCheckOutDate(LocalDate.parse(actualCheckOutDate));
        } else {
            application.setActualCheckOutDate(LocalDate.now());
        }
        return checkOutApplicationService.updateById(application);
    }
}