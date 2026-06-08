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
 * 预订记录表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 预订ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 预订编号
     */
    private String bookingNo;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 预计入住日期
     */
    private LocalDate checkInDate;

    /**
     * 预计退租日期
     */
    private LocalDate checkOutDate;

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
     * 预订状态: 1-待确认, 2-已确认, 3-已入住, 4-已取消, 5-已过期
     */
    private Byte bookingStatus;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 预订过期时间
     */
    private LocalDateTime expireTime;

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
