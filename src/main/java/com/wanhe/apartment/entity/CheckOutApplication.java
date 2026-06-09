package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@TableName("check_out_application")
public class CheckOutApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String applicationNo;

    private Long contractId;

    private Long tenantId;

    private Long roomId;

    private LocalDate applicationDate;

    private LocalDate expectedCheckOutDate;

    private LocalDate actualCheckOutDate;

    private String checkOutReason;

    private Byte applicationStatus;

    private String approver;

    private LocalDateTime approveTime;

    private String approveRemark;

    private String remark;

    private String createdBy;

    private LocalDateTime createdTime;

    private String updatedBy;

    private LocalDateTime updatedTime;

    private Byte isDeleted;
}