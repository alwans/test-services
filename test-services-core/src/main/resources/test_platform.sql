/*
 Navicat Premium Data Transfer

 Source Server         : 158
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : 192.168.100.158:3306
 Source Schema         : test_platform

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 20/05/2021 13:49:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sandbox_id` int(255) NOT NULL COMMENT 'sandbox_info表id',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_record` datetime(0) NOT NULL COMMENT '录制时间',
  `app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名',
  `environment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '环境信息',
  `host` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机器IP',
  `trace_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链路追踪ID',
  `entrance_desc` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '链路追踪ID',
  `wrapper_record` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '记录序列化信息',
  `request` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求参数JSON',
  `response` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '返回值JSON',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '录制信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for repeater_config
-- ----------------------------
DROP TABLE IF EXISTS `repeater_config`;
CREATE TABLE `repeater_config`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sandboxId` int(10) NOT NULL COMMENT 'sandbox_info表id',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '环境信息',
  `appName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名',
  `config` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置信息',
  `isDelete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除标记:0:未删除 | 1:已删除',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人信息',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for replay
-- ----------------------------
DROP TABLE IF EXISTS `replay`;
CREATE TABLE `replay`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime(0) NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `app_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '应用名',
  `environment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '环境信息',
  `ip` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '机器IP',
  `repeat_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回放ID',
  `status` tinyint(4) NOT NULL COMMENT '回放状态',
  `trace_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链路追踪ID',
  `cost` bigint(20) NULL DEFAULT NULL COMMENT '回放耗时',
  `diff_result` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'diff结果',
  `response` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '回放结果',
  `mock_invocation` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'mock过程',
  `success` bit(1) NULL DEFAULT NULL COMMENT '是否回放成功',
  `record_id` bigint(20) NULL DEFAULT NULL COMMENT '外键',
  `batch` bit(1) NULL DEFAULT b'0' COMMENT '是否批量执行',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '回放信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sandbox_info
-- ----------------------------
DROP TABLE IF EXISTS `sandbox_info`;
CREATE TABLE `sandbox_info`  (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `env` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用对应的环境,例:dev;test;release',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用所在的机器ip',
  `serverName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用名称',
  `serverPid` int(8) NULL DEFAULT NULL COMMENT '应用进程id',
  `aideServerPort` int(8) NULL DEFAULT NULL COMMENT 'test-sandbox-aide服务的端口号',
  `sandboxNameSpace` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sandbox的nameSpace',
  `sandboxPort` int(8) NULL DEFAULT NULL COMMENT 'sandbox中nettyServer端口',
  `sandboxStatus` tinyint(1) NULL DEFAULT NULL COMMENT 'sandbox启动状态:0(未启动) |  1(已启动)',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sandbox的token',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modifyTime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `operator` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
