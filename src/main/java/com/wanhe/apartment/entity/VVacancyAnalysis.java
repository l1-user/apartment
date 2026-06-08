package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("v_vacancy_analysis")
public class VVacancyAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 门店名称
     */
    private String storeName;

    /**
     * 楼栋ID
     */
    private Long buildingId;

    /**
     * 楼栋名称
     */
    private String buildingName;

    private Long totalRooms;

    private BigDecimal vacantRooms;

    private BigDecimal rentedRooms;

    private BigDecimal vacancyRate;

    private BigDecimal avgVacancyDays;
}
