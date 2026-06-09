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
 * VIEW
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("v_active_contracts")
public class VActiveContracts implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 合同ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 合同类型: 1-新签, 2-续签, 3-变更
     */
    private Byte contractType;

    /**
     * 签约日期
     */
    private LocalDate signDate;

    /**
     * 合同开始日期
     */
    private LocalDate startDate;

    /**
     * 合同结束日期
     */
    private LocalDate endDate;

    /**
     * 租期(月)
     */
    private Integer leaseTerm;

    /**
     * 月租金
     */
    private BigDecimal rentAmount;

    /**
     * 押金金额
     */
    private BigDecimal depositAmount;

    /**
     * 付款周期: 1-月付, 2-季付, 3-半年付, 4-年付
     */
    private Byte paymentCycle;

    /**
     * 每月付款日(几号)
     */
    private Integer paymentDay;

    /**
     * 免租天数
     */
    private Integer rentFreeDays;

    /**
     * 免租金额
     */
    private BigDecimal rentFreeAmount;

    /**
     * 其他费用配置: {"water":0, "electric":0, "property":0, "internet":0}
     */
    private String otherFees;

    /**
     * 合同文件URL
     */
    private String contractFileUrl;

    /**
     * 合同状态: 1-草稿, 2-生效中, 3-已到期, 4-已终止, 5-已续签
     */
    private Byte contractStatus;

    /**
     * 终止原因
     */
    private String terminationReason;

    /**
     * 终止日期
     */
    private LocalDate terminationDate;

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
     * 删除标记
     */
    private Byte isDeleted;
}
