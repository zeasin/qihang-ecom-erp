-- ----------------------------
-- Table structure for erp_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `erp_goods_category`;
CREATE TABLE `erp_goods_category`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `number` varchar(10)  DEFAULT NULL COMMENT '分类编码',
  `name` varchar(20)  DEFAULT NULL COMMENT '分类名称',
  `remark` varchar(50)  DEFAULT NULL,
  `parent_id` int(0) DEFAULT NULL COMMENT '上架分类id',
  `path` varchar(45)  NOT NULL DEFAULT '' COMMENT '分类路径',
  `sort` int(0) DEFAULT 0 COMMENT '排序值',
  `image` varchar(100)  DEFAULT NULL COMMENT '图片',
  `isDelete` tinyint(1) DEFAULT 0 COMMENT '0正常  1删除',
  `create_by` varchar(25)  DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(25)  DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) 
) 

-- ----------------------------
-- Records of erp_goods_category
-- ----------------------------
INSERT INTO `erp_goods_category` VALUES (1, 'NVZHUANG', '女装', NULL, 0, '0', 0, '', 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (2, 'MAOYI', '毛衣/针织衫', NULL, 1, '0|1', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (3, 'TXU', 'T恤', NULL, 1, '0|1', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (4, 'LIANYIQUN', '连衣裙', NULL, 1, '0|1', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (5, 'XIUXIANKU', '休闲裤', NULL, 1, '0|1', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (6, 'WAITAO', '外套', NULL, 1, '0|1', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (7, 'ZHENZHIPJ', '内衣/背心', NULL, 1, '0|1', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (8, 'SUOZHI', '半身裙', NULL, 1, '0|1', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (9, NULL, '衬衫', NULL, 1, '0|1', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (10, NULL, '箱包', NULL, 0, '0|1', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (11, NULL, '双肩包', NULL, 10, '0|10', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (12, NULL, '单肩包', NULL, 10, '0|10', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (13, NULL, '套装', NULL, 1, '0|1', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (14, NULL, '短裙', NULL, 10, '0|8', 0, NULL, 1, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (15, NULL, '半身裙', NULL, 10, '0|8', 0, NULL, 1, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (16, NULL, '连衣裙', NULL, 10, '0|1', 0, NULL, 1, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (17, NULL, '针织裤', NULL, 10, '0|1', 0, NULL, 1, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (18, NULL, '针织短裙', NULL, 10, '0|1', 0, NULL, 1, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (19, NULL, '针织外套', NULL, 10, '0|1', 0, NULL, 1, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (20, NULL, '针织背心', NULL, 10, '0|1', 0, NULL, 1, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (21, NULL, 'T恤衫', NULL, 10, '0|8', 0, NULL, 1, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (22, NULL, '梭织衬衫', NULL, 10, '0|8', 0, NULL, 1, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (23, 'BAIHUO', '百货', NULL, 0, '0', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (24, NULL, '调味调料', NULL, 23, '0|23', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (25, NULL, '小百货', NULL, 23, '0|23', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (26, 'NIUZAI', '牛仔裤', NULL, 1, '0|1|26', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (27, NULL, '牛仔短裤', NULL, 26, '0|26', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (28, NULL, '阔腿裤', NULL, 26, '0|26', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (29, NULL, '喇叭裤', NULL, 26, '0|26', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (30, NULL, '哈伦裤', '哈伦萝卜老爹裤', 26, '0|26', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (31, NULL, '铅笔裤', '小脚裤、紧身裤、铅笔裤', 26, '0|26', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (32, NULL, '直筒裤', '小直筒烟管裤', 26, '0|26', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);
INSERT INTO `erp_goods_category` VALUES (33, NULL, '背带裤', '', 26, '0|26', 0, NULL, 0, 'admin', '2023-12-29 14:42:36', NULL, NULL);

-- ----------------------------
-- Table structure for erp_goods_category_attribute
-- ----------------------------
DROP TABLE IF EXISTS `erp_goods_category_attribute`;
CREATE TABLE `erp_goods_category_attribute`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `category_id` int(0) NOT NULL,
  `type` int(0) NOT NULL DEFAULT 0 COMMENT '类型：0属性1规格',
  `title` varchar(45)  DEFAULT NULL COMMENT '属性名',
  `code` char(5)  DEFAULT NULL COMMENT '固定值color颜色size尺码style款式',
  PRIMARY KEY (`id`) 
) 

-- ----------------------------
-- Records of erp_goods_category_attribute
-- ----------------------------
INSERT INTO `erp_goods_category_attribute` VALUES (114, 1, 1, '颜色', 'color');
INSERT INTO `erp_goods_category_attribute` VALUES (115, 1, 1, '尺码', 'size');
INSERT INTO `erp_goods_category_attribute` VALUES (116, 1, 1, '款式', 'style');

-- ----------------------------
-- Table structure for erp_goods_category_attribute_value
-- ----------------------------
DROP TABLE IF EXISTS `erp_goods_category_attribute_value`;
CREATE TABLE `erp_goods_category_attribute_value`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，属性值id',
  `category_attribute_id` int(0) DEFAULT NULL COMMENT '属性id',
  `value` varchar(45)  DEFAULT NULL COMMENT '属性值文本',
  `sku_code` varchar(10)  DEFAULT NULL COMMENT '生成SKU的编码',
  `orderNum` int(0) DEFAULT 0,
  `isDelete` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) 
) 
-- ----------------------------
-- Records of erp_goods_category_attribute_value
-- ----------------------------
INSERT INTO `erp_goods_category_attribute_value` VALUES (308, 114, '黑色', '01', 999, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (309, 114, '米杏', '02', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (310, 114, '芒果黄', '03', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (311, 114, '中花灰', '04', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (312, 114, '浅杏', '05', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (313, 114, '中粉', '06', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (314, 114, '烟灰色', '07', 88, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (315, 114, '秋香绿', '08', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (316, 114, '花兰', '09', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (317, 114, '杏色', '10', 698, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (318, 114, '芥黄', '11', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (319, 114, '深蓝色', '12', 993, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (320, 114, '大红', '13', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (321, 114, '彩兰', '14', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (322, 114, '白色', '15', 99, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (323, 114, '花米驼', '16', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (324, 114, '粉红', '17', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (325, 114, '黄杏', '18', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (326, 114, '橙红', '19', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (327, 114, '木绿', '20', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (328, 114, '草绿', '21', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (329, 114, '早红', '22', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (330, 114, '咖啡', '23', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (331, 114, '灰色', '24', 99, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (332, 114, '浅绿', '25', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (333, 114, '大红色', '26', 99, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (334, 114, '桔色', '27', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (335, 114, '卡其', '28', 992, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (336, 114, '蓝灰色', '29', 996, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (337, 114, '湖蓝', '30', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (338, 114, '咖啡色', '31', 99, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (339, 114, '蓝绿', '32', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (340, 115, '均码', '00', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (341, 115, 'S', '01', 88, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (342, 115, 'M', '02', 87, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (343, 115, 'L', '03', 86, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (344, 115, 'XL', '04', 85, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (345, 115, '2XL', '05', 84, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (346, 115, '3XL', '06', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (347, 115, '4XL', '07', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (348, 115, '5XL', '08', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (349, 114, '雾霾蓝', '33', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (350, 114, '浅粉红', '34', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (351, 114, '蓝色', '35', 998, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (352, 114, '花色', '36', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (353, 114, '香槟色', '37', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (354, 114, '紫色', '38', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (355, 114, '砖红色', '39', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (356, 114, '复古蓝', '40', 995, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (357, 114, '烟灰', '41', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (358, 114, '橙黄', '42', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (359, 114, '黄色', '43', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (360, 114, '梅子色', '56', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (361, 114, '浅蓝色', '50', 994, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (362, 114, '宝蓝色', '44', 99, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (363, 114, '浅黄', '45', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (364, 114, '浅紫', '49', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (365, 114, '酒红', '57', 99, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (366, 114, '米白', '47', 99, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (367, 114, '复古蓝九分', '48', 88, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (368, 114, '卡灰', '51', 992, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (369, 114, '玫红', '52', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (370, 114, '彩蓝', '53', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (371, 114, '青蓝淡黄', '54', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (372, 114, '紫白', '55', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (373, 114, '碳灰', '58', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (374, 114, '深蓝色加绒', '59', 794, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (375, 114, '深紫色', '60', 99, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (376, 114, '橙色', '61', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (377, 114, '墨绿色', '62', 99, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (378, 114, '桃粉色', '63', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (379, 114, '蓝灰色九分', '64', 88, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (380, 114, '黑色九分', '65', 88, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (381, 114, '新洋米', '67', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (382, 114, '藏蓝色', '68', 99, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (383, 114, '深蓝九分', '69', 88, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (384, 114, '深蓝长款', '70', 88, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (385, 114, '米白色', '71', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (386, 114, '军绿色', '72', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (387, 114, '柠檬黄', '73', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (388, 114, '灰紫', '74', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (389, 114, '黑灰色', '75', 997, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (390, 114, '焦糖红', '76', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (391, 114, '酱紫', '78', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (392, 114, '红杏', '46', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (393, 114, '银灰', '79', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (394, 115, 'XS', '09', 89, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (395, 114, '无色', '00', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (396, 114, '条纹', '80', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (397, 114, '花色/彩色', '81', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (398, 114, '波点', '82', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (399, 114, '字母', '83', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (400, 114, '绿色', '84', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (401, 115, '25', '25', 69, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (402, 115, '26', '26', 68, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (403, 115, '27', '27', 67, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (404, 115, '28', '28', 66, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (405, 115, '29', '29', 65, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (406, 115, '30', '30', 64, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (407, 115, '31', '31', 63, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (408, 115, '32', '32', 62, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (409, 114, '黑色加绒', '101', 799, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (410, 114, '黑灰色加绒', '175', 798, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (411, 114, '蓝灰色加绒', '129', 797, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (412, 114, '复古蓝加绒', '140', 796, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (413, 114, '浅蓝色加绒', '150', 795, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (414, 116, '默认', NULL, 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (415, 116, '加长裤', 'JC', 66, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (416, 116, '长裤', 'CK', 68, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (417, 116, '九分', 'JF', 88, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (418, 116, '八分', 'BF', 86, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (419, 116, '九分加绒', 'JFR', 87, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (420, 116, '单裤', 'D', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (421, 116, '加绒', 'R', 0, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (422, 116, '长裤加绒', 'CR', 65, 0);
INSERT INTO `erp_goods_category_attribute_value` VALUES (423, 116, '加长裤加绒', 'JCR', 67, 0);