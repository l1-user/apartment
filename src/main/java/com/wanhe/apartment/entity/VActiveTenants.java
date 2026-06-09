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
@TableName("v_active_tenants")
public class VActiveTenants implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别: 1-男, 2-女, 3-未知
     */
    private Byte gender;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 身份证正面图片URL
     */
    private String idCardFrontUrl;

    /**
     * 身份证反面图片URL
     */
    private String idCardBackUrl;

    /**
     * OCR识别结果(JSON格式)
     */
    private String ocrResult;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * 支付宝账号
     */
    private String alipay;

    /**
     * 职业
     */
    private String profession;

    /**
     * 工作单位
     */
    private String companyName;

    /**
     * 工作单位地址
     */
    private String companyAddress;

    /**
     * 学历
     */
    private String education;

    /**
     * 婚姻状况: 1-未婚, 2-已婚, 3-离异
     */
    private Byte maritalStatus;

    /**
     * 紧急联系人姓名
     */
    private String emergencyContactName;

    /**
     * 紧急联系人电话
     */
    private String emergencyContactPhone;

    /**
     * 紧急联系人关系
     */
    private String emergencyContactRelation;

    /**
     * 紧急联系人地址
     */
    private String emergencyContactAddress;

    /**
     * 状态: 1-正常, 2-黑名单, 3-已退租
     */
    private Byte status;

    /**
     * 信用评分(0-100)
     */
    private Integer creditScore;

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
