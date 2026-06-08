package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 保洁计划表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("cleaning_plan")
public class CleaningPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 计划ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 计划编号
     */
    private String planNo;

    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 计划类型: 1-日常保洁, 2-深度保洁, 3-退租保洁, 4-定期大扫除
     */
    private Byte planType;

    /**
     * 执行频率: daily, weekly, monthly, quarterly
     */
    private String frequency;

    /**
     * 频率值(如每周几:1-7;每月几号:1-31)
     */
    private Integer frequencyValue;

    /**
     * 指派保洁人员
     */
    private String assignedTo;

    /**
     * 预计耗时(分钟)
     */
    private Integer estimatedDuration;

    /**
     * 状态: 1-启用, 0-停用
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
     * 删除标记: 0-未删除, 1-已删除
     */
    private Byte isDeleted;
}
