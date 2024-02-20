/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : nacos

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 20/02/2024 14:50:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS `config_info`;
CREATE TABLE `config_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `c_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `c_use` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `effect` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `c_schema` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfo_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of config_info
-- ----------------------------
INSERT INTO `config_info` VALUES (11, 'ecerp-dev.yaml', 'DEFAULT_GROUP', 'zhijian:\n  # 名称\n  name: qihangecerp\n  # 版本\n  version: 0.1.0\n  # 版权年份\n  copyrightYear: 2024\n  # 实例演示开关\n  demoEnabled: true\n  # 获取ip地址开关\n  addressEnabled: false\n  # 验证码类型 math 数字计算 char 字符验证\n  captchaType: math\n\n# 防止XSS攻击\nxss:\n  # 过滤开关\n  enabled: true\n  # 排除链接（多个用逗号分隔）\n  excludes: /system/notice\n  # 匹配链接\n  urlPatterns: /system/*,/monitor/*,/tool/*\n\n# mybatis 日志\nmybatis-plus:\n    configuration:\n      log-impl: org.apache.ibatis.logging.stdout.StdOutImpl # 开启sql日志\n\n\nspring:\n  # redis 配置\n  redis:\n    # 地址\n#    host: 8.130.98.215\n    host: 127.0.0.1\n    # 端口，默认为6379\n    port: 6379\n    # 数据库索引\n    database: 0\n    # 密码\n#    password: 123321\n    # 连接超时时间\n    timeout: 10s\n    lettuce:\n      pool:\n        # 连接池中的最小空闲连接\n        min-idle: 0\n        # 连接池中的最大空闲连接\n        max-idle: 8\n        # 连接池的最大数据库连接数\n        max-active: 8\n        # #连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/qihang-erp?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: root\n    password: Andy_123\n  \n# minio配置\nminio:\n  endpoint: http://127.0.0.1:9000 #换成自己的minio服务端地址\n  accessKey: 0AksAWDtI20Lpb4qxq5S\n  secretKey: yOAZkOVwrhPooBRel5PB0GwS5uw8VAgmqYykHl3T\n  secure: false\n  bucketName: ecerp', '9ab41e9e6329a955e8947d9b3f9d1dcd', '2024-01-19 13:54:08', '2024-01-19 14:26:38', 'nacos', '127.0.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (22, 'qihang-oms.yaml', 'DEFAULT_GROUP', 'zhijian:\n  # 名称\n  name: qihangecerp\n  # 版本\n  version: 0.1.0\n  # 版权年份\n  copyrightYear: 2024\n  # 实例演示开关\n  demoEnabled: true\n  # 获取ip地址开关\n  addressEnabled: false\n  # 验证码类型 math 数字计算 char 字符验证\n  captchaType: math\n\n# 防止XSS攻击\nxss:\n  # 过滤开关\n  enabled: true\n  # 排除链接（多个用逗号分隔）\n  excludes: /system/notice\n  # 匹配链接\n  urlPatterns: /system/*,/monitor/*,/tool/*\n\n# # mybatis 日志\n# mybatis-plus:\n#     configuration:\n#       log-impl: org.apache.ibatis.logging.stdout.StdOutImpl # 开启sql日志\n\n\nspring:\n  # redis 配置\n  redis:\n    # 地址\n#    host: 8.130.98.215\n    host: 127.0.0.1\n    # 端口，默认为6379\n    port: 6379\n    # 数据库索引\n    database: 0\n    # 密码\n#    password: 123321\n    # 连接超时时间\n    timeout: 10s\n    lettuce:\n      pool:\n        # 连接池中的最小空闲连接\n        min-idle: 0\n        # 连接池中的最大空闲连接\n        max-idle: 8\n        # 连接池的最大数据库连接数\n        max-active: 8\n        # #连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/qihang-oms?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: root\n    password: Andy_123\n  \n# minio配置\nminio:\n  endpoint: http://127.0.0.1:9000 #换成自己的minio服务端地址\n  accessKey: 0AksAWDtI20Lpb4qxq5S\n  secretKey: yOAZkOVwrhPooBRel5PB0GwS5uw8VAgmqYykHl3T\n  secure: false\n  bucketName: ecerp', 'f7c4e11a4c084abcd594dea34feedbff', '2024-01-19 15:44:58', '2024-01-26 09:06:44', 'nacos', '127.0.0.1', '', '', '', '', '', 'yaml', '', '');
INSERT INTO `config_info` VALUES (26, 'qihangec-erp.yaml', 'DEFAULT_GROUP', 'zhijian:\r\n  # 名称\r\n  name: qihangecerp\r\n  # 版本\r\n  version: 0.1.0\r\n  # 版权年份\r\n  copyrightYear: 2024\r\n  # 实例演示开关\r\n  demoEnabled: true\r\n  # 获取ip地址开关\r\n  addressEnabled: false\r\n  # 验证码类型 math 数字计算 char 字符验证\r\n  captchaType: math\r\n\r\n# 防止XSS攻击\r\nxss:\r\n  # 过滤开关\r\n  enabled: true\r\n  # 排除链接（多个用逗号分隔）\r\n  excludes: /system/notice\r\n  # 匹配链接\r\n  urlPatterns: /system/*,/monitor/*,/tool/*\r\n\r\n# # mybatis 日志\r\n# mybatis-plus:\r\n#     configuration:\r\n#       log-impl: org.apache.ibatis.logging.stdout.StdOutImpl # 开启sql日志\r\n\r\n\r\nspring:\r\n  # redis 配置\r\n  redis:\r\n    # 地址\r\n#    host: 8.130.98.215\r\n    host: 127.0.0.1\r\n    # 端口，默认为6379\r\n    port: 6379\r\n    # 数据库索引\r\n    database: 0\r\n    # 密码\r\n#    password: 123321\r\n    # 连接超时时间\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        # 连接池中的最小空闲连接\r\n        min-idle: 0\r\n        # 连接池中的最大空闲连接\r\n        max-idle: 8\r\n        # 连接池的最大数据库连接数\r\n        max-active: 8\r\n        # #连接池最大阻塞等待时间（使用负值表示没有限制）\r\n        max-wait: -1ms\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/qihang-oms?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: Andy_123\r\n  \r\n# minio配置\r\nminio:\r\n  endpoint: http://127.0.0.1:9000 #换成自己的minio服务端地址\r\n  accessKey: 0AksAWDtI20Lpb4qxq5S\r\n  secretKey: yOAZkOVwrhPooBRel5PB0GwS5uw8VAgmqYykHl3T\r\n  secure: false\r\n  bucketName: ecerp', '885d9e43358ac92138ee2e9acd9d40f6', '2024-02-01 09:31:23', '2024-02-01 09:31:23', NULL, '127.0.0.1', '', '', NULL, NULL, NULL, 'yaml', NULL, '');
INSERT INTO `config_info` VALUES (35, 'tech.qihangec.erp.service.UserService', 'mapping', 'service-user', '13d20b4876b20daaeef6902854d28cb2', '2024-02-01 15:34:36', '2024-02-01 15:34:36', NULL, '10.0.100.36', '', '', NULL, NULL, NULL, 'text', NULL, '');
INSERT INTO `config_info` VALUES (36, 'tech.qihangec.erp.service.UserService:::provider:service-user', 'dubbo', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"dubbo\":\"2.0.2\",\"application\":\"service-user\",\"release\":\"3.2.10\",\"anyhost\":\"true\",\"pid\":\"7804\",\"side\":\"provider\",\"interface\":\"tech.qihangec.erp.service.UserService\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706774456019\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', 'd1161f6c85d80c9390e0808d4086ed5c', '2024-02-01 15:34:36', '2024-02-01 16:00:57', NULL, '10.0.100.36', '', '', NULL, NULL, NULL, 'text', NULL, '');

-- ----------------------------
-- Table structure for config_info_aggr
-- ----------------------------
DROP TABLE IF EXISTS `config_info_aggr`;
CREATE TABLE `config_info_aggr`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `datum_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'datum_id',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfoaggr_datagrouptenantdatum`(`data_id`, `group_id`, `tenant_id`, `datum_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '增加租户字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_beta
-- ----------------------------
DROP TABLE IF EXISTS `config_info_beta`;
CREATE TABLE `config_info_beta`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `beta_ips` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'betaIps',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfobeta_datagrouptenant`(`data_id`, `group_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_beta' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_info_tag
-- ----------------------------
DROP TABLE IF EXISTS `config_info_tag`;
CREATE TABLE `config_info_tag`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tag_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_id',
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'content',
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'md5',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin COMMENT 'source user',
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'source ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_configinfotag_datagrouptenanttag`(`data_id`, `group_id`, `tenant_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_info_tag' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS `config_tags_relation`;
CREATE TABLE `config_tags_relation`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `tag_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'tag_name',
  `tag_type` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tag_type',
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'data_id',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'group_id',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `nid` bigint(0) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`nid`) USING BTREE,
  UNIQUE INDEX `uk_configtagrelation_configidtag`(`id`, `tag_name`, `tag_type`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'config_tag_relation' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS `group_capacity`;
CREATE TABLE `group_capacity`  (
  `id` bigint unsigned NOT NULL COMMENT '主键ID',
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Group ID，空字符表示整个集群',
  `quota` int unsigned NOT NULL COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL COMMENT '使用量',
  `max_size` int unsigned NOT NULL COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL COMMENT '聚合子配置最大个数，，0表示使用默认值',
  `max_aggr_size` int unsigned NOT NULL COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '集群、各Group容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS `his_config_info`;
CREATE TABLE `his_config_info`  (
  `id` bigint unsigned NOT NULL,
  `nid` bigint unsigned NOT NULL,
  `data_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `group_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `app_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'app_name',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `md5` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `src_user` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin,
  `src_ip` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `op_type` char(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT '租户字段',
  `encrypted_data_key` text CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '秘钥',
  PRIMARY KEY (`nid`) USING BTREE,
  INDEX `idx_gmt_create`(`gmt_create`) USING BTREE,
  INDEX `idx_gmt_modified`(`gmt_modified`) USING BTREE,
  INDEX `idx_did`(`data_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '多租户改造' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of his_config_info
-- ----------------------------
INSERT INTO `his_config_info` VALUES (22, 30, 'qihang-oms.yaml', 'DEFAULT_GROUP', '', 'zhijian:\n  # 名称\n  name: qihangecerp\n  # 版本\n  version: 0.1.0\n  # 版权年份\n  copyrightYear: 2024\n  # 实例演示开关\n  demoEnabled: true\n  # 获取ip地址开关\n  addressEnabled: false\n  # 验证码类型 math 数字计算 char 字符验证\n  captchaType: math\n\n# 防止XSS攻击\nxss:\n  # 过滤开关\n  enabled: true\n  # 排除链接（多个用逗号分隔）\n  excludes: /system/notice\n  # 匹配链接\n  urlPatterns: /system/*,/monitor/*,/tool/*\n\n# mybatis 日志\nmybatis-plus:\n    configuration:\n      log-impl: org.apache.ibatis.logging.stdout.StdOutImpl # 开启sql日志\n\n\nspring:\n  # redis 配置\n  redis:\n    # 地址\n#    host: 8.130.98.215\n    host: 127.0.0.1\n    # 端口，默认为6379\n    port: 6379\n    # 数据库索引\n    database: 0\n    # 密码\n#    password: 123321\n    # 连接超时时间\n    timeout: 10s\n    lettuce:\n      pool:\n        # 连接池中的最小空闲连接\n        min-idle: 0\n        # 连接池中的最大空闲连接\n        max-idle: 8\n        # 连接池的最大数据库连接数\n        max-active: 8\n        # #连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/qihang-erp?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: root\n    password: Andy_123\n  \n# minio配置\nminio:\n  endpoint: http://127.0.0.1:9000 #换成自己的minio服务端地址\n  accessKey: 0AksAWDtI20Lpb4qxq5S\n  secretKey: yOAZkOVwrhPooBRel5PB0GwS5uw8VAgmqYykHl3T\n  secure: false\n  bucketName: ecerp', '9ab41e9e6329a955e8947d9b3f9d1dcd', '2024-01-22 17:20:58', '2024-01-22 17:20:57', 'nacos', '127.0.0.1', 'U', '', '');
INSERT INTO `his_config_info` VALUES (22, 31, 'qihang-oms.yaml', 'DEFAULT_GROUP', '', 'zhijian:\n  # 名称\n  name: qihangecerp\n  # 版本\n  version: 0.1.0\n  # 版权年份\n  copyrightYear: 2024\n  # 实例演示开关\n  demoEnabled: true\n  # 获取ip地址开关\n  addressEnabled: false\n  # 验证码类型 math 数字计算 char 字符验证\n  captchaType: math\n\n# 防止XSS攻击\nxss:\n  # 过滤开关\n  enabled: true\n  # 排除链接（多个用逗号分隔）\n  excludes: /system/notice\n  # 匹配链接\n  urlPatterns: /system/*,/monitor/*,/tool/*\n\n# # mybatis 日志\n# mybatis-plus:\n#     configuration:\n#       log-impl: org.apache.ibatis.logging.stdout.StdOutImpl # 开启sql日志\n\n\nspring:\n  # redis 配置\n  redis:\n    # 地址\n#    host: 8.130.98.215\n    host: 127.0.0.1\n    # 端口，默认为6379\n    port: 6379\n    # 数据库索引\n    database: 0\n    # 密码\n#    password: 123321\n    # 连接超时时间\n    timeout: 10s\n    lettuce:\n      pool:\n        # 连接池中的最小空闲连接\n        min-idle: 0\n        # 连接池中的最大空闲连接\n        max-idle: 8\n        # 连接池的最大数据库连接数\n        max-active: 8\n        # #连接池最大阻塞等待时间（使用负值表示没有限制）\n        max-wait: -1ms\n  datasource:\n    type: com.alibaba.druid.pool.DruidDataSource\n    driverClassName: com.mysql.cj.jdbc.Driver\n    url: jdbc:mysql://127.0.0.1:3306/qihang-erp?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\n    username: root\n    password: Andy_123\n  \n# minio配置\nminio:\n  endpoint: http://127.0.0.1:9000 #换成自己的minio服务端地址\n  accessKey: 0AksAWDtI20Lpb4qxq5S\n  secretKey: yOAZkOVwrhPooBRel5PB0GwS5uw8VAgmqYykHl3T\n  secure: false\n  bucketName: ecerp', '5393b2efc2e6fc4fa732762218d83608', '2024-01-26 09:06:44', '2024-01-26 09:06:44', 'nacos', '127.0.0.1', 'U', '', '');
INSERT INTO `his_config_info` VALUES (0, 32, 'qihangec-erp.yaml', 'DEFAULT_GROUP', '', 'zhijian:\r\n  # 名称\r\n  name: qihangecerp\r\n  # 版本\r\n  version: 0.1.0\r\n  # 版权年份\r\n  copyrightYear: 2024\r\n  # 实例演示开关\r\n  demoEnabled: true\r\n  # 获取ip地址开关\r\n  addressEnabled: false\r\n  # 验证码类型 math 数字计算 char 字符验证\r\n  captchaType: math\r\n\r\n# 防止XSS攻击\r\nxss:\r\n  # 过滤开关\r\n  enabled: true\r\n  # 排除链接（多个用逗号分隔）\r\n  excludes: /system/notice\r\n  # 匹配链接\r\n  urlPatterns: /system/*,/monitor/*,/tool/*\r\n\r\n# # mybatis 日志\r\n# mybatis-plus:\r\n#     configuration:\r\n#       log-impl: org.apache.ibatis.logging.stdout.StdOutImpl # 开启sql日志\r\n\r\n\r\nspring:\r\n  # redis 配置\r\n  redis:\r\n    # 地址\r\n#    host: 8.130.98.215\r\n    host: 127.0.0.1\r\n    # 端口，默认为6379\r\n    port: 6379\r\n    # 数据库索引\r\n    database: 0\r\n    # 密码\r\n#    password: 123321\r\n    # 连接超时时间\r\n    timeout: 10s\r\n    lettuce:\r\n      pool:\r\n        # 连接池中的最小空闲连接\r\n        min-idle: 0\r\n        # 连接池中的最大空闲连接\r\n        max-idle: 8\r\n        # 连接池的最大数据库连接数\r\n        max-active: 8\r\n        # #连接池最大阻塞等待时间（使用负值表示没有限制）\r\n        max-wait: -1ms\r\n  datasource:\r\n    type: com.alibaba.druid.pool.DruidDataSource\r\n    driverClassName: com.mysql.cj.jdbc.Driver\r\n    url: jdbc:mysql://127.0.0.1:3306/qihang-oms?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8\r\n    username: root\r\n    password: Andy_123\r\n  \r\n# minio配置\r\nminio:\r\n  endpoint: http://127.0.0.1:9000 #换成自己的minio服务端地址\r\n  accessKey: 0AksAWDtI20Lpb4qxq5S\r\n  secretKey: yOAZkOVwrhPooBRel5PB0GwS5uw8VAgmqYykHl3T\r\n  secure: false\r\n  bucketName: ecerp', '885d9e43358ac92138ee2e9acd9d40f6', '2024-02-01 09:31:22', '2024-02-01 09:31:23', NULL, '127.0.0.1', 'I', '', '');
INSERT INTO `his_config_info` VALUES (0, 33, 'tech.qihangec.erp.service.UserService:::provider:provider_application_8083', 'dubbo', '', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"anyhost\":\"true\",\"pid\":\"776\",\"side\":\"provider\",\"release\":\"3.2.10\",\"application\":\"provider_application_8083\",\"interface\":\"tech.qihangec.erp.service.UserService\",\"dubbo\":\"2.0.2\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706756318911\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', '68e0f7157f4eead44fa4e5edb034da74', '2024-02-01 10:58:40', '2024-02-01 10:58:41', NULL, '10.0.100.36', 'I', '', '');
INSERT INTO `his_config_info` VALUES (0, 34, 'tech.qihangec.erp.service.UserService', 'mapping', '', 'provider_application_8083', 'ee9030abec53eaa5f36690722ba8691f', '2024-02-01 10:58:40', '2024-02-01 10:58:41', NULL, '10.0.100.36', 'I', '', '');
INSERT INTO `his_config_info` VALUES (0, 35, 'tech.qihangec.erp.service.UserService:::provider:erp-goods', 'dubbo', '', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"anyhost\":\"true\",\"side\":\"provider\",\"application\":\"erp-goods\",\"interface\":\"tech.qihangec.erp.service.UserService\",\"release\":\"3.2.10\",\"pid\":\"2308\",\"dubbo\":\"2.0.2\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706756478468\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', '9c385c280b1a43e21e25d8e060060d16', '2024-02-01 11:01:20', '2024-02-01 11:01:19', NULL, '10.0.100.36', 'I', '', '');
INSERT INTO `his_config_info` VALUES (27, 36, 'tech.qihangec.erp.service.UserService', 'mapping', '', 'provider_application_8083', 'ee9030abec53eaa5f36690722ba8691f', '2024-02-01 11:01:20', '2024-02-01 11:01:19', NULL, '10.0.100.36', 'U', '', '');
INSERT INTO `his_config_info` VALUES (30, 37, 'tech.qihangec.erp.service.UserService:::provider:erp-goods', 'dubbo', '', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"anyhost\":\"true\",\"side\":\"provider\",\"application\":\"erp-goods\",\"interface\":\"tech.qihangec.erp.service.UserService\",\"release\":\"3.2.10\",\"pid\":\"2308\",\"dubbo\":\"2.0.2\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706756478468\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', '9c385c280b1a43e21e25d8e060060d16', '2024-02-01 11:08:47', '2024-02-01 11:08:48', NULL, '10.0.100.36', 'U', '', '');
INSERT INTO `his_config_info` VALUES (27, 38, 'tech.qihangec.erp.service.UserService', 'mapping', '', 'provider_application_8083,erp-goods', '32a56d6a21f3b1c518481e45447311f7', '2024-02-01 11:31:30', '2024-02-01 11:31:31', NULL, '10.0.100.36', 'U', '', '');
INSERT INTO `his_config_info` VALUES (0, 39, 'tech.qihangec.erp.service.UserService:::provider:service-user', 'dubbo', '', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"pid\":\"6172\",\"anyhost\":\"true\",\"dubbo\":\"2.0.2\",\"application\":\"service-user\",\"interface\":\"tech.qihangec.erp.service.UserService\",\"release\":\"3.2.10\",\"side\":\"provider\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706758289641\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', '67d60f42b8bb720f9a0d37baae6eb6b1', '2024-02-01 11:31:30', '2024-02-01 11:31:31', NULL, '10.0.100.36', 'I', '', '');
INSERT INTO `his_config_info` VALUES (33, 40, 'tech.qihangec.erp.service.UserService:::provider:service-user', 'dubbo', '', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"pid\":\"6172\",\"anyhost\":\"true\",\"dubbo\":\"2.0.2\",\"application\":\"service-user\",\"interface\":\"tech.qihangec.erp.service.UserService\",\"release\":\"3.2.10\",\"side\":\"provider\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706758289641\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', '67d60f42b8bb720f9a0d37baae6eb6b1', '2024-02-01 14:04:39', '2024-02-01 14:04:39', NULL, '10.0.100.36', 'U', '', '');
INSERT INTO `his_config_info` VALUES (27, 41, 'tech.qihangec.erp.service.UserService', 'mapping', '', 'provider_application_8083,erp-goods,service-user', 'c9f6028e34542527bad9f7c83453c236', '2024-02-01 15:33:54', '2024-02-01 15:33:54', NULL, '127.0.0.1', 'D', '', '');
INSERT INTO `his_config_info` VALUES (28, 42, 'tech.qihangec.erp.service.UserService:::provider:provider_application_8083', 'dubbo', '', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"anyhost\":\"true\",\"pid\":\"776\",\"side\":\"provider\",\"release\":\"3.2.10\",\"application\":\"provider_application_8083\",\"interface\":\"tech.qihangec.erp.service.UserService\",\"dubbo\":\"2.0.2\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706756318911\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', '68e0f7157f4eead44fa4e5edb034da74', '2024-02-01 15:34:08', '2024-02-01 15:34:08', NULL, '127.0.0.1', 'D', '', '');
INSERT INTO `his_config_info` VALUES (30, 43, 'tech.qihangec.erp.service.UserService:::provider:erp-goods', 'dubbo', '', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"interface\":\"tech.qihangec.erp.service.UserService\",\"application\":\"erp-goods\",\"dubbo\":\"2.0.2\",\"side\":\"provider\",\"release\":\"3.2.10\",\"anyhost\":\"true\",\"pid\":\"11172\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706756926931\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', '201b96d9f37ea58021c36430288fdc9a', '2024-02-01 15:34:12', '2024-02-01 15:34:12', NULL, '127.0.0.1', 'D', '', '');
INSERT INTO `his_config_info` VALUES (33, 44, 'tech.qihangec.erp.service.UserService:::provider:service-user', 'dubbo', '', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"interface\":\"tech.qihangec.erp.service.UserService\",\"release\":\"3.2.10\",\"side\":\"provider\",\"application\":\"service-user\",\"anyhost\":\"true\",\"pid\":\"5524\",\"dubbo\":\"2.0.2\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706767477949\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', '234eb9edb220bf06b5dbce0b52f5403a', '2024-02-01 15:34:15', '2024-02-01 15:34:16', NULL, '127.0.0.1', 'D', '', '');
INSERT INTO `his_config_info` VALUES (0, 45, 'tech.qihangec.erp.service.UserService', 'mapping', '', 'service-user', '13d20b4876b20daaeef6902854d28cb2', '2024-02-01 15:34:36', '2024-02-01 15:34:36', NULL, '10.0.100.36', 'I', '', '');
INSERT INTO `his_config_info` VALUES (0, 46, 'tech.qihangec.erp.service.UserService:::provider:service-user', 'dubbo', '', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"application\":\"service-user\",\"dubbo\":\"2.0.2\",\"side\":\"provider\",\"release\":\"3.2.10\",\"anyhost\":\"true\",\"pid\":\"4872\",\"interface\":\"tech.qihangec.erp.service.UserService\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706772875091\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', '959275de72a3f82eb6298a1458609d0c', '2024-02-01 15:34:36', '2024-02-01 15:34:36', NULL, '10.0.100.36', 'I', '', '');
INSERT INTO `his_config_info` VALUES (36, 47, 'tech.qihangec.erp.service.UserService:::provider:service-user', 'dubbo', '', '{\"annotations\":[],\"canonicalName\":\"tech.qihangec.erp.service.UserService\",\"codeSource\":\"file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\",\"methods\":[{\"annotations\":[],\"name\":\"getUserById\",\"parameterTypes\":[\"java.lang.Long\"],\"parameters\":[],\"returnType\":\"tech.qihangec.erp.domain.User\"}],\"parameters\":{\"application\":\"service-user\",\"dubbo\":\"2.0.2\",\"side\":\"provider\",\"release\":\"3.2.10\",\"anyhost\":\"true\",\"pid\":\"4872\",\"interface\":\"tech.qihangec.erp.service.UserService\",\"executor-management-mode\":\"isolation\",\"file-cache\":\"true\",\"methods\":\"getUserById\",\"deprecated\":\"false\",\"service-name-mapping\":\"true\",\"generic\":\"false\",\"bind.port\":\"20880\",\"bind.ip\":\"10.0.100.36\",\"prefer.serialization\":\"fastjson2,hessian2\",\"background\":\"false\",\"dynamic\":\"true\",\"timestamp\":\"1706772875091\"},\"types\":[{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.Long\"},{\"enums\":[],\"items\":[],\"properties\":{\"name\":\"java.lang.String\",\"id\":\"java.lang.String\"},\"type\":\"tech.qihangec.erp.domain.User\"},{\"enums\":[],\"items\":[],\"properties\":{},\"type\":\"java.lang.String\"}],\"uniqueId\":\"tech.qihangec.erp.service.UserService@file:/D:/dev/projects/qihang-erp-dubbo/service/target/classes/\"}', '959275de72a3f82eb6298a1458609d0c', '2024-02-01 16:00:57', '2024-02-01 16:00:57', NULL, '10.0.100.36', 'U', '', '');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `action` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `uk_role_permission`(`role`, `resource`, `action`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  UNIQUE INDEX `idx_user_role`(`username`, `role`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('nacos', 'ROLE_ADMIN');

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS `tenant_capacity`;
CREATE TABLE `tenant_capacity`  (
  `id` bigint unsigned NOT NULL COMMENT '主键ID',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '' COMMENT 'Tenant ID',
  `quota` int unsigned NOT NULL COMMENT '配额，0表示使用默认值',
  `usage` int unsigned NOT NULL COMMENT '使用量',
  `max_size` int unsigned NOT NULL COMMENT '单个配置大小上限，单位为字节，0表示使用默认值',
  `max_aggr_count` int unsigned NOT NULL COMMENT '聚合子配置最大个数',
  `max_aggr_size` int unsigned NOT NULL COMMENT '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值',
  `max_history_count` int unsigned NOT NULL COMMENT '最大变更历史数量',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '租户容量信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS `tenant_info`;
CREATE TABLE `tenant_info`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `kp` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT 'kp',
  `tenant_id` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_id',
  `tenant_name` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT '' COMMENT 'tenant_name',
  `tenant_desc` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'tenant_desc',
  `create_source` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL COMMENT 'create_source',
  `gmt_create` bigint(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` bigint(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_tenant_info_kptenantid`(`kp`, `tenant_id`) USING BTREE,
  INDEX `idx_tenant_id`(`tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = 'tenant_info' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', 1);

SET FOREIGN_KEY_CHECKS = 1;
