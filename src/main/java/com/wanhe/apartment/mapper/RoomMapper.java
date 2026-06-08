package com.wanhe.apartment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wanhe.apartment.entity.Room;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 房间表 Mapper 接口
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
public interface RoomMapper extends BaseMapper<Room> {
    @Select("SELECT " +
            "   s.id AS storeId, " +
            "   s.store_name AS storeName, " +
            "   COUNT(r.id) AS totalRooms, " +
            "   SUM(CASE WHEN r.status = 1 THEN 1 ELSE 0 END) AS idleCount, " +
            "   SUM(CASE WHEN r.status = 2 THEN 1 ELSE 0 END) AS rentedCount, " +
            "   SUM(CASE WHEN r.status = 3 THEN 1 ELSE 0 END) AS maintenanceCount, " +
            "   SUM(CASE WHEN r.status = 4 THEN 1 ELSE 0 END) AS reservedCount, " +
            "   SUM(CASE WHEN r.status = 5 THEN 1 ELSE 0 END) AS bookedCount, " +
            "   ROUND(SUM(CASE WHEN r.status = 2 THEN 1 ELSE 0 END) * 100.0 / COUNT(r.id), 2) AS occupancyRate " +
            "FROM store s " +
            "LEFT JOIN building b ON s.id = b.store_id AND b.is_deleted = 0 " +
            "LEFT JOIN room r ON b.id = r.building_id AND r.is_deleted = 0 " +
            "WHERE s.is_deleted = 0 " +
            "GROUP BY s.id, s.store_name " +
            "ORDER BY s.id")
    List<Map<String, Object>> getStoreRoomStatistics();

}
