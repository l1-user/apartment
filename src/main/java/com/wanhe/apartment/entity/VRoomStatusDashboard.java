package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("v_room_status_dashboard")
public class VRoomStatusDashboard implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 门店ID
     */
    @TableId(value = "store_id", type = IdType.INPUT)
    private Long storeId;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 楼栋ID
     */
    private Long buildingId;

    /**
     * 楼栋名称
     */
    private String buildingName;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 房间编码/房间号
     */
    private String roomCode;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 所在楼层
     */
    private Integer floorNumber;

    /**
     * 房型名称(如: 单人间、双人间、loft等)
     */
    private String roomTypeName;

    /**
     * 实际面积(平方米)
     */
    private BigDecimal area;

    /**
     * 房间状态: 1-空闲, 2-已租, 3-维修中, 4-预留, 5-已预订
     */
    private Byte roomStatus;

    private String statusText;

    private String statusColor;

    /**
     * 当前租金
     */
    private BigDecimal currentRent;

    /**
     * 当前租客ID(关联租客表)
     */
    private Long currentTenantId;

    /**
     * 状态备注(如维修原因、预留说明等)
     */
    private String statusRemark;

    /**
     * 更新时间
     */
    private LocalDateTime lastStatusUpdate;
}
