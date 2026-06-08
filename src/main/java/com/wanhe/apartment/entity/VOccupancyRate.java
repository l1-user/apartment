package com.wanhe.apartment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
@TableName("v_occupancy_rate")
public class VOccupancyRate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 门店名称
     */
    private String storeName;

    private LocalDate statDate;

    private Long occupiedRooms;

    private Long totalRooms;

    private BigDecimal occupancyRate;
}
