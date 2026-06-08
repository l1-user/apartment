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
    @Operation(summary = "查询所有退租申请信息")
    public List<CheckOutApplication> showAll() {
        LambdaQueryWrapper<CheckOutApplication> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckOutApplication::getIsDeleted, 0);
        wrapper.orderByDesc(CheckOutApplication::getApplicationDate);
        return checkOutApplicationService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询退租申请信息")
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
            wrapper.eq(CheckOutApplication::getApplicationStatus, applicationStatus);
        }
        wrapper.orderByDesc(CheckOutApplication::getApplicationDate);
        return checkOutApplicationService.page(page, wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询退租申请信息")
    public CheckOutApplication showById(@Parameter(description = "退租申请ID") @PathVariable Long id) {
        return checkOutApplicationService.getById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "新增退租申请")
    public boolean save(@RequestBody CheckOutApplication checkOutApplication) {
        checkOutApplication.setIsDeleted((byte) 0);
        checkOutApplication.setApplicationStatus((byte) 1);
        checkOutApplication.setApplicationDate(LocalDate.now());
        return checkOutApplicationService.save(checkOutApplication);
    }

    @PutMapping("/update")
    @Operation(summary = "更新退租申请信息")
    public boolean update(@RequestBody CheckOutApplication checkOutApplication) {
        return checkOutApplicationService.updateById(checkOutApplication);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除退租申请")
    public boolean delete(@Parameter(description = "退租申请ID") @PathVariable Long id) {
        CheckOutApplication checkOutApplication = new CheckOutApplication();
        checkOutApplication.setId(id);
        checkOutApplication.setIsDeleted((byte) 1);
        return checkOutApplicationService.updateById(checkOutApplication);
    }

    @PatchMapping("/approve/{id}")
    @Operation(summary = "审核通过退租申请")
    public boolean approve(
            @Parameter(description = "退租申请ID") @PathVariable Long id,
            @Parameter(description = "审核人") @RequestParam(required = false) String approver,
            @Parameter(description = "审核备注") @RequestParam(required = false) String approveRemark) {
        CheckOutApplication checkOutApplication = checkOutApplicationService.getById(id);
        if (checkOutApplication != null) {
            checkOutApplication.setApplicationStatus((byte) 2);
            checkOutApplication.setApprover(approver);
            checkOutApplication.setApproveRemark(approveRemark);
            checkOutApplication.setApproveTime(LocalDateTime.now());
            return checkOutApplicationService.updateById(checkOutApplication);
        }
        return false;
    }

    @PatchMapping("/reject/{id}")
    @Operation(summary = "审核拒绝退租申请")
    public boolean reject(
            @Parameter(description = "退租申请ID") @PathVariable Long id,
            @Parameter(description = "审核人") @RequestParam(required = false) String approver,
            @Parameter(description = "审核备注") @RequestParam(required = false) String approveRemark) {
        CheckOutApplication checkOutApplication = checkOutApplicationService.getById(id);
        if (checkOutApplication != null) {
            checkOutApplication.setApplicationStatus((byte) 3);
            checkOutApplication.setApprover(approver);
            checkOutApplication.setApproveRemark(approveRemark);
            checkOutApplication.setApproveTime(LocalDateTime.now());
            return checkOutApplicationService.updateById(checkOutApplication);
        }
        return false;
    }

    @PatchMapping("/complete/{id}")
    @Operation(summary = "完成退租申请")
    public boolean complete(
            @Parameter(description = "退租申请ID") @PathVariable Long id,
            @Parameter(description = "实际退租日期") @RequestParam(required = false) LocalDate actualCheckOutDate) {
        CheckOutApplication checkOutApplication = checkOutApplicationService.getById(id);
        if (checkOutApplication != null) {
            checkOutApplication.setApplicationStatus((byte) 4);
            checkOutApplication.setActualCheckOutDate(actualCheckOutDate);
            return checkOutApplicationService.updateById(checkOutApplication);
        }
        return false;
    }
}
