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
 * 租客档案附件表
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Getter
@Setter
@ToString
@TableName("tenant_attachment")
public class TenantAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 附件类型: id_card_front, id_card_back, work_proof, deposit_receipt, contract, other
     */
    private String attachmentType;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件URL
     */
    private String fileUrl;

    /**
     * 文件大小(字节)
     */
    private Long fileSize;

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
