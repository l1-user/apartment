package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.SysOperLog;
import com.wanhe.apartment.service.ISysOperLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 操作日志表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/sysOperLog")
@Tag(name = "操作日志管理")
public class SysOperLogController {

    @Autowired
    private ISysOperLogService sysOperLogService;

    @GetMapping("/page")
    @Operation(summary = "分页查询")
    public ResponseEntity<?> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String operName,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        
        Page<SysOperLog> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysOperLog> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysOperLog::getIsDeleted, 0);
        
        if (operName != null && !operName.isEmpty()) {
            wrapper.like(SysOperLog::getUsername, operName);
        }
        if (startTime != null) {
            wrapper.ge(SysOperLog::getOperTime, startTime);
        }
        if (endTime != null) {
            wrapper.le(SysOperLog::getOperTime, endTime);
        }
        
        wrapper.orderByDesc(SysOperLog::getOperTime);
        
        IPage<SysOperLog> result = sysOperLogService.page(page, wrapper);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", result.getRecords());
        response.put("total", result.getTotal());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询详情")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        SysOperLog log = sysOperLogService.getById(id);
        
        Map<String, Object> response = new HashMap<>();
        if (log != null && log.getIsDeleted() == 0) {
            response.put("success", true);
            response.put("data", log);
        } else {
            response.put("success", false);
            response.put("message", "数据不存在");
        }
        return ResponseEntity.ok(response);
    }
}
