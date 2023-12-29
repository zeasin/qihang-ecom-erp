/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : zhijian

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 29/12/2023 13:52:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for erp_goods_brand
-- ----------------------------
DROP TABLE IF EXISTS `erp_goods_brand`;
CREATE TABLE `erp_goods_brand`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '品牌名',
  `status` int(0) DEFAULT NULL COMMENT '状态',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `create_time` datetime(0) DEFAULT NULL,
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `update_time` datetime(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of erp_goods_brand
-- ----------------------------
INSERT INTO `erp_goods_brand` VALUES (1, '梦小妮', 1, 'admin', '2023-12-29 13:44:29', 'admin', '2023-12-29 13:44:29');

SET FOREIGN_KEY_CHECKS = 1;
