

-- ----------------------------
-- Table structure for erp_goods_brand
-- ----------------------------
DROP TABLE IF EXISTS `erp_goods_brand`;
CREATE TABLE `erp_goods_brand`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50)  NOT NULL COMMENT '品牌名',
  `status` int(0) DEFAULT NULL COMMENT '状态',
  `create_by` varchar(20)  DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_by` varchar(20)  DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

-- ----------------------------
-- Records of erp_goods_brand
-- ----------------------------
INSERT INTO `erp_goods_brand` VALUES (1, '梦小妮', 1, 'admin', '2023-12-29 13:44:29', 'admin', '2023-12-29 13:44:29');


