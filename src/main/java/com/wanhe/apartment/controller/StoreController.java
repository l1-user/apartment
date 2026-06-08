package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.Store;
import com.wanhe.apartment.service.IStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 门店/公寓表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/store")
@Tag(name = "门店管理接口")
public class StoreController {

    @Autowired
    private IStoreService storeService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有门店信息")
    public List<Store> showAll() {
        LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Store::getIsDeleted, 0);
        wrapper.orderByAsc(Store::getId);
        return storeService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询门店信息")
    public Page<Store> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "门店名称") @RequestParam(required = false) String storeName,
            @Parameter(description = "城市") @RequestParam(required = false) String city) {
        Page<Store> page = new Page<>(current, size);
        LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Store::getIsDeleted, 0);
        if (storeName != null && !storeName.isEmpty()) {
            wrapper.like(Store::getStoreName, storeName);
        }
        if (city != null && !city.isEmpty()) {
            wrapper.eq(Store::getCity, city);
        }
        wrapper.orderByAsc(Store::getId);
        return storeService.page(page, wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询门店信息")
    public Store showById(@Parameter(description = "门店ID") @PathVariable Long id) {
        return storeService.getById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "新增门店")
    public boolean save(@RequestBody Store store) {
        store.setIsDeleted((byte) 0);
        return storeService.save(store);
    }

    @PutMapping("/update")
    @Operation(summary = "更新门店信息")
    public boolean update(@RequestBody Store store) {
        return storeService.updateById(store);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除门店")
    public boolean delete(@Parameter(description = "门店ID") @PathVariable Long id) {
        Store store = new Store();
        store.setId(id);
        store.setIsDeleted((byte) 1);
        return storeService.updateById(store);
    }

    @GetMapping("/count")
    @Operation(summary = "统计门店数量")
    public long count() {
        LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Store::getIsDeleted, 0);
        return storeService.count(wrapper);
    }
}