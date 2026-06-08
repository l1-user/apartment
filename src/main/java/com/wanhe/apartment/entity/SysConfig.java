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
 * 系统参数表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("sys_config")
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 参数ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 参数键
     */
    private String configKey;

    /**
     * 参数名称
     */
    private String configName;

    /**
     * 参数值
     */
    private String configValue;

    /**
     * 参数类型: string, int, boolean, json
     */
    private String configType;

    /**
     * 是否系统内置: 0-否, 1-是
     */
    private Byte isSystem;

    /**
     * 是否可编辑: 0-否, 1-是
     */
    private Byte editable;

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
