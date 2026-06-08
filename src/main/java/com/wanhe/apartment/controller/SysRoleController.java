package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.SysRole;
import com.wanhe.apartment.service.ISysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/sysRole")
@Tag(name = "角色管理接口")
public class SysRoleController {

    @Autowired
    private ISysRoleService sysRoleService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有角色信息")
    public List<SysRole> showAll() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getIsDeleted, 0);
        wrapper.orderByAsc(SysRole::getSortOrder);
        return sysRoleService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询角色信息")
    public Page<SysRole> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "角色名称") @RequestParam(required = false) String roleName) {
        Page<SysRole> page = new Page<>(current, size);
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getIsDeleted, 0);
        if (roleName != null && !roleName.isEmpty()) {
            wrapper.like(SysRole::getRoleName, roleName);
        }
        wrapper.orderByAsc(SysRole::getSortOrder);
        return sysRoleService.page(page, wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询角色信息")
    public SysRole showById(@Parameter(description = "角色ID") @PathVariable Long id) {
        return sysRoleService.getById(id);
    }

    @GetMapping("/getByRoleCode")
    @Operation(summary = "根据角色编码查询")
    public SysRole getByRoleCode(@Parameter(description = "角色编码") @RequestParam String roleCode) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleCode, roleCode);
        wrapper.eq(SysRole::getIsDeleted, 0);
        return sysRoleService.getOne(wrapper);
    }

    @PostMapping("/save")
    @Operation(summary = "新增角色")
    public boolean save(@RequestBody SysRole role) {
        role.setIsDeleted((byte) 0);
        return sysRoleService.save(role);
    }

    @PutMapping("/update")
    @Operation(summary = "更新角色信息")
    public boolean update(@RequestBody SysRole role) {
        return sysRoleService.updateById(role);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除角色")
    public boolean delete(@Parameter(description = "角色ID") @PathVariable Long id) {
        SysRole role = new SysRole();
        role.setId(id);
        role.setIsDeleted((byte) 1);
        return sysRoleService.updateById(role);
    }
}