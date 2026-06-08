package com.wanhe.apartment.service.impl;

import com.wanhe.apartment.entity.Bill;
import com.wanhe.apartment.mapper.BillMapper;
import com.wanhe.apartment.service.IBillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账单表 服务实现类
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements IBillService {

}
