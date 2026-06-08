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
 * 房型定义表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("room_type")
public class RoomType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房型ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 门店ID(为空表示全局房型)
     */
    private Long storeId;

    /**
     * 房型编码
     */
    private String typeCode;

    /**
     * 房型名称(如: 单人间、双人间、loft等)
     */
    private String typeName;

    /**
     * 床型(如: 单人床、双人床、上下铺)
     */
    private String bedType;

    /**
     * 最多可住人数
     */
    private Integer maxOccupancy;

    /**
     * 面积(平方米)
     */
    private BigDecimal area;

    /**
     * 窗户类型: 1-无窗, 2-内窗, 3-外窗
     */
    private Byte windowType;

    /**
     * 是否有阳台: 0-无, 1-有
     */
    private Byte hasBalcony;

    /**
     * 是否有独立卫浴: 0-无, 1-有
     */
    private Byte hasBathroom;

    /**
     * 是否有厨房: 0-无, 1-有
     */
    private Byte hasKitchen;

    /**
     * 是否有空调: 0-无, 1-有
     */
    private Byte hasAirConditioner;

    /**
     * 是否有暖气: 0-无, 1-有
     */
    private Byte hasHeating;

    /**
     * 是否有热水器: 0-无, 1-有
     */
    private Byte hasWaterHeater;

    /**
     * 是否有洗衣机: 0-无, 1-有
     */
    private Byte hasWashingMachine;

    /**
     * 是否有冰箱: 0-无, 1-有
     */
    private Byte hasRefrigerator;

    /**
     * 是否有电视: 0-无, 1-有
     */
    private Byte hasTv;

    /**
     * 是否有WIFI: 0-无, 1-有
     */
    private Byte hasWifi;

    /**
     * 标准月租金
     */
    private BigDecimal standardRent;

    /**
     * 押金月数
     */
    private Byte depositMonths;

    /**
     * 状态: 0-停用, 1-启用
     */
    private Byte status;

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
