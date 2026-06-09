package com.wanhe.apartment.service;

import com.wanhe.apartment.dto.LoginRequest;
import com.wanhe.apartment.dto.LoginResponse;

/**
 * 认证服务接口
 */
public interface IAuthService {
    
    /**
     * 用户登录
     * @param request 登录请求
     * @return 登录响应
     */
    LoginResponse login(LoginRequest request);
    
    /**
     * 验证令牌
     * @param token 令牌
     * @return 用户信息
     */
    LoginResponse validateToken(String token);
    
    /**
     * 退出登录
     * @param token 令牌
     */
    void logout(String token);
}
