package com.wanhe.apartment.dto;

import lombok.Data;

import java.util.List;

/**
 * 登录响应DTO
 */
@Data
public class LoginResponse {
    
    /**
     * 访问令牌
     */
    private String token;
    
    /**
     * 令牌类型
     */
    private String tokenType = "Bearer";
    
    /**
     * 用户ID
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 真实姓名
     */
    private String realName;
    
    /**
     * 用户类型
     */
    private Integer userType;
    
    /**
     * 用户类型名称
     */
    private String userTypeName;
    
    /**
     * 所属门店ID
     */
    private Long storeId;
    
    /**
     * 可用菜单列表
     */
    private List<MenuDTO> menus;
    
    /**
     * 租户信息（仅租客用户返回）
     */
    private TenantInfo tenantInfo;
    
    /**
     * 租户信息内部类
     */
    @Data
    public static class TenantInfo {
        /**
         * 租户ID
         */
        private Long tenantId;
        
        /**
         * 租户编号
         */
        private String tenantCode;
        
        /**
         * 租住房间号
         */
        private String roomCode;
        
        /**
         * 楼栋名称
         */
        private String buildingName;
    }
}
