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
 * 保洁执行记录表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("cleaning_execution")
public class CleaningExecution implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 执行记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 保洁计划ID
     */
    private Long planId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 执行日期
     */
    private LocalDate executionDate;

    /**
     * 执行人
     */
    private String executor;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 保洁项目清单
     */
    private String cleaningItems;

    /**
     * 保洁前照片
     */
    private String beforePhotos;

    /**
     * 保洁后照片
     */
    private String afterPhotos;

    /**
     * 执行状态: 1-待执行, 2-执行中, 3-已完成, 4-已取消
     */
    private Byte executionStatus;

    /**
     * 质检结果: 1-合格, 2-不合格需返工
     */
    private Byte qualityCheckResult;

    /**
     * 质检人
     */
    private String qualityChecker;

    /**
     * 质检时间
     */
    private LocalDateTime qualityCheckTime;

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
