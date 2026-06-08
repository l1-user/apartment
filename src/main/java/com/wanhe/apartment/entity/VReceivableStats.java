package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("v_receivable_stats")
public class VReceivableStats implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 合同编号
     */
    private String contractNo;

    private BigDecimal overdueAmount;

    private BigDecimal totalUnpaid;

    private Long overdueBillCount;

    /**
     * 缴费截止日期
     */
    private LocalDate maxOverdueDate;
}
