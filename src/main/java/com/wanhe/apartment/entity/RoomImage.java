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
 * 房间图片表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("room_image")
public class RoomImage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 图片URL
     */
    private String imageUrl;

    /**
     * 图片类型: 1-房间全景, 2-卧室, 3-客厅, 4-厨房, 5-卫生间, 6-其他
     */
    private Byte imageType;

    /**
     * 是否封面: 0-否, 1-是
     */
    private Byte isCover;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 上传人
     */
    private String uploadBy;

    /**
     * 上传时间
     */
    private LocalDateTime uploadTime;

    /**
     * 删除标记: 0-未删除, 1-已删除
     */
    private Byte isDeleted;

    /**
     * 备注
     */
    private String remark;
}
