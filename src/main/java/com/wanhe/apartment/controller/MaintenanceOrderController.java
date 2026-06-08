package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.MaintenanceOrder;
import com.wanhe.apartment.service.IMaintenanceOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 维修工单表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/maintenanceOrder")
@Tag(name = "维修工单管理接口")
public class MaintenanceOrderController {

    @Autowired
    private IMaintenanceOrderService maintenanceOrderService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有维修工单")
    public List<MaintenanceOrder> showAll() {
        LambdaQueryWrapper<MaintenanceOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MaintenanceOrder::getIsDeleted, 0);
        wrapper.orderByDesc(MaintenanceOrder::getReportTime);
        return maintenanceOrderService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询维修工单")
    public Page<MaintenanceOrder> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "门店ID") @RequestParam(required = false) Long storeId,
            @Parameter(description = "工单状态") @RequestParam(required = false) Integer orderStatus,
            @Parameter(description = "紧急程度") @RequestParam(required = false) Integer urgencyLevel) {
        Page<MaintenanceOrder> page = new Page<>(current, size);
        LambdaQueryWrapper<MaintenanceOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MaintenanceOrder::getIsDeleted, 0);
        if (storeId != null) {
            wrapper.eq(MaintenanceOrder::getStoreId, storeId);
        }
        if (orderStatus != null) {
            wrapper.eq(MaintenanceOrder::getOrderStatus, orderStatus);
        }
        if (urgencyLevel != null) {
            wrapper.eq(MaintenanceOrder::getUrgencyLevel, urgencyLevel);
        }
        wrapper.orderByDesc(MaintenanceOrder::getReportTime);
        return maintenanceOrderService.page(page, wrapper);
    }

    @GetMapping("/listByRoom/{roomId}")
    @Operation(summary = "根据房间ID查询工单")
    public List<MaintenanceOrder> listByRoom(@Parameter(description = "房间ID") @PathVariable Long roomId) {
        LambdaQueryWrapper<MaintenanceOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MaintenanceOrder::getRoomId, roomId);
        wrapper.eq(MaintenanceOrder::getIsDeleted, 0);
        wrapper.orderByDesc(MaintenanceOrder::getReportTime);
        return maintenanceOrderService.list(wrapper);
    }

    @GetMapping("/pending")
    @Operation(summary = "查询待派单工单")
    public List<MaintenanceOrder> getPendingOrders() {
        LambdaQueryWrapper<MaintenanceOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MaintenanceOrder::getOrderStatus, 1);
        wrapper.eq(MaintenanceOrder::getIsDeleted, 0);
        wrapper.orderByDesc(MaintenanceOrder::getUrgencyLevel);
        return maintenanceOrderService.list(wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询工单")
    public MaintenanceOrder showById(@Parameter(description = "工单ID") @PathVariable Long id) {
        return maintenanceOrderService.getById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "新增维修工单")
    public boolean save(@RequestBody MaintenanceOrder order) {
        order.setIsDeleted((byte) 0);
        order.setReportTime(LocalDateTime.now());
        order.setOrderStatus((byte) 1);
        return maintenanceOrderService.save(order);
    }

    @PutMapping("/update")
    @Operation(summary = "更新工单信息")
    public boolean update(@RequestBody MaintenanceOrder order) {
        return maintenanceOrderService.updateById(order);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除工单")
    public boolean delete(@Parameter(description = "工单ID") @PathVariable Long id) {
        MaintenanceOrder order = new MaintenanceOrder();
        order.setId(id);
        order.setIsDeleted((byte) 1);
        return maintenanceOrderService.updateById(order);
    }

    @PatchMapping("/assign/{id}")
    @Operation(summary = "派单")
    public boolean assign(
            @Parameter(description = "工单ID") @PathVariable Long id,
            @Parameter(description = "维修人员ID") @RequestParam Long assigneeId,
            @Parameter(description = "维修人员姓名") @RequestParam String assigneeName) {
        MaintenanceOrder order = new MaintenanceOrder();
        order.setId(id);
        order.setAssigneeId(assigneeId);
        order.setAssigneeName(assigneeName);
        order.setAssignTime(LocalDateTime.now());
        order.setOrderStatus((byte) 2);
        return maintenanceOrderService.updateById(order);
    }

    @PatchMapping("/complete/{id}")
    @Operation(summary = "完成工单")
    public boolean complete(
            @Parameter(description = "工单ID") @PathVariable Long id,
            @Parameter(description = "维修结果") @RequestParam String repairResult,
            @Parameter(description = "实际费用") @RequestParam(required = false) BigDecimal actualCost) {
        MaintenanceOrder order = new MaintenanceOrder();
        order.setId(id);
        order.setRepairResult(repairResult);
        order.setActualCost(actualCost);
        order.setRepairTime(LocalDateTime.now());
        order.setOrderStatus((byte) 5);
        return maintenanceOrderService.updateById(order);
    }
}