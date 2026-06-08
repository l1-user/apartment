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
 * 逾期记录表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("overdue_record")
public class OverdueRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账单ID
     */
    private Long billId;

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 逾期天数
     */
    private Integer overdueDays;

    /**
     * 逾期金额
     */
    private BigDecimal overdueAmount;

    /**
     * 产生的逾期费
     */
    private BigDecimal lateFee;

    /**
     * 通知状态: 0-未通知, 1-已通知, 2-已催收
     */
    private Byte notifyStatus;

    /**
     * 最后通知时间
     */
    private LocalDateTime notifyTime;

    /**
     * 处理状态: 0-未处理, 1-已处理
     */
    private Byte resolveStatus;

    /**
     * 处理时间
     */
    private LocalDateTime resolveTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标记: 0-未删除, 1-已删除
     */
    private Byte isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}
