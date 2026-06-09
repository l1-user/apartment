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

@Getter
@Setter
@ToString
@TableName("check_in")
public class CheckIn implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String checkInNo;

    private Long contractId;

    private Long tenantId;

    private Long roomId;

    private LocalDate actualCheckInDate;

    private String keyCardNo;

    private Integer keyCardCount;

    private String keyCode;

    private BigDecimal meterReadingElectric;

    private BigDecimal meterReadingWater;

    private BigDecimal meterReadingGas;

    private String roomConditionPhotos;

    private Byte hasSignedChecklist;

    private String checklistFileUrl;

    private Byte status;

    private String remark;

    private String createdBy;

    private LocalDateTime createdTime;

    private String updatedBy;

    private LocalDateTime updatedTime;

    private Byte isDeleted;
}