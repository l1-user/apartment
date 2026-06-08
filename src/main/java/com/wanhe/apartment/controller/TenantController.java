package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.Tenant;
import com.wanhe.apartment.service.ITenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 租户基本信息表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/tenant")
@Tag(name = "租户管理接口")
public class TenantController {

    @Autowired
    private ITenantService tenantService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有租户信息")
    public List<Tenant> showAll() {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getIsDeleted, 0);
        wrapper.orderByAsc(Tenant::getId);
        return tenantService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询租户信息")
    public Page<Tenant> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "真实姓名") @RequestParam(required = false) String realName,
            @Parameter(description = "手机号") @RequestParam(required = false) String phone,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status) {
        Page<Tenant> page = new Page<>(current, size);
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getIsDeleted, 0);
        if (realName != null && !realName.isEmpty()) {
            wrapper.like(Tenant::getRealName, realName);
        }
        if (phone != null && !phone.isEmpty()) {
            wrapper.like(Tenant::getPhone, phone);
        }
        if (status != null) {
            wrapper.eq(Tenant::getStatus, status);
        }
        wrapper.orderByDesc(Tenant::getCreatedTime);
        return tenantService.page(page, wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询租户信息")
    public Tenant showById(@Parameter(description = "租户ID") @PathVariable Long id) {
        return tenantService.getById(id);
    }

    @GetMapping("/searchByIdCard")
    @Operation(summary = "根据身份证号查询租户")
    public Tenant searchByIdCard(@Parameter(description = "身份证号") @RequestParam String idCardNo) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getIdCardNo, idCardNo);
        wrapper.eq(Tenant::getIsDeleted, 0);
        return tenantService.getOne(wrapper);
    }

    @GetMapping("/searchByPhone")
    @Operation(summary = "根据手机号查询租户")
    public Tenant searchByPhone(@Parameter(description = "手机号") @RequestParam String phone) {
        LambdaQueryWrapper<Tenant> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Tenant::getPhone, phone);
        wrapper.eq(Tenant::getIsDeleted, 0);
        return tenantService.getOne(wrapper);
    }

    @PostMapping("/save")
    @Operation(summary = "新增租户")
    public boolean save(@RequestBody Tenant tenant) {
        tenant.setIsDeleted((byte) 0);
        return tenantService.save(tenant);
    }

    @PutMapping("/update")
    @Operation(summary = "更新租户信息")
    public boolean update(@RequestBody Tenant tenant) {
        return tenantService.updateById(tenant);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除租户")
    public boolean delete(@Parameter(description = "租户ID") @PathVariable Long id) {
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setIsDeleted((byte) 1);
        return tenantService.updateById(tenant);
    }

    @PatchMapping("/updateStatus/{id}")
    @Operation(summary = "更新租户状态")
    public boolean updateStatus(
            @Parameter(description = "租户ID") @PathVariable Long id,
            @Parameter(description = "状态") @RequestParam Byte status) {
        Tenant tenant = new Tenant();
        tenant.setId(id);
        tenant.setStatus(status);
        return tenantService.updateById(tenant);
    }
}
