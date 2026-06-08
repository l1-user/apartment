package com.wanhe.apartment.service.impl;

import com.wanhe.apartment.entity.FinancialTransaction;
import com.wanhe.apartment.mapper.FinancialTransactionMapper;
import com.wanhe.apartment.service.IFinancialTransactionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收支明细表 服务实现类
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Service
public class FinancialTransactionServiceImpl extends ServiceImpl<FinancialTransactionMapper, FinancialTransaction> implements IFinancialTransactionService {

}
