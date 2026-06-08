package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wanhe.apartment.entity.Room;
import com.wanhe.apartment.service.IRoomService;
import com.wanhe.apartment.service.IStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 房态看板 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@RestController
@RequestMapping("/api/dashboard")
@Tag(name = "房态看板接口")
public class DashboardController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private IStoreService storeService;

    @GetMapping("/roomStatusStats")
    @Operation(summary = "获取房态统计")
    public Map<Integer, Long> getRoomStatusStats(
            @Parameter(description = "门店ID") @RequestParam(required = false) Long storeId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getIsDeleted, 0);
        if (storeId != null) {
            wrapper.eq(Room::getStoreId, storeId);
        }
        List<Room> rooms = roomService.list(wrapper);

        Map<Integer, Long> stats = new HashMap<>();
        stats.put(1, rooms.stream().filter(r -> r.getStatus() == 1).count()); // 空闲
        stats.put(2, rooms.stream().filter(r -> r.getStatus() == 2).count()); // 已租
        stats.put(3, rooms.stream().filter(r -> r.getStatus() == 3).count()); // 维修中
        stats.put(4, rooms.stream().filter(r -> r.getStatus() == 4).count()); // 预留
        stats.put(5, rooms.stream().filter(r -> r.getStatus() == 5).count()); // 已预订
        return stats;
    }

    @GetMapping("/roomListByStatus")
    @Operation(summary = "根据状态获取房间列表")
    public List<Room> getRoomListByStatus(
            @Parameter(description = "房间状态") @RequestParam Integer status,
            @Parameter(description = "门店ID") @RequestParam(required = false) Long storeId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getStatus, status);
        wrapper.eq(Room::getIsDeleted, 0);
        if (storeId != null) {
            wrapper.eq(Room::getStoreId, storeId);
        }
        wrapper.orderByAsc(Room::getStoreId, Room::getBuildingId, Room::getFloorNumber, Room::getRoomCode);
        return roomService.list(wrapper);
    }

    @GetMapping("/storeRoomStats")
    @Operation(summary = "获取各门店房态统计")
    public List<Map<String, Object>> getStoreRoomStats() {
        return roomService.getStoreRoomStatistics();
    }
}
