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
 * 入住登记表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("check_in")
public class CheckIn implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 入住登记ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 入住登记编号
     */
    private String checkInNo;

    /**
     * 合同ID
     */
    private Long contractId;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 实际入住日期
     */
    private LocalDate actualCheckInDate;

    /**
     * 门禁卡号
     */
    private String keyCardNo;

    /**
     * 门禁卡数量
     */
    private Integer keyCardCount;

    /**
     * 密码锁密码
     */
    private String keyCode;

    /**
     * 电表读数(入住时)
     */
    private BigDecimal meterReadingElectric;

    /**
     * 水表读数(入住时)
     */
    private BigDecimal meterReadingWater;

    /**
     * 燃气表读数(入住时)
     */
    private BigDecimal meterReadingGas;

    /**
     * 房屋状况照片(入住时)
     */
    private String roomConditionPhotos;

    /**
     * 是否签署入住清单: 0-否, 1-是
     */
    private Byte hasSignedChecklist;

    /**
     * 入住清单文件URL
     */
    private String checklistFileUrl;

    /**
     * 状态: 1-正常, 2-已退租
     */
    private Byte status;

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
