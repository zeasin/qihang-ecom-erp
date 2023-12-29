DROP TABLE IF EXISTS `scm_supplier`;
CREATE TABLE `scm_supplier`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(50)  DEFAULT NULL COMMENT '供应商名称',
  `number` varchar(18)  DEFAULT NULL COMMENT '供应商编码',
  `taxRate` double DEFAULT 0 COMMENT '税率',
  `amount` double DEFAULT 0 COMMENT '期初应付款',
  `periodMoney` double DEFAULT 0 COMMENT '期初预付款',
  `difMoney` double DEFAULT 0 COMMENT '初期往来余额',
  `beginDate` date DEFAULT NULL COMMENT '余额日期',
  `remark` varchar(100)  DEFAULT '' COMMENT '备注',
  `place` varchar(100)  DEFAULT '' COMMENT '职位',
  `linkMan` varchar(10)  DEFAULT NULL COMMENT '联系人',
  `contact` varchar(15)  DEFAULT '' COMMENT '联系方式',
  `province` varchar(20)  DEFAULT NULL COMMENT '省',
  `city` varchar(20)  DEFAULT NULL COMMENT '市',
  `county` varchar(20)  DEFAULT NULL COMMENT '区县',
  `address` varchar(100)  DEFAULT NULL COMMENT '收货地址详情',
  `pinYin` varchar(50)  DEFAULT '',
  `disable` tinyint(1) DEFAULT 0 COMMENT '0启用   1禁用',
  `isDelete` tinyint(1) DEFAULT 0 COMMENT '0正常 1删除',
  `purchaserName` varchar(50)  DEFAULT NULL COMMENT '分管采购员',
  `createTime` datetime(0) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `id`(`id`)
) 

-- ----------------------------
-- Records of scm_supplier
-- ----------------------------
INSERT INTO `scm_supplier` VALUES (1, '自营仓库', 'A0001', 0, 0, 0, 0, NULL, '', '', NULL, '15000000000', NULL, NULL, NULL, NULL, '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (13, '可可家一号店', '6972501884875', 0, 0, 0, 0, NULL, '', '', NULL, '15815252000', NULL, NULL, NULL, NULL, '', 1, 1, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (14, '衫人家', '6972501884876', 0, 0, 0, 0, NULL, '', '', NULL, '15815252000', NULL, NULL, NULL, NULL, '', 1, 1, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (15, '依依', 'ZB001', 0, 0, 0, 0, NULL, '', '', NULL, '13000000000', NULL, NULL, NULL, NULL, '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (16, '念念', 'ZB002', 0, 0, 0, 0, NULL, '', '', NULL, '13000000000', NULL, NULL, NULL, NULL, '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (17, '华衣服饰', 'ZB003', 0, 0, 0, 0, NULL, '', '', NULL, '13000000000', NULL, NULL, NULL, NULL, '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (18, '小颖家', 'ZB004', 0, 0, 0, 0, NULL, '', '', NULL, '13000000000', NULL, NULL, NULL, NULL, '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (19, '森庄农品旗舰店', 'TM-SZNPQJD', 0, 0, 0, 0, NULL, '', '', NULL, '13600000000', NULL, NULL, NULL, NULL, '', 1, 1, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (20, '小仙', 'XFZ001', 0, 0, 0, 0, NULL, '', '', NULL, '18516258530', '上海', '上海市', '松江区', '上海市松江区中山街道松江万达广场一号楼417', '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (21, '岳鑫', '', 0, 0, 0, 0, NULL, '', '', NULL, '15081033273', '河北省', '邯郸市', '黄粱梦镇', '河北省 邯郸市 丛台区 黄粱梦镇黄粱梦村', '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (22, '三夫食品专营店', 'SFSP', 0, 0, 0, 0, NULL, '', '', NULL, '13600000000', NULL, NULL, NULL, NULL, '', 1, 1, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (23, '安琪酵母旗舰店', 'ANQIJM', 0, 0, 0, 0, NULL, '', '', NULL, '13600000000', NULL, NULL, NULL, NULL, '', 1, 1, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (24, '慈溪市观海卫滕洋电器厂', '1688', 0, 0, 0, 0, NULL, '', '', NULL, '13000001111', NULL, NULL, NULL, NULL, '', 1, 1, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (25, '小熊驾到旗舰店', 'XXJD', 0, 0, 0, 0, NULL, '', '', NULL, '13600000000', NULL, NULL, NULL, NULL, '', 1, 1, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (26, '韩牛服饰', 'HN', 0, 0, 0, 0, NULL, '', '', NULL, '13249571426', NULL, NULL, NULL, NULL, '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (27, '深圳市罗湖区晨蝶衣服装厂', 'CDYFZC', 0, 0, 0, 0, NULL, '', '', NULL, '13600000000', NULL, NULL, NULL, NULL, '', 1, 1, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (28, '广州衣菲妮服装厂', 'YIFEINI', 0, 0, 0, 0, NULL, '', '', NULL, '13600000000', NULL, NULL, NULL, NULL, '', 1, 1, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (29, '中山欧熙妮服饰有限公司', 'ZSOXNFS', 0, 0, 0, 0, NULL, '', '', NULL, '13600000000', NULL, NULL, NULL, NULL, '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (30, '零零伊', 'AA001', 0, 0, 0, 0, NULL, '', '抖音', NULL, 'fsd1027', NULL, NULL, NULL, '', '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (31, '中山市金客隆服饰有限公司', 'JKL', 0, 0, 0, 0, NULL, '', '', NULL, '13600000000', NULL, NULL, NULL, NULL, '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (32, '广州柚柚子服饰商行', 'GZYYZ', 0, 0, 0, 0, NULL, '', '', NULL, '18557527708', NULL, NULL, NULL, NULL, '', 0, 0, NULL, current_timestamp());
INSERT INTO `scm_supplier` VALUES (33, '中山裤豪', 'ZSKH', 0, 0, 0, 0, NULL, '档口微信18928102400陈小姐工厂微信18022115438何超贤 ', '', NULL, '18928102400', NULL, NULL, NULL, '中山市沙溪镇水牛城三区二楼35-38卡', '', 0, 0, NULL, current_timestamp());
