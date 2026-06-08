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
 * 维修工单表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("maintenance_order")
public class MaintenanceOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 工单编号
     */
    private String orderNo;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 申报人ID(租户)
     */
    private Long tenantId;

    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 维修分类: 水电, 家电, 家具, 门窗, 卫浴, 网络, 其他
     */
    private String category;

    /**
     * 紧急程度: 1-紧急, 2-普通, 3-一般
     */
    private Byte urgencyLevel;

    /**
     * 故障描述
     */
    private String description;

    /**
     * 故障照片URL列表
     */
    private String photos;

    /**
     * 申报时间
     */
    private LocalDateTime reportTime;

    /**
     * 指派维修人员ID(关联员工表)
     */
    private Long assigneeId;

    /**
     * 指派维修人员姓名
     */
    private String assigneeName;

    /**
     * 派单时间
     */
    private LocalDateTime assignTime;

    /**
     * 预估费用
     */
    private BigDecimal estimatedCost;

    /**
     * 实际费用
     */
    private BigDecimal actualCost;

    /**
     * 维修完成时间
     */
    private LocalDateTime repairTime;

    /**
     * 维修结果
     */
    private String repairResult;

    /**
     * 工单状态: 1-待派单, 2-已派单, 3-维修中, 4-待验收, 5-已完成, 6-已取消
     */
    private Byte orderStatus;

    /**
     * 租户评分(1-5)
     */
    private Byte tenantRating;

    /**
     * 租户评价
     */
    private String tenantComment;

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
