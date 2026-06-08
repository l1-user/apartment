package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 收支明细表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("financial_transaction")
public class FinancialTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 交易流水号
     */
    private String transactionNo;

    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 交易类型: 1-收入, 2-支出
     */
    private Byte transactionType;

    /**
     * 收支分类: rent, deposit, utility, maintenance, cleaning, salary, other
     */
    private String category;

    /**
     * 子分类
     */
    private String subCategory;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 交易日期
     */
    private LocalDate transactionDate;

    /**
     * 关联账单ID
     */
    private Long relatedBillId;

    /**
     * 关联支付记录ID
     */
    private Long relatedPaymentId;

    /**
     * 关联合同ID
     */
    private Long relatedContractId;

    /**
     * 关联租户ID
     */
    private Long relatedTenantId;

    /**
     * 支付方式
     */
    private String paymentMethod;

    /**
     * 收款方/付款方
     */
    private String recipient;

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
     * 删除标记: 0-未删除, 1-已删除
     */
    private Byte isDeleted;
}
