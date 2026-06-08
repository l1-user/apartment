package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 费用结算表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
public class Settlement implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 结算ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 结算编号
     */
    private String settlementNo;

    /**
     * 退租申请ID
     */
    private Long checkOutApplicationId;

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 押金金额
     */
    private BigDecimal depositAmount;

    /**
     * 扣款金额
     */
    private BigDecimal deductionAmount;

    /**
     * 未付账单金额
     */
    private BigDecimal unpaidBillAmount;

    /**
     * 应退押金
     */
    private BigDecimal refundDeposit;

    /**
     * 其他费用
     */
    private BigDecimal otherAmount;

    /**
     * 最终结算金额(正数为退,负数为补)
     */
    private BigDecimal finalAmount;

    /**
     * 结算状态: 1-待结算, 2-已结算, 3-已完成
     */
    private Byte settlementStatus;

    /**
     * 结算时间
     */
    private LocalDateTime settlementTime;

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
