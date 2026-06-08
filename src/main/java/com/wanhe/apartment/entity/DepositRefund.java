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
 * 押金退还记录表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("deposit_refund")
public class DepositRefund implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 退还记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 结算ID
     */
    private Long settlementId;

    /**
     * 退还金额
     */
    private BigDecimal refundAmount;

    /**
     * 退还方式: cash, bank_transfer, alipay, wechat
     */
    private String refundMethod;

    /**
     * 退还账号
     */
    private String refundAccount;

    /**
     * 退还时间
     */
    private LocalDateTime refundTime;

    /**
     * 交易流水号
     */
    private String transactionId;

    /**
     * 经办人
     */
    private String handler;

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
