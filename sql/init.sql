-- ============================================
-- 公寓管理系统 - 数据库初始化脚本
-- 数据库名称: apartment_hubs
-- 字符集: utf8mb4
-- ============================================

-- 1. 创建数据库
DROP DATABASE IF EXISTS apartment_hubs;
CREATE DATABASE apartment_hubs DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE apartment_hubs;

-- 2. 创建门店表
CREATE TABLE IF NOT EXISTS `store` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '门店ID',
  `store_code` VARCHAR(50) NOT NULL COMMENT '门店编码',
  `store_name` VARCHAR(100) NOT NULL COMMENT '门店名称',
  `store_type` TINYINT DEFAULT 1 COMMENT '门店类型: 1-直营店, 2-加盟店, 3-托管店',
  `province` VARCHAR(50) COMMENT '省份',
  `city` VARCHAR(50) COMMENT '城市',
  `district` VARCHAR(50) COMMENT '区/县',
  `address` VARCHAR(255) COMMENT '详细地址',
  `longitude` DECIMAL(10,6) COMMENT '经度',
  `latitude` DECIMAL(10,6) COMMENT '纬度',
  `contact_person` VARCHAR(50) COMMENT '联系人',
  `contact_phone` VARCHAR(20) COMMENT '联系电话',
  `total_buildings` INT DEFAULT 0 COMMENT '总楼栋数',
  `total_rooms` INT DEFAULT 0 COMMENT '总房间数',
  `total_floors` INT DEFAULT 0 COMMENT '总楼层数',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0-停用, 1-正常, 2-维护中',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记: 0-未删除, 1-已删除',
  INDEX `idx_store_code` (`store_code`),
  INDEX `idx_store_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='门店/公寓表';

-- 3. 创建楼栋表
CREATE TABLE IF NOT EXISTS `building` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '楼栋ID',
  `building_code` VARCHAR(50) NOT NULL COMMENT '楼栋编码',
  `building_name` VARCHAR(100) NOT NULL COMMENT '楼栋名称',
  `store_id` BIGINT NOT NULL COMMENT '所属门店ID',
  `total_floors` INT DEFAULT 0 COMMENT '总楼层数',
  `total_rooms` INT DEFAULT 0 COMMENT '总房间数',
  `has_elevator` TINYINT DEFAULT 0 COMMENT '是否有电梯: 0-无, 1-有',
  `construction_year` DATE COMMENT '建造年份',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0-停用, 1-正常, 2-维护中',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  INDEX `idx_store_id` (`store_id`),
  INDEX `idx_building_code` (`building_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='楼栋表';

-- 4. 创建房型表
CREATE TABLE IF NOT EXISTS `room_type` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '房型ID',
  `store_id` BIGINT COMMENT '门店ID(为空表示全局房型)',
  `type_code` VARCHAR(50) NOT NULL COMMENT '房型编码',
  `type_name` VARCHAR(100) NOT NULL COMMENT '房型名称',
  `bed_type` VARCHAR(50) COMMENT '床型',
  `max_occupancy` INT DEFAULT 1 COMMENT '最多可住人数',
  `area` DECIMAL(10,2) COMMENT '面积(平方米)',
  `window_type` TINYINT DEFAULT 3 COMMENT '窗户类型: 1-无窗, 2-内窗, 3-外窗',
  `has_balcony` TINYINT DEFAULT 0 COMMENT '是否有阳台: 0-无, 1-有',
  `has_bathroom` TINYINT DEFAULT 0 COMMENT '是否有独立卫浴: 0-无, 1-有',
  `has_kitchen` TINYINT DEFAULT 0 COMMENT '是否有厨房: 0-无, 1-有',
  `has_air_conditioner` TINYINT DEFAULT 0 COMMENT '是否有空调: 0-无, 1-有',
  `has_heating` TINYINT DEFAULT 0 COMMENT '是否有暖气: 0-无, 1-有',
  `has_water_heater` TINYINT DEFAULT 0 COMMENT '是否有热水器: 0-无, 1-有',
  `has_washing_machine` TINYINT DEFAULT 0 COMMENT '是否有洗衣机: 0-无, 1-有',
  `has_refrigerator` TINYINT DEFAULT 0 COMMENT '是否有冰箱: 0-无, 1-有',
  `has_tv` TINYINT DEFAULT 0 COMMENT '是否有电视: 0-无, 1-有',
  `has_wifi` TINYINT DEFAULT 1 COMMENT '是否有WIFI: 0-无, 1-有',
  `standard_rent` DECIMAL(10,2) COMMENT '标准月租金',
  `deposit_months` TINYINT DEFAULT 1 COMMENT '押金月数',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 0-停用, 1-启用',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  INDEX `idx_type_code` (`type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房型定义表';

-- 5. 创建房间表
CREATE TABLE IF NOT EXISTS `room` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '房间ID',
  `room_code` VARCHAR(50) NOT NULL COMMENT '房间编码/房间号',
  `room_name` VARCHAR(100) COMMENT '房间名称',
  `store_id` BIGINT NOT NULL COMMENT '所属门店ID',
  `building_id` BIGINT NOT NULL COMMENT '所属楼栋ID',
  `floor_number` INT COMMENT '所在楼层',
  `room_type_id` BIGINT COMMENT '房型ID',
  `area` DECIMAL(10,2) COMMENT '实际面积(平方米)',
  `orientation` VARCHAR(10) COMMENT '朝向',
  `status` TINYINT DEFAULT 1 COMMENT '房间状态: 1-空闲, 2-已租, 3-维修中, 4-预留, 5-已预订',
  `current_rent` DECIMAL(10,2) COMMENT '当前租金',
  `rent_unit` VARCHAR(10) DEFAULT '月' COMMENT '租金单位: 月/季/年',
  `current_tenant_id` BIGINT COMMENT '当前租客ID',
  `last_maintenance_time` DATETIME COMMENT '最近维护时间',
  `next_maintenance_time` DATETIME COMMENT '下次维护时间',
  `status_remark` VARCHAR(200) COMMENT '状态备注',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  INDEX `idx_room_code` (`room_code`),
  INDEX `idx_building_id` (`building_id`),
  INDEX `idx_room_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间表';

-- 6. 创建租户表
CREATE TABLE IF NOT EXISTS `tenant` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '租户ID',
  `tenant_code` VARCHAR(50) NOT NULL COMMENT '租户编码',
  `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
  `nickname` VARCHAR(50) COMMENT '昵称',
  `gender` TINYINT DEFAULT 3 COMMENT '性别: 1-男, 2-女, 3-未知',
  `birthday` DATE COMMENT '出生日期',
  `id_card_no` VARCHAR(18) COMMENT '身份证号',
  `id_card_front_url` VARCHAR(255) COMMENT '身份证正面图片URL',
  `id_card_back_url` VARCHAR(255) COMMENT '身份证反面图片URL',
  `ocr_result` TEXT COMMENT 'OCR识别结果(JSON格式)',
  `phone` VARCHAR(20) COMMENT '手机号',
  `email` VARCHAR(100) COMMENT '电子邮箱',
  `wechat` VARCHAR(50) COMMENT '微信号',
  `alipay` VARCHAR(100) COMMENT '支付宝账号',
  `profession` VARCHAR(50) COMMENT '职业',
  `company_name` VARCHAR(100) COMMENT '工作单位',
  `company_address` VARCHAR(255) COMMENT '工作单位地址',
  `education` VARCHAR(20) COMMENT '学历',
  `marital_status` TINYINT COMMENT '婚姻状况: 1-未婚, 2-已婚, 3-离异',
  `emergency_contact_name` VARCHAR(50) COMMENT '紧急联系人姓名',
  `emergency_contact_phone` VARCHAR(20) COMMENT '紧急联系人电话',
  `emergency_contact_relation` VARCHAR(20) COMMENT '紧急联系人关系',
  `emergency_contact_address` VARCHAR(255) COMMENT '紧急联系人地址',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 1-正常, 2-黑名单, 3-已退租',
  `credit_score` INT DEFAULT 80 COMMENT '信用评分(0-100)',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  INDEX `idx_tenant_code` (`tenant_code`),
  INDEX `idx_tenant_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='租户基本信息表';

-- 7. 创建合同表
CREATE TABLE IF NOT EXISTS `contract` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '合同ID',
  `contract_no` VARCHAR(50) NOT NULL COMMENT '合同编号',
  `room_id` BIGINT NOT NULL COMMENT '房间ID',
  `tenant_id` BIGINT NOT NULL COMMENT '租户ID',
  `store_id` BIGINT NOT NULL COMMENT '门店ID',
  `contract_type` TINYINT DEFAULT 1 COMMENT '合同类型: 1-新签, 2-续签, 3-变更',
  `sign_date` DATE COMMENT '签约日期',
  `start_date` DATE COMMENT '合同开始日期',
  `end_date` DATE COMMENT '合同结束日期',
  `lease_term` INT COMMENT '租期(月)',
  `rent_amount` DECIMAL(10,2) COMMENT '月租金',
  `deposit_amount` DECIMAL(10,2) COMMENT '押金金额',
  `payment_cycle` TINYINT DEFAULT 1 COMMENT '付款周期: 1-月付, 2-季付, 3-半年付, 4-年付',
  `payment_day` INT DEFAULT 1 COMMENT '每月付款日(几号)',
  `rent_free_days` INT DEFAULT 0 COMMENT '免租天数',
  `rent_free_amount` DECIMAL(10,2) DEFAULT 0 COMMENT '免租金额',
  `other_fees` VARCHAR(500) COMMENT '其他费用配置(JSON)',
  `contract_file_url` VARCHAR(255) COMMENT '合同文件URL',
  `contract_status` TINYINT DEFAULT 1 COMMENT '合同状态: 1-草稿, 2-生效中, 3-已到期, 4-已终止, 5-已续签',
  `termination_reason` VARCHAR(200) COMMENT '终止原因',
  `termination_date` DATE COMMENT '终止日期',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  INDEX `idx_contract_no` (`contract_no`),
  INDEX `idx_contract_status` (`contract_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合同表';

-- 8. 创建账单表
CREATE TABLE IF NOT EXISTS `bill` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '账单ID',
  `bill_no` VARCHAR(50) NOT NULL COMMENT '账单编号',
  `contract_id` BIGINT COMMENT '合同ID',
  `tenant_id` BIGINT COMMENT '租户ID',
  `room_id` BIGINT NOT NULL COMMENT '房间ID',
  `store_id` BIGINT NOT NULL COMMENT '门店ID',
  `bill_type` TINYINT DEFAULT 1 COMMENT '账单类型: 1-租金, 2-押金, 3-水电费, 4-物业费, 5-网络费, 6-维修费, 7-违约金, 8-其他',
  `bill_period_start` DATE COMMENT '账单周期开始',
  `bill_period_end` DATE COMMENT '账单周期结束',
  `due_date` DATE COMMENT '缴费截止日期',
  `amount` DECIMAL(10,2) COMMENT '账单金额',
  `paid_amount` DECIMAL(10,2) DEFAULT 0 COMMENT '已付金额',
  `discount_amount` DECIMAL(10,2) DEFAULT 0 COMMENT '优惠金额',
  `late_fee` DECIMAL(10,2) DEFAULT 0 COMMENT '逾期费',
  `final_amount` DECIMAL(10,2) COMMENT '最终应付金额',
  `bill_status` TINYINT DEFAULT 1 COMMENT '账单状态: 1-待支付, 2-部分支付, 3-已支付, 4-逾期, 5-已作废',
  `payment_method` VARCHAR(20) COMMENT '支付方式',
  `payment_time` DATETIME COMMENT '支付时间',
  `payment_transaction_id` VARCHAR(100) COMMENT '支付交易流水号',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  INDEX `idx_bill_no` (`bill_no`),
  INDEX `idx_bill_status` (`bill_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账单表';

-- 9. 创建维修工单表
CREATE TABLE IF NOT EXISTS `maintenance_order` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '工单ID',
  `order_no` VARCHAR(50) NOT NULL COMMENT '工单编号',
  `room_id` BIGINT NOT NULL COMMENT '房间ID',
  `tenant_id` BIGINT COMMENT '申报人ID(租户)',
  `store_id` BIGINT NOT NULL COMMENT '门店ID',
  `category` VARCHAR(20) DEFAULT '其他' COMMENT '维修分类',
  `urgency_level` TINYINT DEFAULT 2 COMMENT '紧急程度: 1-紧急, 2-普通, 3-一般',
  `description` VARCHAR(500) COMMENT '故障描述',
  `photos` VARCHAR(1000) COMMENT '故障照片URL列表',
  `report_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '申报时间',
  `assignee_id` BIGINT COMMENT '指派维修人员ID',
  `assignee_name` VARCHAR(50) COMMENT '指派维修人员姓名',
  `assign_time` DATETIME COMMENT '派单时间',
  `estimated_cost` DECIMAL(10,2) COMMENT '预估费用',
  `actual_cost` DECIMAL(10,2) COMMENT '实际费用',
  `repair_time` DATETIME COMMENT '维修完成时间',
  `repair_result` VARCHAR(500) COMMENT '维修结果',
  `order_status` TINYINT DEFAULT 1 COMMENT '工单状态: 1-待派单, 2-已派单, 3-维修中, 4-待验收, 5-已完成, 6-已取消',
  `tenant_rating` TINYINT COMMENT '租户评分(1-5)',
  `tenant_comment` VARCHAR(500) COMMENT '租户评价',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  INDEX `idx_order_no` (`order_no`),
  INDEX `idx_order_status` (`order_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修工单表';

-- 10. 创建保洁计划表
CREATE TABLE IF NOT EXISTS `cleaning_plan` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '计划ID',
  `plan_no` VARCHAR(50) COMMENT '计划编号',
  `store_id` BIGINT NOT NULL COMMENT '门店ID',
  `plan_name` VARCHAR(100) NOT NULL COMMENT '计划名称',
  `plan_type` TINYINT DEFAULT 1 COMMENT '计划类型: 1-日常保洁, 2-深度保洁, 3-退租保洁, 4-定期大扫除',
  `frequency` VARCHAR(20) DEFAULT 'weekly' COMMENT '执行频率',
  `frequency_value` INT DEFAULT 1 COMMENT '频率值',
  `assigned_to` VARCHAR(50) COMMENT '指派保洁人员',
  `estimated_duration` INT DEFAULT 60 COMMENT '预计耗时(分钟)',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 1-启用, 0-停用',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  INDEX `idx_plan_no` (`plan_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='保洁计划表';

-- 11. 创建入住登记表
CREATE TABLE IF NOT EXISTS `check_in` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '入住登记ID',
  `check_in_no` VARCHAR(50) NOT NULL COMMENT '入住登记编号',
  `contract_id` BIGINT COMMENT '合同ID',
  `tenant_id` BIGINT COMMENT '租户ID',
  `room_id` BIGINT COMMENT '房间ID',
  `actual_check_in_date` DATE COMMENT '实际入住日期',
  `key_card_no` VARCHAR(50) COMMENT '门禁卡号',
  `key_card_count` INT DEFAULT 1 COMMENT '门禁卡数量',
  `key_code` VARCHAR(20) COMMENT '密码锁密码',
  `meter_reading_electric` DECIMAL(10,2) COMMENT '电表读数(入住时)',
  `meter_reading_water` DECIMAL(10,2) COMMENT '水表读数(入住时)',
  `meter_reading_gas` DECIMAL(10,2) COMMENT '燃气表读数(入住时)',
  `room_condition_photos` VARCHAR(1000) COMMENT '房屋状况照片(入住时)',
  `has_signed_checklist` TINYINT DEFAULT 0 COMMENT '是否签署入住清单',
  `checklist_file_url` VARCHAR(255) COMMENT '入住清单文件URL',
  `status` TINYINT DEFAULT 1 COMMENT '状态: 1-正常, 2-已退租',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  INDEX `idx_check_in_no` (`check_in_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='入住登记表';

-- 12. 创建退租申请表
CREATE TABLE IF NOT EXISTS `check_out_application` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '申请ID',
  `application_no` VARCHAR(50) NOT NULL COMMENT '申请编号',
  `contract_id` BIGINT COMMENT '合同ID',
  `tenant_id` BIGINT COMMENT '租户ID',
  `room_id` BIGINT COMMENT '房间ID',
  `application_date` DATE COMMENT '申请日期',
  `expected_check_out_date` DATE COMMENT '预计退租日期',
  `actual_check_out_date` DATE COMMENT '实际退租日期',
  `check_out_reason` VARCHAR(200) COMMENT '退租原因',
  `application_status` TINYINT DEFAULT 1 COMMENT '申请状态: 1-待审核, 2-审核通过, 3-已拒绝, 4-已完成',
  `approver` VARCHAR(50) COMMENT '审核人',
  `approve_time` DATETIME COMMENT '审核时间',
  `approve_remark` VARCHAR(200) COMMENT '审核备注',
  `remark` VARCHAR(500) COMMENT '备注',
  `created_by` VARCHAR(50) COMMENT '创建人',
  `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` VARCHAR(50) COMMENT '更新人',
  `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
  INDEX `idx_application_no` (`application_no`),
  INDEX `idx_application_status` (`application_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退租申请表';

-- ============================================
-- 插入测试数据
-- ============================================

-- 插入门店数据
INSERT INTO `store` (`store_code`, `store_name`, `store_type`, `province`, `city`, `address`, `contact_person`, `contact_phone`, `total_buildings`, `total_rooms`, `status`) VALUES
('ST001', '滨江旗舰公寓', 1, '浙江省', '杭州市', '滨江区江南大道1000号', '张经理', '13800138001', 2, 200, 1),
('ST002', '西湖精品公寓', 1, '浙江省', '杭州市', '西湖区文三路888号', '李经理', '13800138002', 1, 80, 1),
('ST003', '未来科技城公寓', 2, '浙江省', '杭州市', '余杭区文一西路999号', '王经理', '13800138003', 1, 150, 1),
('ST004', '城东商务公寓', 3, '浙江省', '杭州市', '江干区富春路777号', '赵经理', '13800138004', 2, 180, 1);

-- 插入楼栋数据
INSERT INTO `building` (`building_code`, `building_name`, `store_id`, `total_floors`, `total_rooms`, `has_elevator`, `status`) VALUES
('B1', '1号楼', 1, 18, 120, 1, 1),
('B2', '2号楼', 1, 12, 80, 1, 1),
('B1', 'A栋', 2, 8, 80, 0, 1),
('test', 'test', 3, 1, 1, 1, 1);

-- 插入房型数据
INSERT INTO `room_type` (`type_code`, `type_name`, `bed_type`, `max_occupancy`, `area`, `window_type`, `has_bathroom`, `has_air_conditioner`, `has_wifi`, `standard_rent`, `deposit_months`, `status`) VALUES
('DBR001', '标准单人间', '1.5米双人床', 1, 25.00, 3, 1, 1, 1, 2500.00, 1, 1),
('DBR002', '豪华单人间', '1.8米大床', 1, 35.00, 3, 1, 1, 1, 3500.00, 2, 1),
('DBR003', '标准双人间', '两张1.2米床', 2, 45.00, 3, 1, 1, 1, 4500.00, 2, 1),
('DBR004', '家庭套房', '1.8米大床+1.2米床', 3, 65.00, 3, 1, 1, 1, 6500.00, 2, 1),
('DBR005', 'LOFT公寓', '上层1.8米床', 2, 50.00, 3, 1, 1, 1, 5500.00, 2, 1);

-- 插入房间数据（滨江旗舰公寓 - 1号楼）
INSERT INTO `room` (`room_code`, `room_name`, `store_id`, `building_id`, `floor_number`, `room_type_id`, `area`, `orientation`, `status`, `current_rent`, `rent_unit`) VALUES
('101', '101号房', 1, 1, 1, 1, 25.00, '南', 2, 2500.00, '月'),
('102', '102号房', 1, 1, 1, 1, 25.00, '东', 1, 2500.00, '月'),
('103', '103号房', 1, 1, 1, 1, 26.00, '南', 1, 2600.00, '月'),
('104', '104号房', 1, 1, 1, 1, 25.00, '西', 3, 2500.00, '月'),
('105', '105号房', 1, 1, 1, 2, 35.00, '南', 1, 3500.00, '月'),
('201', '201号房', 1, 1, 2, 1, 25.00, '南', 2, 2550.00, '月'),
('202', '202号房', 1, 1, 2, 1, 25.00, '东', 1, 2550.00, '月'),
('203', '203号房', 1, 1, 2, 3, 45.00, '南', 2, 4500.00, '月'),
('204', '204号房', 1, 1, 2, 1, 25.00, '西', 1, 2550.00, '月'),
('205', '205号房', 1, 1, 2, 2, 35.00, '南', 4, 3500.00, '月'),
('301', '301号房', 1, 1, 3, 1, 28.00, '东南', 1, 2800.00, '月'),
('302', '302号房', 1, 1, 3, 1, 24.00, '北', 2, 2450.00, '月');

-- 插入租户数据
INSERT INTO `tenant` (`tenant_code`, `real_name`, `nickname`, `gender`, `phone`, `email`, `profession`, `company_name`, `status`, `credit_score`) VALUES
('T001', '张三', '小张', 1, '13900139001', 'zhangsan@example.com', '程序员', '阿里科技', 1, 95),
('T002', '李四', '小李', 1, '13900139002', 'lisi@example.com', '产品经理', '字节跳动', 1, 90),
('T003', '王五', '小王', 2, '13900139003', 'wangwu@example.com', '设计师', '腾讯', 1, 88),
('T004', '赵六', '小赵', 1, '13900139004', 'zhaoliu@example.com', '运营', '网易', 1, 85),
('T005', '钱七', '小钱', 2, '13900139005', 'qianqi@example.com', '财务', '美团', 1, 92),
('T006', '孙八', '小孙', 1, '13900139006', 'sunba@example.com', '测试', '京东', 1, 87),
('T007', '周九', '小周', 2, '13900139007', 'zhoujiu@example.com', '运维', '华为', 1, 91),
('T008', '吴十', '小吴', 1, '13900139008', 'wushi@example.com', '销售', '小米', 1, 89),
('T009', '郑十一', '小郑', 2, '13900139009', 'zheng11@example.com', 'HR', '百度', 1, 93),
('T010', '王小明', '小明', 1, '13900139010', 'wangxm@example.com', '前端开发', '滴滴', 1, 90);

-- 插入合同数据
INSERT INTO `contract` (`contract_no`, `room_id`, `tenant_id`, `store_id`, `contract_type`, `sign_date`, `start_date`, `end_date`, `lease_term`, `rent_amount`, `deposit_amount`, `payment_cycle`, `contract_status`) VALUES
('CT2026001', 1, 1, 1, 1, '2026-01-01', '2026-01-15', '2027-01-14', 12, 2500.00, 2500.00, 1, 2),
('CT2026002', 6, 2, 1, 1, '2026-02-01', '2026-02-10', '2027-02-09', 12, 2550.00, 5100.00, 1, 2),
('CT2026003', 8, 3, 1, 1, '2026-03-01', '2026-03-01', '2027-03-01', 12, 4500.00, 9000.00, 2, 2),
('CT2026004', 11, 4, 1, 1, '2026-04-01', '2026-04-15', '2027-04-14', 12, 2800.00, 2800.00, 1, 2),
('CT2026005', 12, 5, 1, 1, '2026-05-01', '2026-05-01', '2027-05-01', 12, 2450.00, 4900.00, 1, 2);

-- 插入账单数据
INSERT INTO `bill` (`bill_no`, `contract_id`, `tenant_id`, `room_id`, `store_id`, `bill_type`, `bill_period_start`, `bill_period_end`, `due_date`, `amount`, `paid_amount`, `final_amount`, `bill_status`) VALUES
('BL202606001', 1, 1, 1, 1, 1, '2026-06-01', '2026-06-30', '2026-06-15', 2500.00, 2500.00, 2500.00, 3),
('BL202606002', 2, 2, 6, 1, 1, '2026-06-01', '2026-06-30', '2026-06-10', 2550.00, 2550.00, 2550.00, 3),
('BL202606003', 3, 3, 8, 1, 1, '2026-06-01', '2026-06-30', '2026-06-01', 4500.00, 0.00, 4500.00, 1),
('BL202606004', 4, 4, 11, 1, 1, '2026-06-01', '2026-06-30', '2026-06-15', 2800.00, 1400.00, 2800.00, 2),
('BL202606005', 5, 5, 12, 1, 1, '2026-06-01', '2026-06-30', '2026-06-01', 2450.00, 0.00, 2450.00, 4);

-- 插入维修工单数据
INSERT INTO `maintenance_order` (`order_no`, `room_id`, `tenant_id`, `store_id`, `category`, `urgency_level`, `description`, `assignee_name`, `estimated_cost`, `order_status`) VALUES
('WO202606001', 4, 1, 1, '水电', 1, '卫生间水龙头漏水严重', '李师傅', 200.00, 5),
('WO202606002', 7, 2, 1, '家电', 2, '空调制冷效果差', '王师傅', 350.00, 3),
('WO202606003', 9, 3, 1, '家具', 3, '衣柜门合页松动', '张师傅', 50.00, 2);

-- 插入保洁计划数据
INSERT INTO `cleaning_plan` (`plan_no`, `store_id`, `plan_name`, `plan_type`, `frequency`, `frequency_value`, `assigned_to`, `estimated_duration`, `status`) VALUES
('CP2026001', 1, '1号楼日常保洁', 1, 'daily', 1, '保洁组A', 60, 1),
('CP2026002', 1, '2号楼深度保洁', 2, 'weekly', 1, '保洁组B', 180, 1),
('CP2026003', 2, 'A栋定期大扫除', 4, 'monthly', 15, '保洁组C', 300, 1);

-- 插入入住登记数据
INSERT INTO `check_in` (`check_in_no`, `contract_id`, `tenant_id`, `room_id`, `actual_check_in_date`, `key_card_no`, `key_card_count`, `status`) VALUES
('CI2026001', 1, 1, 1, '2026-01-15', 'KC001', 2, 1),
('CI2026002', 2, 2, 6, '2026-02-10', 'KC002', 1, 1),
('CI2026003', 3, 3, 8, '2026-03-01', 'KC003', 2, 1);

-- 插入退租申请数据
INSERT INTO `check_out_application` (`application_no`, `contract_id`, `tenant_id`, `room_id`, `application_date`, `expected_check_out_date`, `check_out_reason`, `application_status`) VALUES
('CO2026001', 1, 1, 1, '2026-12-01', '2026-12-15', '工作调动', 1);

-- ============================================
-- 创建视图（根据实体类中的View定义）
-- ============================================

-- 活跃合同视图
CREATE VIEW `v_active_contracts` AS
SELECT c.*, t.real_name AS tenant_name, r.room_code, s.store_name
FROM `contract` c
LEFT JOIN `tenant` t ON c.tenant_id = t.id
LEFT JOIN `room` r ON c.room_id = r.id
LEFT JOIN `store` s ON c.store_id = s.id
WHERE c.contract_status = 2 AND c.is_deleted = 0;

-- 活跃房间视图
CREATE VIEW `v_active_rooms` AS
SELECT r.*, b.building_name, rt.type_name, t.real_name AS current_tenant_name
FROM `room` r
LEFT JOIN `building` b ON r.building_id = b.id
LEFT JOIN `room_type` rt ON r.room_type_id = rt.id
LEFT JOIN `tenant` t ON r.current_tenant_id = t.id
WHERE r.status != 3 AND r.is_deleted = 0;

-- 活跃租户视图
CREATE VIEW `v_active_tenants` AS
SELECT t.*, r.room_code, b.building_name
FROM `tenant` t
LEFT JOIN `room` r ON t.id = r.current_tenant_id
LEFT JOIN `building` b ON r.building_id = b.id
WHERE t.status = 1 AND t.is_deleted = 0;

-- 入住率视图
CREATE VIEW `v_occupancy_rate` AS
SELECT 
    s.store_id,
    s.store_name,
    COUNT(DISTINCT r.id) AS total_rooms,
    SUM(CASE WHEN r.status = 2 THEN 1 ELSE 0 END) AS occupied_rooms,
    ROUND(SUM(CASE WHEN r.status = 2 THEN 1 ELSE 0 END) / COUNT(DISTINCT r.id) * 100, 2) AS occupancy_rate
FROM `room` r
LEFT JOIN `store` s ON r.store_id = s.id
WHERE r.is_deleted = 0
GROUP BY s.store_id, s.store_name;

-- 逾期租户TOP10视图
CREATE VIEW `v_overdue_tenant_top10` AS
SELECT 
    t.id,
    t.real_name,
    t.phone,
    COUNT(b.id) AS overdue_count,
    SUM(b.amount - b.paid_amount) AS total_overdue_amount
FROM `tenant` t
LEFT JOIN `bill` b ON t.id = b.tenant_id
WHERE b.bill_status = 4 AND b.is_deleted = 0 AND t.is_deleted = 0
GROUP BY t.id, t.real_name, t.phone
ORDER BY total_overdue_amount DESC
LIMIT 10;

-- 应收统计视图
CREATE VIEW `v_receivable_stats` AS
SELECT 
    s.store_id,
    s.store_name,
    SUM(CASE WHEN b.bill_status = 1 THEN b.final_amount ELSE 0 END) AS pending_amount,
    SUM(CASE WHEN b.bill_status = 4 THEN b.final_amount ELSE 0 END) AS overdue_amount,
    SUM(b.final_amount - b.paid_amount) AS total_receivable
FROM `bill` b
LEFT JOIN `store` s ON b.store_id = s.id
WHERE b.is_deleted = 0
GROUP BY s.store_id, s.store_name;

-- 租金趋势视图
CREATE VIEW `v_rent_trend` AS
SELECT 
    DATE_FORMAT(b.created_time, '%Y-%m') AS month,
    SUM(CASE WHEN b.bill_type = 1 THEN b.paid_amount ELSE 0 END) AS rent_income,
    SUM(CASE WHEN b.bill_type = 2 THEN b.paid_amount ELSE 0 END) AS deposit_income
FROM `bill` b
WHERE b.bill_status = 3 AND b.is_deleted = 0
GROUP BY DATE_FORMAT(b.created_time, '%Y-%m')
ORDER BY month DESC;

-- 房态仪表盘视图
CREATE VIEW `v_room_status_dashboard` AS
SELECT 
    s.store_id,
    s.store_name,
    SUM(CASE WHEN r.status = 1 THEN 1 ELSE 0 END) AS available_count,
    SUM(CASE WHEN r.status = 2 THEN 1 ELSE 0 END) AS rented_count,
    SUM(CASE WHEN r.status = 3 THEN 1 ELSE 0 END) AS maintenance_count,
    SUM(CASE WHEN r.status = 4 THEN 1 ELSE 0 END) AS reserved_count,
    SUM(CASE WHEN r.status = 5 THEN 1 ELSE 0 END) AS booked_count,
    COUNT(r.id) AS total_count
FROM `room` r
LEFT JOIN `store` s ON r.store_id = s.id
WHERE r.is_deleted = 0
GROUP BY s.store_id, s.store_name;

-- 门店运营报表视图
CREATE VIEW `v_store_operation_report` AS
SELECT 
    s.store_id,
    s.store_name,
    COUNT(DISTINCT r.id) AS total_rooms,
    SUM(CASE WHEN r.status = 2 THEN 1 ELSE 0 END) AS occupied_rooms,
    COUNT(DISTINCT c.id) AS contract_count,
    SUM(b.amount) AS total_bill_amount,
    SUM(b.paid_amount) AS total_paid_amount,
    COUNT(mo.id) AS maintenance_count
FROM `store` s
LEFT JOIN `room` r ON s.id = r.store_id
LEFT JOIN `contract` c ON s.id = c.store_id AND c.contract_status = 2
LEFT JOIN `bill` b ON s.id = b.store_id
LEFT JOIN `maintenance_order` mo ON s.id = mo.store_id
WHERE s.is_deleted = 0
GROUP BY s.store_id, s.store_name;

-- 空置分析视图
CREATE VIEW `v_vacancy_analysis` AS
SELECT 
    b.building_id,
    b.building_name,
    s.store_name,
    COUNT(r.id) AS total_rooms,
    SUM(CASE WHEN r.status = 1 THEN 1 ELSE 0 END) AS vacant_rooms,
    ROUND(SUM(CASE WHEN r.status = 1 THEN 1 ELSE 0 END) / COUNT(r.id) * 100, 2) AS vacancy_rate,
    AVG(DATEDIFF(CURDATE(), r.created_time)) AS avg_days_vacant
FROM `building` b
LEFT JOIN `room` r ON b.id = r.building_id AND r.status = 1
LEFT JOIN `store` s ON b.store_id = s.id
WHERE b.is_deleted = 0
GROUP BY b.building_id, b.building_name, s.store_name;

COMMIT;

-- ============================================
-- 脚本执行完成
-- ============================================
SELECT '数据库初始化完成' AS message, 
       (SELECT COUNT(*) FROM `store`) AS store_count,
       (SELECT COUNT(*) FROM `building`) AS building_count,
       (SELECT COUNT(*) FROM `room`) AS room_count,
       (SELECT COUNT(*) FROM `tenant`) AS tenant_count,
       (SELECT COUNT(*) FROM `contract`) AS contract_count,
       (SELECT COUNT(*) FROM `bill`) AS bill_count;
