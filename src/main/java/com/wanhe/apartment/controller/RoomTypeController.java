package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.RoomType;
import com.wanhe.apartment.service.IRoomTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 房型定义表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/roomType")
@Tag(name = "房型管理接口")
public class RoomTypeController {

    @Autowired
    private IRoomTypeService roomTypeService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有房型信息")
    public List<RoomType> showAll() {
        LambdaQueryWrapper<RoomType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoomType::getIsDeleted, 0);
        wrapper.orderByAsc(RoomType::getId);
        return roomTypeService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询房型信息")
    public Page<RoomType> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "门店ID") @RequestParam(required = false) Long storeId) {
        Page<RoomType> page = new Page<>(current, size);
        LambdaQueryWrapper<RoomType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoomType::getIsDeleted, 0);
        if (storeId != null) {
            wrapper.eq(RoomType::getStoreId, storeId);
        }
        wrapper.orderByAsc(RoomType::getId);
        return roomTypeService.page(page, wrapper);
    }

    @GetMapping("/global")
    @Operation(summary = "查询全局房型")
    public List<RoomType> getGlobalTypes() {
        LambdaQueryWrapper<RoomType> wrapper = new LambdaQueryWrapper<>();
        wrapper.isNull(RoomType::getStoreId);
        wrapper.eq(RoomType::getIsDeleted, 0);
        return roomTypeService.list(wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询房型信息")
    public RoomType showById(@Parameter(description = "房型ID") @PathVariable Long id) {
        return roomTypeService.getById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "新增房型")
    public boolean save(@RequestBody RoomType roomType) {
        roomType.setIsDeleted((byte) 0);
        return roomTypeService.save(roomType);
    }

    @PutMapping("/update")
    @Operation(summary = "更新房型信息")
    public boolean update(@RequestBody RoomType roomType) {
        return roomTypeService.updateById(roomType);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除房型")
    public boolean delete(@Parameter(description = "房型ID") @PathVariable Long id) {
        RoomType roomType = new RoomType();
        roomType.setId(id);
        roomType.setIsDeleted((byte) 1);
        return roomTypeService.updateById(roomType);
    }
}
