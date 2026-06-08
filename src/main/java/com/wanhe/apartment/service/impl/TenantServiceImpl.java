package com.wanhe.apartment.service.impl;

import com.wanhe.apartment.entity.Tenant;
import com.wanhe.apartment.mapper.TenantMapper;
import com.wanhe.apartment.service.ITenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户基本信息表 服务实现类
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

}
