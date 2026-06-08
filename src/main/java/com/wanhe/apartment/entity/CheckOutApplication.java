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
 * 退租申请表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("check_out_application")
public class CheckOutApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 申请ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 申请编号
     */
    private String applicationNo;

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 申请日期
     */
    private LocalDate applicationDate;

    /**
     * 预计退租日期
     */
    private LocalDate expectedCheckOutDate;

    /**
     * 实际退租日期
     */
    private LocalDate actualCheckOutDate;

    /**
     * 退租原因
     */
    private String checkOutReason;

    /**
     * 申请状态: 1-待审核, 2-审核通过, 3-已拒绝, 4-已完成
     */
    private Byte applicationStatus;

    /**
     * 审核人
     */
    private String approver;

    /**
     * 审核时间
     */
    private LocalDateTime approveTime;

    /**
     * 审核备注
     */
    private String approveRemark;

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
