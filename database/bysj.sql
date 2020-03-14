/*
Navicat MySQL Data Transfer

Source Server         : Msql
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : bysj

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2020-03-14 20:32:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `history`
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `systemid` varchar(50) NOT NULL,
  `fileid` varchar(50) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `cjsj` datetime DEFAULT NULL,
  PRIMARY KEY (`systemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history
-- ----------------------------

-- ----------------------------
-- Table structure for `shoucang`
-- ----------------------------
DROP TABLE IF EXISTS `shoucang`;
CREATE TABLE `shoucang` (
  `systemid` varchar(50) NOT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `fileid` varchar(50) DEFAULT NULL,
  `cjsj` datetime DEFAULT NULL,
  PRIMARY KEY (`systemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shoucang
-- ----------------------------

-- ----------------------------
-- Table structure for `t_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary`;
CREATE TABLE `t_dictionary` (
  `id` varchar(50) NOT NULL,
  `kind` varchar(50) DEFAULT NULL,
  `pid` varchar(50) DEFAULT NULL,
  `kind_code` varchar(50) DEFAULT NULL,
  `detail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dictionary
-- ----------------------------
INSERT INTO `t_dictionary` VALUES ('1', 'menu', null, '01', '小初高资料');
INSERT INTO `t_dictionary` VALUES ('10', 'menutag_01', 'menu', '04', '政治');
INSERT INTO `t_dictionary` VALUES ('11', 'menutag_01', 'menu', '05', '历史');
INSERT INTO `t_dictionary` VALUES ('12', 'menutag_01', 'menu', '06', '地理');
INSERT INTO `t_dictionary` VALUES ('13', 'menutag_01', 'menu', '07', '物理');
INSERT INTO `t_dictionary` VALUES ('14', 'menutag_01', 'menu', '08', '生物');
INSERT INTO `t_dictionary` VALUES ('15', 'menutag_01', 'menu', '09', '化学');
INSERT INTO `t_dictionary` VALUES ('16', 'menutag_02', 'menu', '10', '哲学');
INSERT INTO `t_dictionary` VALUES ('17', 'menutag_02', 'menu', '11', '经济学');
INSERT INTO `t_dictionary` VALUES ('18', 'menutag_02', 'menu', '12', '法学');
INSERT INTO `t_dictionary` VALUES ('19', 'menutag_02', 'menu', '13', '教育学');
INSERT INTO `t_dictionary` VALUES ('2', 'menu', null, '02', '高等教育');
INSERT INTO `t_dictionary` VALUES ('20', 'menutag_02', 'menu', '14', '文学');
INSERT INTO `t_dictionary` VALUES ('21', 'menutag_02', 'menu', '15', '历史学');
INSERT INTO `t_dictionary` VALUES ('22', 'menutag_02', 'menu', '16', '理学');
INSERT INTO `t_dictionary` VALUES ('23', 'menutag_02', 'menu', '17', '工学');
INSERT INTO `t_dictionary` VALUES ('24', 'menutag_02', 'menu', '18', '农学');
INSERT INTO `t_dictionary` VALUES ('25', 'menutag_02', 'menu', '19', '医学');
INSERT INTO `t_dictionary` VALUES ('26', 'menutag_02', 'menu', '20', '军事学');
INSERT INTO `t_dictionary` VALUES ('27', 'menutag_02', 'menu', '21', '管理学');
INSERT INTO `t_dictionary` VALUES ('28', 'menutag_02', 'menu', '22', '艺术学');
INSERT INTO `t_dictionary` VALUES ('29', 'menutag_03', 'menu', '23', '广告/传媒');
INSERT INTO `t_dictionary` VALUES ('3', 'menu', null, '03', '专业资料');
INSERT INTO `t_dictionary` VALUES ('30', 'menutag_03', 'menu', '24', '艺术/设计');
INSERT INTO `t_dictionary` VALUES ('31', 'menutag_03', 'menu', '25', '销售/营销');
INSERT INTO `t_dictionary` VALUES ('32', 'menutag_03', 'menu', '26', '财务管理');
INSERT INTO `t_dictionary` VALUES ('33', 'menutag_03', 'menu', '27', '会计');
INSERT INTO `t_dictionary` VALUES ('34', 'menutag_03', 'menu', '28', '行政/公共管理');
INSERT INTO `t_dictionary` VALUES ('35', 'menutag_03', 'menu', '29', '金融/投资');
INSERT INTO `t_dictionary` VALUES ('36', 'menutag_03', 'menu', '30', '农林牧渔');
INSERT INTO `t_dictionary` VALUES ('37', 'menutag_03', 'menu', '31', '医药卫生');
INSERT INTO `t_dictionary` VALUES ('38', 'menutag_03', 'menu', '32', '自然科学');
INSERT INTO `t_dictionary` VALUES ('39', 'menutag_04', 'menu', '33', '法律资料');
INSERT INTO `t_dictionary` VALUES ('4', 'menu', null, '04', '人文社科');
INSERT INTO `t_dictionary` VALUES ('40', 'menutag_04', 'menu', '34', '文化/宗教');
INSERT INTO `t_dictionary` VALUES ('42', 'menutag_04', 'menu', '35', '哲学/历史');
INSERT INTO `t_dictionary` VALUES ('43', 'menutag_04', 'menu', '36', '社会学');
INSERT INTO `t_dictionary` VALUES ('44', 'menutag_05', 'menu', '37', '建筑/土木');
INSERT INTO `t_dictionary` VALUES ('45', 'menutag_05', 'menu', '38', '城乡/园林规划');
INSERT INTO `t_dictionary` VALUES ('46', 'menutag_05', 'menu', '39', '环境/食品科学');
INSERT INTO `t_dictionary` VALUES ('47', 'menutag_05', 'menu', '40', '电力/水利');
INSERT INTO `t_dictionary` VALUES ('48', 'menutag_05', 'menu', '41', '能源/化工');
INSERT INTO `t_dictionary` VALUES ('49', 'menutag_05', 'menu', '42', '交通运输');
INSERT INTO `t_dictionary` VALUES ('5', 'menu', null, '05', '工程科技');
INSERT INTO `t_dictionary` VALUES ('50', 'menutag_05', 'menu', '43', '机械/仪表');
INSERT INTO `t_dictionary` VALUES ('51', 'menutag_05', 'menu', '44', '纺织/轻工业');
INSERT INTO `t_dictionary` VALUES ('52', 'menutag_05', 'menu', '45', '材料科学');
INSERT INTO `t_dictionary` VALUES ('53', 'menutag_05', 'menu', '45', 'IT/计算机');
INSERT INTO `t_dictionary` VALUES ('54', 'menutag_06', 'menu', '46', '求职/职场');
INSERT INTO `t_dictionary` VALUES ('55', 'menutag_06', 'menu', '47', '计划/解决方案');
INSERT INTO `t_dictionary` VALUES ('56', 'menutag_06', 'menu', '48', '总结/汇报');
INSERT INTO `t_dictionary` VALUES ('57', 'menutag_06', 'menu', '49', '党团工作');
INSERT INTO `t_dictionary` VALUES ('58', 'menutag_06', 'menu', '50', '工作范文');
INSERT INTO `t_dictionary` VALUES ('59', 'menutag_06', 'menu', '51', '表格/模板');
INSERT INTO `t_dictionary` VALUES ('6', 'menu', null, '06', '应用文书');
INSERT INTO `t_dictionary` VALUES ('60', 'menutag', 'menu', '52', '其他');
INSERT INTO `t_dictionary` VALUES ('61', 'menutag_06', 'menu', '53', '心得体会');
INSERT INTO `t_dictionary` VALUES ('7', 'menutag_01', 'menu', '01', '语文');
INSERT INTO `t_dictionary` VALUES ('8', 'menutag_01', 'menu', '02', '数学');
INSERT INTO `t_dictionary` VALUES ('9', 'menutag_01', 'menu', '03', '英语');

-- ----------------------------
-- Table structure for `t_filehtml`
-- ----------------------------
DROP TABLE IF EXISTS `t_filehtml`;
CREATE TABLE `t_filehtml` (
  `systemid` varchar(50) NOT NULL,
  `html` longtext,
  PRIMARY KEY (`systemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_filehtml
-- ----------------------------

-- ----------------------------
-- Table structure for `t_files`
-- ----------------------------
DROP TABLE IF EXISTS `t_files`;
CREATE TABLE `t_files` (
  `systemid` varchar(50) NOT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `cn_name` varchar(50) DEFAULT NULL,
  `filename` varchar(50) DEFAULT NULL,
  `filepath` varchar(100) DEFAULT NULL,
  `sort` varchar(50) DEFAULT NULL,
  `tags` varchar(50) DEFAULT NULL,
  `cjsj` datetime DEFAULT NULL,
  `open` varchar(10) DEFAULT NULL,
  `html` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`systemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_files
-- ----------------------------

-- ----------------------------
-- Table structure for `t_files_his`
-- ----------------------------
DROP TABLE IF EXISTS `t_files_his`;
CREATE TABLE `t_files_his` (
  `systemid` varchar(50) NOT NULL,
  `fileid` varchar(50) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `cn_name` varchar(50) DEFAULT NULL,
  `filename` varchar(50) DEFAULT NULL,
  `filepath` varchar(100) DEFAULT NULL,
  `sort` varchar(50) DEFAULT NULL,
  `tags` varchar(50) DEFAULT NULL,
  `cjsj` datetime DEFAULT NULL,
  `html` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`systemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_files_his
-- ----------------------------
INSERT INTO `t_files_his` VALUES ('305f4ffdb7294ec1a31c12f591aaefda', 'c05a015ad31d44dea96fdc4507819e77', 'xm123456', '自查表', 'ac9b6a506ed64d4b839af8300cd4c73e', 'C:\\BYSJFiles\\5404a440851c4a3fb495415bf4111e32\\', '应用文书', '工作范文', '2020-03-10 00:00:00', 'a19a8e7c5aeb4b33957b9bf0272a09a0');
INSERT INTO `t_files_his` VALUES ('5b8ce10cae9b40b885c68f9486289dcf', 'eb3619562a624b69a4632d17b542641c', 'xm123456', '撒', 'a809097a6b6043608c30ac00f9b09479', 'C:\\BYSJFiles\\ea96ff606fb64e6eb6336e1dd6ce4ba8\\', '高等教育', '文学', '2020-03-07 00:00:00', null);
INSERT INTO `t_files_his` VALUES ('8c2863aead204cd8954be4f6f7d98fd5', 'c05a015ad31d44dea96fdc4507819e77', 'xm123456', '自查表', '4f06ea18726d47f8999804f98ba78f6e', 'C:\\BYSJFiles\\5404a440851c4a3fb495415bf4111e32\\', '应用文书', '工作范文', '2020-03-10 00:00:00', '57fb74ba352746ea94a186b3d7b82783');
INSERT INTO `t_files_his` VALUES ('8de24acbe6b546308451b7be45ce46f5', '02c1c7f2fb6143f9b9df9cbffb9bc6ac', 'xm123456', '我的开题报告', 'e652c52b42854603b866e7611b17a1ce', 'C:\\BYSJFiles\\55537d4e319646dab4483a3239403c25\\', '应用文书', '工作范文', '2020-03-08 00:00:00', null);
INSERT INTO `t_files_his` VALUES ('c0ff9763698044a7bc605f3ccaaccdd4', 'c05a015ad31d44dea96fdc4507819e77', 'xm123456', '自查表', 'c064c5c6973646fab53c770cbe9e9159', 'C:\\BYSJFiles\\5404a440851c4a3fb495415bf4111e32\\', '应用文书', '工作范文', '2020-03-10 00:00:00', null);
INSERT INTO `t_files_his` VALUES ('f5b75b1855c64bf4a5de4dbfa3aac260', '5068772c2948403fafc58a51536e5a5d', 'xm123456', 'test', '07c58667e0ba4870a7aaa4c5d844dbdb', 'C:\\BYSJFiles\\29e6faf2ff29435089a89b93273af6a7\\', '高等教育', '教育学', '2020-03-10 00:00:00', null);

-- ----------------------------
-- Table structure for `t_qxmanager`
-- ----------------------------
DROP TABLE IF EXISTS `t_qxmanager`;
CREATE TABLE `t_qxmanager` (
  `systemid` varchar(50) NOT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `fileid` varchar(50) DEFAULT NULL,
  `sqsj` datetime DEFAULT NULL,
  PRIMARY KEY (`systemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_qxmanager
-- ----------------------------

-- ----------------------------
-- Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `systemid` varchar(50) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `pwd` varchar(50) NOT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `csrq` date DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  `touxiang` text,
  PRIMARY KEY (`systemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------

-- ----------------------------
-- Table structure for `userwt`
-- ----------------------------
DROP TABLE IF EXISTS `userwt`;
CREATE TABLE `userwt` (
  `systemid` varchar(50) NOT NULL,
  `userid` varchar(50) DEFAULT NULL,
  `wtmc` varchar(200) DEFAULT NULL,
  `wtda` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`systemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userwt
-- ----------------------------
