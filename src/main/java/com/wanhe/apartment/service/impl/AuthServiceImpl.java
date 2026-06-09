package com.wanhe.apartment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wanhe.apartment.config.MenuConfig;
import com.wanhe.apartment.dto.LoginRequest;
import com.wanhe.apartment.dto.LoginResponse;
import com.wanhe.apartment.entity.SysUser;
import com.wanhe.apartment.entity.Tenant;
import com.wanhe.apartment.entity.Room;
import com.wanhe.apartment.entity.Building;
import com.wanhe.apartment.service.ISysUserService;
import com.wanhe.apartment.service.ITenantService;
import com.wanhe.apartment.service.IRoomService;
import com.wanhe.apartment.service.IBuildingService;
import com.wanhe.apartment.service.IAuthService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 认证服务实现类
 */
@Service
public class AuthServiceImpl implements IAuthService {
    
    @Autowired
    private ISysUserService sysUserService;
    
    @Autowired
    private ITenantService tenantService;
    
    @Autowired
    private IRoomService roomService;
    
    @Autowired
    private IBuildingService buildingService;
    
    @Autowired
    private MenuConfig menuConfig;
    
    /**
     * 存储用户会话（生产环境建议使用Redis）
     */
    private static final Map<String, LoginResponse> SESSION_STORE = new HashMap<>();
    
    /**
     * Token过期时间（2小时，单位毫秒）
     */
    private static final long TOKEN_EXPIRE_TIME = 2 * 60 * 60 * 1000;
    
    @Override
    public LoginResponse login(LoginRequest request) {
        // 验证参数
        if (request.getUsername() == null || request.getPassword() == null) {
            return null;
        }
        
        // 查询用户
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, request.getUsername());
        wrapper.eq(SysUser::getIsDeleted, 0);
        wrapper.eq(SysUser::getStatus, 1);
        SysUser user = sysUserService.getOne(wrapper);
        
        // 验证密码
        if (user == null) {
            return null;
        }
        
        // 检查密码是否为BCrypt格式（以$开头）
        boolean passwordMatch = false;
        String storedPassword = user.getPassword();
        
        if (storedPassword != null && storedPassword.startsWith("$")) {
            // BCrypt格式密码
            passwordMatch = BCrypt.checkpw(request.getPassword(), storedPassword);
        } else {
            // 非BCrypt格式，使用简单比较（兼容旧数据）
            passwordMatch = request.getPassword().equals(storedPassword);
        }
        
        if (!passwordMatch) {
            return null;
        }
        
        // 生成Token
        String token = UUID.randomUUID().toString().replace("-", "");
        
        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setRealName(user.getRealName());
        Integer userTypeInt = user.getUserType() != null ? user.getUserType().intValue() : 1;
        response.setUserType(userTypeInt);
        response.setUserTypeName(menuConfig.getUserTypeName(userTypeInt));
        response.setStoreId(user.getStoreId() != null ? user.getStoreId().longValue() : null);
        
        // 根据用户类型获取菜单
        response.setMenus(menuConfig.getMenusByUserType(userTypeInt));
        
        // 如果是租户用户，获取租户信息
        if (user.getUserType() != null && user.getUserType() == 8) {
            response.setTenantInfo(getTenantInfo(user));
        }
        
        // 保存会话
        SESSION_STORE.put(token, response);
        
        return response;
    }
    
    @Override
    public LoginResponse validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }
        
        // Bearer token格式处理
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        return SESSION_STORE.get(token);
    }
    
    @Override
    public void logout(String token) {
        if (token != null) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            SESSION_STORE.remove(token);
        }
    }
    
    /**
     * 获取租户信息
     */
    private LoginResponse.TenantInfo getTenantInfo(SysUser user) {
        try {
            // 通过手机号关联租户
            LambdaQueryWrapper<Tenant> tenantWrapper = new LambdaQueryWrapper<>();
            tenantWrapper.eq(Tenant::getPhone, user.getPhone());
            tenantWrapper.eq(Tenant::getIsDeleted, 0);
            Tenant tenant = tenantService.getOne(tenantWrapper);
            
            if (tenant != null) {
                LoginResponse.TenantInfo info = new LoginResponse.TenantInfo();
                info.setTenantId(tenant.getId());
                info.setTenantCode(tenant.getTenantCode());
                
                // 获取租住房间信息
                LambdaQueryWrapper<Room> roomWrapper = new LambdaQueryWrapper<>();
                roomWrapper.eq(Room::getCurrentTenantId, tenant.getId());
                roomWrapper.eq(Room::getIsDeleted, 0);
                Room room = roomService.getOne(roomWrapper);
                
                if (room != null) {
                    info.setRoomCode(room.getRoomCode());
                    
                    // 获取楼栋信息
                    Building building = buildingService.getById(room.getBuildingId());
                    if (building != null) {
                        info.setBuildingName(building.getBuildingName());
                    }
                }
                
                return info;
            }
        } catch (Exception e) {
            // 忽略查询异常
        }
        
        return null;
    }
}
