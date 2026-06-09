package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wanhe.apartment.dto.LoginRequest;
import com.wanhe.apartment.dto.LoginResponse;
import com.wanhe.apartment.entity.SysUser;
import com.wanhe.apartment.service.IAuthService;
import com.wanhe.apartment.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 处理用户登录、验证、退出等操作
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证接口")
public class AuthController {
    
    @Autowired
    private IAuthService authService;
    
    @Autowired
    private ISysUserService sysUserService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "支持管理员和租户登录，根据用户类型返回不同的菜单权限")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        
        if (response != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("data", response);
            return ResponseEntity.ok(result);
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return ResponseEntity.ok(result);
        }
    }
    
    /**
     * 验证Token
     */
    @GetMapping("/validate")
    @Operation(summary = "验证Token", description = "验证登录令牌是否有效")
    public ResponseEntity<?> validateToken(
            @Parameter(description = "Bearer Token") @RequestHeader(value = "Authorization", required = false) String token) {
        
        LoginResponse response = authService.validateToken(token);
        
        if (response != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "Token有效");
            result.put("data", response);
            return ResponseEntity.ok(result);
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "Token无效或已过期");
            return ResponseEntity.ok(result);
        }
    }
    
    /**
     * 用户退出
     */
    @PostMapping("/logout")
    @Operation(summary = "用户退出", description = "清除用户登录状态")
    public ResponseEntity<?> logout(
            @Parameter(description = "Bearer Token") @RequestHeader(value = "Authorization", required = false) String token) {
        
        authService.logout(token);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "退出成功");
        return ResponseEntity.ok(result);
    }
    
    /**
     * 获取登录用户信息
     */
    @GetMapping("/userInfo")
    @Operation(summary = "获取用户信息", description = "获取当前登录用户的详细信息")
    public ResponseEntity<?> getUserInfo(
            @Parameter(description = "Bearer Token") @RequestHeader(value = "Authorization", required = false) String token) {
        
        LoginResponse response = authService.validateToken(token);
        
        if (response != null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "获取成功");
            result.put("data", response);
            return ResponseEntity.ok(result);
        } else {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "请先登录");
            return ResponseEntity.ok(result);
        }
    }
    
    /**
     * 修改密码
     */
    @PostMapping("/changePassword")
    @Operation(summary = "修改密码", description = "用户修改自己的密码")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, Object> request) {
        Long userId = ((Number) request.get("userId")).longValue();
        String oldPassword = (String) request.get("oldPassword");
        String newPassword = (String) request.get("newPassword");
        
        // 验证参数
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "请输入原密码");
            return ResponseEntity.ok(result);
        }
        
        if (newPassword == null || newPassword.trim().isEmpty()) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "请输入新密码");
            return ResponseEntity.ok(result);
        }
        
        if (newPassword.length() < 6) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "新密码长度不能少于6位");
            return ResponseEntity.ok(result);
        }
        
        // 查询用户
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户不存在");
            return ResponseEntity.ok(result);
        }
        
        // 验证原密码
        if (!user.getPassword().equals(oldPassword)) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "原密码错误");
            return ResponseEntity.ok(result);
        }
        
        // 更新密码
        user.setPassword(newPassword);
        boolean success = sysUserService.updateById(user);
        
        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("success", true);
            result.put("message", "密码修改成功");
        } else {
            result.put("success", false);
            result.put("message", "密码修改失败");
        }
        return ResponseEntity.ok(result);
    }
}
