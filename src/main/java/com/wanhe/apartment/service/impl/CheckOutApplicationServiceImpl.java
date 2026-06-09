package com.wanhe.apartment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanhe.apartment.entity.CheckOutApplication;
import com.wanhe.apartment.mapper.CheckOutApplicationMapper;
import com.wanhe.apartment.service.ICheckOutApplicationService;
import org.springframework.stereotype.Service;

@Service
public class CheckOutApplicationServiceImpl extends ServiceImpl<CheckOutApplicationMapper, CheckOutApplication> implements ICheckOutApplicationService {
}