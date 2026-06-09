-- ============================================
-- 退租申请测试数据脚本
-- 说明：插入多条退租申请数据，覆盖所有4种申请状态
-- 依赖：需要先有合同、租户、房间数据
-- 申请状态: 1-待审核, 2-审核通过, 3-已拒绝, 4-已完成
-- ============================================

USE apartment_hubs;

-- ============================================
-- 先获取需要用到的合同ID（通过合同编号查询）
-- 这些合同是在 contract_bill_test_data.sql 中插入的
-- ============================================

-- 获取合同ID
SELECT id INTO @contract_id_10 FROM contract WHERE contract_no = 'CT2026010';
SELECT id INTO @contract_id_11 FROM contract WHERE contract_no = 'CT2026011';
SELECT id INTO @contract_id_12 FROM contract WHERE contract_no = 'CT2026012';
SELECT id INTO @contract_id_13 FROM contract WHERE contract_no = 'CT2026013';
SELECT id INTO @contract_id_14 FROM contract WHERE contract_no = 'CT2026014';

-- 也获取原有的合同ID（init.sql中插入的）
SELECT id INTO @contract_id_1 FROM contract WHERE contract_no = 'CT2026001';
SELECT id INTO @contract_id_2 FROM contract WHERE contract_no = 'CT2026002';
SELECT id INTO @contract_id_3 FROM contract WHERE contract_no = 'CT2026003';

-- ============================================
-- 插入退租申请数据
-- 覆盖所有4种申请状态
-- ============================================

INSERT INTO `check_out_application` (
    `application_no`, `contract_id`, `tenant_id`, `room_id`,
    `application_date`, `expected_check_out_date`, `actual_check_out_date`,
    `check_out_reason`, `application_status`, `approver`, `approve_time`,
    `approve_remark`, `remark`, `created_by`, `created_time`
) VALUES
-- 申请1: 状态1-待审核 - 租户6申请退租
('CO2026002', @contract_id_10, 6, 2,
 '2026-06-01', '2026-06-30', NULL,
 '工作原因，需要搬到公司附近居住',
 1, NULL, NULL, NULL,
 '租户已提交申请，等待审核',
 'system', NOW()),

-- 申请2: 状态2-审核通过 - 租户7申请退租
('CO2026003', @contract_id_11, 7, 3,
 '2026-05-15', '2026-06-15', NULL,
 '家庭团聚，回老家发展',
 2, '张经理', '2026-05-18 10:30:00',
 '合同未到期，按合同约定扣除1个月押金作为违约金',
 '审核通过，等待租户办理退租手续',
 'system', NOW()),

-- 申请3: 状态3-已拒绝 - 租户8申请退租（已拒绝示例）
('CO2026004', @contract_id_12, 8, 4,
 '2026-05-20', '2026-06-20', NULL,
 '房间太小，想换一间大一点的',
 3, '李经理', '2026-05-22 14:15:00',
 '退租原因不符合合同约定的特殊情况条款，且合同还有10个月才到期。建议可以申请换房而不是退租。',
 '已拒绝，租户可申请换房',
 'system', NOW()),

-- 申请4: 状态4-已完成 - 租户9申请退租（已完成示例）
('CO2026005', @contract_id_13, 9, 5,
 '2026-05-01', '2026-05-15', '2026-05-15',
 '合同到期，不再续租',
 4, '王经理', '2026-05-05 09:00:00',
 '合同正常到期，全额退还押金',
 '已完成退租手续，退还押金3500元，水电费已结清',
 'system', NOW()),

-- 申请5: 状态2-审核通过 - 租户10申请退租（短租合同）
('CO2026006', @contract_id_14, 10, 7,
 '2026-02-20', '2026-03-05', '2026-03-05',
 '找到更便宜的房源',
 2, '赵经理', '2026-02-22 11:00:00',
 '合同已提前终止，按合同约定处理押金',
 '已办理退租，合同编号CT2026014提前终止',
 'system', NOW()),

-- 申请6: 状态1-待审核 - 租户1申请退租（原合同1）
('CO2026007', @contract_id_1, 1, 1,
 '2026-06-05', '2026-12-15', NULL,
 '个人原因，计划年底搬家',
 1, NULL, NULL, NULL,
 '提前6个月提交申请，计划合同到期后退租',
 'system', NOW()),

-- 申请7: 状态4-已完成 - 租户3申请退租（原合同3，已完成退租）
('CO2026008', @contract_id_3, 3, 8,
 '2026-04-10', '2026-05-01', '2026-05-01',
 '购买了新房，不需要租房了',
 4, '李经理', '2026-04-15 16:00:00',
 '合同正常履约，无违约情况，押金全额退还',
 '已完成退租，退还押金9000元，钥匙门禁卡已回收',
 'system', NOW());

-- ============================================
-- 验证：查询退租申请数据
-- ============================================

SELECT '--- 退租申请数据汇总 ---' AS info;
SELECT
    ca.id AS '申请ID',
    ca.application_no AS '申请编号',
    c.contract_no AS '关联合同',
    t.real_name AS '租户姓名',
    t.phone AS '联系电话',
    r.room_code AS '房间号',
    b.building_name AS '楼栋',
    ca.application_date AS '申请日期',
    ca.expected_check_out_date AS '预计退租日期',
    ca.actual_check_out_date AS '实际退租日期',
    ca.check_out_reason AS '退租原因',
    CASE ca.application_status
        WHEN 1 THEN '待审核'
        WHEN 2 THEN '审核通过'
        WHEN 3 THEN '已拒绝'
        WHEN 4 THEN '已完成'
        ELSE '未知'
    END AS '申请状态',
    ca.approver AS '审核人',
    ca.approve_time AS '审核时间',
    ca.approve_remark AS '审核备注'
FROM check_out_application ca
LEFT JOIN contract c ON ca.contract_id = c.id
LEFT JOIN tenant t ON ca.tenant_id = t.id
LEFT JOIN room r ON ca.room_id = r.id
LEFT JOIN building b ON r.building_id = b.id
ORDER BY ca.id;

SELECT '--- 各申请状态统计 ---' AS info;
SELECT
    CASE application_status
        WHEN 1 THEN '待审核'
        WHEN 2 THEN '审核通过'
        WHEN 3 THEN '已拒绝'
        WHEN 4 THEN '已完成'
        ELSE '未知'
    END AS '申请状态',
    COUNT(*) AS '申请数量'
FROM check_out_application
GROUP BY application_status
ORDER BY application_status;

SELECT '--- 退租申请与关联合同详情 ---' AS info;
SELECT
    ca.application_no AS '申请编号',
    c.contract_no AS '合同编号',
    t.real_name AS '租户',
    CONCAT(r.room_code, ' (', b.building_name, ')') AS '房间信息',
    CONCAT('¥', c.rent_amount, '/月') AS '月租金',
    CONCAT('¥', c.deposit_amount) AS '押金',
    c.start_date AS '合同开始',
    c.end_date AS '合同结束',
    CASE c.contract_status
        WHEN 1 THEN '草稿'
        WHEN 2 THEN '生效中'
        WHEN 3 THEN '已到期'
        WHEN 4 THEN '已终止'
        WHEN 5 THEN '已续签'
        ELSE '未知'
    END AS '合同状态',
    CASE ca.application_status
        WHEN 1 THEN '待审核'
        WHEN 2 THEN '审核通过'
        WHEN 3 THEN '已拒绝'
        WHEN 4 THEN '已完成'
        ELSE '未知'
    END AS '退租状态',
    ca.check_out_reason AS '退租原因'
FROM check_out_application ca
LEFT JOIN contract c ON ca.contract_id = c.id
LEFT JOIN tenant t ON ca.tenant_id = t.id
LEFT JOIN room r ON ca.room_id = r.id
LEFT JOIN building b ON r.building_id = b.id
ORDER BY ca.application_status, ca.application_date DESC;

SELECT '--- 退租测试数据脚本执行完成 ---' AS message;
