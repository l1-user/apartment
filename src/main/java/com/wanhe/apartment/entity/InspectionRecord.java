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
 * 物品验收表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("inspection_record")
public class InspectionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 验收记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 退租申请ID
     */
    private Long checkOutApplicationId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 验收日期
     */
    private LocalDateTime inspectionDate;

    /**
     * 验收人
     */
    private String inspector;

    /**
     * 损坏物品清单: [{"item":"床垫","damage_desc":"破损","repair_cost":200}]
     */
    private String damageItems;

    /**
     * 保洁费
     */
    private BigDecimal cleaningFee;

    /**
     * 维修费
     */
    private BigDecimal repairFee;

    /**
     * 其他扣款
     */
    private BigDecimal otherDeduction;

    /**
     * 总扣款金额
     */
    private BigDecimal totalDeduction;

    /**
     * 损坏照片URL列表
     */
    private String damagePhotos;

    /**
     * 验收结果: 1-合格, 2-需扣款, 3-需返修
     */
    private Byte inspectionResult;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标记: 0-未删除, 1-已删除
     */
    private Byte isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
}
