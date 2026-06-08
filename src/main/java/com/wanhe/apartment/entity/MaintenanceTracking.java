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
 * 维修工单进度跟踪表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("maintenance_tracking")
public class MaintenanceTracking implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 跟踪记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 工单ID
     */
    private Long orderId;

    /**
     * 操作类型: assigned, accepted, start_repair, complete, cancel, comment
     */
    private String actionType;

    /**
     * 操作描述
     */
    private String actionDescription;

    /**
     * 操作人ID
     */
    private Long operatorId;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作时间
     */
    private LocalDateTime actionTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标记: 0-未删除, 1-已删除
     */
    private Byte isDeleted;
}
