-- ============================================
-- 合同与账单测试数据脚本
-- 说明：插入5条新合同及关联的不同状态账单数据
-- 执行前请确认数据库中已有门店、楼栋、房间、租户数据
-- ============================================

USE apartment_hubs;

-- ============================================
-- 插入5条新合同数据
-- 使用房间ID: 2, 3, 4, 5, 7, 9, 10（未被已有合同占用）
-- 租户ID: 6, 7, 8, 9, 10
-- 合同状态: 1-草稿, 2-生效中, 3-已到期, 4-已终止, 5-已续签
-- ============================================

INSERT INTO `contract` (
    `contract_no`, `room_id`, `tenant_id`, `store_id`, `contract_type`,
    `sign_date`, `start_date`, `end_date`, `lease_term`, `rent_amount`,
    `deposit_amount`, `payment_cycle`, `payment_day`, `rent_free_days`,
    `rent_free_amount`, `other_fees`, `contract_status`, `remark`,
    `created_by`, `created_time`
) VALUES
-- 合同1: 新签合同 - 生效中 - 关联状态1(待支付)账单
('CT2026010', 2, 6, 1, 1, '2026-01-10', '2026-01-20', '2027-01-19', 12,
 2500.00, 2500.00, 1, 20, 0, 0.00, '{"water":50,"electric":100,"property":0,"internet":0}',
 2, '测试合同1 - 关联待支付账单', 'system', NOW()),

-- 合同2: 新签合同 - 生效中 - 关联状态2(部分支付)账单
('CT2026011', 3, 7, 1, 1, '2026-02-15', '2026-03-01', '2027-02-28', 12,
 2600.00, 2600.00, 1, 1, 3, 300.00, '{"water":50,"electric":150,"property":0,"internet":0}',
 2, '测试合同2 - 关联部分支付账单', 'system', NOW()),

-- 合同3: 续签合同 - 生效中 - 关联状态3(已支付)账单
('CT2026012', 4, 8, 1, 2, '2026-03-20', '2026-04-01', '2027-03-31', 12,
 3500.00, 7000.00, 2, 1, 0, 0.00, '{"water":80,"electric":200,"property":50,"internet":30}',
 2, '测试合同3 - 关联已支付账单', 'system', NOW()),

-- 合同4: 新签合同 - 已到期 - 关联状态4(逾期)账单
('CT2026013', 5, 9, 1, 1, '2025-06-01', '2025-06-15', '2026-06-14', 12,
 3500.00, 3500.00, 1, 15, 0, 0.00, '{"water":60,"electric":120,"property":0,"internet":0}',
 3, '测试合同4 - 关联逾期账单（合同已到期）', 'system', NOW()),

-- 合同5: 新签合同 - 已终止 - 关联状态5(已作废)账单
('CT2026014', 7, 10, 1, 1, '2026-01-05', '2026-01-10', '2026-03-09', 2,
 2550.00, 2550.00, 1, 10, 0, 0.00, '{"water":50,"electric":100,"property":0,"internet":0}',
 4, '测试合同5 - 关联已作废账单（合同已终止）', 'system', NOW());

-- ============================================
-- 查看刚插入的5条合同的ID
-- ============================================
SELECT @contract1_id := LAST_INSERT_ID() AS contract_id;
SET @contract1_id = @contract1_id;

-- 获取新插入的5条合同的ID（由于是连续自增，ID应该是 n, n+1, n+2, n+3, n+4）
-- 我们通过合同编号来获取准确的ID
SELECT id INTO @contract_id_1 FROM contract WHERE contract_no = 'CT2026010';
SELECT id INTO @contract_id_2 FROM contract WHERE contract_no = 'CT2026011';
SELECT id INTO @contract_id_3 FROM contract WHERE contract_no = 'CT2026012';
SELECT id INTO @contract_id_4 FROM contract WHERE contract_no = 'CT2026013';
SELECT id INTO @contract_id_5 FROM contract WHERE contract_no = 'CT2026014';

-- ============================================
-- 插入与合同关联的账单数据
-- 账单状态: 1-待支付, 2-部分支付, 3-已支付, 4-逾期, 5-已作废
-- 确保每类账单状态都至少有一条数据
-- ============================================

INSERT INTO `bill` (
    `bill_no`, `contract_id`, `tenant_id`, `room_id`, `store_id`, `bill_type`,
    `bill_period_start`, `bill_period_end`, `due_date`, `amount`, `paid_amount`,
    `discount_amount`, `late_fee`, `final_amount`, `bill_status`, `payment_method`,
    `payment_time`, `remark`, `created_by`, `created_time`
) VALUES
-- 账单1: 状态1-待支付 - 租金账单 - 6月份
('BL202606010', @contract_id_1, 6, 2, 1, 1,
 '2026-06-01', '2026-06-30', '2026-06-20',
 2500.00, 0.00, 0.00, 0.00, 2500.00, 1,
 NULL, NULL, '6月份租金账单 - 待支付', 'auto', NOW()),

-- 账单2: 状态2-部分支付 - 租金账单 - 5月份
('BL202605011', @contract_id_2, 7, 3, 1, 1,
 '2026-05-01', '2026-05-31', '2026-05-01',
 2600.00, 1500.00, 0.00, 0.00, 2600.00, 2,
 'wechat', '2026-05-05 14:30:00', '5月份租金账单 - 部分支付（剩余1100元）', 'auto', NOW()),

-- 账单3: 状态3-已支付 - 租金账单 - 5月份
('BL202605012', @contract_id_3, 8, 4, 1, 1,
 '2026-05-01', '2026-05-31', '2026-05-01',
 3500.00, 3500.00, 0.00, 0.00, 3500.00, 3,
 'alipay', '2026-05-02 10:15:00', '5月份租金账单 - 已支付（支付宝）', 'auto', NOW()),

-- 账单4: 状态4-逾期 - 租金账单 - 5月份
('BL202605013', @contract_id_4, 9, 5, 1, 1,
 '2026-05-01', '2026-05-31', '2026-05-15',
 3500.00, 0.00, 0.00, 350.00, 3850.00, 4,
 NULL, NULL, '5月份租金账单 - 逾期未付（产生10%逾期费）', 'auto', NOW()),

-- 账单5: 状态5-已作废 - 租金账单 - 2月份
('BL202602014', @contract_id_5, 10, 7, 1, 1,
 '2026-02-01', '2026-02-28', '2026-02-10',
 2550.00, 0.00, 0.00, 0.00, 2550.00, 5,
 NULL, NULL, '2月份租金账单 - 已作废（合同提前终止）', 'auto', NOW());

-- ============================================
-- 验证：查询合同数据和关联的账单数据
-- ============================================
SELECT '--- 新插入的合同数据 ---' AS info;
SELECT
    c.id AS '合同ID',
    c.contract_no AS '合同编号',
    t.real_name AS '租户姓名',
    r.room_code AS '房间号',
    CASE c.contract_type
        WHEN 1 THEN '新签'
        WHEN 2 THEN '续签'
        WHEN 3 THEN '变更'
        ELSE '未知'
    END AS '合同类型',
    CASE c.contract_status
        WHEN 1 THEN '草稿'
        WHEN 2 THEN '生效中'
        WHEN 3 THEN '已到期'
        WHEN 4 THEN '已终止'
        WHEN 5 THEN '已续签'
        ELSE '未知'
    END AS '合同状态',
    CONCAT(c.rent_amount, '元/月') AS '月租金',
    CONCAT(c.lease_term, '个月') AS '租期',
    c.start_date AS '开始日期',
    c.end_date AS '结束日期'
FROM contract c
LEFT JOIN tenant t ON c.tenant_id = t.id
LEFT JOIN room r ON c.room_id = r.id
WHERE c.contract_no LIKE 'CT202601%'
ORDER BY c.id;

SELECT '--- 新插入的账单数据（覆盖所有5种状态） ---' AS info;
SELECT
    b.id AS '账单ID',
    b.bill_no AS '账单编号',
    c.contract_no AS '关联合同',
    t.real_name AS '租户姓名',
    r.room_code AS '房间号',
    CASE b.bill_type
        WHEN 1 THEN '租金'
        WHEN 2 THEN '押金'
        WHEN 3 THEN '水电费'
        WHEN 4 THEN '物业费'
        WHEN 5 THEN '网络费'
        WHEN 6 THEN '维修费'
        WHEN 7 THEN '违约金'
        WHEN 8 THEN '其他'
        ELSE '未知'
    END AS '账单类型',
    CONCAT('¥', b.amount) AS '账单金额',
    CONCAT('¥', b.paid_amount) AS '已付金额',
    CONCAT('¥', b.late_fee) AS '逾期费',
    CASE b.bill_status
        WHEN 1 THEN '待支付'
        WHEN 2 THEN '部分支付'
        WHEN 3 THEN '已支付'
        WHEN 4 THEN '逾期'
        WHEN 5 THEN '已作废'
        ELSE '未知'
    END AS '账单状态',
    b.payment_method AS '支付方式',
    b.due_date AS '缴费截止日期',
    b.payment_time AS '支付时间'
FROM bill b
LEFT JOIN contract c ON b.contract_id = c.id
LEFT JOIN tenant t ON b.tenant_id = t.id
LEFT JOIN room r ON b.room_id = r.id
WHERE b.bill_no LIKE 'BL2026%010' OR b.bill_no LIKE 'BL2026%011'
   OR b.bill_no LIKE 'BL2026%012' OR b.bill_no LIKE 'BL2026%013'
   OR b.bill_no LIKE 'BL2026%014'
ORDER BY b.bill_status;

SELECT '--- 各账单状态统计 ---' AS info;
SELECT
    CASE bill_status
        WHEN 1 THEN '待支付'
        WHEN 2 THEN '部分支付'
        WHEN 3 THEN '已支付'
        WHEN 4 THEN '逾期'
        WHEN 5 THEN '已作废'
        ELSE '未知'
    END AS '账单状态',
    COUNT(*) AS '账单数量',
    CONCAT('¥', SUM(amount)) AS '总金额',
    CONCAT('¥', SUM(paid_amount)) AS '已收金额'
FROM bill
WHERE bill_no LIKE 'BL2026%01_'
GROUP BY bill_status
ORDER BY bill_status;

SELECT '--- 脚本执行完成 ---' AS message;
