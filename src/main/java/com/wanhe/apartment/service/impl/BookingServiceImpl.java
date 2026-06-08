package com.wanhe.apartment.service.impl;

import com.wanhe.apartment.entity.Booking;
import com.wanhe.apartment.mapper.BookingMapper;
import com.wanhe.apartment.service.IBookingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 预订记录表 服务实现类
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@Service
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking> implements IBookingService {

}
