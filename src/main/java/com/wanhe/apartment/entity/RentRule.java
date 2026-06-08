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
 * 租金规则配置表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("rent_rule")
public class RentRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规则ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 门店ID(为空表示全局规则)
     */
    private Long storeId;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 支持付款周期: ["monthly","quarterly","yearly"]
     */
    private String paymentCycles;

    /**
     * 每月付款截止日(几号)
     */
    private Integer paymentDeadlineDay;

    /**
     * 逾期费率(%/天)
     */
    private BigDecimal lateFeeRate;

    /**
     * 最低逾期费
     */
    private BigDecimal lateFeeMin;

    /**
     * 押金月数
     */
    private Byte depositMonths;

    /**
     * 是否允许自动续租
     */
    private Byte allowAutoRenew;

    /**
     * 提前续租提醒天数
     */
    private Integer autoRenewDays;

    /**
     * 宽限期天数
     */
    private Integer gracePeriodDays;

    /**
     * 是否默认规则
     */
    private Byte isDefault;

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
     * 删除标记: 0-未删除, 1-已删除
     */
    private Byte isDeleted;
}
