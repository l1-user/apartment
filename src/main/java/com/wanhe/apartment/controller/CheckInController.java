package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.CheckIn;
import com.wanhe.apartment.service.ICheckInService;
import com.wanhe.apartment.service.IRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@ResponseBody
@RestController
@RequestMapping("/api/checkIn")
@Tag(name = "入住登记管理接口")
public class CheckInController {

    @Autowired
    private IRoomService roomService;

    @Autowired
    private ICheckInService checkInService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有入住登记信息")
    public List<CheckIn> showAll() {
        LambdaQueryWrapper<CheckIn> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckIn::getIsDeleted, 0);
        wrapper.orderByDesc(CheckIn::getCreatedTime);
        return checkInService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询入住登记信息")
    public Page<CheckIn> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "房间ID") @RequestParam(required = false) Long roomId,
            @Parameter(description = "租户ID") @RequestParam(required = false) Long tenantId,
            @Parameter(description = "合同ID") @RequestParam(required = false) Long contractId,
            @Parameter(description = "状态") @RequestParam(required = false) Byte status) {
        Page<CheckIn> page = new Page<>(current, size);
        LambdaQueryWrapper<CheckIn> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CheckIn::getIsDeleted, 0);
        if (roomId != null) {
            wrapper.eq(CheckIn::getRoomId, roomId);
        }
        if (tenantId != null) {
            wrapper.eq(CheckIn::getTenantId, tenantId);
        }
        if (contractId != null) {
            wrapper.eq(CheckIn::getContractId, contractId);
        }
        if (status != null) {
            wrapper.eq(CheckIn::getStatus, status);
        }
        wrapper.orderByDesc(CheckIn::getCreatedTime);
        return checkInService.page(page, wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询入住登记信息")
    public CheckIn showById(@Parameter(description = "入住登记ID") @PathVariable Long id) {
        return checkInService.getById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "新增入住登记")
    public boolean save(@RequestBody CheckIn checkIn) {
        checkIn.setIsDeleted((byte) 0);
        checkIn.setCreatedTime(LocalDateTime.now());
        return checkInService.save(checkIn);
    }

    @PutMapping("/update")
    @Operation(summary = "更新入住登记信息")
    public boolean update(@RequestBody CheckIn checkIn) {
        return checkInService.updateById(checkIn);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除入住登记")
    public boolean delete(@Parameter(description = "入住登记ID") @PathVariable Long id) {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(id);
        checkIn.setIsDeleted((byte) 1);
        return checkInService.updateById(checkIn);
    }

    @PatchMapping("/updateStatus/{id}")
    @Operation(summary = "更新入住登记状态")
    public boolean updateStatus(
            @Parameter(description = "入住登记ID") @PathVariable Long id,
            @Parameter(description = "状态") @RequestParam Byte status) {
        CheckIn checkIn = new CheckIn();
        checkIn.setId(id);
        checkIn.setStatus(status);
        return checkInService.updateById(checkIn);
    }
}
