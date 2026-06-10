package com.wanhe.apartment.controller;

import com.wanhe.apartment.dto.LoginRequest;
import com.wanhe.apartment.dto.LoginResponse;
import com.wanhe.apartment.entity.SysOperLog;
import com.wanhe.apartment.entity.SysUser;
import com.wanhe.apartment.service.IAuthService;
import com.wanhe.apartment.service.ISysOperLogService;
import com.wanhe.apartment.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    
    @Autowired
    private ISysOperLogService sysOperLogService;
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "支持管理员和租户登录，根据用户类型返回不同的菜单权限")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        LoginResponse response = authService.login(request);
        
        // 获取真实IP地址
        String ipAddress = getClientIpAddress(httpRequest);
        
        if (response != null) {
            // 记录成功登录的操作日志
            saveOperLog(request.getUsername(), ipAddress, "POST", "/api/auth/login", 
                       "{\"username\":\"" + request.getUsername() + "\"}", 1, "登录成功");
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("data", response);
            return ResponseEntity.ok(result);
        } else {
            // 记录登录失败的操作日志
            saveOperLog(request.getUsername(), ipAddress, "POST", "/api/auth/login", 
                       "{\"username\":\"" + request.getUsername() + "\"}", 0, "用户名或密码错误");
            
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户名或密码错误");
            return ResponseEntity.ok(result);
        }
    }
    
    /**
     * 获取客户端真实IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 如果是多个IP（代理链），取第一个
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
    
    /**
     * 保存操作日志
     */
    private void saveOperLog(String username, String ipAddress, String method, 
                           String requestUrl, String requestParams, int status, String errorMsg) {
        try {
            SysOperLog operLog = new SysOperLog();
            operLog.setUserId(null); // 登录时还没有用户ID
            operLog.setUsername(username);
            operLog.setOperation("用户登录");
            operLog.setMethod(method);
            operLog.setRequestUrl(requestUrl);
            operLog.setRequestParams(requestParams);
            operLog.setStatus((byte) status);
            operLog.setIpAddress(ipAddress);
            operLog.setErrorMsg(errorMsg);
            operLog.setOperTime(LocalDateTime.now());
            operLog.setIsDeleted((byte) 0);
            sysOperLogService.save(operLog);
        } catch (Exception e) {
            // 日志记录失败不影响业务
            e.printStackTrace();
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
