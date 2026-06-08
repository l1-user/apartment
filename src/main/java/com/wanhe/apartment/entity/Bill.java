package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 账单表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 账单编号
     */
    private String billNo;

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
     * 门店ID
     */
    private Long storeId;

    /**
     * 账单类型: 1-租金, 2-押金, 3-水电费, 4-物业费, 5-网络费, 6-维修费, 7-违约金, 8-其他
     */
    private Byte billType;

    /**
     * 账单周期开始
     */
    private LocalDate billPeriodStart;

    /**
     * 账单周期结束
     */
    private LocalDate billPeriodEnd;

    /**
     * 缴费截止日期
     */
    private LocalDate dueDate;

    /**
     * 账单金额
     */
    private BigDecimal amount;

    /**
     * 已付金额
     */
    private BigDecimal paidAmount;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 逾期费
     */
    private BigDecimal lateFee;

    /**
     * 最终应付金额
     */
    private BigDecimal finalAmount;

    /**
     * 账单状态: 1-待支付, 2-部分支付, 3-已支付, 4-逾期, 5-已作废
     */
    private Byte billStatus;

    /**
     * 支付方式: cash, bank_transfer, alipay, wechat
     */
    private String paymentMethod;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;

    /**
     * 支付交易流水号
     */
    private String paymentTransactionId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人(系统自动生成标识为auto)
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
