package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 房间设施扩展表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("room_facility")
public class RoomFacility implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 设施名称
     */
    private String facilityName;

    /**
     * 设施分类
     */
    private String facilityType;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 状态: 0-故障, 1-正常, 2-维修中
     */
    private Byte status;

    /**
     * 购买日期
     */
    private LocalDate purchaseDate;

    /**
     * 保修截止日期
     */
    private LocalDate warrantyExpireDate;

    /**
     * 最近维护时间
     */
    private LocalDateTime lastMaintenanceTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

    /**
     * 删除标记: 0-未删除, 1-已删除
     */
    private Byte isDeleted;
}
