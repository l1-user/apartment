package com.wanhe.apartment.entity;

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
@TableName("v_active_rooms")
public class VActiveRooms implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房间ID
     */
    private Long id;

    /**
     * 房间编码/房间号
     */
    private String roomCode;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 所属门店ID
     */
    private Long storeId;

    /**
     * 所属楼栋ID
     */
    private Long buildingId;

    /**
     * 所在楼层
     */
    private Integer floorNumber;

    /**
     * 房型ID
     */
    private Long roomTypeId;

    /**
     * 实际面积(平方米)
     */
    private BigDecimal area;

    /**
     * 朝向: 东、南、西、北、东南等
     */
    private String orientation;

    /**
     * 房间状态: 1-空闲, 2-已租, 3-维修中, 4-预留, 5-已预订
     */
    private Byte status;

    /**
     * 当前租金
     */
    private BigDecimal currentRent;

    /**
     * 租金单位: 月/季/年
     */
    private String rentUnit;

    /**
     * 当前租客ID(关联租客表)
     */
    private Long currentTenantId;

    /**
     * 最近维护时间
     */
    private LocalDateTime lastMaintenanceTime;

    /**
     * 下次维护时间
     */
    private LocalDateTime nextMaintenanceTime;

    /**
     * 状态备注(如维修原因、预留说明等)
     */
    private String statusRemark;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 删除标记
     */
    private Byte isDeleted;
}
