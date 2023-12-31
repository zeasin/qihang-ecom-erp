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

 Date: 31/12/2023 17:39:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for s_shop
-- ----------------------------
DROP TABLE IF EXISTS `s_shop`;
CREATE TABLE `s_shop`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '店铺名',
  `nickName` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺别名',
  `ename` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标识',
  `company` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺主题',
  `type` int NOT NULL COMMENT '对应第三方平台Id',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '店铺url',
  `orderNum` int NOT NULL DEFAULT 9 COMMENT '排序',
  `isDelete` int NOT NULL DEFAULT 0 COMMENT '是否删除0否1是',
  `isShow` int NULL DEFAULT 0 COMMENT '是否显示(0：是1否）',
  `modify_on` bigint NOT NULL COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `sellerUserId` bigint NOT NULL DEFAULT 0 COMMENT '第三方平台店铺id，淘宝天猫开放平台使用',
  `sellerUserIdStr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '卖家userId',
  `sessionKey` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方平台sessionKey',
  `appkey` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Appkey暂时抖音用',
  `appSercet` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Appsercet暂时抖音用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '数据中心-店铺' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_shop
-- ----------------------------
INSERT INTO `s_shop` VALUES (1, '华衣云商1688店', '华衣云商（1688）', 'ali', '华衣云商', 1, 'https://huayiyungou.1688.com/', 6, 1, 0, 1568258513, '阿里店铺', 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (2, '东方概念旗舰店(天猫)', '东方概念旗舰店', 'tmall', '华衣科技', 4, 'https://dfgn.tmall.com/', 0, 1, 0, 1568258513, '天猫店铺', 2206529834322, '', '61003129c973ba045c8017a49c36b177d11f2af1c4e97a32206529834322', NULL, NULL);
INSERT INTO `s_shop` VALUES (3, '华衣云购平台', '华衣云购', 'yungou', '华衣云商', 9, 'http://www.huayiyungou.com/', 1, 1, 1, 1568258513, '华衣云购平台', 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (4, '线下批发系统', '线下批发系统', 'offline', '华衣云商', 0, 'http://order.huayiyungou.com/', 0, 1, 0, 1568258513, '华衣云商批发下单系统', 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (5, '梦小妮潮流女装', '拼多多-梦小妮', 'pdd', '煜梦服饰', 5, NULL, 99, 0, 1, 1680698886, 'pdd10006159121', 100061591, '', '87f8044d2a5f45a489aa3a952785b0d35e61788a', NULL, NULL);
INSERT INTO `s_shop` VALUES (6, '梦小妮牛仔裤', '淘宝-梦小妮', 'taobao', '煜梦服饰', 4, '', 98, 0, 1, 0, '华衣云商直销店（淘宝）', 2200787809358, '', '610140071d1070a37cecea89f1c1f3d6e5d19bf4b58dd942200787809358', NULL, NULL);
INSERT INTO `s_shop` VALUES (7, '东方符号旗舰店(天猫)', '东方符号旗舰店(天猫)', 'taobao', '华衣云商', 4, 'https://dongfangfuhao.tmall.com/', 9, 1, 1, 0, NULL, 2207286326942, '', '6101002a5f13225d334f845340944d01d77e7cc4d6d22f12207286326942', '31014100', '7b0769269b0c0ca88949791c14eb3a5c');
INSERT INTO `s_shop` VALUES (8, '东方概念女装-抖音', '东方概念女装-抖音', 'douyin', '华衣云商', 6, 'http://openapi.jinritemai.com', 9, 1, 0, 1663668085, NULL, 2148336, '', '10dba883-35da-4493-8466-e386a02cf761', '7005157746437834253', '8104c8b8-9085-4a80-9248-629759b4f1a3');
INSERT INTO `s_shop` VALUES (9, '批批网', '批批网', 'pipi', '合作机构', 8, NULL, 0, 1, 1, 1, NULL, 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (10, '华衣云购有赞旗舰店', '有赞', 'youzan', '华衣云商', 2, 'https://shop43253270.youzan.com/', 0, 1, 0, 1568258513, '有赞店铺', 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (11, '直播机构系统', '直播机构系统', 'daifa', '华衣云商', 0, '', 0, 1, 0, 0, '', 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (12, '东方符号-蘑菇街', '东方符号-蘑菇街', 'mogujie', '华衣云商', 12, NULL, 9, 1, 0, 0, NULL, 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (13, '梦小妮牛仔裤-快手', '快手小店', 'kuaishou', '华衣云商', 13, NULL, 9, 1, 0, 0, NULL, 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (14, '珍姐姐百货店', '百货', 'pdd', '煜梦服饰', 5, NULL, 98, 0, 1, 1671700046, 'pdd97891863852', 978918638, '978918638', '7a675d249b794ad19983d999a0420dccd4632e24', NULL, NULL);
INSERT INTO `s_shop` VALUES (15, '候鸟', '候鸟', 'houniao', '候鸟', 8, NULL, 9, 1, 1, 0, NULL, 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (16, '净倍纯官方旗舰店(京东)', '净倍纯官方旗舰店(京东)', 'jdjbc', '华衣云商', 16, NULL, 0, 1, 1, 0, NULL, 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (17, '甘小姐的包包', '箱包', 'pddjbc2', '煜梦服饰', 5, NULL, 88, 1, 0, 1662361230, NULL, 831497404, '', '860430baa92740efa55fe50c701868e7a4c17864', NULL, NULL);
INSERT INTO `s_shop` VALUES (18, '华衣云购(百货店)', '华衣云购', 'pddbh', '华衣云商', 18, NULL, 9, 1, 0, 1631065553, NULL, 171504183, '', 'eddcf3d19f2549078420f529586b60afdec2b02c', NULL, NULL);
INSERT INTO `s_shop` VALUES (19, '华衣男装-抖音', '华衣男装-抖音', 'douyin', '华衣云商', 6, 'http://openapi.jinritemai.com', 9, 1, 0, 1636615450, NULL, 2754322, '', '975ee98f-0407-4fee-b5cc-9b43af7a8710', '7005157746437834253', '8104c8b8-9085-4a80-9248-629759b4f1a3');
INSERT INTO `s_shop` VALUES (20, '红蜘蛛企业店-抖音', '红蜘蛛企业店-抖音', 'douyin', '红蜘蛛', 6, 'http://openapi.jinritemai.com', 2, 1, 0, 0, NULL, 42781469, '', '535bbd0e-608f-4021-aa37-ca59f16f8e05', '7005157746437834253', '8104c8b8-9085-4a80-9248-629759b4f1a3');
INSERT INTO `s_shop` VALUES (21, '珍姐姐de衣柜的店', '启航家常菜的店-小红书', 'xhs', '启航', 7, 'https://ark.xiaohongshu.com/ark/open_api/v3/common_controller', 2, 0, 0, 1658303081, NULL, 21, '6255224c3801e1000190d3d0', 'token-0f3f8a5fc5aa465aa29a66d27c6cf170-dad68769d83e4e1a9f52a950a680b9f2', '621919dd99484598a69c', '1747d77da2ce58b97483932041c5503e');
INSERT INTO `s_shop` VALUES (22, '梦小妮牛仔裤', '抖音-梦小妮', 'douyin', '华衣云商', 6, 'http://openapi.jinritemai.com', 87, 0, 1, 1653672695, NULL, 0, '0', '', '', '');
INSERT INTO `s_shop` VALUES (99, 'ERP订单', 'ERP订单', 'hezuo', '华衣云商', 8, NULL, 0, 1, 0, 0, NULL, 0, '', NULL, NULL, NULL);
INSERT INTO `s_shop` VALUES (100, '甘小姐de店', '女装', 'pddjbc', '煜梦服饰', 5, NULL, 89, 1, 1, 1667305500, 'pdd80948120395', 809481203, '', 'fa36ee2130364ac28df04d8d2eecac5370c370bc', NULL, NULL);

-- ----------------------------
-- Table structure for s_shop_setting
-- ----------------------------
DROP TABLE IF EXISTS `s_shop_setting`;
CREATE TABLE `s_shop_setting`  (
  `id` int NOT NULL COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置名',
  `app_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'appKey',
  `app_secret` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT 'appSecret',
  `access_token` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '阿里access token',
  `expires_in` bigint NULL DEFAULT NULL COMMENT '到期',
  `access_token_begin` bigint NULL DEFAULT NULL COMMENT 'access_token开始时间',
  `refresh_token` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '刷新token',
  `refresh_token_timeout` bigint NULL DEFAULT NULL COMMENT '刷新token过期时间',
  `modify_on` bigint NOT NULL COMMENT '更新时间',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `request_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求url',
  `third_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方店铺id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '第三方平台设置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of s_shop_setting
-- ----------------------------
INSERT INTO `s_shop_setting` VALUES (1, '1688', '9380846', 'MJC3doohMxCG', '1dc697c1955f4b75a96fe309b8d7bba8e6c98843', 86392, 1620181504, '445767c1a15e469c922d81734e132caa10af7626', 0, 1620181504, '1688开放平台', NULL, NULL);
INSERT INTO `s_shop_setting` VALUES (2, '有赞', '', '', NULL, NULL, NULL, NULL, NULL, 1573610045, NULL, NULL, NULL);
INSERT INTO `s_shop_setting` VALUES (4, '淘系', '31014100', '7b0769269b0c0ca88949791c14eb3a5c', '6100b26a3d196c826e10f06b9e1eb74dcf1256fd4618dc82206529834322', NULL, NULL, NULL, NULL, 1573610045, '淘宝开放平台', 'http://gw.api.taobao.com/router/rest', NULL);
INSERT INTO `s_shop_setting` VALUES (5, '拼多多', 'dc953bcf16d24b27abf3e64a59e1ecd1', 'de296599e194a08cbfbb2b3b340e11fec7a1bacc', '58647a23b96640e3b29596fb621e57ecbe4d80cc', 86394, 1625123356, '97f019f9be134bb49d3a8e53b9ac496ff18f4bcd', 0, 1625123356, '拼多多开放平台', NULL, '593374804');
INSERT INTO `s_shop_setting` VALUES (6, '抖音', '', '', '2ea26e2f-97c6-4b74-965f-fbbae31796c5', NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `s_shop_setting` VALUES (7, '小红书', '', '', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `s_shop_setting` VALUES (9, '华衣云购', '', '', NULL, NULL, NULL, NULL, NULL, 1573610045, NULL, NULL, NULL);
INSERT INTO `s_shop_setting` VALUES (10, '其他', '', '', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `s_shop_setting` VALUES (11, '直播机构订单', '', '', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `s_shop_setting` VALUES (12, '蘑菇街', '102525', '0C73B4A94AE4C334C46347764950EEC7', 'D2657032B7AF139B9861235D80F5A3FD', 1590396706, 1589187106, '306EBA8D5830EE32D9D64B4838C20758', 0, 1589187106, NULL, 'https://openapi.mogujie.com/invoke', NULL);
INSERT INTO `s_shop_setting` VALUES (13, '快手', '', '', 'ChFvYXV0aC5hY2Nlc3NUb2tlbhJg8dGIYbcw7BG59wR280oy4-skCVU4yMI284Skj7t4Bb5aA9F6xf8CeII793MWj4FsKxYtoGMm0L9f1Xy4dp55XVlZku34dVeXgEkGas86rEVdgN6afECyCIx0biFptyIQGhLmzpljLPVHbK1DJTZ-cNTpKfIiIORSNvnBg-JisR3A3fyJMg6C1iOMNmrVhGyML8L80tAwKAUwAQ', 172800, 1593392516, 'ChJvYXV0aC5yZWZyZXNoVG9rZW4SoAHKJkgO01CrIeyVVv4eRK7ieOKi4Lgh-6M-qn0_73XsotF5kgF0rTpivZy--qKdFJfp-5qx5iuyShB4Fyi4ds7XNLRN1b8OjX2v773v1KgfLQseaDGJPnDnzChRoIWZOpyo82lLRX_2YGjsC4bRkVU9fVyH6Wt2nsJEd1hHyuPhAbbsx3Nq481TYxvW1eJTT2EuvvMYCEMO79s_eva5Z3IaGhLFjPzG3QMu6L4V2LLNSvQ9zkEiIAVAXZnyBIRKWm_kn7xeI4L93T-jVkriYJAxPhfASQscKAUwAQ', 0, 1593392516, NULL, NULL, NULL);
INSERT INTO `s_shop_setting` VALUES (14, '净倍纯华衣云商专卖店', 'dc953bcf16d24b27abf3e64a59e1ecd1', 'de296599e194a08cbfbb2b3b340e11fec7a1bacc', '70804fd3362b4985b20ab7f86ac4d55213140eaa', NULL, NULL, NULL, NULL, 0, '净倍纯华衣云商专卖店', NULL, NULL);
INSERT INTO `s_shop_setting` VALUES (16, '京东', '', '', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, NULL);
INSERT INTO `s_shop_setting` VALUES (99, 'ERP系统', '', '', NULL, NULL, NULL, NULL, NULL, 1573610045, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
