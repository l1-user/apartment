-- ============================================
-- 系统用户表 - 租客用户测试数据脚本
-- 用户类型: 1-超级管理员, 2-门店管理员, 3-店长, 4-管家, 5-财务, 6-维修工, 7-保洁员, 8-租客
-- ============================================

USE apartment_hubs;

-- ============================================
-- 1. 如果 sys_user 表不存在，则创建
-- ============================================
CREATE TABLE IF NOT EXISTS `sys_user` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码(加密)',
    `real_name` VARCHAR(50) COMMENT '真实姓名',
    `avatar` VARCHAR(255) COMMENT '头像URL',
    `email` VARCHAR(100) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `store_id` BIGINT COMMENT '所属门店ID(多门店可空)',
    `user_type` TINYINT DEFAULT 1 COMMENT '用户类型: 1-超级管理员, 2-门店管理员, 3-店长, 4-管家, 5-财务, 6-维修工, 7-保洁员, 8-租客',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    `last_login_time` DATETIME COMMENT '最后登录时间',
    `last_login_ip` VARCHAR(50) COMMENT '最后登录IP',
    `remark` VARCHAR(500) COMMENT '备注',
    `created_by` VARCHAR(50) COMMENT '创建人',
    `created_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(50) COMMENT '更新人',
    `updated_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_deleted` TINYINT DEFAULT 0 COMMENT '删除标记',
    UNIQUE INDEX `idx_username` (`username`),
    INDEX `idx_user_type` (`user_type`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

-- ============================================
-- 2. 插入管理员用户（作为参考，密码统一）
-- ============================================
-- 检查是否已存在管理员用户
SET @admin_exists = 0;
SELECT COUNT(*) INTO @admin_exists FROM sys_user WHERE username = 'admin';

INSERT INTO `sys_user` (`username`, `password`, `real_name`, `email`, `phone`, `user_type`, `status`, `remark`, `created_by`)
SELECT 'admin', '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', '超级管理员', 'admin@example.com', '13800138000', 1, 1, '系统超级管理员', 'system'
WHERE @admin_exists = 0;

-- ============================================
-- 3. 插入两条租客用户数据 (user_type=8)
-- 关联已有的租户数据（租户ID: 1-10）
-- ============================================

-- 租客用户1: 关联租户张三(ID=1)
INSERT INTO `sys_user` (
    `username`, `password`, `real_name`, `email`, `phone`, 
    `store_id`, `user_type`, `status`, `remark`, `created_by`
) VALUES (
    'tenant_zhangsan', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', 
    '张三', 
    'zhangsan@example.com', 
    '13900139001', 
    1, 
    8, 
    1, 
    '租客用户 - 张三，租住101号房', 
    'system'
);

-- 租客用户2: 关联租户李四(ID=2)
INSERT INTO `sys_user` (
    `username`, `password`, `real_name`, `email`, `phone`, 
    `store_id`, `user_type`, `status`, `remark`, `created_by`
) VALUES (
    'tenant_lisi', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', 
    '李四', 
    'lisi@example.com', 
    '13900139002', 
    1, 
    8, 
    1, 
    '租客用户 - 李四，租住201号房', 
    'system'
);

-- 租客用户3: 关联租户王五(ID=3)
INSERT INTO `sys_user` (
    `username`, `password`, `real_name`, `email`, `phone`, 
    `store_id`, `user_type`, `status`, `remark`, `created_by`
) VALUES (
    'tenant_wangwu', 
    '$2a$10$N9qo8uLOickgx2ZMRZoMye.IjzqAKL9xL5jvMFVdNJHvGCgTq/VEq', 
    '王五', 
    'wangwu@example.com', 
    '13900139003', 
    1, 
    8, 
    1, 
    '租客用户 - 王五，租住203号房', 
    'system'
);

-- ============================================
-- 验证：查询所有用户数据
-- ============================================

SELECT '--- 系统用户表数据 ---' AS info;
SELECT
    id AS '用户ID',
    username AS '用户名',
    real_name AS '真实姓名',
    email AS '邮箱',
    phone AS '手机号',
    store_id AS '门店ID',
    CASE user_type
        WHEN 1 THEN '超级管理员'
        WHEN 2 THEN '门店管理员'
        WHEN 3 THEN '店长'
        WHEN 4 THEN '管家'
        WHEN 5 THEN '财务'
        WHEN 6 THEN '维修工'
        WHEN 7 THEN '保洁员'
        WHEN 8 THEN '租客'
        ELSE '未知'
    END AS '用户类型',
    CASE status
        WHEN 0 THEN '禁用'
        WHEN 1 THEN '启用'
        ELSE '未知'
    END AS '状态',
    remark AS '备注',
    created_time AS '创建时间'
FROM sys_user
ORDER BY user_type, id;

SELECT '--- 用户类型统计 ---' AS info;
SELECT
    CASE user_type
        WHEN 1 THEN '超级管理员'
        WHEN 2 THEN '门店管理员'
        WHEN 3 THEN '店长'
        WHEN 4 THEN '管家'
        WHEN 5 THEN '财务'
        WHEN 6 THEN '维修工'
        WHEN 7 THEN '保洁员'
        WHEN 8 THEN '租客'
        ELSE '未知'
    END AS '用户类型',
    COUNT(*) AS '数量'
FROM sys_user
GROUP BY user_type
ORDER BY user_type;

SELECT '--- 租客用户详情 ---' AS info;
SELECT
    su.id AS '用户ID',
    su.username AS '用户名',
    su.real_name AS '姓名',
    su.phone AS '手机号',
    su.email AS '邮箱',
    t.tenant_code AS '租户编号',
    r.room_code AS '租住房间',
    b.building_name AS '楼栋',
    s.store_name AS '门店'
FROM sys_user su
LEFT JOIN tenant t ON su.phone = t.phone
LEFT JOIN room r ON t.id = r.current_tenant_id
LEFT JOIN building b ON r.building_id = b.id
LEFT JOIN store s ON su.store_id = s.id
WHERE su.user_type = 8
ORDER BY su.id;

SELECT '--- 脚本执行完成 ---' AS message;
