package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.SysUser;
import com.wanhe.apartment.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/sysUser")
@Tag(name = "用户管理接口")
public class SysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有用户信息")
    public List<SysUser> showAll() {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getIsDeleted, 0);
        wrapper.orderByAsc(SysUser::getId);
        return sysUserService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询用户信息")
    public Page<SysUser> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "用户名") @RequestParam(required = false) String username,
            @Parameter(description = "用户类型") @RequestParam(required = false) Integer userType) {
        Page<SysUser> page = new Page<>(current, size);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getIsDeleted, 0);
        if (username != null && !username.isEmpty()) {
            wrapper.like(SysUser::getUsername, username);
        }
        if (userType != null) {
            wrapper.eq(SysUser::getUserType, userType);
        }
        wrapper.orderByAsc(SysUser::getId);
        return sysUserService.page(page, wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户信息")
    public SysUser showById(@Parameter(description = "用户ID") @PathVariable Long id) {
        return sysUserService.getById(id);
    }

    @GetMapping("/searchByUsername")
    @Operation(summary = "根据用户名查询用户")
    public SysUser searchByUsername(@Parameter(description = "用户名") @RequestParam String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        wrapper.eq(SysUser::getIsDeleted, 0);
        return sysUserService.getOne(wrapper);
    }

    @PostMapping("/save")
    @Operation(summary = "新增用户")
    public boolean save(@RequestBody SysUser user) {
        user.setIsDeleted((byte) 0);
        return sysUserService.save(user);
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户信息")
    public boolean update(@RequestBody SysUser user) {
        return sysUserService.updateById(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除用户")
    public boolean delete(@Parameter(description = "用户ID") @PathVariable Long id) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setIsDeleted((byte) 1);
        return sysUserService.updateById(user);
    }

    @PatchMapping("/updateStatus/{id}")
    @Operation(summary = "更新用户状态")
    public boolean updateStatus(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Parameter(description = "状态") @RequestParam Byte status) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(status);
        return sysUserService.updateById(user);
    }

    @GetMapping("/getTenantUsers")
    @Operation(summary = "获取所有租户用户")
    public List<SysUser> getTenantUsers() {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUserType, 8);
        wrapper.eq(SysUser::getIsDeleted, 0);
        wrapper.eq(SysUser::getStatus, 1);
        wrapper.orderByAsc(SysUser::getId);
        return sysUserService.list(wrapper);
    }
}
