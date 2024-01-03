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

 Date: 03/01/2024 11:06:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(0) DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(0) DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由参数',
  `is_frame` int(0) DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(0) DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2045 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 99, 'system', '', '', 1, 0, 'M', '0', '0', '', 'system', 'admin', '2023-12-27 15:00:27', 'admin', '2023-12-29 09:07:42.856856', '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '系统监控', 0, 100, 'monitor', '', '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', '2023-12-27 15:00:27', 'admin', '2023-12-29 09:07:51.123192', '系统监控目录');
INSERT INTO `sys_menu` VALUES (3, '系统工具', 0, 101, 'tool', '', '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', '2023-12-27 15:00:27', 'admin', '2023-12-29 09:08:06.918539', '系统工具目录');
INSERT INTO `sys_menu` VALUES (4, '采购管理', 0, 2, 'scm', '', '', 1, 0, 'M', '0', '0', '', 'server', 'admin', '2023-12-27 15:00:27', 'admin', '2024-01-03 11:05:06', '至简官网地址');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', '2023-12-27 15:00:27', '', '', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', '2023-12-27 15:00:27', '', '', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', '2023-12-27 15:00:27', '', '', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (103, '部门管理', 1, 4, 'dept', 'system/dept/index', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', '2023-12-27 15:00:27', '', '', '部门管理菜单');
INSERT INTO `sys_menu` VALUES (104, '岗位管理', 1, 5, 'post', 'system/post/index', '', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', '2023-12-27 15:00:27', '', '', '岗位管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', '2023-12-27 15:00:27', '', '', '字典管理菜单');
INSERT INTO `sys_menu` VALUES (106, '参数设置', 1, 7, 'config', 'system/config/index', '', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', '2023-12-27 15:00:27', '', '', '参数设置菜单');
INSERT INTO `sys_menu` VALUES (107, '通知公告', 1, 8, 'notice', 'system/notice/index', '', 1, 0, 'C', '0', '0', 'system:notice:list', 'message', 'admin', '2023-12-27 15:00:27', '', '', '通知公告菜单');
INSERT INTO `sys_menu` VALUES (108, '日志管理', 1, 9, 'log', '', '', 1, 0, 'M', '0', '0', '', 'log', 'admin', '2023-12-27 15:00:27', '', '', '日志管理菜单');
INSERT INTO `sys_menu` VALUES (109, '在线用户', 2, 1, 'online', 'monitor/online/index', '', 1, 0, 'C', '0', '0', 'monitor:online:list', 'online', 'admin', '2023-12-27 15:00:27', '', '', '在线用户菜单');
INSERT INTO `sys_menu` VALUES (110, '定时任务', 2, 2, 'job', 'monitor/job/index', '', 1, 0, 'C', '0', '0', 'monitor:job:list', 'job', 'admin', '2023-12-27 15:00:27', '', '', '定时任务菜单');
INSERT INTO `sys_menu` VALUES (111, '数据监控', 2, 3, 'druid', 'monitor/druid/index', '', 1, 0, 'C', '0', '0', 'monitor:druid:list', 'druid', 'admin', '2023-12-27 15:00:27', '', '', '数据监控菜单');
INSERT INTO `sys_menu` VALUES (112, '服务监控', 2, 4, 'server', 'monitor/server/index', '', 1, 0, 'C', '0', '0', 'monitor:server:list', 'server', 'admin', '2023-12-27 15:00:27', '', '', '服务监控菜单');
INSERT INTO `sys_menu` VALUES (113, '缓存监控', 2, 5, 'cache', 'monitor/cache/index', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis', 'admin', '2023-12-27 15:00:27', '', '', '缓存监控菜单');
INSERT INTO `sys_menu` VALUES (114, '缓存列表', 2, 6, 'cacheList', 'monitor/cache/list', '', 1, 0, 'C', '0', '0', 'monitor:cache:list', 'redis-list', 'admin', '2023-12-27 15:00:27', '', '', '缓存列表菜单');
INSERT INTO `sys_menu` VALUES (115, '表单构建', 3, 1, 'build', 'tool/build/index', '', 1, 0, 'C', '0', '0', 'tool:build:list', 'build', 'admin', '2023-12-27 15:00:27', '', '', '表单构建菜单');
INSERT INTO `sys_menu` VALUES (116, '代码生成', 3, 2, 'gen', 'tool/gen/index', '', 1, 0, 'C', '0', '0', 'tool:gen:list', 'code', 'admin', '2023-12-27 15:00:27', '', '', '代码生成菜单');
INSERT INTO `sys_menu` VALUES (117, '系统接口', 3, 3, 'swagger', 'tool/swagger/index', '', 1, 0, 'C', '0', '0', 'tool:swagger:list', 'swagger', 'admin', '2023-12-27 15:00:27', '', '', '系统接口菜单');
INSERT INTO `sys_menu` VALUES (500, '操作日志', 108, 1, 'operlog', 'monitor/operlog/index', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list', 'form', 'admin', '2023-12-27 15:00:27', '', '', '操作日志菜单');
INSERT INTO `sys_menu` VALUES (501, '登录日志', 108, 2, 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor', 'admin', '2023-12-27 15:00:27', '', '', '登录日志菜单');
INSERT INTO `sys_menu` VALUES (1000, '用户查询', 100, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1001, '用户新增', 100, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1002, '用户修改', 100, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1003, '用户删除', 100, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1004, '用户导出', 100, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1005, '用户导入', 100, 6, '', '', '', 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1006, '重置密码', 100, 7, '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1007, '角色查询', 101, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1008, '角色新增', 101, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1009, '角色修改', 101, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1010, '角色删除', 101, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1011, '角色导出', 101, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1012, '菜单查询', 102, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1013, '菜单新增', 102, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1014, '菜单修改', 102, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1015, '菜单删除', 102, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1016, '部门查询', 103, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1017, '部门新增', 103, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1018, '部门修改', 103, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1019, '部门删除', 103, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1020, '岗位查询', 104, 1, '', '', '', 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1021, '岗位新增', 104, 2, '', '', '', 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1022, '岗位修改', 104, 3, '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1023, '岗位删除', 104, 4, '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1024, '岗位导出', 104, 5, '', '', '', 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1035, '公告查询', 107, 1, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1036, '公告新增', 107, 2, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1037, '公告修改', 107, 3, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1038, '公告删除', 107, 4, '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1041, '日志导出', 500, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1042, '登录查询', 501, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1043, '登录删除', 501, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1044, '日志导出', 501, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1045, '账户解锁', 501, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1054, '任务导出', 110, 6, '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1055, '生成查询', 116, 1, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1056, '生成修改', 116, 2, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1057, '生成删除', 116, 3, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1058, '导入代码', 116, 4, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1059, '预览代码', 116, 5, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (1060, '生成代码', 116, 6, '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code', '#', 'admin', '2023-12-27 15:00:27', '', '', '');
INSERT INTO `sys_menu` VALUES (2000, '市场洞察', 0, 20, 'data', '', '', 1, 0, 'M', '0', '0', '', 'international', 'admin', '2023-12-22 16:20:02', 'admin', '2023-12-29 16:52:09.2174', '');
INSERT INTO `sys_menu` VALUES (2001, '热搜词', 2000, 1, 'keyword/hot_keyword_list', 'data/keyword/hot_keyword', '', 1, 0, 'C', '0', '0', 'data:keyword:list', 'dict', 'admin', '2023-12-22 16:22:59', 'admin', '2023-12-26 11:55:45', '');
INSERT INTO `sys_menu` VALUES (2002, '推荐商品', 2000, 8, 'goods/goods_recommend', 'data/goods/recommend_list', '', 1, 0, 'C', '1', '0', 'data:goods:recommend', 'star', 'admin', '2023-12-25 16:57:48', 'admin', '2023-12-29 09:07:16.570167', '');
INSERT INTO `sys_menu` VALUES (2003, '商品热卖榜', 2000, 9, 'goods/sale_hot_list', 'data/goods/sale_hot_list', '', 1, 0, 'C', '1', '0', 'data:goods:salehot', 'list', 'admin', '2023-12-25 17:08:24', 'admin', '2023-12-29 09:07:20.615237', '');
INSERT INTO `sys_menu` VALUES (2004, '市场动态', 2000, 0, 'market_info', 'data/market/info', '', 1, 0, 'C', '1', '0', 'data:market:info', 'guide', 'admin', '2023-12-26 11:55:33', 'admin', '2023-12-29 09:07:09.697533', '');
INSERT INTO `sys_menu` VALUES (2005, '供应商管理', 4, 9, 'supplier/list', 'scm/supplier/index', '', 1, 0, 'C', '0', '0', 'scm:supplier', 'qq', 'admin', '2023-12-29 09:14:02', 'admin', '2023-12-29 09:17:27.514466', '');
INSERT INTO `sys_menu` VALUES (2006, '商品管理', 0, 9, 'goods', '', '', 1, 0, 'M', '0', '0', '', 'theme', 'admin', '2023-12-29 13:29:44', '', '', '');
INSERT INTO `sys_menu` VALUES (2007, '商品管理', 2006, 1, 'goods_list', 'goods/index', '', 1, 0, 'C', '0', '0', 'goods:list', 'theme', 'admin', '2023-12-29 13:31:01', 'admin', '2023-12-29 15:02:40.869685', '');
INSERT INTO `sys_menu` VALUES (2008, '商品分类', 2006, 88, 'goods_category', 'goods/category/index', '', 1, 0, 'C', '0', '0', 'goods:category', 'tree-table', 'admin', '2023-12-29 13:32:41', 'admin', '2023-12-29 15:02:22.220534', '');
INSERT INTO `sys_menu` VALUES (2009, '品牌管理', 2006, 99, 'goods/brand', 'goods/brand/index', '', 1, 0, 'C', '0', '0', 'goods:brand', 'clipboard', 'admin', '2023-12-29 13:34:49', 'admin', '2023-12-29 13:58:43.724029', '');
INSERT INTO `sys_menu` VALUES (2010, '采购订单管理', 4, 1, 'purchase/order', 'scm/purchase/order', '', 1, 0, 'C', '0', '0', 'scm:purchase:order', 'button', 'admin', '2023-12-29 16:35:55', 'admin', '2023-12-29 16:36:57.653118', '');
INSERT INTO `sys_menu` VALUES (2011, '采购合同管理', 4, 2, 'purchase/contract', 'scm/purchase/contract', '', 1, 0, 'C', '0', '1', 'scm:purchase:contract', 'clipboard', 'admin', '2023-12-29 16:39:43', 'admin', '2023-12-30 18:07:58.363465', '');
INSERT INTO `sys_menu` VALUES (2012, '采购物流管理', 4, 3, 'purchase/ship', 'scm/purchase/ship', '', 1, 0, 'C', '0', '0', 'scm:purchase:ship', 'component', 'admin', '2023-12-29 16:45:42', 'admin', '2023-12-30 20:48:47.282509', '');
INSERT INTO `sys_menu` VALUES (2013, '采购费用管理', 4, 4, 'purchase/cost', 'scm/purchase/cost', '', 1, 0, 'C', '0', '0', 'scm:purchase:cost', 'checkbox', 'admin', '2023-12-29 16:47:07', '', '', '');
INSERT INTO `sys_menu` VALUES (2014, '销售管理', 0, 1, 'sale', '', '', 1, 0, 'M', '0', '0', '', 'shopping', 'admin', '2023-12-29 16:53:03', 'admin', '2024-01-03 11:03:17', '');
INSERT INTO `sys_menu` VALUES (2015, '店铺管理', 2014, 10, 'list', 'shop/index', '', 1, 0, 'C', '0', '0', 'shop:list', 'example', 'admin', '2023-12-29 16:54:02', 'admin', '2023-12-29 17:08:18.125544', '');
INSERT INTO `sys_menu` VALUES (2016, '店铺商品管理', 2014, 9, 'goods', 'shop/goods', '', 1, 0, 'C', '0', '0', 'shop:goods', 'color', 'admin', '2023-12-29 17:02:42', 'admin', '2023-12-31 18:20:12.86152', '');
INSERT INTO `sys_menu` VALUES (2017, '店铺数据统计', 2014, 8, 'data', 'shop/data', '', 1, 0, 'C', '0', '1', 'shop:data', 'chart', 'admin', '2023-12-29 17:04:08', 'admin', '2023-12-31 19:04:58.785609', '');
INSERT INTO `sys_menu` VALUES (2018, '财务管理', 0, 10, 'finanice', '', '', 1, 0, 'M', '0', '0', '', 'email', 'admin', '2023-12-29 17:07:23', 'admin', '2024-01-01 14:25:11.68661', '');
INSERT INTO `sys_menu` VALUES (2019, '账单管理', 2018, 1, 'zdlist', '', '', 1, 0, 'C', '0', '0', '', 'druid', 'admin', '2023-12-29 17:09:32', 'admin', '2024-01-01 14:25:42.087888', '');
INSERT INTO `sys_menu` VALUES (2021, '创建采购订单', 4, 0, 'purchase/order/create', 'scm/purchase/order/create', '', 1, 0, 'C', '0', '0', '', 'edit', 'admin', '2023-12-29 21:23:45', 'admin', '2023-12-29 21:26:17.594875', '');
INSERT INTO `sys_menu` VALUES (2025, '采购订单详情', 4, 1, 'purchase/order/detail', 'scm/purchase/order/detail', '', 1, 0, 'C', '1', '0', '', 'button', 'admin', '2023-12-30 17:08:01', 'admin', '2023-12-30 17:09:38.154348', '');
INSERT INTO `sys_menu` VALUES (2026, '库存管理', 0, 3, 'wms', '', '', 1, 0, 'M', '0', '0', '', 'lock', 'admin', '2023-12-31 12:14:33', 'admin', '2024-01-03 11:04:48', '');
INSERT INTO `sys_menu` VALUES (2027, '入库单管理', 2026, 0, 'stock_in_entry/list', 'wms/WmsStockInEntry', '', 1, 0, 'C', '0', '0', 'wms:stock_in_entry:list', 'documentation', 'admin', '2023-12-31 12:27:37', '', '', '');
INSERT INTO `sys_menu` VALUES (2028, '生成采购入库单', 4, 3, 'purchase/ship/create_stock_in_entry', 'scm/purchase/ship/create_stock_in_entry', '', 1, 0, 'C', '1', '0', '', 'button', 'admin', '2023-12-31 12:31:32', 'admin', '2023-12-31 12:37:39.956753', '');
INSERT INTO `sys_menu` VALUES (2029, '店铺订单处理', 2014, 4, 'order/handling', 'shop/order/handling', '', 1, 0, 'M', '0', '0', '', 'edit', 'admin', '2023-12-31 17:29:03', 'admin', '2024-01-02 14:11:00', '');
INSERT INTO `sys_menu` VALUES (2030, '手动创建订单', 2014, 1, 'order/create', 'shop/order/create', '', 1, 0, 'C', '0', '0', '', 'documentation', 'admin', '2023-12-31 20:01:22', 'admin', '2024-01-03 11:02:32', '');
INSERT INTO `sys_menu` VALUES (2032, 'API拉取订单', 2014, 3, 'order/pull', 'shop/order/pull', '', 1, 0, 'M', '0', '1', '', 'upload', 'admin', '2023-12-31 20:04:12', 'admin', '2024-01-01 14:34:22.157829', '');
INSERT INTO `sys_menu` VALUES (2033, '订单查询', 2014, 5, 'order/list', 'shop/order/index', '', 1, 0, 'C', '0', '0', '', 'list', 'admin', '2023-12-31 20:05:05', 'admin', '2024-01-03 11:02:42', '');
INSERT INTO `sys_menu` VALUES (2034, '销售管理2', 0, 2, 'sale', '', '', 1, 0, 'M', '0', '1', '', 'excel', 'admin', '2024-01-01 14:08:04', 'admin', '2024-01-03 11:02:53', '');
INSERT INTO `sys_menu` VALUES (2035, '多多订单', 2029, 1, 'pdd', '', '', 1, 0, 'C', '0', '0', '', 'cascader', 'admin', '2024-01-01 14:13:47', 'admin', '2024-01-02 14:11:13', '');
INSERT INTO `sys_menu` VALUES (2036, '店铺订单管理', 2014, 2, 'order', '', '', 1, 0, 'M', '0', '0', '', 'excel', 'admin', '2024-01-01 14:14:42', 'admin', '2024-01-03 11:03:57', '');
INSERT INTO `sys_menu` VALUES (2037, '多多订单', 2036, 0, 'pdd', 'pdd/order', '', 1, 0, 'C', '0', '0', '', 'button', 'admin', '2024-01-01 14:15:28', 'admin', '2024-01-02 15:10:44', '');
INSERT INTO `sys_menu` VALUES (2038, '拼多多订单拉取', 2032, 0, 'pdd', '', '', 1, 0, 'C', '0', '0', '', 'bug', 'admin', '2024-01-01 14:20:19', '', '', '');
INSERT INTO `sys_menu` VALUES (2039, '抖店订单', 2036, 2, 'dou', 'dou/order', '', 1, 0, 'C', '0', '0', '', 'cascader', 'admin', '2024-01-01 14:35:27', 'admin', '2024-01-02 18:29:30', '');
INSERT INTO `sys_menu` VALUES (2040, '淘宝订单', 2036, 3, 'tao', 'tao/order', NULL, 1, 0, 'C', '0', '0', '', 'checkbox', 'admin', '2024-01-02 13:54:15', 'admin', '2024-01-03 09:10:37', '');
INSERT INTO `sys_menu` VALUES (2041, '抖店订单', 2029, 2, 'dou', NULL, NULL, 1, 0, 'C', '0', '0', NULL, 'checkbox', 'admin', '2024-01-02 14:11:34', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2042, '淘宝订单', 2029, 3, 'tao', NULL, NULL, 1, 0, 'M', '0', '0', '', '404', 'admin', '2024-01-02 14:11:56', 'admin', '2024-01-02 14:12:14', '');
INSERT INTO `sys_menu` VALUES (2043, '小红书', 2036, 4, 'xhs', 'xhs/order', NULL, 1, 0, 'C', '0', '0', '', 'color', 'admin', '2024-01-02 18:53:14', 'admin', '2024-01-03 09:11:16', '');
INSERT INTO `sys_menu` VALUES (2044, '小红书订单', 2029, 4, 'xhs', NULL, NULL, 1, 0, 'C', '0', '0', NULL, 'cascader', 'admin', '2024-01-03 09:18:53', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2045, '发货管理', 2026, 3, 'ship', NULL, NULL, 1, 0, 'C', '0', '0', NULL, 'clipboard', 'admin', '2024-01-03 10:59:54', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2046, '出库管理', 2026, 5, 'chuku', NULL, NULL, 1, 0, 'C', '0', '0', NULL, '#', 'admin', '2024-01-03 11:00:53', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2047, '库存查询', 2026, 6, 'st', NULL, NULL, 1, 0, 'C', '0', '0', NULL, '404', 'admin', '2024-01-03 11:01:14', '', NULL, '');
INSERT INTO `sys_menu` VALUES (2048, '库存盘点', 2026, 9, 'pan', NULL, NULL, 1, 0, 'C', '0', '0', NULL, 'bug', 'admin', '2024-01-03 11:01:43', '', NULL, '');

SET FOREIGN_KEY_CHECKS = 1;
