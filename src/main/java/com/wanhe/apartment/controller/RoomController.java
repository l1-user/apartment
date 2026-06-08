package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.Room;
import com.wanhe.apartment.service.IRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 房间表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/room")
@Tag(name = "房间管理接口")
public class RoomController {

    @Autowired
    private IRoomService roomService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有房间信息")
    public List<Room> showAll() {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getIsDeleted, 0);
        wrapper.orderByAsc(Room::getStoreId, Room::getBuildingId, Room::getFloorNumber, Room::getRoomCode);
        return roomService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询房间信息")
    public Page<Room> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "门店ID") @RequestParam(required = false) Long storeId,
            @Parameter(description = "楼栋ID") @RequestParam(required = false) Long buildingId,
            @Parameter(description = "房间状态") @RequestParam(required = false) Integer status) {
        Page<Room> page = new Page<>(current, size);
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getIsDeleted, 0);
        if (storeId != null) {
            wrapper.eq(Room::getStoreId, storeId);
        }
        if (buildingId != null) {
            wrapper.eq(Room::getBuildingId, buildingId);
        }
        if (status != null) {
            wrapper.eq(Room::getStatus, status);
        }
        wrapper.orderByAsc(Room::getStoreId, Room::getBuildingId, Room::getFloorNumber, Room::getRoomCode);
        return roomService.page(page, wrapper);
    }

    @GetMapping("/listByBuilding/{buildingId}")
    @Operation(summary = "根据楼栋ID查询房间列表")
    public List<Room> listByBuilding(@Parameter(description = "楼栋ID") @PathVariable Long buildingId) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getBuildingId, buildingId);
        wrapper.eq(Room::getIsDeleted, 0);
        wrapper.orderByAsc(Room::getFloorNumber, Room::getRoomCode);
        return roomService.list(wrapper);
    }

    @GetMapping("/listByStatus/{status}")
    @Operation(summary = "根据状态查询房间列表")
    public List<Room> listByStatus(@Parameter(description = "房间状态") @PathVariable Integer status) {
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getStatus, status);
        wrapper.eq(Room::getIsDeleted, 0);
        return roomService.list(wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询房间信息")
    public Room showById(@Parameter(description = "房间ID") @PathVariable Long id) {
        return roomService.getById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "新增房间")
    public boolean save(@RequestBody Room room) {
        room.setIsDeleted((byte) 0);
        return roomService.save(room);
    }

    @PutMapping("/update")
    @Operation(summary = "更新房间信息")
    public boolean update(@RequestBody Room room) {
        return roomService.updateById(room);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除房间")
    public boolean delete(@Parameter(description = "房间ID") @PathVariable Long id) {
        Room room = new Room();
        room.setId(id);
        room.setIsDeleted((byte) 1);
        return roomService.updateById(room);
    }

    @PatchMapping("/updateStatus/{id}")
    @Operation(summary = "更新房间状态")
    public boolean updateStatus(
            @Parameter(description = "房间ID") @PathVariable Long id,
            @Parameter(description = "房间状态") @RequestParam Byte status,
            @Parameter(description = "状态备注") @RequestParam(required = false) String statusRemark) {
        Room room = new Room();
        room.setId(id);
        room.setStatus(status);
        room.setStatusRemark(statusRemark);
        return roomService.updateById(room);
    }
}