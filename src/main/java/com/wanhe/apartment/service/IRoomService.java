package com.wanhe.apartment.service;

import com.wanhe.apartment.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 房间表 服务类
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
public interface IRoomService extends IService<Room> {

    List<Map<String, Object>> getStoreRoomStatistics();

}
