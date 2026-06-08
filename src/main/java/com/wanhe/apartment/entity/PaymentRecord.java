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
 * 支付记录表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("payment_record")
public class PaymentRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 支付记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 支付流水号
     */
    private String paymentNo;

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
     * 支付金额
     */
    private BigDecimal amount;

    /**
     * 支付方式: cash, bank_transfer, alipay, wechat
     */
    private String paymentMethod;

    /**
     * 支付渠道
     */
    private String paymentChannel;

    /**
     * 第三方交易流水号
     */
    private String transactionId;

    /**
     * 支付状态: 1-待支付, 2-支付成功, 3-支付失败, 4-已退款
     */
    private Byte paymentStatus;

    /**
     * 支付成功时间
     */
    private LocalDateTime paymentTime;

    /**
     * 收据编号
     */
    private String receiptNo;

    /**
     * 退款金额
     */
    private BigDecimal refundAmount;

    /**
     * 退款时间
     */
    private LocalDateTime refundTime;

    /**
     * 退款原因
     */
    private String refundReason;

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
