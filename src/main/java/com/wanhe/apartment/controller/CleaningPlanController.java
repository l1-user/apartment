package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.CleaningPlan;
import com.wanhe.apartment.service.ICleaningPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/cleaningPlan")
@Tag(name = "保洁计划管理接口")
public class CleaningPlanController {

    @Autowired
    private ICleaningPlanService cleaningPlanService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有保洁计划")
    public List<CleaningPlan> showAll() {
        LambdaQueryWrapper<CleaningPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CleaningPlan::getIsDeleted, 0);
        wrapper.orderByDesc(CleaningPlan::getCreatedTime);
        return cleaningPlanService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询保洁计划")
    public Page<CleaningPlan> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "门店ID") @RequestParam(required = false) Long storeId,
            @Parameter(description = "计划类型") @RequestParam(required = false) Byte planType,
            @Parameter(description = "计划状态") @RequestParam(required = false) Byte planStatus,
            @Parameter(description = "计划状态") @RequestParam(required = false) Byte status,
            @Parameter(description = "指派保洁人员") @RequestParam(required = false) String assignedTo) {
        Page<CleaningPlan> page = new Page<>(current, size);
        LambdaQueryWrapper<CleaningPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CleaningPlan::getIsDeleted, 0);
        if (storeId != null) {
            wrapper.eq(CleaningPlan::getStoreId, storeId);
        }
        if (planType != null) {
            wrapper.eq(CleaningPlan::getPlanType, planType);
        }
        Byte finalStatus = planStatus != null ? planStatus : status;
        if (finalStatus != null) {
            wrapper.eq(CleaningPlan::getStatus, finalStatus);
        }
        if (assignedTo != null && !assignedTo.isEmpty()) {
            wrapper.like(CleaningPlan::getAssignedTo, assignedTo);
        }
        wrapper.orderByDesc(CleaningPlan::getCreatedTime);
        return cleaningPlanService.page(page, wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询保洁计划")
    public CleaningPlan showById(@Parameter(description = "保洁计划ID") @PathVariable Long id) {
        return cleaningPlanService.getById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "新增保洁计划")
    public boolean save(@RequestBody CleaningPlan cleaningPlan) {
        cleaningPlan.setIsDeleted((byte) 0);
        cleaningPlan.setStatus((byte) 1);
        cleaningPlan.setCreatedTime(LocalDateTime.now());
        return cleaningPlanService.save(cleaningPlan);
    }

    @PutMapping("/update")
    @Operation(summary = "更新保洁计划")
    public boolean update(@RequestBody CleaningPlan cleaningPlan) {
        return cleaningPlanService.updateById(cleaningPlan);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除保洁计划")
    public boolean delete(@Parameter(description = "保洁计划ID") @PathVariable Long id) {
        CleaningPlan cleaningPlan = new CleaningPlan();
        cleaningPlan.setId(id);
        cleaningPlan.setIsDeleted((byte) 1);
        return cleaningPlanService.updateById(cleaningPlan);
    }

    @PatchMapping("/updateStatus/{id}")
    @Operation(summary = "更新保洁计划状态")
    public boolean updateStatus(
            @Parameter(description = "保洁计划ID") @PathVariable Long id,
            @Parameter(description = "状态: 1-启用, 0-停用") @RequestParam Byte status,
            @Parameter(description = "指派保洁人员") @RequestParam(required = false) String assignedTo) {
        CleaningPlan cleaningPlan = new CleaningPlan();
        cleaningPlan.setId(id);
        cleaningPlan.setStatus(status);
        if (assignedTo != null) {
            cleaningPlan.setAssignedTo(assignedTo);
        }
        return cleaningPlanService.updateById(cleaningPlan);
    }
}
