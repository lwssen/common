/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : myshiro

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 25/10/2019 11:55:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sss_menu
-- ----------------------------
DROP TABLE IF EXISTS `sss_menu`;
CREATE TABLE `sss_menu`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parent_id` int(20) NULL DEFAULT NULL COMMENT '父节点',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限字符串名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问url',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_permission`(`permission`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sss_menu
-- ----------------------------
INSERT INTO `sss_menu` VALUES (1, '测试菜单', 0, 'perms[view]', '/user/menus', 0, '2019-09-06 14:04:33', '2019-10-25 08:27:32');
INSERT INTO `sss_menu` VALUES (2, '测试菜单2', 0, 'perms[view2]', '/user/menus222', 0, '2019-09-06 14:04:56', '2019-10-25 08:27:34');

-- ----------------------------
-- Table structure for sss_role
-- ----------------------------
DROP TABLE IF EXISTS `sss_role`;
CREATE TABLE `sss_role`  (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sss_role
-- ----------------------------
INSERT INTO `sss_role` VALUES (1, '管理员');
INSERT INTO `sss_role` VALUES (2, '游客');

-- ----------------------------
-- Table structure for sss_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sss_role_menu`;
CREATE TABLE `sss_role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sss_role_menu
-- ----------------------------
INSERT INTO `sss_role_menu` VALUES (71, 1, 1);
INSERT INTO `sss_role_menu` VALUES (72, 2, 1);
INSERT INTO `sss_role_menu` VALUES (73, 2, 2);

-- ----------------------------
-- Table structure for sss_user
-- ----------------------------
DROP TABLE IF EXISTS `sss_user`;
CREATE TABLE `sss_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sss_user
-- ----------------------------
INSERT INTO `sss_user` VALUES (1, 'admin', 'admin', '2019-09-06 14:02:45', '2019-09-06 14:02:45');
INSERT INTO `sss_user` VALUES (2, 'admin11', 'admin11', '2019-09-06 16:45:12', '2019-09-06 17:24:19');

-- ----------------------------
-- Table structure for sss_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sss_user_role`;
CREATE TABLE `sss_user_role`  (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `user_id` int(32) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` int(32) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色绑定表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sss_user_role
-- ----------------------------
INSERT INTO `sss_user_role` VALUES (1, 1, 1);
INSERT INTO `sss_user_role` VALUES (2, 2, 2);

SET FOREIGN_KEY_CHECKS = 1;
