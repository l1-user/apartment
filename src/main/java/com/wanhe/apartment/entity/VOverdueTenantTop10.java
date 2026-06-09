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
@TableName("v_overdue_tenant_top10")
public class VOverdueTenantTop10 implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    @TableId(value = "tenant_id", type = IdType.INPUT)
    private Long tenantId;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 房间编码/房间号
     */
    private String roomCode;

    private BigDecimal overdueAmount;

    private Long overdueBillCount;

    /**
     * 缴费截止日期
     */
    private LocalDate latestOverdueDate;

    private Integer maxOverdueDays;
}
