package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 钥匙/门禁分配记录表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("key_distribution")
public class KeyDistribution implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 入住登记ID
     */
    private Long checkInId;

    /**
     * 钥匙类型: 1-门禁卡, 2-机械钥匙, 3-密码锁密码, 4-蓝牙钥匙
     */
    private Byte keyType;

    /**
     * 钥匙标识(卡号/密码/钥匙编号)
     */
    private String keyIdentifier;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 分配时间
     */
    private LocalDateTime distributionDate;

    /**
     * 归还时间
     */
    private LocalDateTime returnDate;

    /**
     * 状态: 1-使用中, 2-已归还, 3-丢失
     */
    private Byte status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标记: 0-未删除, 1-已删除
     */
    private Byte isDeleted;
}
