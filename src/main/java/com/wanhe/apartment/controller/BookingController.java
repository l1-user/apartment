package com.wanhe.apartment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wanhe.apartment.entity.Booking;
import com.wanhe.apartment.entity.Room;
import com.wanhe.apartment.entity.Tenant;
import com.wanhe.apartment.entity.SysUser;
import com.wanhe.apartment.service.IBookingService;
import com.wanhe.apartment.service.IRoomService;
import com.wanhe.apartment.service.ITenantService;
import com.wanhe.apartment.service.ISysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预订记录表 前端控制器
 * </p>
 *
 * @author xiaoke.liu
 * @since 2026-06-03
 */
@ResponseBody
@RestController
@RequestMapping("/api/booking")
@Tag(name = "预订接口")
public class BookingController {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private ISysUserService sysUserService;

    @PostMapping("/apply")
    @Operation(summary = "提交租赁申请", description = "租客提交租房预订申请")
    public ResponseEntity<?> submitBookingApplication(@RequestBody Map<String, Object> request) {
        Long userId = ((Number) request.get("userId")).longValue();
        Long roomId = ((Number) request.get("roomId")).longValue();
        Integer leaseTerm = (Integer) request.get("leaseTerm");
        String remark = (String) request.get("remark");

        // 查询房间信息
        Room room = roomService.getById(roomId);
        if (room == null || room.getIsDeleted() == 1) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "房间不存在");
            return ResponseEntity.ok(result);
        }

        // 检查房间状态
        if (room.getStatus() != 1) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "该房间当前不可预订");
            return ResponseEntity.ok(result);
        }

        // 获取租户信息
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户不存在");
            return ResponseEntity.ok(result);
        }

        // 检查是否已有租户记录，如果没有则创建
        LambdaQueryWrapper<Tenant> tenantWrapper = new LambdaQueryWrapper<>();
        tenantWrapper.eq(Tenant::getPhone, user.getPhone());
        tenantWrapper.eq(Tenant::getIsDeleted, 0);
        Tenant tenant = tenantService.getOne(tenantWrapper);

        Long tenantId = null;
        if (tenant != null) {
            tenantId = tenant.getId();
        } else {
            // 如果没有租户记录，创建一个新的租户
            Tenant newTenant = new Tenant();
            newTenant.setRealName(user.getRealName() != null ? user.getRealName() : user.getUsername());
            newTenant.setPhone(user.getPhone());
            newTenant.setIdCardNo("");
            newTenant.setEmail("");
            newTenant.setEmergencyContactName("");
            newTenant.setEmergencyContactPhone("");
            newTenant.setStatus((byte) 1);
            newTenant.setIsDeleted((byte) 0);
            tenantService.save(newTenant);
            tenantId = newTenant.getId();
        }

        // 计算预计入住和退房日期
        LocalDate checkInDate = LocalDate.now().plusDays(1);
        LocalDate checkOutDate = checkInDate.plusMonths(leaseTerm);

        // 创建预订记录
        Booking booking = new Booking();
        booking.setBookingNo("BK" + System.currentTimeMillis());
        booking.setRoomId(roomId);
        booking.setTenantId(tenantId);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setLeaseTerm(leaseTerm);
        booking.setRentAmount(room.getCurrentRent());
        booking.setDepositAmount(room.getCurrentRent()); // 押金设置为与租金相同，或根据业务需求调整
        booking.setBookingStatus((byte) 1);
        booking.setRemark(remark);
        booking.setCreatedBy(user.getUsername());
        booking.setCreatedTime(LocalDateTime.now());
        booking.setIsDeleted((byte) 0);

        boolean success = bookingService.save(booking);

        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("success", true);
            result.put("message", "预订申请提交成功，请等待管理员确认");
        } else {
            result.put("success", false);
            result.put("message", "提交失败");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/list/{userId}")
    @Operation(summary = "获取用户预订列表", description = "获取当前用户的所有预订记录")
    public ResponseEntity<?> getBookingList(@PathVariable Long userId) {
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("message", "用户不存在");
            return ResponseEntity.ok(result);
        }

        Long tenantId = null;
        LambdaQueryWrapper<Tenant> tenantWrapper = new LambdaQueryWrapper<>();
        tenantWrapper.eq(Tenant::getPhone, user.getPhone());
        tenantWrapper.eq(Tenant::getIsDeleted, 0);
        Tenant tenant = tenantService.getOne(tenantWrapper);
        if (tenant != null) {
            tenantId = tenant.getId();
        }

        LambdaQueryWrapper<Booking> bookingWrapper = new LambdaQueryWrapper<>();
        bookingWrapper.eq(Booking::getTenantId, tenantId);
        bookingWrapper.eq(Booking::getIsDeleted, 0);
        bookingWrapper.orderByDesc(Booking::getCreatedTime);
        List<Booking> bookings = bookingService.list(bookingWrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "获取成功");
        result.put("data", bookings);
        return ResponseEntity.ok(result);
    }
}
