package com.wanhe.apartment.service.impl;

import com.wanhe.apartment.entity.Room;
import com.wanhe.apartment.mapper.RoomMapper;
import com.wanhe.apartment.service.IRoomService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements IRoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<Map<String, Object>> getStoreRoomStatistics() {
        // 方法1：使用 SQL 查询
        return roomMapper.getStoreRoomStatistics();

        // 或者方法2：使用 MyBatis-Plus 查询后计算
        // return getStoreRoomStatisticsByCode();
    }

    // 方法2：使用 Java 代码计算（如果不写 SQL）
    private List<Map<String, Object>> getStoreRoomStatisticsByCode() {
        // 查询所有未删除的房间
        LambdaQueryWrapper<Room> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Room::getIsDeleted, 0);
        List<Room> rooms = this.list(wrapper);

        // 按门店ID分组统计
        Map<Long, Map<String, Object>> storeStats = new HashMap<>();

        for (Room room : rooms) {
            Long storeId = room.getStoreId();
            Integer status = Integer.valueOf(room.getStatus());

            // 初始化门店统计
            if (!storeStats.containsKey(storeId)) {
                Map<String, Object> stats = new HashMap<>();
                stats.put("storeId", storeId);
                stats.put("storeName", getStoreName(storeId)); // 需要注入 StoreService
                stats.put("totalRooms", 0);
                stats.put("idleCount", 0);
                stats.put("rentedCount", 0);
                stats.put("maintenanceCount", 0);
                stats.put("reservedCount", 0);
                stats.put("bookedCount", 0);
                storeStats.put(storeId, stats);
            }

            // 更新统计
            Map<String, Object> stats = storeStats.get(storeId);
            stats.put("totalRooms", (Integer) stats.get("totalRooms") + 1);

            switch (status) {
                case 1:
                    stats.put("idleCount", (Integer) stats.get("idleCount") + 1);
                    break;
                case 2:
                    stats.put("rentedCount", (Integer) stats.get("rentedCount") + 1);
                    break;
                case 3:
                    stats.put("maintenanceCount", (Integer) stats.get("maintenanceCount") + 1);
                    break;
                case 4:
                    stats.put("reservedCount", (Integer) stats.get("reservedCount") + 1);
                    break;
                case 5:
                    stats.put("bookedCount", (Integer) stats.get("bookedCount") + 1);
                    break;
            }
        }

        return new ArrayList<>(storeStats.values());
    }

    private String getStoreName(Long storeId) {
        // 需要注入 IStoreService
        // 简化处理，实际应该从数据库查询
        return "门店" + storeId;
    }
}