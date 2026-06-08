package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.Contract;
import com.wanhe.apartment.entity.Room;
import com.wanhe.apartment.service.IContractService;
import com.wanhe.apartment.service.IRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 合同表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@RestController
@RequestMapping("/api/contract")
@Tag(name = "合同管理接口")
public class ContractController {

    @Autowired
    private IContractService contractService;

    @Autowired
    private IRoomService roomService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有合同信息")
    public List<Contract> showAll() {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Contract::getIsDeleted, 0);
        wrapper.orderByDesc(Contract::getSignDate);
        return contractService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询合同信息")
    public Page<Contract> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "门店ID") @RequestParam(required = false) Long storeId,
            @Parameter(description = "合同状态") @RequestParam(required = false) Integer contractStatus) {
        Page<Contract> page = new Page<>(current, size);
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Contract::getIsDeleted, 0);
        if (storeId != null) {
            wrapper.eq(Contract::getStoreId, storeId);
        }
        if (contractStatus != null) {
            wrapper.eq(Contract::getContractStatus, contractStatus);
        }
        wrapper.orderByDesc(Contract::getSignDate);
        return contractService.page(page, wrapper);
    }

    @GetMapping("/listByTenant/{tenantId}")
    @Operation(summary = "根据租户ID查询合同列表")
    public List<Contract> listByTenant(@Parameter(description = "租户ID") @PathVariable Long tenantId) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Contract::getTenantId, tenantId);
        wrapper.eq(Contract::getIsDeleted, 0);
        wrapper.orderByDesc(Contract::getSignDate);
        return contractService.list(wrapper);
    }

    @GetMapping("/listByRoom/{roomId}")
    @Operation(summary = "根据房间ID查询合同列表")
    public List<Contract> listByRoom(@Parameter(description = "房间ID") @PathVariable Long roomId) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Contract::getRoomId, roomId);
        wrapper.eq(Contract::getIsDeleted, 0);
        wrapper.orderByDesc(Contract::getSignDate);
        return contractService.list(wrapper);
    }

    @GetMapping("/active")
    @Operation(summary = "查询生效中的合同")
    public List<Contract> getActiveContracts() {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Contract::getContractStatus, 2);
        wrapper.eq(Contract::getIsDeleted, 0);
        wrapper.le(Contract::getStartDate, LocalDate.now());
        wrapper.ge(Contract::getEndDate, LocalDate.now());
        return contractService.list(wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询合同信息")
    public Contract showById(@Parameter(description = "合同ID") @PathVariable Long id) {
        return contractService.getById(id);
    }

    @GetMapping("/getByContractNo")
    @Operation(summary = "根据合同编号查询")
    public Contract getByContractNo(@Parameter(description = "合同编号") @RequestParam String contractNo) {
        LambdaQueryWrapper<Contract> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Contract::getContractNo, contractNo);
        wrapper.eq(Contract::getIsDeleted, 0);
        return contractService.getOne(wrapper);
    }

    @PostMapping("/save")
    @Operation(summary = "新增合同")
    public boolean save(@RequestBody Contract contract) {
        // 如果未设置storeId，根据roomId自动获取
        if (contract.getStoreId() == null && contract.getRoomId() != null) {
            Room room = roomService.getById(contract.getRoomId());
            if (room != null) {
                contract.setStoreId(room.getStoreId());
            }
        }
        contract.setIsDeleted((byte) 0);
        return contractService.save(contract);
    }

    @PutMapping("/update")
    @Operation(summary = "更新合同信息")
    public boolean update(@RequestBody Contract contract) {
        return contractService.updateById(contract);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除合同")
    public boolean delete(@Parameter(description = "合同ID") @PathVariable Long id) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setIsDeleted((byte) 1);
        return contractService.updateById(contract);
    }

    @PatchMapping("/updateStatus/{id}")
    @Operation(summary = "更新合同状态")
    public boolean updateStatus(
            @Parameter(description = "合同ID") @PathVariable Long id,
            @Parameter(description = "合同状态") @RequestParam Byte contractStatus) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setContractStatus(contractStatus);
        return contractService.updateById(contract);
    }
}
