package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 楼栋表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
public class Building implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 楼栋ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 楼栋编码
     */
    private String buildingCode;

    /**
     * 楼栋名称
     */
    private String buildingName;

    /**
     * 所属门店ID
     */
    private Long storeId;

    /**
     * 总楼层数
     */
    private Integer totalFloors;

    /**
     * 总房间数
     */
    private Integer totalRooms;

    /**
     * 是否有电梯: 0-无, 1-有
     */
    private Byte hasElevator;

    /**
     * 建造年份
     */
    private LocalDate constructionYear;

    /**
     * 状态: 0-停用, 1-正常, 2-维护中
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
