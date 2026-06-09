package com.wanhe.apartment.config;

import com.wanhe.apartment.dto.MenuDTO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 菜单配置类
 * 根据用户类型返回不同的菜单
 */
@Component
public class MenuConfig {
    
    /**
     * 获取管理员菜单（用户类型1-7）
     */
    public List<MenuDTO> getAdminMenus() {
        return Arrays.asList(
            // 仪表盘
            createMenu("dashboard", "仪表盘", "Histogram", "/dashboard", "dashboard/Index.vue", false, false, null),
            
            // 房源管理
            createMenu("housing", "房源管理", "House", "/housing", null, false, false, Arrays.asList(
                createMenu("housing-room", "房间管理", null, "/housing/room", "housing/room/Index.vue", false, false, null),
                createMenu("housing-store", "门店管理", null, "/housing/store", "housing/store/Index.vue", false, false, null),
                createMenu("housing-building", "楼栋管理", null, "/housing/building", "housing/building/Index.vue", false, false, null),
                createMenu("housing-roomType", "房型模板", null, "/housing/roomType", "housing/roomType/Index.vue", false, false, null)
            )),
            
            // 租户管理
            createMenu("tenant", "租户管理", "User", "/tenant", "tenant/Index.vue", false, false, null),
            
            // 入住管理
            createMenu("checkin", "入住管理", "Key", "/checkin", "checkin/Index.vue", false, false, null),
            
            // 租金账单
            createMenu("bill", "租金账单", "Money", "/bill", "bill/Index.vue", false, false, null),
            
            // 退租管理
            createMenu("checkout", "退租管理", "SwitchButton", "/checkout", "checkout/Index.vue", false, false, null),
            
            // 维修保洁
            createMenu("maintenance", "维修保洁", "Tools", "/maintenance", null, false, false, Arrays.asList(
                createMenu("maintenance-order", "维修工单", null, "/maintenance/order", "maintenance/order/Index.vue", false, false, null),
                createMenu("maintenance-cleaning", "保洁计划", null, "/maintenance/cleaning", "maintenance/cleaning/Index.vue", false, false, null)
            )),
            
            // 财务报表
            createMenu("finance", "财务报表", "DataAnalysis", "/finance", "finance/Index.vue", false, false, null),
            
            // 系统管理
            createMenu("system", "系统管理", "Settings", "/system", null, false, false, Arrays.asList(
                createMenu("system-user", "用户管理", null, "/system/user", "system/user/Index.vue", false, false, null),
                createMenu("system-role", "角色管理", null, "/system/role", "system/role/Index.vue", false, false, null),
                createMenu("system-dict", "数据字典", null, "/system/dict", "system/dict/Index.vue", false, false, null),
                createMenu("system-config", "系统配置", null, "/system/config", "system/config/Index.vue", false, false, null)
            ))
        );
    }
    
    /**
     * 获取租户菜单（用户类型8）
     */
    public List<MenuDTO> getTenantMenus() {
        return Arrays.asList(
            // 我的首页
            createMenu("tenant-home", "我的首页", "Home", "/tenant/home", "tenant/home/Index.vue", false, false, null),
            
            // 我的合同
            createMenu("tenant-contract", "我的合同", "FileText", "/tenant/contract", "tenant/contract/Index.vue", false, false, null),
            
            // 我的账单
            createMenu("tenant-bill", "我的账单", "CreditCard", "/tenant/bill", "tenant/bill/Index.vue", false, false, null),
            
            // 维修服务
            createMenu("tenant-maintenance", "维修服务", "Wrench", "/tenant/maintenance", "tenant/maintenance/Index.vue", false, false, null),
            
            // 退租申请
            createMenu("tenant-checkout", "退租申请", "LogOut", "/tenant/checkout", "tenant/checkout/Index.vue", false, false, null),
            
            // 个人中心
            createMenu("tenant-profile", "个人中心", "User", "/tenant/profile", "tenant/profile/Index.vue", false, false, null)
        );
    }
    
    /**
     * 创建菜单对象
     */
    private MenuDTO createMenu(String id, String name, String icon, String path, String component,
                               boolean expanded, boolean selected, List<MenuDTO> children) {
        MenuDTO menu = new MenuDTO();
        menu.setId(id);
        menu.setName(name);
        menu.setIcon(icon);
        menu.setPath(path);
        menu.setComponent(component);
        menu.setExpanded(expanded);
        menu.setSelected(selected);
        menu.setChildren(children);
        return menu;
    }
    
    /**
     * 根据用户类型获取菜单
     */
    public List<MenuDTO> getMenusByUserType(Integer userType) {
        if (userType == null) {
            return getAdminMenus();
        }
        
        // 租户用户（类型8）
        if (userType == 8) {
            return getTenantMenus();
        }
        
        // 管理员用户（类型1-7）
        return getAdminMenus();
    }
    
    /**
     * 获取用户类型名称
     */
    public String getUserTypeName(Integer userType) {
        if (userType == null) {
            return "未知";
        }
        
        switch (userType) {
            case 1: return "超级管理员";
            case 2: return "门店管理员";
            case 3: return "店长";
            case 4: return "管家";
            case 5: return "财务";
            case 6: return "维修工";
            case 7: return "保洁员";
            case 8: return "租客";
            default: return "未知";
        }
    }
}
