package com.wanhe.apartment.dto;

import lombok.Data;

import java.util.List;

/**
 * 菜单数据结构
 */
@Data
public class MenuDTO {
    
    /**
     * 菜单ID
     */
    private String id;
    
    /**
     * 菜单名称
     */
    private String name;
    
    /**
     * 菜单图标
     */
    private String icon;
    
    /**
     * 路由路径
     */
    private String path;
    
    /**
     * 组件路径
     */
    private String component;
    
    /**
     * 是否展开
     */
    private boolean expanded;
    
    /**
     * 是否选中
     */
    private boolean selected;
    
    /**
     * 子菜单
     */
    private List<MenuDTO> children;
}
