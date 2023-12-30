-- ----------------------------
-- Table structure for scm_purchase_order_cost
-- ----------------------------
DROP TABLE IF EXISTS `scm_purchase_order_cost`;
CREATE TABLE `scm_purchase_order_cost`  (
  `id` bigint NOT NULL COMMENT '采购单ID（主键）',
  `order_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '采购单金额',
  `actual_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '实际金额',
  `freight` decimal(6, 2) NULL DEFAULT NULL COMMENT '运费',
  `confirm_user` varchar(25)  NULL DEFAULT NULL COMMENT '确认人',
  `confirm_time` datetime NULL DEFAULT NULL COMMENT '确认时间',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) 
) 

-- ----------------------------
-- Table structure for scm_purchase_order_ship
-- ----------------------------
DROP TABLE IF EXISTS `scm_purchase_order_ship`;
CREATE TABLE `scm_purchase_order_ship`  (
  `id` bigint NOT NULL COMMENT '采购单ID（主键）',
  `ship_company` varchar(20)  NULL DEFAULT NULL COMMENT '物流公司',
  `ship_no` varchar(50)  NULL DEFAULT NULL COMMENT '物流单号',
  `freight` decimal(6, 0) NULL DEFAULT NULL COMMENT '运费',
  `ship_time` datetime NULL DEFAULT NULL COMMENT '运送时间',
  `create_by` varchar(50)  NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) 
) 


