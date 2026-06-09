package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.*;
import com.wanhe.apartment.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ResponseBody
@RestController
@RequestMapping("/api/tenant")
@Tag(name = "租户门户接口")
public class TenantPortalController {

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private IContractService contractService;

    @Autowired
    private IBillService billService;

    @Autowired
    private IMaintenanceOrderService maintenanceOrderService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IBuildingService buildingService;

    @Autowired
    private IRoomTypeService roomTypeService;

    @Autowired
    private ISysUserService sysUserService;

    private Long getTenantIdByUserId(Long userId) {
        if (userId == null) {
            List<Tenant> allTenants = tenantService.list(new LambdaQueryWrapper<Tenant>().eq(Tenant::getIsDeleted, 0));
            if (!allTenants.isEmpty()) {
                return allTenants.get(0).getId();
            }
            return 1L;
        }
        
        SysUser user = sysUserService.getById(userId);
        if (user != null) {
            LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
            
            if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                wrapper.eq(Tenant::getPhone, user.getPhone());
                wrapper.eq(Tenant::getIsDeleted, 0);
                Tenant tenant = tenantService.getOne(wrapper);
                if (tenant != null) {
                    return tenant.getId();
                }
            }
            
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                wrapper.clear();
                wrapper.eq(Tenant::getEmail, user.getEmail());
                wrapper.eq(Tenant::getIsDeleted, 0);
                Tenant tenant = tenantService.getOne(wrapper);
                if (tenant != null) {
                    return tenant.getId();
                }
            }
            
            if (user.getRealName() != null && !user.getRealName().isEmpty()) {
                wrapper.clear();
                wrapper.eq(Tenant::getRealName, user.getRealName());
                wrapper.eq(Tenant::getIsDeleted, 0);
                Tenant tenant = tenantService.getOne(wrapper);
                if (tenant != null) {
                    return tenant.getId();
                }
            }
        }
        
        List<Tenant> allTenants = tenantService.list(new LambdaQueryWrapper<Tenant>().eq(Tenant::getIsDeleted, 0));
        if (!allTenants.isEmpty()) {
            return allTenants.get(0).getId();
        }
        
        return 1L;
    }

    @GetMapping("/stats/{userId}")
    @Operation(summary = "获取租户统计信息")
    public ResponseEntity<?> getTenantStats(@Parameter(description = "用户ID") @PathVariable Long userId) {
        Long tenantId = getTenantIdByUserId(userId);
        
        Map<String, Object> stats = new HashMap<>();

        LambdaQueryWrapper<Contract> contractWrapper = new LambdaQueryWrapper<>();
        contractWrapper.eq(Contract::getTenantId, tenantId);
        contractWrapper.eq(Contract::getIsDeleted, 0);
        long contractCount = contractService.count(contractWrapper);
        stats.put("contractCount", contractCount);

        contractWrapper.eq(Contract::getContractStatus, 2);
        long activeContractCount = contractService.count(contractWrapper);
        stats.put("activeContractCount", activeContractCount);

        LambdaQueryWrapper<Bill> billWrapper = new LambdaQueryWrapper<>();
        billWrapper.eq(Bill::getTenantId, tenantId);
        billWrapper.eq(Bill::getIsDeleted, 0);
        billWrapper.eq(Bill::getBillStatus, 1);
        long pendingBillCount = billService.count(billWrapper);
        stats.put("pendingBillCount", pendingBillCount);

        LambdaQueryWrapper<MaintenanceOrder> orderWrapper = new LambdaQueryWrapper<>();
        orderWrapper.eq(MaintenanceOrder::getTenantId, tenantId);
        orderWrapper.eq(MaintenanceOrder::getIsDeleted, 0);
        orderWrapper.in(MaintenanceOrder::getOrderStatus, 1, 2, 3, 4);
        long pendingOrderCount = maintenanceOrderService.count(orderWrapper);
        stats.put("pendingOrderCount", pendingOrderCount);

        stats.put("pendingCheckoutCount", 0);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "获取成功");
        result.put("data", stats);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/contracts/{userId}")
    @Operation(summary = "获取租户合同列表")
    public ResponseEntity<?> getTenantContracts(@Parameter(description = "用户ID") @PathVariable Long userId) {
        Long tenantId = getTenantIdByUserId(userId);
        
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Contract::getTenantId, tenantId);
        wrapper.eq(Contract::getIsDeleted, 0);
        wrapper.orderByDesc(Contract::getCreatedTime);
        List<Contract> contracts = contractService.list(wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "获取成功");
        result.put("data", contracts);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/bills/{userId}")
    @Operation(summary = "获取租户账单列表")
    public ResponseEntity<?> getTenantBills(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size) {
        Long tenantId = getTenantIdByUserId(userId);
        
        Page<Bill> page = new Page<>(current, size);
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bill::getTenantId, tenantId);
        wrapper.eq(Bill::getIsDeleted, 0);
        wrapper.orderByDesc(Bill::getCreatedTime);
        Page<Bill> billPage = billService.page(page, wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "获取成功");
        result.put("data", billPage);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/maintenance/orders/{userId}")
    @Operation(summary = "获取租户维修工单")
    public ResponseEntity<?> getTenantMaintenanceOrders(@Parameter(description = "用户ID") @PathVariable Long userId) {
        Long tenantId = getTenantIdByUserId(userId);
        
        LambdaQueryWrapper<MaintenanceOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MaintenanceOrder::getTenantId, tenantId);
        wrapper.eq(MaintenanceOrder::getIsDeleted, 0);
        wrapper.orderByDesc(MaintenanceOrder::getCreatedTime);
        List<MaintenanceOrder> orders = maintenanceOrderService.list(wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "获取成功");
        result.put("data", orders);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/profile/{userId}")
    @Operation(summary = "获取租户个人信息")
    public ResponseEntity<?> getTenantProfile(@Parameter(description = "用户ID") @PathVariable Long userId) {
        Long tenantId = getTenantIdByUserId(userId);
        
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getId, tenantId);
        wrapper.eq(Tenant::getIsDeleted, 0);
        Tenant tenant = tenantService.getOne(wrapper);

        Map<String, Object> result = new HashMap<>();
        if (tenant != null) {
            result.put("success", true);
            result.put("message", "获取成功");
            result.put("data", tenant);
        } else {
            result.put("success", false);
            result.put("message", "租户不存在");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/maintenance/submit")
    @Operation(summary = "提交维修工单")
    public ResponseEntity<?> submitMaintenanceOrder(@RequestBody Map<String, Object> orderData) {
        Long userId = ((Number) orderData.get("tenantId")).longValue();
        Long tenantId = getTenantIdByUserId(userId);
        
        Room currentRoom = null;
        
        // 1. 首先尝试通过房间表查询
        LambdaQueryWrapper<Room> roomWrapper = new LambdaQueryWrapper<>();
        roomWrapper.eq(Room::getCurrentTenantId, tenantId);
        roomWrapper.eq(Room::getIsDeleted, 0);
        currentRoom = roomService.getOne(roomWrapper);
        
        // 2. 如果房间表没有关联，则通过合同表查询
        if (currentRoom == null) {
            LambdaQueryWrapper<Contract> contractWrapper = new LambdaQueryWrapper<>();
            contractWrapper.eq(Contract::getTenantId, tenantId);
            contractWrapper.eq(Contract::getContractStatus, 2);
            contractWrapper.eq(Contract::getIsDeleted, 0);
            contractWrapper.orderByDesc(Contract::getStartDate);
            Contract activeContract = contractService.getOne(contractWrapper);
            
            if (activeContract != null && activeContract.getRoomId() != null) {
                currentRoom = roomService.getById(activeContract.getRoomId());
            }
        }
        
        // 3. 如果还是找不到房间，返回错误
        if (currentRoom == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "未找到您租住的房间信息，请联系管理员");
            return ResponseEntity.ok(result);
        }
        
        MaintenanceOrder order = new MaintenanceOrder();
        order.setOrderNo("WO" + System.currentTimeMillis());
        order.setTenantId(tenantId);
        order.setRoomId(currentRoom.getId());
        order.setStoreId(currentRoom.getStoreId());
        order.setCategory((String) orderData.get("category"));
        order.setUrgencyLevel(((Number) orderData.get("urgencyLevel")).byteValue());
        order.setDescription((String) orderData.get("description"));
        order.setIsDeleted((byte) 0);
        order.setOrderStatus((byte) 1);
        order.setReportTime(java.time.LocalDateTime.now());
        
        boolean success = maintenanceOrderService.save(order);

        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("success", true);
            result.put("message", "提交成功");
        } else {
            result.put("success", false);
            result.put("message", "提交失败");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/checkout/apply")
    @Operation(summary = "提交退租申请")
    public ResponseEntity<?> submitCheckoutApplication(@RequestBody Map<String, Object> application) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", false);
        result.put("message", "退租申请功能暂未开放");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/available-rooms")
    @Operation(summary = "获取可用房源")
    public ResponseEntity<?> getAvailableRooms(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size) {
        Page<Room> page = new Page<>(current, size);
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getIsDeleted, 0);
        wrapper.eq(Room::getStatus, 1);
        wrapper.orderByAsc(Room::getBuildingId).orderByAsc(Room::getFloorNumber).orderByAsc(Room::getRoomCode);
        Page<Room> roomPage = roomService.page(page, wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "获取成功");
        result.put("data", roomPage);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/unpaid-amount/{userId}")
    @Operation(summary = "获取租户未付金额")
    public ResponseEntity<?> getUnpaidAmount(@Parameter(description = "用户ID") @PathVariable Long userId) {
        Long tenantId = getTenantIdByUserId(userId);
        
        LambdaQueryWrapper<Bill> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Bill::getTenantId, tenantId);
        wrapper.eq(Bill::getIsDeleted, 0);
        wrapper.eq(Bill::getBillStatus, 1);
        BigDecimal unpaidAmount = billService.list(wrapper).stream()
                .map(Bill::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "获取成功");
        result.put("data", unpaidAmount);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/current-room/{userId}")
    @Operation(summary = "获取租户当前租住的房间")
    public ResponseEntity<?> getCurrentRoom(@Parameter(description = "用户ID") @PathVariable Long userId) {
        Long tenantId = getTenantIdByUserId(userId);
        
        Room room = null;
        
        LambdaQueryWrapper<Room> roomWrapper = new LambdaQueryWrapper<>();
        roomWrapper.eq(Room::getCurrentTenantId, tenantId);
        roomWrapper.eq(Room::getIsDeleted, 0);
        room = roomService.getOne(roomWrapper);
        
        if (room == null) {
            LambdaQueryWrapper<Contract> contractWrapper = new LambdaQueryWrapper<>();
            contractWrapper.eq(Contract::getTenantId, tenantId);
            contractWrapper.eq(Contract::getContractStatus, 2);
            contractWrapper.eq(Contract::getIsDeleted, 0);
            contractWrapper.orderByDesc(Contract::getStartDate);
            Contract activeContract = contractService.getOne(contractWrapper);
            
            if (activeContract != null && activeContract.getRoomId() != null) {
                room = roomService.getById(activeContract.getRoomId());
            }
        }

        Map<String, Object> result = new HashMap<>();
        if (room != null) {
            Building building = buildingService.getById(room.getBuildingId());
            RoomType roomType = roomTypeService.getById(room.getRoomTypeId());
            
            Map<String, Object> roomInfo = new HashMap<>();
            roomInfo.put("id", room.getId());
            roomInfo.put("roomCode", room.getRoomCode());
            roomInfo.put("roomName", room.getRoomName());
            roomInfo.put("buildingName", building != null ? building.getBuildingName() : "");
            roomInfo.put("floorNumber", room.getFloorNumber());
            roomInfo.put("area", room.getArea());
            roomInfo.put("currentRent", room.getCurrentRent());
            roomInfo.put("typeName", roomType != null ? roomType.getTypeName() : "");
            
            result.put("success", true);
            result.put("message", "获取成功");
            result.put("data", roomInfo);
        } else {
            result.put("success", false);
            result.put("message", "未找到租住的房间");
            result.put("data", null);
        }
        return ResponseEntity.ok(result);
    }
}