package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("v_store_operation_report")
public class VStoreOperationReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 门店ID
     */
    @TableId(value = "store_id", type = IdType.INPUT)
    private Long storeId;

    /**
     * 门店名称
     */
    private String storeName;

    private String reportMonth;

    private BigDecimal totalIncome;

    private BigDecimal totalExpense;

    private BigDecimal netProfit;

    private BigDecimal rentIncome;

    private BigDecimal depositIncome;

    private BigDecimal utilityIncome;

    private Long payingTenantCount;
}
