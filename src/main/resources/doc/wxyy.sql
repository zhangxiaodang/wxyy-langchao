/*
Navicat MySQL Data Transfer

Source Server         : 172.30.27.129
Source Server Version : 80013
Source Host           : 172.30.27.129:3306
Source Database       : wxyy

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-09-19 10:04:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` varchar(35) NOT NULL DEFAULT '' COMMENT '主键uuid',
  `uid` varchar(35) DEFAULT NULL COMMENT '登录用户id',
  `uname` varchar(300) DEFAULT NULL COMMENT '用户姓名',
  `passwd` varchar(32) DEFAULT NULL COMMENT '32位md5加密密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('22f303e4d1c14831900fc41d26bb2e7e', 'admin', '管理员', '21232f297a57a5a743894a0e4a801fc3');

-- ----------------------------
-- Table structure for wx_user_info
-- ----------------------------
DROP TABLE IF EXISTS `wx_user_info`;
CREATE TABLE `wx_user_info` (
  `id` char(35) NOT NULL COMMENT '主键uuid',
  `openid` char(50) DEFAULT NULL COMMENT '微信openid',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wx_user_info
-- ----------------------------

-- ----------------------------
-- Table structure for yy_bmwh
-- ----------------------------
DROP TABLE IF EXISTS `yy_bmwh`;
CREATE TABLE `yy_bmwh` (
  `id` varchar(32) NOT NULL COMMENT 'uuid主键',
  `lx` varchar(5) DEFAULT NULL COMMENT '编码类型',
  `bm` varchar(10) DEFAULT NULL COMMENT '编码',
  `mc` varchar(300) DEFAULT NULL COMMENT '名称',
  `bz` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yy_bmwh
-- ----------------------------
INSERT INTO `yy_bmwh` VALUES ('0415d69540d64c2c82de379bd887438f', '1002', '02', '手动生成', '节假日类型');
INSERT INTO `yy_bmwh` VALUES ('09012665a3fd4324afd6886195bc2f39', '1000', '01', '车驾管业务', '业务类型');
INSERT INTO `yy_bmwh` VALUES ('554d88bc78d74ef7936952af6da3732e', '1001', '01', '自动生成', '节假日类型');
INSERT INTO `yy_bmwh` VALUES ('788dcb296d5e4a67944c3946f1d7b65a', '1000', '02', '违法处理', '业务类型');

-- ----------------------------
-- Table structure for yy_gzsj
-- ----------------------------
DROP TABLE IF EXISTS `yy_gzsj`;
CREATE TABLE `yy_gzsj` (
  `id` varchar(35) NOT NULL COMMENT 'uuid主键',
  `a_kssj` varchar(4) DEFAULT NULL COMMENT '上午工作开始时间',
  `a_jssj` varchar(4) DEFAULT NULL COMMENT '上午工作结束时间',
  `p_kssj` varchar(4) DEFAULT NULL COMMENT '下午工作开始时间',
  `p_jssj` varchar(4) DEFAULT NULL COMMENT '下午工作结束时间',
  `jgsj` varchar(3) DEFAULT NULL COMMENT '业务间隔时间,以分钟为单位',
  `kyyrs` varchar(3) DEFAULT NULL COMMENT '可预约人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yy_gzsj
-- ----------------------------
INSERT INTO `yy_gzsj` VALUES ('44287a8694b548cb953e3ca78a92d96d', '0900', '1200', '1400', '1730', '20', '5');

-- ----------------------------
-- Table structure for yy_jjr
-- ----------------------------
DROP TABLE IF EXISTS `yy_jjr`;
CREATE TABLE `yy_jjr` (
  `id` varchar(35) NOT NULL COMMENT 'uuid主键',
  `rq` varchar(8) DEFAULT NULL COMMENT '日期,YYYYMMDD',
  `bz` varchar(100) DEFAULT NULL COMMENT '节假日说明',
  `lx` varchar(2) DEFAULT NULL COMMENT '节假日类型',
  `year` varchar(4) DEFAULT NULL COMMENT '所属年份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yy_jjr
-- ----------------------------
INSERT INTO `yy_jjr` VALUES ('03afec8a1c3a49739e23ef954a40d5d4', '20191117', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('043b5cd25b9d4d1e862bcc5d09e44943', '20190921', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('068cd179c59040f88c5da859ec8cb1a3', '20191110', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('0de25ea9b288425496d4e6d5306bcaea', '20191002', '国庆节', '02', '2019');
INSERT INTO `yy_jjr` VALUES ('0e080a5689594d8797a9eee3eb7ab573', '20190811', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('123968c57c464f60994d66baf7551f83', '20191012', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('199c823dee4d46889eb33eb09e2799da', '20191005', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('1de43d6c2369448d802b2b7b3337dc4a', '20190824', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('231fcdbd76c84940b3f1eedd94113664', '20190901', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('26db12f778e349b1acff7caef6055d44', '20190922', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('280a563ea915497d9f805b5ccdf6776c', '20190728', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('283c9d4d207349ab85e7071e9355c348', '20190908', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('2fcb9407dd88437b89dac5cebd5ccaee', '20190810', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('3100d281a4ca40c5855e0eaad0488178', '20190825', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('31c4c9eeb425448686a72c61a009bdeb', '20191201', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('342c6f30f31d482ab77af0562cfbf9fe', '20191221', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('3646af3caed44a60aa4292b5f33612a4', '20191026', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('3d89aa3155a842df8685afcaa9540fca', '20190818', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('4fd5c6e3b6c94f2bb57ce306586c449d', '20190929', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('510833bc0a4242d0af45b1ae44a219a7', '20190907', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('62b9f61eb55d40f9b0bbb8aae5afbdac', '20191116', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('64305680274b4dc1aa198065fed6aba0', '20191001', '国庆节', '02', '2019');
INSERT INTO `yy_jjr` VALUES ('6bb4dfe13b024ebfb44a70e12b35e027', '20191006', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('6ddbb232899a40e4ac0313ebc0844fa7', '20190804', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('73e9a56b1c9341d1b97d447a39266dbf', '20191214', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('774e1f15cc20492c9756cb7fa17c0011', '20190914', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('7ce89fd1b74649cbba9b9ff54fd141dc', '20191228', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('80e28fff51b94609b434d96a153c4187', '20191003', '国庆节', '02', '2019');
INSERT INTO `yy_jjr` VALUES ('8baf0b4f5eee454c92d2626aafcea553', '20191007', '国庆节', '02', '2019');
INSERT INTO `yy_jjr` VALUES ('91141c0314284978ae114ef661b468a8', '20190817', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('92d533d16ac64636bf1068e8e717bb7b', '20191215', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('964514ddbe514848bfc702d24c20944f', '20191013', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('9a12d2c3074a46d48103dd008940d0d6', '20191229', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('9ff9acd9e7d7466a8cf33a29843d152e', '20190913', '中秋节', '02', '2019');
INSERT INTO `yy_jjr` VALUES ('a1394918fc1f472694cfe61e88d0a850', '20190915', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('aceb55b7ad9c41d9830487949ad48e98', '20191123', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('b306d7953cb44261aef44de79cd02fc9', '20191124', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('c1933a308c6f4d84acf97e31fdcc5ed1', '20191130', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('c9654a46ef2248189233d076db9b76d0', '20191019', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('ce4ead22543f4bb08e81b107a608c186', '20191103', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('d505a31e2405422e85958d95128fd38d', '20191020', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('d668423316db4ce48c7f0ce9e99ed36a', '20191102', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('dcb266b4cce04ac6912fa9fd8d8f2bab', '20190928', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('ddcbd1578a76445d9ef55685ac503e29', '20191109', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('e22fc3494ac048129cb78eb02a36cd13', '20191027', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('e886024c39cb4397a2082eebc98739ac', '20190831', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('ea315a21ce3f478f9d186d169d022dcc', '20191207', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('ee115af2c766492fb06101a329e40f5a', '20191208', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('f198cf58b7c64a7d848f94430a7ded2d', '20191222', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('f58e0811cf364155954e4ddf13245f1d', '20190803', '周末(自动生成)', '01', '2019');
INSERT INTO `yy_jjr` VALUES ('f7cc0ee15fa0437e9d63cded6f0d2b14', '20191004', '国庆节', '02', '2019');

-- ----------------------------
-- Table structure for yy_ls
-- ----------------------------
DROP TABLE IF EXISTS `yy_ls`;
CREATE TABLE `yy_ls` (
  `id` varchar(35) NOT NULL COMMENT 'uuid主键',
  `openid` varchar(50) DEFAULT NULL COMMENT '微信用户openid',
  `yyrq` varchar(8) DEFAULT NULL COMMENT '预约日期,YYYYMMDD',
  `yykssj` varchar(6) DEFAULT NULL COMMENT '预约时间,HHMM',
  `yylx` varchar(2) DEFAULT NULL COMMENT '预约类型,01:车管业务,02:违法处理',
  `ywfl` varchar(35) DEFAULT NULL COMMENT '业务分类,01:变更登记,02:注册登记。。。详情查看业务表',
  `yysjh` varchar(11) DEFAULT NULL COMMENT '预约手机号',
  `yyxm` varchar(300) DEFAULT NULL COMMENT '预约姓名',
  `yyid` varchar(18) DEFAULT NULL COMMENT '预约身份证号',
  `yycp` varchar(20) DEFAULT NULL COMMENT '预约车牌号',
  `sfqh` varchar(1) DEFAULT NULL COMMENT '是否取号,0:未取，1:已取',
  `bz` varchar(300) DEFAULT NULL COMMENT '备注',
  `qhsj` varchar(15) DEFAULT NULL COMMENT '取号时间,YYYYMMDDHHMMSS',
  `sfgq` varchar(1) DEFAULT NULL COMMENT '是否过期,0:否,1:是',
  `yyjssj` varchar(6) DEFAULT NULL COMMENT '预约结束时间,HHMM',
  `ywfl2` varchar(300) DEFAULT NULL COMMENT '车管业务业务名称',
  `sfqx` varchar(1) DEFAULT NULL COMMENT '是否取消,0:未取消,1:已取消',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yy_ls
-- ----------------------------
INSERT INTO `yy_ls` VALUES ('05383f1d4f3d42549f79afad6e92216e', 'o2bZjwYZfjnUE8iuY5n7aD39kAyw', '20190912', '1440', '01', 'd503c9896bc84496a3ea4462693db22d', '13335132655', '张开瑞', '371581198808155117', '', '0', '', null, '1', '1500', '社保办理', '0');
INSERT INTO `yy_ls` VALUES ('0d53c37eb04c40f59bb71d4b8806cf33', 'o2bZjwY8h9sM8rdtZPHJmONVsie0', '20190912', '1140', '01', 'c02f13f0b54b41f0ac3ff2777281dff3', '18013584288', '董自强', '622425199001034112', '', '0', '', null, '1', '1200', '入职手续办理', '0');
INSERT INTO `yy_ls` VALUES ('0eaa6b97e45545999324017596a28ff9', 'o2bZjwYzyqVCcIKlIMyzvM-PeZeQ', '20190904', '1400', '01', '0af987c81afd4d708a8b26ad83426e34', '15905414860', '王玉杰', '370724199709184323', '', '0', '', null, '1', '1420', '劳动合同续签', '0');
INSERT INTO `yy_ls` VALUES ('0f4110ef25284587a9244895e0b66ef6', 'o2bZjwaCW86QgvERHZBFdl27cv40', '20190905', '1600', '01', '1434ff8972714375b21b632c33013767', '18080292001', '李昊原', '511622199704010059', '', '0', '', null, '0', '1620', '离职手续办理', '1');
INSERT INTO `yy_ls` VALUES ('25f1dd2c2d9e47638f477b60d02a7c64', 'o2bZjwb3hWQkiy27rShwgnOS9tzo', '20190909', '1520', '01', 'd636e89df7064a1ab7a83cee2e530d2c', '18653182312', '靳云姝', '370102197403192128', '', '0', '', null, '1', '1540', '卡证办理', '0');
INSERT INTO `yy_ls` VALUES ('2da853a7bcdf4dea993529178bb4fa64', 'o2bZjwYzyqVCcIKlIMyzvM-PeZeQ', '20190905', '0920', '01', 'c02f13f0b54b41f0ac3ff2777281dff3', '15905414860', '张晓敏', '370112198609196017', '', '0', '', null, '0', '0940', '入职手续办理', '1');
INSERT INTO `yy_ls` VALUES ('38486a99a77147fc968b5883ab42ff37', 'null', '20190829', '0900', '01', '1434ff8972714375b21b632c33013767', '15905414860', '张孝党', '370112198609196017', '', '0', '', null, '1', '0930', '离职手续办理', '0');
INSERT INTO `yy_ls` VALUES ('6be84d07c2f0492ab28cbe7b8dd1d70b', 'null', '20190909', '1640', '01', 'd636e89df7064a1ab7a83cee2e530d2c', '18653182312', '靳云姝', '370102197403192128', '', '0', '', null, '1', '1700', '卡证办理', '0');
INSERT INTO `yy_ls` VALUES ('779580899d39453d9e96f12474c3f43a', 'o2bZjwb7HR42smu_6-7N5mqrRJIQ', '20190905', '0900', '01', '1434ff8972714375b21b632c33013767', '18053117002', '刘兢', '420102198611221412', '', '0', '', null, '1', '0920', '离职手续办理', '0');
INSERT INTO `yy_ls` VALUES ('8a646d44a6c74709bf7c3e503665aa02', 'o2bZjwZD1OJYcDzVQJyKFOcNMkKQ', '20190910', '0900', '01', 'c02f13f0b54b41f0ac3ff2777281dff3', '18615230991', '王延威', '370406199002135019', '', '0', '', null, '1', '0920', '入职手续办理', '0');
INSERT INTO `yy_ls` VALUES ('8bfd3ba6d3c747cf99c083bc9ef53d3a', 'o2bZjwYzyqVCcIKlIMyzvM-PeZeQ', '20190905', '0900', '01', 'c02f13f0b54b41f0ac3ff2777281dff3', '15905414860', '张晓敏', '370112198609196017', '', '0', '', null, '0', '0920', '入职手续办理', '1');
INSERT INTO `yy_ls` VALUES ('9d5aaf9ce37c4826bec4ef4c170ec73a', 'o2bZjwRl9R45_wOw2KUDwbgFhQDU', '20190916', '1020', '01', '1434ff8972714375b21b632c33013767', '13021785178', '倪琳琳', '37082919971205464X', '', '0', '', null, '1', '1040', '离职手续办理', '0');
INSERT INTO `yy_ls` VALUES ('de0b356132364dd9bf9ca627bf55debf', 'o2bZjwcbEh3LvlpFRRr2XhBOBQRk', '20190918', '1640', '01', 'd636e89df7064a1ab7a83cee2e530d2c', '18253100211', '颜琳梓', '370102199001244529', '', '0', '', null, '1', '1700', '卡证办理', '0');

-- ----------------------------
-- Table structure for yy_msg
-- ----------------------------
DROP TABLE IF EXISTS `yy_msg`;
CREATE TABLE `yy_msg` (
  `id` varchar(35) NOT NULL,
  `msg` varchar(600) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yy_msg
-- ----------------------------
INSERT INTO `yy_msg` VALUES ('6a150db85f6045e4988862ad898af77e', 'wwwwwwwwwwwwwwwwwwww吧发');

-- ----------------------------
-- Table structure for yy_ywgl
-- ----------------------------
DROP TABLE IF EXISTS `yy_ywgl`;
CREATE TABLE `yy_ywgl` (
  `id` varchar(35) NOT NULL COMMENT 'uuid主键',
  `sort` int(11) DEFAULT NULL COMMENT '排序号',
  `content` varchar(1000) DEFAULT NULL COMMENT '业务内容',
  `uname` varchar(30) DEFAULT NULL COMMENT '业务名称',
  `parent_id` varchar(35) DEFAULT NULL COMMENT '父级id，用来标识当前自己属于哪个父级，父级默认是0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yy_ywgl
-- ----------------------------
INSERT INTO `yy_ywgl` VALUES ('0af987c81afd4d708a8b26ad83426e34', '2', '', '劳动合同续签', '1');
INSERT INTO `yy_ywgl` VALUES ('1', '1', '业务内容', '办理业务', '0');
INSERT INTO `yy_ywgl` VALUES ('1434ff8972714375b21b632c33013767', '3', '', '离职手续办理', '1');
INSERT INTO `yy_ywgl` VALUES ('c02f13f0b54b41f0ac3ff2777281dff3', '1', '', '入职手续办理', '1');
INSERT INTO `yy_ywgl` VALUES ('cb093d1d640b4e1a9e31f1c96b1e3214', '4', '', '档案调转', '1');
INSERT INTO `yy_ywgl` VALUES ('d503c9896bc84496a3ea4462693db22d', '5', '', '社保办理', '1');
INSERT INTO `yy_ywgl` VALUES ('d636e89df7064a1ab7a83cee2e530d2c', '6', '', '卡证办理', '1');

-- ----------------------------
-- Table structure for yy_yyjh
-- ----------------------------
DROP TABLE IF EXISTS `yy_yyjh`;
CREATE TABLE `yy_yyjh` (
  `id` varchar(35) NOT NULL COMMENT 'uuid主键',
  `kssj` varchar(6) DEFAULT NULL COMMENT '开始时间,HHMM',
  `jssj` varchar(6) DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yy_yyjh
-- ----------------------------
INSERT INTO `yy_yyjh` VALUES ('0227e83a00454da287bf02f3dcb85bc8', '0940', '1000');
INSERT INTO `yy_yyjh` VALUES ('0361ef95826b44f8bd868e5b1583b82d', '1500', '1520');
INSERT INTO `yy_yyjh` VALUES ('113eb4eb6cd6444893f728401ca0d59c', '1700', '1720');
INSERT INTO `yy_yyjh` VALUES ('19bf3640ac0b4e939235230378a859fe', '0920', '0940');
INSERT INTO `yy_yyjh` VALUES ('3c6fcaacd6a04379887f345108a0e9b7', '1720', '1730');
INSERT INTO `yy_yyjh` VALUES ('3ebea20034b0424c80b8fcc1b952288b', '1600', '1620');
INSERT INTO `yy_yyjh` VALUES ('3ef4f25ac06a4149b94e9df058ccbf0d', '1120', '1140');
INSERT INTO `yy_yyjh` VALUES ('56dcbdfe34c748c1a70beb2f4a6158a6', '1040', '1100');
INSERT INTO `yy_yyjh` VALUES ('5d72394ac7bd4c7db9676b479fe8ce41', '1420', '1440');
INSERT INTO `yy_yyjh` VALUES ('79ca23f863aa46849911b21a7ac0a0a3', '1140', '1200');
INSERT INTO `yy_yyjh` VALUES ('7b8ae41d69cf457caa1247ded836d839', '1000', '1020');
INSERT INTO `yy_yyjh` VALUES ('9089904aea234eef8f88522167ae2cc2', '1540', '1600');
INSERT INTO `yy_yyjh` VALUES ('b741c9a1dc124ea58054228626f2d2ea', '1020', '1040');
INSERT INTO `yy_yyjh` VALUES ('bb16ab71a0e44a948ae077fc90a9b5aa', '0900', '0920');
INSERT INTO `yy_yyjh` VALUES ('d7874e236f5844e784728500637ce09c', '1400', '1420');
INSERT INTO `yy_yyjh` VALUES ('ebdc154027bc4d88b67d4c41830e7e4d', '1520', '1540');
INSERT INTO `yy_yyjh` VALUES ('f69bed1b68054884bb0aaa88db476275', '1440', '1500');
INSERT INTO `yy_yyjh` VALUES ('f6c512f25ef44d6390dcaa529d3f3c91', '1640', '1700');
INSERT INTO `yy_yyjh` VALUES ('f7a8d1fcd040407596c27e0630158992', '1100', '1120');
INSERT INTO `yy_yyjh` VALUES ('ffb4d6e356dd475b9d19643e0b52822e', '1620', '1640');

-- ----------------------------
-- Table structure for yy_yysj
-- ----------------------------
DROP TABLE IF EXISTS `yy_yysj`;
CREATE TABLE `yy_yysj` (
  `id` varchar(35) NOT NULL COMMENT 'uuid主键',
  `kssj` varchar(4) DEFAULT NULL COMMENT '预约开始时间',
  `jssj` varchar(4) DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yy_yysj
-- ----------------------------

-- ----------------------------
-- Table structure for yy_yzm
-- ----------------------------
DROP TABLE IF EXISTS `yy_yzm`;
CREATE TABLE `yy_yzm` (
  `id` varchar(35) NOT NULL COMMENT 'uuid主键',
  `openid` varchar(55) DEFAULT NULL COMMENT '微信openid',
  `sjh` varchar(15) DEFAULT NULL COMMENT '手机号',
  `yzm` varchar(10) DEFAULT NULL COMMENT '验证码',
  `cjsj` varchar(15) DEFAULT NULL COMMENT '创建时间,YYYYMMDDHHMMSS',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of yy_yzm
-- ----------------------------
INSERT INTO `yy_yzm` VALUES ('457245c184e74dd08e34f35a2d3a68cf', '123', '15905414860', '6505', '20190828223520');
