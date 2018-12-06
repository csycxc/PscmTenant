/*
Navicat MySQL Data Transfer

Source Server         : arzy
Source Server Version : 50067
Source Host           : localhost:3306
Source Database       : tenant

Target Server Type    : MYSQL
Target Server Version : 50067
File Encoding         : 65001

Date: 2018-07-18 16:13:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for biz_authority
-- ----------------------------
DROP TABLE IF EXISTS `biz_authority`;
CREATE TABLE `biz_authority` (
  `tenant_code` varchar(50) NOT NULL,
  `biz_code` varchar(50) NOT NULL,
  `biz_chinese_name` varchar(50) default NULL,
  PRIMARY KEY  (`tenant_code`,`biz_code`),
  KEY `FK_BizCode` USING BTREE (`biz_code`),
  CONSTRAINT `FK_BizCode` FOREIGN KEY (`biz_code`) REFERENCES `business` (`biz_code`),
  CONSTRAINT `FK_TenantCode` FOREIGN KEY (`tenant_code`) REFERENCES `tenant` (`tenant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='租户分配功能数据表';

-- ----------------------------
-- Records of biz_authority
-- ----------------------------
INSERT INTO `biz_authority` VALUES ('1000', 'b1', '修改密码a');
INSERT INTO `biz_authority` VALUES ('1000', 'b2', '添加');
INSERT INTO `biz_authority` VALUES ('1001', 'b2', '添加');
INSERT INTO `biz_authority` VALUES ('1002', 'b2', '添加');

-- ----------------------------
-- Table structure for business
-- ----------------------------
DROP TABLE IF EXISTS `business`;
CREATE TABLE `business` (
  `biz_code` varchar(50) NOT NULL,
  `biz_english_name` varchar(50) default NULL,
  `biz_chinese_name` varchar(50) default NULL,
  `biz_desc` varchar(100) default NULL,
  PRIMARY KEY  (`biz_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='功能模块数据表';

-- ----------------------------
-- Records of business
-- ----------------------------
INSERT INTO `business` VALUES ('b1', 'updatePasswprd', '修改密码a', '的说辞');
INSERT INTO `business` VALUES ('b2', 'add', '添加11', 'desc');

-- ----------------------------
-- Table structure for dynamic_source
-- ----------------------------
DROP TABLE IF EXISTS `dynamic_source`;
CREATE TABLE `dynamic_source` (
  `source_code` varchar(50) NOT NULL,
  `source_name` varchar(50) default NULL,
  `drive_name` varchar(50) default NULL,
  `link_url` varchar(50) default NULL,
  `user_name` varchar(50) default NULL,
  `user_password` varchar(50) default NULL,
  PRIMARY KEY  (`source_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='动态数据源表';

-- ----------------------------
-- Records of dynamic_source
-- ----------------------------
INSERT INTO `dynamic_source` VALUES ('s1', 'sss', '驱动器1', 'http://localhost:8080', 'admin', '1231');
INSERT INTO `dynamic_source` VALUES ('s2', 'uuu', '驱动器2', 'http', '张三', '123');
INSERT INTO `dynamic_source` VALUES ('s3', 's3name', '驱动器dd', 'www.baidu.com', 'lisi', '123');

-- ----------------------------
-- Table structure for tenant
-- ----------------------------
DROP TABLE IF EXISTS `tenant`;
CREATE TABLE `tenant` (
  `tenant_code` varchar(50) NOT NULL,
  `account` varchar(50) default NULL,
  `password` varchar(50) default NULL,
  `name` varchar(50) default NULL,
  `tenant_type` int(11) default NULL COMMENT '租户类型:0项目部,1项目部所在的公司,2公司的上级集团公司。以此类推。100业主,101监理,102设计,102咨询',
  `create_date` date default NULL,
  `start_date` date default NULL,
  `end_date` date default NULL,
  `status` int(11) default NULL COMMENT '租户的状态：1正常,2欠费,3过期,4没有使用',
  `parent_code` varchar(50) NOT NULL COMMENT '任何一个租户都存在父租户。没有父租户的租户是不存在的。如果根租户，其父租户编码为0。',
  `real_code` varchar(50) default NULL,
  `source_code` varchar(50) default NULL,
  PRIMARY KEY  (`tenant_code`),
  KEY `FK_TenantReal` USING BTREE (`real_code`),
  KEY `FK_DynamicSource` USING BTREE (`source_code`),
  CONSTRAINT `FK_DynamicSource` FOREIGN KEY (`source_code`) REFERENCES `dynamic_source` (`source_code`),
  CONSTRAINT `FK_TenantReal` FOREIGN KEY (`real_code`) REFERENCES `tenant_real` (`real_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='租户表';

-- ----------------------------
-- Records of tenant
-- ----------------------------
INSERT INTO `tenant` VALUES ('1000', 'ab', '123', '租户1000', '0', null, '2017-01-01', null, '1', '0', null, null);
INSERT INTO `tenant` VALUES ('1001', 'aaa', '123', '租户1', '1', null, null, null, '1', '1000', '111', 's1');
INSERT INTO `tenant` VALUES ('1002', 'bbb', '123', 'zuhu2', '1', null, null, null, '1', '1000', null, null);
INSERT INTO `tenant` VALUES ('2000', 'cc', '123', '租户2000', '0', null, null, null, '1', '0', null, null);
INSERT INTO `tenant` VALUES ('2001', 'ccc', '123', 'c1', '1', null, null, null, '1', '2000', '111', 's1');
INSERT INTO `tenant` VALUES ('2002', 'disange', null, 'c2', null, null, null, null, null, '2000', null, null);
INSERT INTO `tenant` VALUES ('rrr', 'rr', null, 'rrr', null, null, null, null, null, '1000', '111', null);

-- ----------------------------
-- Table structure for tenant_real
-- ----------------------------
DROP TABLE IF EXISTS `tenant_real`;
CREATE TABLE `tenant_real` (
  `real_code` varchar(50) NOT NULL,
  `name` varchar(50) default NULL,
  `address` varchar(50) default NULL,
  `post_code` varchar(20) default NULL,
  `contacter` varchar(20) default NULL,
  `telephone` varchar(20) default NULL,
  `biz_license` varbinary(100) default NULL,
  `other_scanner` varbinary(100) default NULL,
  `real_type` varchar(50) default NULL,
  PRIMARY KEY  (`real_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='企业数据表';

-- ----------------------------
-- Records of tenant_real
-- ----------------------------
INSERT INTO `tenant_real` VALUES ('1000', 'banry3', '西三旗桥东', null, '55', '1235446', null, null, 'a');
INSERT INTO `tenant_real` VALUES ('111', '安瑞', '北京市', null, '46', '123456', null, null, 'b111');
