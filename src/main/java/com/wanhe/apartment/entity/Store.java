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
 * 门店/公寓表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 门店ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 门店编码
     */
    private String storeCode;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 门店类型: 1-直营店, 2-加盟店, 3-托管店
     */
    private Byte storeType;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区/县
     */
    private String district;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 总楼栋数
     */
    private Integer totalBuildings;

    /**
     * 总房间数
     */
    private Integer totalRooms;

    /**
     * 总楼层数
     */
    private Integer totalFloors;

    /**
     * 状态: 0-停用, 1-正常, 2-维护中
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
