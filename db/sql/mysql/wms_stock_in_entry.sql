/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80200
 Source Host           : localhost:3306
 Source Schema         : zhijian

 Target Server Type    : MySQL
 Target Server Version : 80200
 File Encoding         : 65001

 Date: 31/12/2023 16:48:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wms_stock_in_entry
-- ----------------------------
DROP TABLE IF EXISTS `wms_stock_in_entry`;
CREATE TABLE `wms_stock_in_entry`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `no` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '单据编号',
  `source_no` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '来源单号',
  `source_id` bigint NULL DEFAULT NULL COMMENT '来源单id',
  `source_type` int NOT NULL COMMENT '来源类型（1采购订单2退货订单）',
  `source_goods_unit` int NULL DEFAULT NULL COMMENT '采购订单商品数',
  `source_spec_unit_total` int NULL DEFAULT NULL COMMENT '采购订单总件数',
  `source_spec_unit` int NULL DEFAULT NULL COMMENT '采购订单商品规格数',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `stock_in_operator_id` int NULL DEFAULT NULL COMMENT '操作入库人id',
  `stock_in_operator` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '操作入库人',
  `stock_in_time` datetime NULL DEFAULT NULL COMMENT '入库时间',
  `create_by` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '入库单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_stock_in_entry
-- ----------------------------

-- ----------------------------
-- Table structure for wms_stock_in_entry_item
-- ----------------------------
DROP TABLE IF EXISTS `wms_stock_in_entry_item`;
CREATE TABLE `wms_stock_in_entry_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `entry_id` bigint NOT NULL COMMENT '入库单id',
  `source_type` int NULL DEFAULT NULL COMMENT '来源类型（1采购订单2退货订单）',
  `source_id` bigint NULL DEFAULT NULL COMMENT '来源单id',
  `source_item_id` bigint NOT NULL COMMENT '来源单itemId',
  `goods_id` bigint NOT NULL COMMENT '商品id',
  `goods_num` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  `goods_name` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `spec_id` bigint NOT NULL COMMENT '商品规格id',
  `spec_num` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '商品规格编码',
  `color_value` varchar(55) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '颜色',
  `color_image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片',
  `size_value` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '尺码',
  `style_value` varchar(55) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '款式',
  `original_quantity` bigint NULL DEFAULT NULL COMMENT '原始数量',
  `in_quantity` bigint NOT NULL COMMENT '入库数量',
  `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '' COMMENT '备注',
  `location_id` int NULL DEFAULT NULL COMMENT '入库仓位',
  `location_num` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '入库仓位编码',
  `create_by` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `specIndex`(`spec_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '入库单明细' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wms_stock_in_entry_item
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
