package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanhe.apartment.entity.Building;
import com.wanhe.apartment.entity.Store;
import com.wanhe.apartment.service.IBuildingService;
import com.wanhe.apartment.service.IStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 楼栋表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/building")
@Tag(name = "楼栋管理接口")
public class BuildingController {

    @Autowired
    private IBuildingService buildingService;

    @Autowired
    private IStoreService storeService;

    @GetMapping("/showAll")
    @Operation(summary = "查询所有楼栋信息")
    public List<Building> showAll() {
        LambdaQueryWrapper<Building> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Building::getIsDeleted, 0);
        wrapper.orderByAsc(Building::getStoreId, Building::getId);
        return buildingService.list(wrapper);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询楼栋信息")
    public Page<Building> page(
            @Parameter(description = "当前页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "门店ID") @RequestParam(required = false) Long storeId) {
        Page<Building> page = new Page<>(current, size);
        LambdaQueryWrapper<Building> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Building::getIsDeleted, 0);
        if (storeId != null) {
            wrapper.eq(Building::getStoreId, storeId);
        }
        wrapper.orderByAsc(Building::getStoreId, Building::getId);
        return buildingService.page(page, wrapper);
    }

    @GetMapping("/listByStore/{storeId}")
    @Operation(summary = "根据门店ID查询楼栋列表")
    public List<Building> listByStore(@Parameter(description = "门店ID") @PathVariable Long storeId) {
        LambdaQueryWrapper<Building> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Building::getStoreId, storeId);
        wrapper.eq(Building::getIsDeleted, 0);
        wrapper.orderByAsc(Building::getId);
        return buildingService.list(wrapper);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询楼栋信息")
    public Building showById(@Parameter(description = "楼栋ID") @PathVariable Long id) {
        return buildingService.getById(id);
    }

    @PostMapping("/save")
    @Operation(summary = "新增楼栋")
    public ResponseEntity<Map<String, Object>> save(@RequestBody Building building) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 参数校验
            if (building.getBuildingCode() == null || building.getBuildingCode().isEmpty()) {
                result.put("success", false);
                result.put("message", "楼栋编码不能为空");
                return ResponseEntity.badRequest().body(result);
            }
            if (building.getBuildingName() == null || building.getBuildingName().isEmpty()) {
                result.put("success", false);
                result.put("message", "楼栋名称不能为空");
                return ResponseEntity.badRequest().body(result);
            }
            if (building.getStoreId() == null) {
                result.put("success", false);
                result.put("message", "所属门店ID不能为空");
                return ResponseEntity.badRequest().body(result);
            }

            // 检查门店是否存在（改进版：给出更友好的提示）
            LambdaQueryWrapper<Store> storeWrapper = new LambdaQueryWrapper<>();
            storeWrapper.eq(Store::getId, building.getStoreId());
            storeWrapper.eq(Store::getIsDeleted, 0);
            Store store = storeService.getOne(storeWrapper);

            if (store == null) {
                // 获取所有可用的门店ID，给用户提示
                LambdaQueryWrapper<Store> allStoresWrapper = new LambdaQueryWrapper<>();
                allStoresWrapper.eq(Store::getIsDeleted, 0);
                allStoresWrapper.select(Store::getId, Store::getStoreName);
                List<Store> availableStores = storeService.list(allStoresWrapper);

                result.put("success", false);
                result.put("message", "门店ID不存在或已删除: " + building.getStoreId());
                result.put("availableStores", availableStores);
                result.put("hint", "可用的门店ID: " + availableStores.stream()
                        .map(s -> s.getId() + "(" + s.getStoreName() + ")")
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("无可用门店"));
                return ResponseEntity.badRequest().body(result);
            }

            // 检查楼栋编码是否已存在
            LambdaQueryWrapper<Building> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Building::getBuildingCode, building.getBuildingCode());
            wrapper.eq(Building::getStoreId, building.getStoreId());
            wrapper.eq(Building::getIsDeleted, 0);
            Building existing = buildingService.getOne(wrapper);
            if (existing != null) {
                result.put("success", false);
                result.put("message", "该门店下楼栋编码已存在");
                return ResponseEntity.badRequest().body(result);
            }

            // 设置默认值
            building.setId(null);
            building.setIsDeleted((byte) 0);
            building.setCreatedTime(LocalDateTime.now());
            building.setUpdatedTime(LocalDateTime.now());
            if (building.getStatus() == null) {
                building.setStatus((byte) 1);
            }
            if (building.getTotalRooms() == null) {
                building.setTotalRooms(0);
            }
            if (building.getTotalFloors() == null) {
                building.setTotalFloors(1);
            }
            if (building.getHasElevator() == null) {
                building.setHasElevator((byte) 0);
            }

            boolean success = buildingService.save(building);
            result.put("success", success);
            result.put("message", success ? "新增成功" : "新增失败");
            result.put("data", building);

            if (success) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "新增失败: " + e.getMessage());
            result.put("error", e.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新楼栋信息")
    public boolean update(@RequestBody Building building) {
        return buildingService.updateById(building);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "逻辑删除楼栋")
    public boolean delete(@Parameter(description = "楼栋ID") @PathVariable Long id) {
        Building building = new Building();
        building.setId(id);
        building.setIsDeleted((byte) 1);
        return buildingService.updateById(building);
    }
}