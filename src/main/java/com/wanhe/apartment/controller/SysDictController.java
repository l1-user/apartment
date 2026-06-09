package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.SysDict;
import com.wanhe.apartment.service.ISysDictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/sysDict")
@Tag(name = "数据字典管理")
public class SysDictController {

    @Autowired
    private ISysDictService sysDictService;

    @GetMapping("/page")
    @Operation(summary = "分页查询")
    public ResponseEntity<?> page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String dictType) {
        
        Page<SysDict> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getIsDeleted, 0);
        if (dictType != null && !dictType.isEmpty()) {
            wrapper.like(SysDict::getDictType, dictType);
        }
        wrapper.orderByDesc(SysDict::getCreatedTime);
        
        IPage<SysDict> result = sysDictService.page(page, wrapper);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", result.getRecords());
        response.put("total", result.getTotal());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    @Operation(summary = "查询所有")
    public ResponseEntity<?> list() {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getIsDeleted, 0);
        wrapper.orderByDesc(SysDict::getCreatedTime);
        List<SysDict> list = sysDictService.list(wrapper);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", list);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{dictType}")
    @Operation(summary = "按类型查询")
    public ResponseEntity<?> getByType(@PathVariable String dictType) {
        LambdaQueryWrapper<SysDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDict::getDictType, dictType);
        wrapper.eq(SysDict::getIsDeleted, 0);
        wrapper.orderByAsc(SysDict::getSortOrder);
        List<SysDict> list = sysDictService.list(wrapper);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", list);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "查询详情")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        SysDict dict = sysDictService.getById(id);
        
        Map<String, Object> response = new HashMap<>();
        if (dict != null && dict.getIsDeleted() == 0) {
            response.put("success", true);
            response.put("data", dict);
        } else {
            response.put("success", false);
            response.put("message", "数据不存在");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    @Operation(summary = "保存")
    public ResponseEntity<?> save(@RequestBody SysDict dict) {
        dict.setIsDeleted((byte) 0);
        boolean success = sysDictService.saveOrUpdate(dict);
        
        Map<String, Object> response = new HashMap<>();
        if (success) {
            response.put("success", true);
            response.put("message", "保存成功");
        } else {
            response.put("success", false);
            response.put("message", "保存失败");
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        SysDict dict = sysDictService.getById(id);
        if (dict != null) {
            dict.setIsDeleted((byte) 1);
            sysDictService.updateById(dict);
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "删除成功");
        return ResponseEntity.ok(response);
    }
}
