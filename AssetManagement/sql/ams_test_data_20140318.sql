/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50153
Source Host           : localhost:3306
Source Database       : ams

Target Server Type    : MYSQL
Target Server Version : 50153
File Encoding         : 65001

Date: 2014-03-18 09:13:38
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `asset`
-- ----------------------------
DROP TABLE IF EXISTS `asset`;
CREATE TABLE `asset` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `asset_id` varchar(128) NOT NULL,
  `asset_name` varchar(128) NOT NULL,
  `bar_code` varchar(64) DEFAULT NULL,
  `check_in_time` datetime DEFAULT NULL,
  `check_out_time` datetime DEFAULT NULL,
  `entity` varchar(255) DEFAULT NULL,
  `fixed` bit(1) DEFAULT NULL,
  `keeper` varchar(2000) DEFAULT NULL,
  `manufacturer` varchar(128) DEFAULT NULL,
  `memo` varchar(1024) DEFAULT NULL,
  `owner_ship` varchar(255) DEFAULT NULL,
  `photo_path` varchar(256) DEFAULT NULL,
  `po_no` varchar(128) DEFAULT NULL,
  `series_no` varchar(256) DEFAULT NULL,
  `status` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL,
  `vendor` varchar(255) DEFAULT NULL,
  `warranty_time` datetime DEFAULT NULL,
  `customer_id` varchar(32) DEFAULT NULL,
  `location_id` varchar(32) DEFAULT NULL,
  `project_id` varchar(32) DEFAULT NULL,
  `software_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK58CEAF0D4296E1E` (`project_id`),
  KEY `FK58CEAF0B6C6BB16` (`location_id`),
  KEY `FK58CEAF05B697F5F` (`user_id`),
  KEY `FK58CEAF0DF0D5E76` (`customer_id`),
  KEY `FK58CEAF0AA111DD6` (`software_id`),
  CONSTRAINT `FK58CEAF05B697F5F` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK58CEAF0AA111DD6` FOREIGN KEY (`software_id`) REFERENCES `software` (`id`),
  CONSTRAINT `FK58CEAF0B6C6BB16` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK58CEAF0D4296E1E` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  CONSTRAINT `FK58CEAF0DF0D5E76` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of asset
-- ----------------------------
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7562000b0', '2014-02-26 11:32:09', '\0', '2014-02-26 11:32:09', 'Asset201400000', 'Desktop_Machine', '011010201000912', null, null, '群硕上海', '', '', 'Lenovo', '\\N', 'Augmentum', null, '', '', 'AVAILABLE', 'MACHINE', '', null, '4028960f446dee5101446df753af00ad', '4028960f43c2157f0143c2170a15000f', '4028960f446dee5101446df754d800ae', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7584300b2', '2014-02-26 11:32:10', '\0', '2014-02-26 11:32:10', 'Asset201400001', 'Desktop_Machine', '011010201000516', null, null, '群硕上海', '', '', 'Lenovo', '2GB RAM', 'Augmentum', null, '', '', 'AVAILABLE', 'MACHINE', '', null, '4028960f446dee5101446df753af00ad', 'ff7489e5c42011e299cafa163e30ed85', '4028960f446dee5101446df754d800ae', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df75a5600b4', '2014-02-26 11:32:10', '\0', '2014-02-26 11:32:10', 'Asset201400002', 'Desktop_Machine', '011010201000499', null, null, '群硕上海', '', '', 'Lenovo', '2G Memory', 'Augmentum', null, '', '', 'AVAILABLE', 'MACHINE', '', null, '4028960f446dee5101446df753af00ad', 'ffa24127c42011e299cafa163e30ed85', '4028960f446dee5101446df754d800ae', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df75c4a00b6', '2014-02-26 11:32:11', '\0', '2014-02-26 11:32:11', 'Asset201400003', 'Desktop_Machine', '011010201000871', null, null, '群硕上海', '', '', 'Lenovo', '\\N', 'Augmentum', null, '', '', 'AVAILABLE', 'MACHINE', '', null, '4028960f446dee5101446df753af00ad', 'ff6bfe30c42011e299cafa163e30ed85', '4028960f446dee5101446df754d800ae', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df75f8600b8', '2014-02-26 11:32:12', '\0', '2014-02-26 11:32:12', 'Asset201400004', 'Desktop_Machine', '011010201000985', null, null, '群硕上海', '', '', 'Lenovo', 'SPDB,the memory change 2G', 'Augmentum', null, '', '', 'AVAILABLE', 'MACHINE', '', null, '4028960f446dee5101446df753af00ad', 'ffc3b2b4c42011e299cafa163e30ed85', '4028960f446dee5101446df754d800ae', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7618900ba', '2014-02-26 11:32:12', '\0', '2014-02-26 11:32:12', 'Asset201400005', 'Desktop_Machine', '011010201000378', null, null, '群硕上海', '', '', 'Lenovo', '\\N', 'Augmentum', null, '', '', 'AVAILABLE', 'MACHINE', '', null, '4028960f446dee5101446df753af00ad', '4028960f43c2157f0143c2170a15000f', '4028960f446dee5101446df754d800ae', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df764b600bc', '2014-02-26 11:32:13', '\0', '2014-02-26 11:32:13', 'Asset201400006', 'Desktop_Monitor', '011010202000294', null, null, 'Qunshuo Shanghai', '', '', 'Lenovo', '', 'Augmentum', null, '', '', 'AVAILABLE', 'MONITOR', '', null, '4028960f446dee5101446df753af00ad', 'ffb5a99dc42011e299cafa163e30ed85', null, null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df766aa00be', '2014-02-26 11:32:14', '\0', '2014-02-26 11:32:14', 'Asset201400007', 'Desktop_Monitor', '011010202000304', null, null, 'Qunshuo Shanghai', '', '', 'Lenovo', '', 'Augmentum', null, '', '', 'AVAILABLE', 'MONITOR', '', null, '4028960f446dee5101446df753af00ad', 'ff6858c7c42011e299cafa163e30ed85', null, null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df768bd00c0', '2014-02-26 11:32:14', '\0', '2014-02-26 11:32:14', 'Asset201400008', 'Desktop_Monitor', '011010202000085', null, null, 'Qunshuo Shanghai', '', '', 'Lenovo', '', 'Augmentum', null, '', '', 'AVAILABLE', 'MONITOR', '', null, '4028960f446dee5101446df753af00ad', 'ff6858c7c42011e299cafa163e30ed85', null, null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df76c0900c2', '2014-02-26 11:32:15', '\0', '2014-02-26 11:32:15', 'Asset201400009', 'Desktop_Monitor', '011010202000979', null, null, 'Qunshuo Shanghai', '', '', 'Lenovo', '', 'Augmentum', null, '', '', 'AVAILABLE', 'MONITOR', '', null, '4028960f446dee5101446df753af00ad', 'ff6858c7c42011e299cafa163e30ed85', null, null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df76dfd00c4', '2014-02-26 11:32:15', '\0', '2014-02-26 11:32:15', 'Asset201400010', 'Desktop_Monitor', '011010202000832', null, null, 'Qunshuo Shanghai', '', '', 'Lenovo', '', 'Augmentum', null, '', '', 'AVAILABLE', 'MONITOR', '', null, '4028960f446dee5101446df753af00ad', 'ffbde98cc42011e299cafa163e30ed85', null, null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7711a00c6', '2014-02-26 11:32:16', '\0', '2014-02-26 11:32:16', 'Asset201400011', 'Desktop_Monitor', '011010202000807', null, null, 'Qunshuo Shanghai', '', '', 'Lenovo', '', 'Augmentum', null, '', '', 'AVAILABLE', 'MONITOR', '', null, '4028960f446dee5101446df753af00ad', 'ff6858c7c42011e299cafa163e30ed85', null, null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7731d00c8', '2014-02-26 11:32:17', '\0', '2014-02-26 11:32:17', 'Asset201400012', 'Desktop_Monitor', '011010202000954', null, null, 'Qunshuo Shanghai', '', '', 'Lenovo', '', 'Augmentum', null, '', '', 'AVAILABLE', 'MONITOR', '', null, '4028960f446dee5101446df753af00ad', 'ffb5a99dc42011e299cafa163e30ed85', null, null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7775300ca', '2014-02-26 11:32:18', '\0', '2014-02-26 11:32:18', 'Asset201400013', 'Desktop_Monitor', '011010202000959', null, null, 'Qunshuo Shanghai', '', '', 'Viewsonic LCD', '17\'s LCD/server room', 'Augmentum', null, '', '', 'AVAILABLE', 'MONITOR', '', null, '4028960f446dee5101446df753af00ad', 'ffd195e6c42011e299cafa163e30ed85', null, null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df77a3200cd', '2014-02-26 11:32:19', '\0', '2014-02-26 11:32:19', 'Asset201400014', 'Rack for 10 Machines', '01101000056', '2007-07-23 00:00:00', null, 'Qunshuo Shanghai', '', '', 'NONE', 'Rack for 10 Machines', 'Charge to customer', null, '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446df753af00ad', '4028960f43c2157f0143c2173480002a', '4028960f446dee5101446df7794700cc', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df77cd200cf', '2014-02-26 11:32:19', '\0', '2014-02-26 11:32:19', 'Asset201400015', 'Memory', '01101000057', '2007-09-12 00:00:00', null, 'Qunshuo Shanghai', '\0', '', 'VDATA', 'DDR400 1G*16', 'Charge to customer', null, '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446df753af00ad', 'ffbde98cc42011e299cafa163e30ed85', '4028960f446dee5101446df7794700cc', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df77e6800d1', '2014-02-26 11:32:20', '\0', '2014-02-26 11:32:20', 'Asset201400016', 'Memory', '01101000058', '2007-09-12 00:00:00', null, 'Qunshuo Shanghai', '\0', '', 'VDATA', 'DDR400 1G*16', 'Charge to customer', null, '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446df753af00ad', 'ffbde98cc42011e299cafa163e30ed85', '4028960f446dee5101446df7794700cc', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7805c00d3', '2014-02-26 11:32:20', '\0', '2014-02-26 11:32:20', 'Asset201400017', 'Memory', '01101000059', '2007-09-12 00:00:00', null, 'Qunshuo Shanghai', '\0', '', 'VDATA', 'DDR400 1G*16', 'Charge to customer', null, '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446df753af00ad', 'ffbde98cc42011e299cafa163e30ed85', '4028960f446dee5101446df7794700cc', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7836900d5', '2014-02-26 11:32:21', '\0', '2014-02-26 11:32:21', 'Asset201400018', 'Memory', '01101000060', '2007-09-12 00:00:00', null, 'Qunshuo Shanghai', '\0', '', 'VDATA', 'DDR400 1G*16', 'Charge to customer', null, '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446df753af00ad', 'ffae89aec42011e299cafa163e30ed85', '4028960f446dee5101446df7794700cc', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7855d00d7', '2014-02-26 11:32:21', '\0', '2014-02-26 11:32:21', 'Asset201400019', 'Memory', '01101000061', '2007-09-12 00:00:00', null, 'Qunshuo Shanghai', '\0', '', 'VDATA', 'DDR400 1G*16', 'Charge to customer', null, '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446df753af00ad', 'ffae89aec42011e299cafa163e30ed85', '4028960f446dee5101446df7794700cc', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7875100d9', '2014-02-26 11:32:22', '\0', '2014-02-26 11:32:22', 'Asset201400020', 'Memory', '01101000062', '2007-09-12 00:00:00', null, 'Qunshuo Shanghai', '\0', '', 'VDATA', 'DDR400 1G*16 IN011010201000695', 'Charge to customer', null, '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446df753af00ad', 'ffbde98cc42011e299cafa163e30ed85', '4028960f446dee5101446df7794700cc', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7891600db', '2014-02-26 11:32:22', '\0', '2014-02-26 11:32:22', 'Asset201400021', 'Memory', '01101000063', '2007-09-12 00:00:00', null, 'Qunshuo Shanghai', '\0', '', 'VDATA', 'DDR400 1G*16 IN011010201000695', 'Charge to customer', null, '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446df753af00ad', 'ffbde98cc42011e299cafa163e30ed85', '4028960f446dee5101446df7794700cc', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df78e3600dd', '2014-02-26 11:32:24', '\0', '2014-02-26 11:32:24', 'Asset201400022', 'Memory', '01101000064', '2007-09-12 00:00:00', null, 'Qunshuo Shanghai', '\0', '', 'VDATA', 'DDR400 1G*16', 'Charge to customer', null, '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446df753af00ad', 'ffae89aec42011e299cafa163e30ed85', '4028960f446dee5101446df7794700cc', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446df7900b00df', '2014-02-26 11:32:24', '\0', '2014-02-26 11:32:24', 'Asset201400023', 'Memory', '01101000065', '2007-09-12 00:00:00', null, 'Qunshuo Shanghai', '\0', '', 'VDATA', 'DDR400 1G*16', 'Charge to customer', null, '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446df753af00ad', 'ffae89aec42011e299cafa163e30ed85', '4028960f446dee5101446df7794700cc', null, '4028960f446dee5101446df7559300af');
INSERT INTO `asset` VALUES ('4028960f446dee5101446e02b9d300e2', '2014-02-26 11:44:36', '\0', '2014-02-26 13:33:35', 'Asset201400024', 'test', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f446dee5101446e0885d400e7', '2014-02-26 11:50:56', '\0', '2014-02-26 11:50:56', 'Asset201400025', 'test', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', 'test\r\n1\r\n2\r\n3\r\n4\r\n', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f446dee5101446e08869000e9', '2014-02-26 11:50:56', '\0', '2014-02-26 11:57:15', 'Asset201400025', 'test', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', 'test\n1\n2\n3\n4\n', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f447194e30144719d49c20000', '2014-02-27 04:32:17', '\0', '2014-02-27 04:32:17', 'Asset201400027', 'test_update_property_template', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4471a60d014471a6fb3b0000', '2014-02-27 04:42:52', '\0', '2014-02-27 04:42:52', 'Asset201400028', 'test2', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4471dca1014471e19fc70001', '2014-02-27 05:46:55', '\0', '2014-02-27 05:46:55', 'Asset201400029', 'test_software_info', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'SOFTWARE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, '4028960f4471dca1014471e19f980000', null);
INSERT INTO `asset` VALUES ('4028960f4471ed080144721bc61b0002', '2014-02-27 06:50:26', '\0', '2014-02-27 06:50:26', 'Asset201400030', 'test_software_info', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'SOFTWARE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, '4028960f4471ed080144721bc60c0001', null);
INSERT INTO `asset` VALUES ('4028960f44726569014472ca12570000', '2014-02-27 10:00:49', '\0', '2014-02-27 10:00:49', 'Asset201400031', 'test_fixed', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c4014472d7e44b0000', '2014-02-27 10:15:55', '\0', '2014-02-27 10:15:55', 'Asset201400032', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c40144731a91a50002', '2014-02-27 11:28:44', '\0', '2014-02-27 11:28:44', 'Asset201400033', 'test', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', 'test\r\n1\r\n2\r\n3\r\n4\r\n', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c40144731afcc10004', '2014-02-27 11:29:12', '\0', '2014-02-27 11:29:12', 'Asset201400034', 'test', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', 'test\r\n1\r\n2\r\n3\r\n4\r\n', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c4014473364d490006', '2014-02-27 11:59:02', '\0', '2014-02-27 11:59:02', 'Asset201400035', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c40144733a643e0008', '2014-02-27 12:03:30', '\0', '2014-02-27 12:03:30', 'Asset201400036', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c40144733b06f5000a', '2014-02-27 12:04:12', '\0', '2014-02-27 12:04:12', 'Asset201400037', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c40144733d5d33000c', '2014-02-27 12:06:45', '\0', '2014-02-27 12:06:45', 'Asset201400038', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c40144733e27d4000e', '2014-02-27 12:07:37', '\0', '2014-02-27 12:07:37', 'Asset201400039', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c40144733fa7c90010', '2014-02-27 12:09:15', '\0', '2014-02-27 12:09:15', 'Asset201400040', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c40144734119b50012', '2014-02-27 12:10:50', '\0', '2014-02-27 12:10:50', 'Asset201400041', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4472d5c401447342cf9e0014', '2014-02-27 12:12:42', '\0', '2014-02-27 12:12:42', 'Asset201400042', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b01447348ef0f0000', '2014-02-27 12:19:23', '\0', '2014-02-27 12:19:23', 'Asset201400043', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b01447349d2c60002', '2014-02-27 12:20:21', '\0', '2014-02-27 12:20:21', 'Asset201400044', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b01447349d3620004', '2014-02-27 12:20:21', '\0', '2014-02-27 12:20:21', 'Asset201400044', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144734a47d60006', '2014-02-27 12:20:51', '\0', '2014-02-27 12:20:51', 'Asset201400046', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144734a48ff0008', '2014-02-27 12:20:52', '\0', '2014-02-27 12:20:52', 'Asset201400046', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144734af72f000a', '2014-02-27 12:21:36', '\0', '2014-02-27 12:21:36', 'Asset201400048', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144734e065b000c', '2014-02-27 12:24:57', '\0', '2014-02-27 12:24:57', 'Asset201400049', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144734e07a3000e', '2014-02-27 12:24:57', '\0', '2014-02-27 12:24:57', 'Asset201400049', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b014473526b500010', '2014-02-27 12:29:45', '\0', '2014-02-27 12:29:45', 'Asset201400051', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b01447354908c0012', '2014-02-27 12:32:05', '\0', '2014-02-27 12:32:05', 'Asset201400051', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144735892150014', '2014-02-27 12:36:28', '\0', '2014-02-27 12:36:28', 'Asset201400053', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144735894a50016', '2014-02-27 12:36:28', '\0', '2014-02-27 12:36:28', 'Asset201400053', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144736910740018', '2014-02-27 12:54:29', '\0', '2014-02-27 12:54:29', 'Asset201400055', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144736b293c001a', '2014-02-27 12:56:46', '\0', '2014-02-27 12:56:46', 'Asset201400056', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144736d6924001c', '2014-02-27 12:59:14', '\0', '2014-02-27 12:59:14', 'Asset201400057', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b01447377109b001e', '2014-02-27 13:09:46', '\0', '2014-02-27 13:09:46', 'Asset201400058', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144737887b60020', '2014-02-27 13:11:22', '\0', '2014-02-27 13:11:22', 'Asset201400059', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144737c03760022', '2014-02-27 13:15:11', '\0', '2014-02-27 13:15:11', 'Asset201400060', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144738acca80024', '2014-02-27 13:31:20', '\0', '2014-02-27 13:31:20', 'Asset201400061', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144738b5ee40026', '2014-02-27 13:31:57', '\0', '2014-02-27 13:31:57', 'Asset201400062', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144738b624f0028', '2014-02-27 13:31:58', '\0', '2014-02-27 13:31:58', 'Asset201400061', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b0144738c1774002a', '2014-02-27 13:32:44', '\0', '2014-02-27 13:32:44', 'Asset201400064', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4473472b014473983d71002c', '2014-02-27 13:46:00', '\0', '2014-03-04 08:24:36', 'Asset201400065', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'RETURNED', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4485f67e014487c2caad0014', '2014-03-03 11:44:53', '\0', '2014-03-04 08:24:33', 'Asset201400066', 'test_fixed_3', '', null, null, '鹏智上海', '', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'RETURNED', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f4494c6350144950b59de00c6', '2014-03-06 01:39:12', '\0', '2014-03-06 01:39:12', 'Asset201400067', 'test_fixed_3', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'RETURNED', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', 'ff72ab71c42011e299cafa163e30ed85', null, null, null);
INSERT INTO `asset` VALUES ('4028960f44b54c0c0144b564ab0d0000', '2014-03-12 08:24:37', '\0', '2014-03-12 08:24:37', 'Asset201400068', 'new_dropdown', '', null, null, '鹏智上海', '\0', 'Raymond Bao;', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f44b567b00144b579c9d60000', '2014-03-12 08:47:41', '\0', '2014-03-12 08:47:41', 'Asset201400069', 'test_entity', '', null, null, '鹏智上海', '\0', 'Raymond Bao', '', '', 'UTCFS', '', '', '', 'AVAILABLE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f44b567b00144b57c4dbd0002', '2014-03-12 08:50:26', '\0', '2014-03-12 11:19:57', 'Asset201400069', '更帅', '', null, null, '鹏智上海', '\0', 'Raymond Bao', '', null, 'UTCFS', '', '', '', 'IN_USE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, '4028960f44b5da910144b5dc331b0000');
INSERT INTO `asset` VALUES ('4028960f44b583d00144b58d9bfd0001', '2014-03-12 09:09:20', '\0', '2014-03-12 10:39:28', 'Asset201400071', 'test_entity', '', null, null, '群硕上海', '\0', 'Alden Xu; Ping Zhou; Pearson Liu', '', null, 'Wynn Resorts(Macau) S.A.', '', '', '', 'IN_USE', 'DEVICE', '', null, '4028960f44b583d00144b58d80370000', '4028960f43c2157f0143c21692160000', null, null, '4028960f44b5da910144b5dc331b0000');
INSERT INTO `asset` VALUES ('4028960f44b5da910144b60c2a2b0004', '2014-03-12 11:27:34', '\0', '2014-03-12 11:27:34', 'Asset201400072', '更帅_copy', '', null, null, '鹏智上海', '\0', 'Raymond Bao', '', '', 'UTCFS', '', '', '', 'IN_USE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, '4028960f44b5da910144b5dc331b0000');
INSERT INTO `asset` VALUES ('4028960f44b5da910144b60ea7f90006', '2014-03-12 11:30:17', '\0', '2014-03-12 11:30:17', 'Asset201400072', '更帅_copy', '', null, null, '鹏智上海', '\0', 'Raymond Bao', '', '', 'UTCFS', '', '', '', 'IN_USE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, '4028960f44b5da910144b5dc331b0000');
INSERT INTO `asset` VALUES ('4028960f44b5da910144b610a7030008', '2014-03-12 11:32:28', '\0', '2014-03-12 11:32:28', 'Asset201400072', '更帅_copy_1', '', null, null, '鹏智上海', '\0', 'Raymond Bao', '', '', 'UTCFS', '', '', '', 'IN_USE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, '4028960f44b5da910144b5dc331b0000');
INSERT INTO `asset` VALUES ('4028960f44b5da910144b613930b000a', '2014-03-12 11:35:40', '\0', '2014-03-12 11:35:40', 'Asset201400075', '更帅_copy3', '', null, null, '鹏智上海', '\0', 'Raymond Bao', '', '', 'UTCFS', '', '', '', 'IN_USE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, '4028960f44b5da910144b5dc331b0000');
INSERT INTO `asset` VALUES ('4028960f44b5da910144b616acf9000c', '2014-03-12 11:39:03', '\0', '2014-03-12 11:50:28', 'Asset201400076', '更帅_copy4', '', null, null, '鹏智上海', '', 'Raymond Bao', '', null, 'UTCFS', '', '', '', 'IN_USE', 'DEVICE', '', null, '4028960f446dee5101446e02b8ab00e1', '4028960f43c2157f0143c21692160000', null, null, '4028960f44b5da910144b5dc331b0000');
INSERT INTO `asset` VALUES ('4028960f44be5b7b0144bec423ea0000', '2014-03-14 04:05:29', '\0', '2014-03-14 04:05:29', 'Asset201400077', 'sdfsdfsddf', '', null, null, '群硕上海', '\0', 'Raymond Bao; Ping Zhou; Pearson Liu', '', '', '兴业银行', '', '', '', 'AVAILABLE', 'MONITOR', '', null, '4028960f44b583d00144b58d80370000', '4028960f43c2157f0143c21692160000', null, null, null);
INSERT INTO `asset` VALUES ('4028960f44be5b7b0144bec4308d0003', '2014-03-14 04:05:32', '\0', '2014-03-14 04:09:06', 'Asset201400077', 'sdfsdfsddf', '', null, null, '群硕上海', '\0', 'Raymond Bao; Ping Zhou; Pearson Liu', '', null, '兴业银行', '', '', '', 'IN_USE', 'MONITOR', '', null, '4028960f44b583d00144b58d80370000', '4028960f43c2157f0143c21692160000', null, null, '4028960f44968e740144969109d00000');
INSERT INTO `asset` VALUES ('4028960f44bec6130144bec861660003', '2014-03-14 04:10:07', '\0', '2014-03-14 04:10:07', 'Asset201400079', 'sdfsdfsddf', '', null, null, '群硕上海', '\0', 'Raymond Bao; Ping Zhou; Pearson Liu', '', '', '兴业银行', '', '', '', 'IN_USE', 'DEVICE', '', null, '4028960f44b583d00144b58d80370000', '4028960f43c2157f0143c21692160000', null, null, '4028960f44bec6130144bec85ffe0002');

-- ----------------------------
-- Table structure for `audit`
-- ----------------------------
DROP TABLE IF EXISTS `audit`;
CREATE TABLE `audit` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `status` bit(1) NOT NULL,
  `asset_id` varchar(32) DEFAULT NULL,
  `audit_file_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK58D9BDB61AEBBBE` (`asset_id`),
  KEY `FK58D9BDB29142780` (`audit_file_id`),
  CONSTRAINT `FK58D9BDB29142780` FOREIGN KEY (`audit_file_id`) REFERENCES `audit_file` (`id`),
  CONSTRAINT `FK58D9BDB61AEBBBE` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of audit
-- ----------------------------

-- ----------------------------
-- Table structure for `audit_file`
-- ----------------------------
DROP TABLE IF EXISTS `audit_file`;
CREATE TABLE `audit_file` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `complete` bit(1) NOT NULL DEFAULT b'0',
  `file_name` varchar(256) NOT NULL,
  `operation_time` datetime DEFAULT NULL,
  `employee_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK64DB43609AAA759C` (`employee_id`),
  CONSTRAINT `FK64DB43609AAA759C` FOREIGN KEY (`employee_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of audit_file
-- ----------------------------

-- ----------------------------
-- Table structure for `authority`
-- ----------------------------
DROP TABLE IF EXISTS `authority`;
CREATE TABLE `authority` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of authority
-- ----------------------------
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff20b0000', '2013-12-06 04:17:32', '\0', '2013-12-06 04:17:32', 'Asset:view_own');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff2e60001', '2013-12-06 04:17:32', '\0', '2013-12-06 04:17:32', 'Asset:view_project');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff3340002', '2013-12-06 04:17:33', '\0', '2013-12-06 04:17:33', 'Asset:export');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff3920003', '2013-12-06 04:17:32', '\0', '2013-12-06 04:17:32', 'Asset:apply_asset');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff4f90004', '2013-12-06 04:17:34', '\0', '2013-12-06 04:17:34', 'Asset:assign_asset');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff69f0005', '2013-12-06 04:17:34', '\0', '2013-12-06 04:17:34', 'Asset:check_in_asset');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff6fd0006', '2013-12-06 04:17:34', '\0', '2013-12-06 04:17:34', 'Asset:check_out_asset');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff75b0007', '2013-12-06 04:17:33', '\0', '2013-12-06 04:17:33', 'Asset:view_all');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff9000008', '2013-12-06 04:17:34', '\0', '2013-12-06 04:17:34', 'Asset:create');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff96e0009', '2013-12-06 04:17:34', '\0', '2013-12-06 04:17:34', 'Asset:check_inventory');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ff9cc000a', '2013-12-06 04:17:34', '\0', '2013-12-06 04:17:34', 'Asset:import_asset');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ffa29000b', '2013-12-06 04:17:33', '\0', '2013-12-06 04:17:33', 'TransferLog:*');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ffd75000c', '2013-12-06 04:17:34', '\0', '2013-12-06 04:17:34', 'OperationLog:*');
INSERT INTO `authority` VALUES ('4028961242c61fda0142c61ffde2000d', '2013-12-06 04:17:34', '\0', '2013-12-06 04:17:34', 'User:*');

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `customer_code` varchar(32) DEFAULT NULL,
  `customer_name` varchar(128) NOT NULL,
  `groupId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK24217FDEE4674D1F` (`groupId`),
  CONSTRAINT `FK24217FDEE4674D1F` FOREIGN KEY (`groupId`) REFERENCES `customer_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('4028960f446dee5101446df753af00ad', '2014-02-26 11:32:09', '\0', '2014-02-26 11:32:09', '00900', 'Augmentum', null);
INSERT INTO `customer` VALUES ('4028960f446dee5101446e02b8ab00e1', '2014-02-26 11:44:36', '\0', '2014-03-14 13:34:04', '02400', 'UTCFS', '4028960f44c089f40144c0ccb4640001');
INSERT INTO `customer` VALUES ('4028960f44b583d00144b58d80370000', '2014-03-12 09:09:13', '\0', '2014-03-12 09:09:13', '02460', '兴业银行', null);
INSERT INTO `customer` VALUES ('4028960f44ba714a0144ba7292a70000', '2014-03-13 07:57:54', '\0', '2014-03-13 07:57:54', '00100', 'Internal Systems', null);
INSERT INTO `customer` VALUES ('4028960f44c089f40144c0ccae490000', '2014-03-14 13:34:03', '\0', '2014-03-14 13:34:04', '03600', 'UTCFS Carrier R&D SH', '4028960f44c089f40144c0ccb4640001');

-- ----------------------------
-- Table structure for `customer_group`
-- ----------------------------
DROP TABLE IF EXISTS `customer_group`;
CREATE TABLE `customer_group` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `group_name` varchar(128) NOT NULL,
  `process_type` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer_group
-- ----------------------------
INSERT INTO `customer_group` VALUES ('4028960f44c089f40144c0ccb4640001', '2014-03-14 13:34:04', '\0', '2014-03-14 13:34:04', 'test', 'test', 'SHARED');

-- ----------------------------
-- Table structure for `customized_filter`
-- ----------------------------
DROP TABLE IF EXISTS `customized_filter`;
CREATE TABLE `customized_filter` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `column_en` varchar(32) NOT NULL,
  `column_zh` varchar(32) NOT NULL,
  `sort_name` varchar(32) NOT NULL,
  `type` varchar(32) NOT NULL,
  `value` varchar(512) DEFAULT NULL,
  `creator_Id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9619E8F6B665835E` (`creator_Id`),
  CONSTRAINT `FK9619E8F6B665835E` FOREIGN KEY (`creator_Id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customized_filter
-- ----------------------------

-- ----------------------------
-- Table structure for `customized_property`
-- ----------------------------
DROP TABLE IF EXISTS `customized_property`;
CREATE TABLE `customized_property` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `value` varchar(256) DEFAULT NULL,
  `asset_id` varchar(32) NOT NULL,
  `property_template_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK670D93D361AEBBBE` (`asset_id`),
  KEY `FK670D93D31DFBBF94` (`property_template_id`),
  CONSTRAINT `FK670D93D31DFBBF94` FOREIGN KEY (`property_template_id`) REFERENCES `property_template` (`id`),
  CONSTRAINT `FK670D93D361AEBBBE` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customized_property
-- ----------------------------
INSERT INTO `customized_property` VALUES ('4028960f446dee5101446e060cbd00e6', '2014-02-26 11:48:14', '', '2014-02-26 13:33:45', 'test_property_change', '4028960f446dee5101446e02b9d300e2', '4028960f446dee5101446e05db1f00e5');
INSERT INTO `customized_property` VALUES ('4028960f446e3dd201446e45afd20001', '2014-02-26 12:57:44', '\0', '2014-02-27 04:28:30', '2014-02-27', '4028960f446dee5101446e08869000e9', '4028960f446e3dd201446e3f2ae30000');
INSERT INTO `customized_property` VALUES ('4028960f446e577201446e58e5df0000', '2014-02-26 13:18:43', '\0', '2014-02-27 04:28:33', '2014-02-27', '4028960f446dee5101446e02b9d300e2', '4028960f446e3dd201446e3f2ae30000');
INSERT INTO `customized_property` VALUES ('4028960f447194e30144719d74ba0002', '2014-02-27 04:32:28', '\0', '2014-02-27 04:32:28', '2014-02-27', '4028960f447194e30144719d49c20000', '4028960f446e3dd201446e3f2ae30000');
INSERT INTO `customized_property` VALUES ('4028960f44b5da910144b605894d0003', '2014-03-12 11:20:20', '\0', '2014-03-12 11:20:20', '2014-02-28', '4028960f44b567b00144b57c4dbd0002', '4028960f446e3dd201446e3f2ae30000');
INSERT INTO `customized_property` VALUES ('4028960f44b5da910144b6213eea000e', '2014-03-12 11:50:36', '\0', '2014-03-12 11:50:36', '2014-02-27', '4028960f44b5da910144b616acf9000c', '4028960f446e3dd201446e3f2ae30000');

-- ----------------------------
-- Table structure for `customized_view`
-- ----------------------------
DROP TABLE IF EXISTS `customized_view`;
CREATE TABLE `customized_view` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `creator_id` varchar(32) DEFAULT NULL,
  `creator_name` varchar(32) DEFAULT NULL,
  `operators` varchar(32) NOT NULL,
  `view_name` varchar(32) NOT NULL,
  `category_type` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customized_view
-- ----------------------------
INSERT INTO `customized_view` VALUES ('4028960f4485f67e014487b47907000e', '2014-03-03 11:29:15', '\0', '2014-03-03 11:34:57', 'T00690', 'Raymond Bao', 'and', 'test_new_table', 'asset');
INSERT INTO `customized_view` VALUES ('4028960f448bd98f01448bdd1b200000', '2014-03-04 06:52:07', '\0', '2014-03-04 06:52:07', 'T00690', 'Raymond Bao', 'and', 'test_category', 'asset');
INSERT INTO `customized_view` VALUES ('4028960f448c905701448c9508290000', '2014-03-04 10:13:01', '\0', '2014-03-04 10:13:01', 'T00690', 'Raymond Bao', 'and', 'adf', 'operation log');
INSERT INTO `customized_view` VALUES ('4028960f448fa094014490082a9a0000', '2014-03-05 02:17:38', '\0', '2014-03-05 02:17:38', 'T00690', 'Raymond Bao', 'and', 'test_error', 'asset');
INSERT INTO `customized_view` VALUES ('4028960f448fa0940144900f1c050002', '2014-03-05 02:25:13', '\0', '2014-03-05 02:25:13', 'T00690', 'Raymond Bao', 'and', 'test_lowcase', 'asset');
INSERT INTO `customized_view` VALUES ('4028960f44912b8701449133ae3a0000', '2014-03-05 07:44:47', '\0', '2014-03-05 07:44:47', 'T00690', 'Raymond Bao', 'and', 'test_redirect', 'asset');
INSERT INTO `customized_view` VALUES ('4028960f449145000144914949d80000', '2014-03-05 08:08:23', '\0', '2014-03-05 08:08:23', 'T00690', 'Raymond Bao', 'and', 'test_redirect_2', 'asset');
INSERT INTO `customized_view` VALUES ('4028960f44917ddf014491832fae0000', '2014-03-05 09:11:37', '\0', '2014-03-05 09:11:37', 'T00690', 'Raymond Bao', 'and', 'test_prepage', 'operation log');
INSERT INTO `customized_view` VALUES ('4028960f44bfadf20144bfe4d3030000', '2014-03-14 09:20:48', '\0', '2014-03-14 09:22:33', 'T00690', 'Raymond Bao', 'and', 'testdropdown', 'asset');

-- ----------------------------
-- Table structure for `customized_view_item`
-- ----------------------------
DROP TABLE IF EXISTS `customized_view_item`;
CREATE TABLE `customized_view_item` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `column_name` varchar(32) DEFAULT NULL,
  `column_type` varchar(32) DEFAULT NULL,
  `real_table` varchar(80) DEFAULT NULL,
  `search_column` varchar(80) DEFAULT NULL,
  `search_condition` varchar(32) DEFAULT NULL,
  `value` varchar(64) DEFAULT NULL,
  `customized_view_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK47EFE22FBE0EFC4C` (`customized_view_id`),
  CONSTRAINT `FK47EFE22FBE0EFC4C` FOREIGN KEY (`customized_view_id`) REFERENCES `customized_view` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customized_view_item
-- ----------------------------
INSERT INTO `customized_view_item` VALUES ('4028960f4485f67e014487b47c43000f', '2014-03-03 11:29:16', '\0', '2014-03-03 11:34:57', 'Asset Id', 'string', 'Asset', 'assetId', 'is', 'Asset201400000', '4028960f4485f67e014487b47907000e');
INSERT INTO `customized_view_item` VALUES ('4028960f4485f67e014487b47c430010', '2014-03-03 11:29:16', '\0', '2014-03-03 11:34:57', 'Status', 'string', 'Asset', 'status', 'is', 'AVAILABLE', '4028960f4485f67e014487b47907000e');
INSERT INTO `customized_view_item` VALUES ('4028960f4485f67e014487b47c430011', '2014-03-03 11:29:16', '\0', '2014-03-03 11:34:57', 'Type', 'string', 'Asset', 'type', 'is', 'DEVICE', '4028960f4485f67e014487b47907000e');
INSERT INTO `customized_view_item` VALUES ('4028960f4485f67e014487b47c430012', '2014-03-03 11:29:16', '\0', '2014-03-03 11:34:57', 'FixedAssets', 'boolean', 'Asset', 'fixed', 'is', 'true', '4028960f4485f67e014487b47907000e');
INSERT INTO `customized_view_item` VALUES ('4028960f4485f67e014487b47c430013', '2014-03-03 11:29:16', '\0', '2014-03-03 11:34:57', 'Check-in Time', 'date', 'Asset', 'checkInTime', 'greater than', '2014-03-22', '4028960f4485f67e014487b47907000e');
INSERT INTO `customized_view_item` VALUES ('4028960f448bd98f01448bdd1b6e0001', '2014-03-04 06:52:07', '\0', '2014-03-04 06:52:07', 'Asset Id', 'string', 'Asset', 'assetId', 'is', 'Asset201400000', '4028960f448bd98f01448bdd1b200000');
INSERT INTO `customized_view_item` VALUES ('4028960f448c905701448c9508770001', '2014-03-04 10:13:01', '\0', '2014-03-04 10:13:01', 'Operator', 'string', 'OperationLog', 'operatorName', 'is', 'sdfs', '4028960f448c905701448c9508290000');
INSERT INTO `customized_view_item` VALUES ('4028960f448fa094014490082ae80001', '2014-03-05 02:17:38', '\0', '2014-03-05 02:17:38', 'Customer', 'string', 'Customer', 'customer.customerName', 'is', 'UTCFS', '4028960f448fa094014490082a9a0000');
INSERT INTO `customized_view_item` VALUES ('4028960f448fa0940144900f1c240003', '2014-03-05 02:25:13', '\0', '2014-03-05 02:25:13', 'Asset Name', 'string', 'Asset', 'assetName', 'is', 'test_fixed_3', '4028960f448fa0940144900f1c050002');
INSERT INTO `customized_view_item` VALUES ('4028960f44912b8701449133aea70001', '2014-03-05 07:44:47', '\0', '2014-03-05 07:44:47', 'Asset Name', 'string', 'Asset', 'assetName', 'contains', 'Desktop_Machine', '4028960f44912b8701449133ae3a0000');
INSERT INTO `customized_view_item` VALUES ('4028960f44914500014491494a550001', '2014-03-05 08:08:23', '\0', '2014-03-05 08:08:23', 'Asset Id', 'string', 'Asset', 'assetId', 'is', 'Asset201400000', '4028960f449145000144914949d80000');
INSERT INTO `customized_view_item` VALUES ('4028960f44917ddf01449183300c0001', '2014-03-05 09:11:37', '\0', '2014-03-05 09:11:37', 'Operator', 'string', 'OperationLog', 'operatorName', 'is', 'aaa', '4028960f44917ddf014491832fae0000');
INSERT INTO `customized_view_item` VALUES ('4028960f44bfadf20144bfe4d3410001', '2014-03-14 09:20:48', '\0', '2014-03-14 09:22:33', 'Asset Id', 'string', 'Asset', 'assetId', 'not contains', 'Asset201400002', '4028960f44bfadf20144bfe4d3030000');
INSERT INTO `customized_view_item` VALUES ('4028960f44bfadf20144bfe4d3410002', '2014-03-14 09:20:48', '\0', '2014-03-14 09:22:33', 'Status', 'string', 'Asset', 'status', 'is not', 'IN_USE', '4028960f44bfadf20144bfe4d3030000');
INSERT INTO `customized_view_item` VALUES ('4028960f44bfadf20144bfe4d3410003', '2014-03-14 09:20:48', '', '2014-03-14 09:22:33', 'Type', 'string', 'Asset', 'type', 'is', 'MONITOR', '4028960f44bfadf20144bfe4d3030000');
INSERT INTO `customized_view_item` VALUES ('4028960f44bfadf20144bfe4d3410004', '2014-03-14 09:20:48', '\0', '2014-03-14 09:22:33', 'FixedAssets', 'boolean', 'Asset', 'fixed', 'is', 'true', '4028960f44bfadf20144bfe4d3030000');
INSERT INTO `customized_view_item` VALUES ('4028960f44bfadf20144bfe625230005', '2014-03-14 09:22:14', '\0', '2014-03-14 09:22:33', 'Check-in Time', 'date', 'Asset', 'checkInTime', 'greater than', '2014-03-12', '4028960f44bfadf20144bfe4d3030000');

-- ----------------------------
-- Table structure for `custom_column`
-- ----------------------------
DROP TABLE IF EXISTS `custom_column`;
CREATE TABLE `custom_column` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `category_type` varchar(32) DEFAULT NULL,
  `column_type` varchar(8) NOT NULL,
  `en_name` varchar(32) DEFAULT NULL,
  `is_must_show` bit(1) DEFAULT NULL,
  `real_Table` varchar(20) NOT NULL,
  `search_column` varchar(80) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  `sort_name` varchar(32) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `zh_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of custom_column
-- ----------------------------
INSERT INTO `custom_column` VALUES ('1001', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'group', 'string', 'Name', '', 'CustomerGroup', 'groupName', '1', 'name', '100', '组名');
INSERT INTO `custom_column` VALUES ('1002', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'group', 'string', 'Description', '', 'CustomerGroup', 'description', '2', 'description', '150', '描述');
INSERT INTO `custom_column` VALUES ('1003', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'group', 'boolean', 'Share', '', 'CustomerGroup', 'share', '3', 'share', '100', '共享');
INSERT INTO `custom_column` VALUES ('1004', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'group', 'string', 'Customer Name', '', 'Customer', 'customer.customerName', '4', 'customerName', '400', '客户');
INSERT INTO `custom_column` VALUES ('1005', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'group', 'string', 'Operation', '', 'CustomerGroup', 'operation', '5', 'operation', '100', '操作');
INSERT INTO `custom_column` VALUES ('101', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Asset Id', '', 'Asset', 'assetId', '1', 'assetId', '100', '资产编号');
INSERT INTO `custom_column` VALUES ('102', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Asset Name', '', 'Asset', 'assetName', '2', 'assetName', '100', '资产名称');
INSERT INTO `custom_column` VALUES ('103', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Asset User', '\0', 'User', 'user.userName', '4', 'user', '100', '用户名称');
INSERT INTO `custom_column` VALUES ('104', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Asset Keeper', '\0', 'Asset', 'keeper', '5', 'keeper', '100', '负责人');
INSERT INTO `custom_column` VALUES ('105', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Status', '', 'Asset', 'status', '3', 'status', '150', '状态');
INSERT INTO `custom_column` VALUES ('106', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Customer', '\0', 'Customer', 'customer.customerName', '6', 'customer', '130', '客户');
INSERT INTO `custom_column` VALUES ('107', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Project', '\0', 'Project', 'project.projectName', '7', 'project', '100', '项目');
INSERT INTO `custom_column` VALUES ('108', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Type', '\0', 'Asset', 'type', '8', 'type', '100', '类型');
INSERT INTO `custom_column` VALUES ('109', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Bar-code', '\0', 'Asset', 'barCode', '9', 'barCode', '120', '条形码');
INSERT INTO `custom_column` VALUES ('110', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Location', '\0', 'Location', 'location.site', '10', 'location', '200', '位置');
INSERT INTO `custom_column` VALUES ('1101', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'string', 'Asset Id', '', 'Asset', 'asset.assetId', '1', 'asset.assetId', '100', '资产编号');
INSERT INTO `custom_column` VALUES ('1102', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'string', 'Asset Name', '', 'Asset', 'asset.assetName', '2', 'asset.assetName', '100', '资产名称');
INSERT INTO `custom_column` VALUES ('1103', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'string', 'Customer', '\0', 'Customer', 'customer.customerName', '3', 'asset.customer', '150', '客户');
INSERT INTO `custom_column` VALUES ('1104', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'string', 'Project', '', 'Project', 'project.projectName', '4', 'asset.project', '150', '项目');
INSERT INTO `custom_column` VALUES ('1105', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'string', 'User', '\0', 'User', 'user.userName', '5', 'asset.user', '100', '用户');
INSERT INTO `custom_column` VALUES ('1106', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'string', 'Type', '\0', 'Asset', 'asset.type', '6', 'asset.type', '100', '类型');
INSERT INTO `custom_column` VALUES ('1107', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'string', 'Status', '', 'Asset', 'asset.status', '7', 'asset.status', '150', '状态');
INSERT INTO `custom_column` VALUES ('1108', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'date', 'ReturnedTime', '\0', 'ToDo', 'returned_time', '8', 'returnedTime', '200', '归还时间');
INSERT INTO `custom_column` VALUES ('1109', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'string', 'Returner', '\0', 'User', 'user.userName', '9', 'returner', '100', '归还者');
INSERT INTO `custom_column` VALUES ('111', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Manufacturer', '\0', 'Asset', 'manufacturer', '11', 'manufacturer', '200', '制造商');
INSERT INTO `custom_column` VALUES ('1110', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'date', 'ReceivedTime', '\0', 'ToDo', 'received_time', '10', 'receivedTime', '200', '分配时间');
INSERT INTO `custom_column` VALUES ('1111', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'todo', 'string', 'Receiver', '\0', 'User', 'user.userName', '11', 'assigner', '100', '分配者');
INSERT INTO `custom_column` VALUES ('112', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Ownership', '\0', 'Asset', 'ownerShip', '12', 'ownerShip', '200', '所有者');
INSERT INTO `custom_column` VALUES ('113', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'AugmentumEntity', '\0', 'Asset', 'entityName', '13', 'entity', '200', '公司');
INSERT INTO `custom_column` VALUES ('114', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Memo', '\0', 'Asset', 'memo', '14', 'memo', '200', '简介');
INSERT INTO `custom_column` VALUES ('115', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'boolean', 'FixedAssets', '\0', 'Asset', 'fixed', '15', 'fixed', '100', '固定资产');
INSERT INTO `custom_column` VALUES ('116', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Series_No', '\0', 'Asset', 'seriesNo', '16', 'seriesNo', '200', '序列号');
INSERT INTO `custom_column` VALUES ('117', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Po_No', '\0', 'Asset', 'poNo', '17', 'poNo', '200', '进货号');
INSERT INTO `custom_column` VALUES ('118', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'date', 'Check-in Time', '\0', 'Asset', 'checkInTime', '18', 'checkInTime', '120', '入库时间');
INSERT INTO `custom_column` VALUES ('119', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'date', 'Check-out Time', '\0', 'Asset', 'checkOutTime', '19', 'checkOutTime', '120', '出库时间');
INSERT INTO `custom_column` VALUES ('120', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'string', 'Vendor', '\0', 'Asset', 'vendor', '20', 'vendor', '200', '供应商');
INSERT INTO `custom_column` VALUES ('1201', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Asset Id', '', 'Asset', 'assetId', '1', 'asset.assetId', '100', '资产编号');
INSERT INTO `custom_column` VALUES ('1202', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Asset Name', '', 'Asset', 'assetName', '2', 'asset.assetName', '100', '资产名称');
INSERT INTO `custom_column` VALUES ('1203', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Asset User', '\0', 'User', 'user.userName', '9', 'asset.user', '100', '用户名称');
INSERT INTO `custom_column` VALUES ('1204', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Asset Keeper', '\0', 'Asset', 'keeper', '5', 'asset.keeper', '100', '负责人');
INSERT INTO `custom_column` VALUES ('1205', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Status', '', 'Asset', 'status', '3', 'asset.status', '150', '状态');
INSERT INTO `custom_column` VALUES ('1206', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Customer', '\0', 'Customer', 'customer.customerName', '6', 'asset.customer', '130', '客户');
INSERT INTO `custom_column` VALUES ('1207', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Project', '\0', 'Project', 'project.projectName', '7', 'asset.project', '100', '项目');
INSERT INTO `custom_column` VALUES ('1208', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Type', '\0', 'Asset', 'type', '8', 'asset.type', '100', '类型');
INSERT INTO `custom_column` VALUES ('1209', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Bar-code', '\0', 'Asset', 'barCode', '4', 'asset.barCode', '120', '条形码');
INSERT INTO `custom_column` VALUES ('121', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'asset', 'date', 'Warranty-Time', '\0', 'Asset', 'warrantyTime', '21', 'warrantyTime', '120', '保修期');
INSERT INTO `custom_column` VALUES ('1210', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Location', '\0', 'Location', 'location.site', '10', 'asset.location.site', '200', '位置');
INSERT INTO `custom_column` VALUES ('1211', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Manufacturer', '\0', 'Asset', 'manufacturer', '11', 'asset.manufacturer', '200', '制造商');
INSERT INTO `custom_column` VALUES ('1212', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Ownership', '\0', 'Asset', 'ownerShip', '12', 'asset.ownerShip', '200', '所有者');
INSERT INTO `custom_column` VALUES ('1213', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'AugmentumEntity', '\0', 'Asset', 'entityName', '13', 'asset.entityName', '200', '公司');
INSERT INTO `custom_column` VALUES ('1214', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Memo', '\0', 'Asset', 'memo', '14', 'asset.memo', '200', '简介');
INSERT INTO `custom_column` VALUES ('1215', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'boolean', 'FixedAssets', '\0', 'Asset', 'fixed', '15', 'asset.fixed', '100', '固定资产');
INSERT INTO `custom_column` VALUES ('1216', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Series_No', '\0', 'Asset', 'seriesNo', '16', 'asset.seriesNo', '200', '序列号');
INSERT INTO `custom_column` VALUES ('1217', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Po_No', '\0', 'Asset', 'poNo', '17', 'asset.poNo', '200', '进货号');
INSERT INTO `custom_column` VALUES ('1218', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'date', 'Check-in Time', '\0', 'Asset', 'checkInTime', '18', 'asset.checkInTime', '120', '入库时间');
INSERT INTO `custom_column` VALUES ('1219', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'date', 'Check-out Time', '\0', 'Asset', 'checkOutTime', '19', 'asset.checkOutTime', '120', '出库时间');
INSERT INTO `custom_column` VALUES ('1220', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'string', 'Vendor', '\0', 'Asset', 'vendor', '20', 'asset.vendor', '200', '供应商');
INSERT INTO `custom_column` VALUES ('1221', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'inconsistent', 'date', 'Warranty-Time', '\0', 'Asset', 'warrantyTime', '21', 'asset.warrantyTime', '120', '保修期');
INSERT INTO `custom_column` VALUES ('1301', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'user role', 'string', 'Employee ID', '', 'User', 'userId', '1', 'userId', '191', '员工编号');
INSERT INTO `custom_column` VALUES ('1302', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'user role', 'string', 'Employee Name', '', 'User', 'userName', '2', 'userName', '191', '员工姓名');
INSERT INTO `custom_column` VALUES ('1303', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'user role', 'string', 'IT', '', 'Asset', 'XXX', '3', 'role.it', '176', 'IT');
INSERT INTO `custom_column` VALUES ('1304', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'user role', 'string', 'System Admin', '', 'XXX', 'XXX', '4', 'role.systemAdmin', '176', '系统管理员');
INSERT INTO `custom_column` VALUES ('1305', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'user role', 'string', 'Remove', '', 'XXX', 'XXX', '5', 'delete', '176', '移除');
INSERT INTO `custom_column` VALUES ('201', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Id', '', 'Asset', 'asset.assetId', '1', 'assetId', '100', '资产编号');
INSERT INTO `custom_column` VALUES ('202', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Name', '', 'Asset', 'asset.assetName', '2', 'assetName', '100', '资产名称');
INSERT INTO `custom_column` VALUES ('203', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'User', '\0', 'User', 'asset.user.userName', '4', 'user', '100', '用户名称');
INSERT INTO `custom_column` VALUES ('204', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Keeper', '\0', 'Asset', 'asset.keeper', '5', 'keeper', '100', '负责人');
INSERT INTO `custom_column` VALUES ('205', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Status', '', 'Asset', 'asset.status', '3', 'status', '150', '状态');
INSERT INTO `custom_column` VALUES ('206', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Customer', '\0', 'Customer', 'asset.customer.customerName', '6', 'customer', '130', '客户');
INSERT INTO `custom_column` VALUES ('207', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Project', '\0', 'Project', 'asset.project.projectName', '7', 'project', '100', '项目');
INSERT INTO `custom_column` VALUES ('208', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Type', '\0', 'Asset', 'asset.type', '8', 'type', '100', '类型');
INSERT INTO `custom_column` VALUES ('209', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Bar-code', '\0', 'Asset', 'asset.barCode', '9', 'barCode', '120', '条形码');
INSERT INTO `custom_column` VALUES ('210', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Location', '\0', 'Location', 'asset.location.site', '10', 'location', '200', '位置');
INSERT INTO `custom_column` VALUES ('211', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Manufacturer', '\0', 'Asset', 'asset.manufacturer', '11', 'manufacturer', '200', '制造商');
INSERT INTO `custom_column` VALUES ('212', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Ownership', '\0', 'Asset', 'asset.ownerShip', '12', 'ownerShip', '200', '所有者');
INSERT INTO `custom_column` VALUES ('213', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'AugmentumEntity', '\0', 'Asset', 'asset.entityName', '13', 'entity', '200', '公司');
INSERT INTO `custom_column` VALUES ('214', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Memo', '\0', 'Asset', 'asset.memo', '14', 'memo', '200', '简介');
INSERT INTO `custom_column` VALUES ('215', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'boolean', 'FixedAssets', '\0', 'Asset', 'asset.fixed', '15', 'fixed', '100', '固定资产');
INSERT INTO `custom_column` VALUES ('216', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Series_No', '\0', 'Asset', 'asset.seriesNo', '16', 'seriesNo', '200', '序列号');
INSERT INTO `custom_column` VALUES ('217', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Po_No', '\0', 'Asset', 'asset.poNo', '17', 'poNo', '200', '进货号');
INSERT INTO `custom_column` VALUES ('218', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'date', 'Check-in Time', '\0', 'Asset', 'asset.checkInTime', '18', 'checkInTime', '120', '入库时间');
INSERT INTO `custom_column` VALUES ('219', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'date', 'Check-out Time', '\0', 'Asset', 'asset.checkOutTime', '19', 'checkOutTime', '120', '出库时间');
INSERT INTO `custom_column` VALUES ('220', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Vendor', '\0', 'Asset', 'asset.vendor', '20', 'vendor', '200', '供应商');
INSERT INTO `custom_column` VALUES ('221', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'date', 'Warranty-Time', '\0', 'Asset', 'asset.warrantyTime', '21', 'warrantyTime', '120', '保修期');
INSERT INTO `custom_column` VALUES ('222', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Sub-Type', '\0', 'Machine', 'subTypeName', '22', 'machine.subtype', '120', '子类型');
INSERT INTO `custom_column` VALUES ('223', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Specification', '\0', 'Machine', 'specification', '23', 'machine.specification', '200', '规格');
INSERT INTO `custom_column` VALUES ('224', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Address', '\0', 'Machine', 'address', '24', 'machine.address', '200', '网卡地址');
INSERT INTO `custom_column` VALUES ('225', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'machine', 'string', 'Configuration', '\0', 'Machine', 'configuration', '25', 'machine.configuration', '200', '附加配置');
INSERT INTO `custom_column` VALUES ('301', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Id', '', 'Asset', 'asset.assetId', '1', 'assetId', '100', '资产编号');
INSERT INTO `custom_column` VALUES ('302', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Name', '', 'Asset', 'asset.assetName', '2', 'assetName', '100', '资产名称');
INSERT INTO `custom_column` VALUES ('303', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'User', '\0', 'User', 'asset.user.userName', '4', 'user', '100', '用户名称');
INSERT INTO `custom_column` VALUES ('304', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Keeper', '\0', 'Asset', 'asset.keeper', '5', 'keeper', '100', '负责人');
INSERT INTO `custom_column` VALUES ('305', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Status', '', 'Asset', 'asset.status', '3', 'status', '150', '状态');
INSERT INTO `custom_column` VALUES ('306', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Customer', '\0', 'Customer', 'asset.customer.customerName', '6', 'customer', '130', '客户');
INSERT INTO `custom_column` VALUES ('307', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Project', '\0', 'Project', 'asset.project.projectName', '7', 'project', '100', '项目');
INSERT INTO `custom_column` VALUES ('308', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Type', '\0', 'Asset', 'asset.type', '8', 'type', '100', '类型');
INSERT INTO `custom_column` VALUES ('309', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Bar-code', '\0', 'Asset', 'asset.barCode', '9', 'barCode', '120', '条形码');
INSERT INTO `custom_column` VALUES ('310', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Location', '\0', 'Location', 'asset.location.site', '10', 'location', '200', '位置');
INSERT INTO `custom_column` VALUES ('311', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Manufacturer', '\0', 'Asset', 'asset.manufacturer', '11', 'manufacturer', '200', '制造商');
INSERT INTO `custom_column` VALUES ('312', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Ownership', '\0', 'Asset', 'asset.ownerShip', '12', 'ownerShip', '200', '所有者');
INSERT INTO `custom_column` VALUES ('313', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'AugmentumEntity', '\0', 'Asset', 'asset.entityName', '13', 'entity', '200', '公司');
INSERT INTO `custom_column` VALUES ('314', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Memo', '\0', 'Asset', 'asset.memo', '14', 'memo', '200', '简介');
INSERT INTO `custom_column` VALUES ('315', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'boolean', 'FixedAssets', '\0', 'Asset', 'asset.fixed', '15', 'fixed', '100', '固定资产');
INSERT INTO `custom_column` VALUES ('316', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Series_No', '\0', 'Asset', 'asset.seriesNo', '16', 'seriesNo', '200', '序列号');
INSERT INTO `custom_column` VALUES ('317', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Po_No', '\0', 'Asset', 'asset.poNo', '17', 'poNo', '200', '进货号');
INSERT INTO `custom_column` VALUES ('318', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'date', 'Check-in Time', '\0', 'Asset', 'asset.checkInTime', '18', 'checkInTime', '120', '入库时间');
INSERT INTO `custom_column` VALUES ('319', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'date', 'Check-out Time', '\0', 'Asset', 'asset.checkOutTime', '19', 'checkOutTime', '120', '出库时间');
INSERT INTO `custom_column` VALUES ('320', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Vendor', '\0', 'Asset', 'asset.vendor', '20', 'vendor', '200', '供应商');
INSERT INTO `custom_column` VALUES ('321', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'date', 'Warranty-Time', '\0', 'Asset', 'asset.warrantyTime', '21', 'warrantyTime', '120', '保修期');
INSERT INTO `custom_column` VALUES ('322', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Size', '\0', 'Monitor', 'size', '22', 'monitor.size', '100', '尺寸');
INSERT INTO `custom_column` VALUES ('323', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'monitor', 'string', 'Detail-Conf', '\0', 'Monitor', 'detail', '23', 'monitor.detail', '200', '详细配置');
INSERT INTO `custom_column` VALUES ('401', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Id', '', 'Asset', 'asset.assetId', '1', 'assetId', '100', '资产编号');
INSERT INTO `custom_column` VALUES ('402', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Name', '', 'Asset', 'asset.assetName', '2', 'assetName', '100', '资产名称');
INSERT INTO `custom_column` VALUES ('403', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'User', '\0', 'User', 'asset.user.userName', '4', 'user', '100', '用户名称');
INSERT INTO `custom_column` VALUES ('404', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Keeper', '\0', 'Asset', 'asset.keeper', '5', 'keeper', '100', '负责人');
INSERT INTO `custom_column` VALUES ('405', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Status', '', 'Asset', 'asset.status', '3', 'status', '150', '状态');
INSERT INTO `custom_column` VALUES ('406', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Customer', '\0', 'Customer', 'asset.customer.customerName', '6', 'customer', '130', '客户');
INSERT INTO `custom_column` VALUES ('407', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Project', '\0', 'Project', 'seet.project.projectName', '7', 'project', '100', '项目');
INSERT INTO `custom_column` VALUES ('408', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Type', '\0', 'Asset', 'asset.type', '8', 'type', '100', '类型');
INSERT INTO `custom_column` VALUES ('409', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Bar-code', '\0', 'Asset', 'asset.barCode', '9', 'barCode', '120', '条形码');
INSERT INTO `custom_column` VALUES ('410', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Location', '\0', 'Location', 'asset.location.site', '10', 'location', '200', '位置');
INSERT INTO `custom_column` VALUES ('411', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Manufacturer', '\0', 'Asset', 'asset.manufacturer', '11', 'manufacturer', '200', '制造商');
INSERT INTO `custom_column` VALUES ('412', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Ownership', '\0', 'Asset', 'asset.ownerShip', '12', 'ownerShip', '200', '所有者');
INSERT INTO `custom_column` VALUES ('413', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'AugmentumEntity', '\0', 'Asset', 'asset.entityName', '13', 'entity', '200', '公司');
INSERT INTO `custom_column` VALUES ('414', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Memo', '\0', 'Asset', 'asset.memo', '14', 'memo', '200', '简介');
INSERT INTO `custom_column` VALUES ('415', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'boolean', 'FixedAssets', '\0', 'Asset', 'asset.fixed', '15', 'fixed', '100', '固定资产');
INSERT INTO `custom_column` VALUES ('416', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Series_No', '\0', 'Asset', 'asset.seriesNo', '16', 'seriesNo', '200', '序列号');
INSERT INTO `custom_column` VALUES ('417', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Po_No', '\0', 'Asset', 'asset.poNo', '17', 'poNo', '200', '进货号');
INSERT INTO `custom_column` VALUES ('418', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'date', 'Check-in Time', '\0', 'Asset', 'asset.checkInTime', '18', 'checkInTime', '120', '入库时间');
INSERT INTO `custom_column` VALUES ('419', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'date', 'Check-out Time', '\0', 'Asset', 'asset.checkOutTime', '19', 'checkOutTime', '120', '出库时间');
INSERT INTO `custom_column` VALUES ('420', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Vendor', '\0', 'Asset', 'asset.vendor', '20', 'vendor', '200', '供应商');
INSERT INTO `custom_column` VALUES ('421', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'date', 'Warranty-Time', '\0', 'Asset', 'asset.warrantyTime', '21', 'warrantyTime', '120', '保修期');
INSERT INTO `custom_column` VALUES ('422', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Sub-Type', '\0', 'DeviceSubtype', 'subTypeName', '22', 'device.deviceSubtype', '200', '子类型');
INSERT INTO `custom_column` VALUES ('423', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'device', 'string', 'Configuration', '\0', 'Device', 'configuration', '23', 'device.configuration', '200', '配置');
INSERT INTO `custom_column` VALUES ('501', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Id', '', 'Asset', 'asset.assetId', '1', 'assetId', '100', '资产编号');
INSERT INTO `custom_column` VALUES ('502', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Name', '', 'Asset', 'asset.assetName', '2', 'assetName', '100', '资产名称');
INSERT INTO `custom_column` VALUES ('503', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'User', '\0', 'User', 'asset.user.userName', '4', 'user', '100', '用户名称');
INSERT INTO `custom_column` VALUES ('504', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Keeper', '\0', 'Asset', 'asset.keeper', '5', 'keeper', '100', '负责人');
INSERT INTO `custom_column` VALUES ('505', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Status', '', 'Asset', 'asset.status', '3', 'status', '150', '状态');
INSERT INTO `custom_column` VALUES ('506', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Customer', '\0', 'Customer', 'asset.customer.customerName', '6', 'customer', '130', '客户');
INSERT INTO `custom_column` VALUES ('507', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Project', '\0', 'Project', 'asset.project.projectName', '7', 'project', '100', '项目');
INSERT INTO `custom_column` VALUES ('508', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Type', '\0', 'Asset', 'asset.type', '8', 'type', '100', '类型');
INSERT INTO `custom_column` VALUES ('509', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Bar-code', '\0', 'Asset', 'asset.barCode', '9', 'barCode', '120', '条形码');
INSERT INTO `custom_column` VALUES ('510', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Location', '\0', 'Location', 'asset.location.site', '10', 'location', '200', '位置');
INSERT INTO `custom_column` VALUES ('511', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Manufacturer', '\0', 'Asset', 'asset.manufacturer', '11', 'manufacturer', '200', '制造商');
INSERT INTO `custom_column` VALUES ('512', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Ownership', '\0', 'Asset', 'asset.ownerShip', '12', 'ownerShip', '200', '所有者');
INSERT INTO `custom_column` VALUES ('513', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'AugmentumEntity', '\0', 'Asset', 'asset.entityName', '13', 'entity', '200', '公司');
INSERT INTO `custom_column` VALUES ('514', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Memo', '\0', 'Asset', 'asset.memo', '14', 'memo', '200', '简介');
INSERT INTO `custom_column` VALUES ('515', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'boolean', 'FixedAssets', '\0', 'Asset', 'asset.fixed', '15', 'fixed', '100', '固定资产');
INSERT INTO `custom_column` VALUES ('516', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Series_No', '\0', 'Asset', 'asset.seriesNo', '16', 'seriesNo', '200', '序列号');
INSERT INTO `custom_column` VALUES ('517', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Po_No', '\0', 'Asset', 'asset.poNo', '17', 'poNo', '200', '进货号');
INSERT INTO `custom_column` VALUES ('518', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'date', 'Check-in Time', '\0', 'Asset', 'asset.checkInTime', '18', 'checkInTime', '120', '入库时间');
INSERT INTO `custom_column` VALUES ('519', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'date', 'Check-out Time', '\0', 'Asset', 'asset.checkOutTime', '19', 'checkOutTime', '120', '出库时间');
INSERT INTO `custom_column` VALUES ('520', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Vendor', '\0', 'Asset', 'asset.vendor', '20', 'vendor', '200', '供应商');
INSERT INTO `custom_column` VALUES ('521', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'date', 'Warranty-Time', '\0', 'Asset', 'asset.warrantyTime', '21', 'warrantyTime', '120', '保修期');
INSERT INTO `custom_column` VALUES ('522', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'otherassets', 'string', 'Configuration', '\0', 'OtherAssets', 'detail', '22', 'otherAssets.detail', '200', '详细配置');
INSERT INTO `custom_column` VALUES ('601', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Id', '', 'Asset', 'asset.assetId', '1', 'assetId', '100', '资产编号');
INSERT INTO `custom_column` VALUES ('602', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Name', '', 'Asset', 'asset.assetName', '2', 'assetName', '100', '资产名称');
INSERT INTO `custom_column` VALUES ('603', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'User', '\0', 'User', 'asset.user.userName', '4', 'user', '100', '用户名称');
INSERT INTO `custom_column` VALUES ('604', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Keeper', '\0', 'Asset', 'asset.keeper', '5', 'keeper', '100', '负责人');
INSERT INTO `custom_column` VALUES ('605', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Status', '', 'Asset', 'asset.status', '3', 'status', '150', '状态');
INSERT INTO `custom_column` VALUES ('606', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Customer', '\0', 'Customer', 'aset.customer.customerName', '6', 'customer', '130', '客户');
INSERT INTO `custom_column` VALUES ('607', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Project', '\0', 'Project', 'asset.project.projectName', '7', 'project', '100', '项目');
INSERT INTO `custom_column` VALUES ('608', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Type', '\0', 'Asset', 'asset.type', '8', 'type', '100', '类型');
INSERT INTO `custom_column` VALUES ('609', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Bar-code', '\0', 'Asset', 'asset.barCode', '9', 'barCode', '120', '条形码');
INSERT INTO `custom_column` VALUES ('610', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Location', '\0', 'Location', 'location.site', '10', 'location', '200', '位置');
INSERT INTO `custom_column` VALUES ('611', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Manufacturer', '\0', 'Asset', 'asset.manufacturer', '11', 'manufacturer', '200', '制造商');
INSERT INTO `custom_column` VALUES ('612', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Ownership', '\0', 'Asset', 'asset.ownerShip', '12', 'ownerShip', '200', '所有者');
INSERT INTO `custom_column` VALUES ('613', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'AugmentumEntity', '\0', 'Asset', 'asset.entityName', '13', 'entity', '200', '公司');
INSERT INTO `custom_column` VALUES ('614', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Memo', '\0', 'Asset', 'asset.memo', '14', 'memo', '200', '简介');
INSERT INTO `custom_column` VALUES ('615', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'boolean', 'FixedAssets', '\0', 'Asset', 'asset.fixed', '15', 'fixed', '100', '固定资产');
INSERT INTO `custom_column` VALUES ('616', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Series_No', '\0', 'Asset', 'asset.seriesNo', '16', 'seriesNo', '200', '序列号');
INSERT INTO `custom_column` VALUES ('617', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Po_No', '\0', 'Asset', 'asset.poNo', '17', 'poNo', '200', '进货号');
INSERT INTO `custom_column` VALUES ('618', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'date', 'Check-in Time', '\0', 'Asset', 'asset.checkInTime', '18', 'checkInTime', '120', '入库时间');
INSERT INTO `custom_column` VALUES ('619', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'date', 'Check-out Time', '\0', 'Asset', 'asset.checkOutTime', '19', 'checkOutTime', '120', '出库时间');
INSERT INTO `custom_column` VALUES ('620', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Vendor', '\0', 'Asset', 'asset.vendor', '20', 'vendor', '200', '供应商');
INSERT INTO `custom_column` VALUES ('621', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'date', 'Warranty-Time', '\0', 'Asset', 'asset.warrantyTime', '21', 'warrantyTime', '120', '保修期');
INSERT INTO `custom_column` VALUES ('622', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Version', '\0', 'Software', 'version', '22', 'software.version', '120', '版本');
INSERT INTO `custom_column` VALUES ('623', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'License-Key', '\0', 'Software', 'licenseKey', '23', 'software.licenseKey', '200', '许可证书');
INSERT INTO `custom_column` VALUES ('624', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'software', 'string', 'Additional Info', '\0', 'Software', 'additionalInfo', '24', 'software.additionalInfo', '200', '附加信息');
INSERT INTO `custom_column` VALUES ('701', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'location', 'string', 'Site', '', 'Location', 'site', '1', 'site', '300', '地点');
INSERT INTO `custom_column` VALUES ('702', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'location', 'string', 'Room', '', 'Location', 'room', '2', 'room', '300', '房间');
INSERT INTO `custom_column` VALUES ('703', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'location', 'string', 'Operation', '', 'Location', 'operation', '3', 'operation', '300', '操作');
INSERT INTO `custom_column` VALUES ('801', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'operation log', 'string', 'Operator', '', 'OperationLog', 'operatorName', '1', 'operatorName', '100', '操作者姓名');
INSERT INTO `custom_column` VALUES ('802', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'operation log', 'string', 'Operator Id', '', 'OperationLog', 'operatorID', '2', 'operatorID', '100', '操作者ID');
INSERT INTO `custom_column` VALUES ('803', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'operation log', 'string', 'Operation', '', 'OperationLog', 'operation', '3', 'operation', '250', '操作');
INSERT INTO `custom_column` VALUES ('804', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'operation log', 'string', 'Operation Object', '', 'OperationLog', 'operationObject', '4', 'operationObject', '150', '操作对象');
INSERT INTO `custom_column` VALUES ('805', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'operation log', 'string', 'Operation Object Id', '', 'OperationLog', 'operationObjectID', '5', 'operationObjectID', '200', '操作对象ID');
INSERT INTO `custom_column` VALUES ('806', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'operation log', 'date', 'Time', '', 'OperationLog', 'updateTime', '6', 'createdTime', '120', '时间');
INSERT INTO `custom_column` VALUES ('901', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'transfer log', 'string', 'Asset Id', '', 'Asset', 'asset.assetId', '1', 'asset.assetId', '100', '资产编号');
INSERT INTO `custom_column` VALUES ('902', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'transfer log', 'string', 'Asset Name', '', 'Asset', 'asset.assetName', '2', 'asset.assetName', '100', '资产名称');
INSERT INTO `custom_column` VALUES ('903', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'transfer log', 'string', 'Operator', '', 'User', 'asset.userName', '3', 'user.userName', '100', '操作者');
INSERT INTO `custom_column` VALUES ('904', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'transfer log', 'date', 'Transfer Time', '', 'TransferLog', 'time', '4', 'time', '150', '变更时间');
INSERT INTO `custom_column` VALUES ('905', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'transfer log', 'string', 'Action', '', 'TransferLog', 'action', '5', 'action', '150', '动作');
INSERT INTO `custom_column` VALUES ('906', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'transfer log', 'string', 'Manufacturer', '\0', 'Asset', 'asset.manufacturer', '6', 'asset.manufacturer', '150', '制造商');
INSERT INTO `custom_column` VALUES ('907', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'transfer log', 'string', 'Bar-code', '\0', 'Asset', 'asset.barcode', '7', 'asset.barCode', '120', '条形码');
INSERT INTO `custom_column` VALUES ('908', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'transfer log', 'date', 'Check-in Time', '', 'Asset', 'asset.checkInTime', '8', 'asset.checkInTime', '100', '入库时间');
INSERT INTO `custom_column` VALUES ('909', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'transfer log', 'string', 'Series_No', '\0', 'Asset', 'asset.seriesNo', '9', 'asset.seriesNo', '110', '序列号');
INSERT INTO `custom_column` VALUES ('910', '2013-09-06 00:00:00', '\0', '2013-09-06 00:00:00', 'transfer log', 'string', 'Po_No', '\0', 'Asset', 'asset.poNo', '10', 'asset.poNo', '100', '进货号');

-- ----------------------------
-- Table structure for `device`
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `configuration` varchar(32) DEFAULT NULL,
  `asset_id` varchar(32) DEFAULT NULL,
  `device_subtype_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `asset_id` (`asset_id`),
  KEY `FKB06B1E5661AEBBBE` (`asset_id`),
  KEY `FKB06B1E56CA46C5D1` (`device_subtype_id`),
  CONSTRAINT `FKB06B1E5661AEBBBE` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`),
  CONSTRAINT `FKB06B1E56CA46C5D1` FOREIGN KEY (`device_subtype_id`) REFERENCES `device_subtype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES ('4028960f446dee5101446df77b8900ce', '2014-02-26 11:32:19', '\0', '2014-02-26 11:32:19', '', '4028960f446dee5101446df77a3200cd', null);
INSERT INTO `device` VALUES ('4028960f446dee5101446df77d6e00d0', '2014-02-26 11:32:19', '\0', '2014-02-26 11:32:19', '', '4028960f446dee5101446df77cd200cf', null);
INSERT INTO `device` VALUES ('4028960f446dee5101446df77ef400d2', '2014-02-26 11:32:20', '\0', '2014-02-26 11:32:20', '', '4028960f446dee5101446df77e6800d1', null);
INSERT INTO `device` VALUES ('4028960f446dee5101446df7824000d4', '2014-02-26 11:32:21', '\0', '2014-02-26 11:32:21', '', '4028960f446dee5101446df7805c00d3', null);
INSERT INTO `device` VALUES ('4028960f446dee5101446df7840500d6', '2014-02-26 11:32:21', '\0', '2014-02-26 11:32:21', '', '4028960f446dee5101446df7836900d5', null);
INSERT INTO `device` VALUES ('4028960f446dee5101446df785f900d8', '2014-02-26 11:32:22', '\0', '2014-02-26 11:32:22', '', '4028960f446dee5101446df7855d00d7', null);
INSERT INTO `device` VALUES ('4028960f446dee5101446df787ed00da', '2014-02-26 11:32:22', '\0', '2014-02-26 11:32:22', '', '4028960f446dee5101446df7875100d9', null);
INSERT INTO `device` VALUES ('4028960f446dee5101446df789b200dc', '2014-02-26 11:32:23', '\0', '2014-02-26 11:32:23', '', '4028960f446dee5101446df7891600db', null);
INSERT INTO `device` VALUES ('4028960f446dee5101446df78ed300de', '2014-02-26 11:32:24', '\0', '2014-02-26 11:32:24', '', '4028960f446dee5101446df78e3600dd', null);
INSERT INTO `device` VALUES ('4028960f446dee5101446df790b700e0', '2014-02-26 11:32:24', '\0', '2014-02-26 11:32:24', '', '4028960f446dee5101446df7900b00df', null);
INSERT INTO `device` VALUES ('4028960f446dee5101446e02ba0200e4', '2014-02-26 11:44:36', '\0', '2014-02-26 13:33:35', '', '4028960f446dee5101446e02b9d300e2', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f446dee5101446e0885e400e8', '2014-02-26 11:50:56', '\0', '2014-02-26 11:50:56', '', '4028960f446dee5101446e0885d400e7', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f446dee5101446e08869f00ea', '2014-02-26 11:50:56', '\0', '2014-02-26 11:57:15', '', '4028960f446dee5101446e08869000e9', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f447194e30144719d4a8d0001', '2014-02-27 04:32:17', '\0', '2014-02-27 04:32:17', '', '4028960f447194e30144719d49c20000', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4471a60d014471a6fb990001', '2014-02-27 04:42:52', '\0', '2014-02-27 04:42:52', '', '4028960f4471a60d014471a6fb3b0000', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44726569014472ca12c50001', '2014-02-27 10:00:49', '\0', '2014-02-27 10:00:49', '', '4028960f44726569014472ca12570000', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c4014472d7e5250001', '2014-02-27 10:15:55', '\0', '2014-02-27 10:15:55', '', '4028960f4472d5c4014472d7e44b0000', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c40144731a91b40003', '2014-02-27 11:28:44', '\0', '2014-02-27 11:28:44', '', '4028960f4472d5c40144731a91a50002', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c40144731afcd10005', '2014-02-27 11:29:12', '\0', '2014-02-27 11:29:12', '', '4028960f4472d5c40144731afcc10004', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c4014473364d590007', '2014-02-27 11:59:02', '\0', '2014-02-27 11:59:02', '', '4028960f4472d5c4014473364d490006', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c40144733a644e0009', '2014-02-27 12:03:30', '\0', '2014-02-27 12:03:30', '', '4028960f4472d5c40144733a643e0008', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c40144733b0704000b', '2014-02-27 12:04:12', '\0', '2014-02-27 12:04:12', '', '4028960f4472d5c40144733b06f5000a', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c40144733d5d43000d', '2014-02-27 12:06:45', '\0', '2014-02-27 12:06:45', '', '4028960f4472d5c40144733d5d33000c', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c40144733e27f3000f', '2014-02-27 12:07:37', '\0', '2014-02-27 12:07:37', '', '4028960f4472d5c40144733e27d4000e', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c40144733fa7e80011', '2014-02-27 12:09:15', '\0', '2014-02-27 12:09:15', '', '4028960f4472d5c40144733fa7c90010', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c40144734119c40013', '2014-02-27 12:10:50', '\0', '2014-02-27 12:10:50', '', '4028960f4472d5c40144734119b50012', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4472d5c401447342cfad0015', '2014-02-27 12:12:42', '\0', '2014-02-27 12:12:42', '', '4028960f4472d5c401447342cf9e0014', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b01447348ef9c0001', '2014-02-27 12:19:23', '\0', '2014-02-27 12:19:23', '', '4028960f4473472b01447348ef0f0000', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b01447349d2e50003', '2014-02-27 12:20:21', '\0', '2014-02-27 12:20:21', '', '4028960f4473472b01447349d2c60002', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b01447349d3720005', '2014-02-27 12:20:22', '\0', '2014-02-27 12:20:22', '', '4028960f4473472b01447349d3620004', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144734a48050007', '2014-02-27 12:20:51', '\0', '2014-02-27 12:20:51', '', '4028960f4473472b0144734a47d60006', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144734a491e0009', '2014-02-27 12:20:52', '\0', '2014-02-27 12:20:52', '', '4028960f4473472b0144734a48ff0008', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144734af73e000b', '2014-02-27 12:21:36', '\0', '2014-02-27 12:21:36', '', '4028960f4473472b0144734af72f000a', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144734e066a000d', '2014-02-27 12:24:57', '\0', '2014-02-27 12:24:57', '', '4028960f4473472b0144734e065b000c', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144734e07b3000f', '2014-02-27 12:24:57', '\0', '2014-02-27 12:24:57', '', '4028960f4473472b0144734e07a3000e', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b014473531ceb0011', '2014-02-27 12:30:30', '\0', '2014-02-27 12:30:30', '', '4028960f4473472b014473526b500010', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b01447354909b0013', '2014-02-27 12:32:05', '\0', '2014-02-27 12:32:05', '', '4028960f4473472b01447354908c0012', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144735892340015', '2014-02-27 12:36:28', '\0', '2014-02-27 12:36:28', '', '4028960f4473472b0144735892150014', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144735894b40017', '2014-02-27 12:36:28', '\0', '2014-02-27 12:36:28', '', '4028960f4473472b0144735894a50016', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144736910830019', '2014-02-27 12:54:29', '\0', '2014-02-27 12:54:29', '', '4028960f4473472b0144736910740018', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144736b294c001b', '2014-02-27 12:56:46', '\0', '2014-02-27 12:56:46', '', '4028960f4473472b0144736b293c001a', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144736d6934001d', '2014-02-27 12:59:14', '\0', '2014-02-27 12:59:14', '', '4028960f4473472b0144736d6924001c', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144737710f8001f', '2014-02-27 13:09:46', '\0', '2014-02-27 13:09:46', '', '4028960f4473472b01447377109b001e', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144737887c60021', '2014-02-27 13:11:22', '\0', '2014-02-27 13:11:22', '', '4028960f4473472b0144737887b60020', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144737c03860023', '2014-02-27 13:15:11', '\0', '2014-02-27 13:15:11', '', '4028960f4473472b0144737c03760022', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144738accb80025', '2014-02-27 13:31:20', '\0', '2014-02-27 13:31:20', '', '4028960f4473472b0144738acca80024', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144738b5ef40027', '2014-02-27 13:31:57', '\0', '2014-02-27 13:31:57', '', '4028960f4473472b0144738b5ee40026', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144738b625f0029', '2014-02-27 13:31:58', '\0', '2014-02-27 13:31:58', '', '4028960f4473472b0144738b624f0028', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b0144738c1784002b', '2014-02-27 13:32:44', '\0', '2014-02-27 13:32:44', '', '4028960f4473472b0144738c1774002a', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4473472b014473983d81002d', '2014-02-27 13:46:00', '\0', '2014-02-27 13:46:00', '', '4028960f4473472b014473983d71002c', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4485f67e014487c2caeb0015', '2014-03-03 11:44:53', '\0', '2014-03-03 11:44:53', '', '4028960f4485f67e014487c2caad0014', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f4494c6350144950b5a4b00c7', '2014-03-06 01:39:13', '\0', '2014-03-06 01:39:13', '', '4028960f4494c6350144950b59de00c6', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44b54c0c0144b564abc90001', '2014-03-12 08:24:37', '\0', '2014-03-12 08:24:37', '', '4028960f44b54c0c0144b564ab0d0000', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44b567b00144b579ca720001', '2014-03-12 08:47:41', '\0', '2014-03-12 08:47:41', '', '4028960f44b567b00144b579c9d60000', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44b567b00144b57c4ddd0003', '2014-03-12 08:50:26', '\0', '2014-03-12 11:19:57', '', '4028960f44b567b00144b57c4dbd0002', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44b583d00144b58d9cc80002', '2014-03-12 09:09:20', '\0', '2014-03-12 10:39:29', '', '4028960f44b583d00144b58d9bfd0001', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44b5da910144b60c2a4a0005', '2014-03-12 11:27:34', '\0', '2014-03-12 11:27:34', '', '4028960f44b5da910144b60c2a2b0004', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44b5da910144b60ea8090007', '2014-03-12 11:30:17', '\0', '2014-03-12 11:30:17', '', '4028960f44b5da910144b60ea7f90006', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44b5da910144b610a7ed0009', '2014-03-12 11:32:28', '\0', '2014-03-12 11:32:28', '', '4028960f44b5da910144b610a7030008', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44b5da910144b613931a000b', '2014-03-12 11:35:40', '\0', '2014-03-12 11:35:40', '', '4028960f44b5da910144b613930b000a', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44b5da910144b616ad09000d', '2014-03-12 11:39:03', '\0', '2014-03-12 11:50:28', '', '4028960f44b5da910144b616acf9000c', '4028960f446dee5101446e02b9f300e3');
INSERT INTO `device` VALUES ('4028960f44bec6130144bec862210005', '2014-03-14 04:10:07', '\0', '2014-03-14 04:10:07', '', '4028960f44bec6130144bec861660003', '4028960f446dee5101446e02b9f300e3');

-- ----------------------------
-- Table structure for `device_subtype`
-- ----------------------------
DROP TABLE IF EXISTS `device_subtype`;
CREATE TABLE `device_subtype` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `subtype_name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_subtype
-- ----------------------------
INSERT INTO `device_subtype` VALUES ('4028960f446dee5101446e02b9f300e3', '2014-02-26 11:44:36', '\0', '2014-02-26 11:44:36', '');

-- ----------------------------
-- Table structure for `inconsistent`
-- ----------------------------
DROP TABLE IF EXISTS `inconsistent`;
CREATE TABLE `inconsistent` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `bar_code` varchar(64) DEFAULT NULL,
  `asset_id` varchar(32) DEFAULT NULL,
  `audit_file_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK41A157761AEBBBE` (`asset_id`),
  KEY `FK41A157729142780` (`audit_file_id`),
  CONSTRAINT `FK41A157729142780` FOREIGN KEY (`audit_file_id`) REFERENCES `audit_file` (`id`),
  CONSTRAINT `FK41A157761AEBBBE` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inconsistent
-- ----------------------------

-- ----------------------------
-- Table structure for `location`
-- ----------------------------
DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `room` varchar(32) NOT NULL,
  `site` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of location
-- ----------------------------
INSERT INTO `location` VALUES ('00002b97c42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4205', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('0000a3dfc42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4206', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('00018f67c42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4207', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('000420bac42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4208', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('0006c724c42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4209', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('0009cff4c42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4210', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('000bbd1ac42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4302', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('00101481c42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4303', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('0010ac85c42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4304', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('0015c65ac42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4305', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('0018b46fc42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4306', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('00196924c42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4307', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('001fa950c42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4401', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('0022c328c42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4402', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('0024934ac42111e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4403', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('0029ab50c42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', 'A4404', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('002afd15c42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', 'A4405', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('002b4a18c42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', 'A4406', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('002e16c1c42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', 'A4407', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('002f536fc42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', 'A4408', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('002fc0dfc42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', '101', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('0031c1acc42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', '102', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('0032483fc42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', '103', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('003466ddc42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', '104', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('0034dcbbc42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', '105', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('0038283fc42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', '106', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('00396ae6c42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', '107', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('003bb867c42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', '108', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('003d9c6ec42111e299cafa163e30ed85', '2013-05-24 11:21:19', '\0', '2013-05-24 11:21:19', 'HR', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21692160000', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', '17201', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21692160001', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', 'A4309', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21692160002', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', '32101', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21692160003', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', '32102', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21692160004', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', '17202', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21692160005', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', '17203', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21692160006', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', '17204', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21692160007', '2014-01-24 02:31:35', '\0', '2014-01-24 02:31:35', 'B17F2 Server Room', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c216ccbe0003', '2014-01-24 02:31:50', '\0', '2014-01-24 02:31:50', '17115', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c216fab30006', '2014-01-24 02:32:02', '\0', '2014-01-24 02:32:02', '17307', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c217015a0009', '2014-01-24 02:32:04', '\0', '2014-01-24 02:32:04', '17112', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21706f8000c', '2014-01-24 02:32:05', '\0', '2014-01-24 02:32:05', '18304', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c2170a15000f', '2014-01-24 02:32:06', '\0', '2014-01-24 02:32:06', 'B17F2 Server Room', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c2170d510012', '2014-01-24 02:32:07', '\0', '2014-01-24 02:32:07', '17404', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c217139a0015', '2014-01-24 02:32:08', '\0', '2014-01-24 02:32:08', '17110', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c217188c0018', '2014-01-24 02:32:10', '\0', '2014-01-24 02:32:10', '17110', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c2171be7001b', '2014-01-24 02:32:10', '\0', '2014-01-24 02:32:10', '17110', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c217204c001e', '2014-01-24 02:32:12', '\0', '2014-01-24 02:32:12', '17406', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21727410021', '2014-01-24 02:32:13', '\0', '2014-01-24 02:32:13', '17110', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c2172b580024', '2014-01-24 02:32:14', '\0', '2014-01-24 02:32:14', '17404', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21731440027', '2014-01-24 02:32:16', '\0', '2014-01-24 02:32:16', '28306', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c2173480002a', '2014-01-24 02:32:17', '\0', '2014-01-24 02:32:17', 'yangzhou', 'Augmentum Yangzhou');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21737cc002d', '2014-01-24 02:32:18', '\0', '2014-01-24 02:32:18', '17406', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c2173bc30030', '2014-01-24 02:32:19', '\0', '2014-01-24 02:32:19', '17406', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c2173e440033', '2014-01-24 02:32:19', '\0', '2014-01-24 02:32:19', '17406', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21741420036', '2014-01-24 02:32:20', '\0', '2014-01-24 02:32:20', '17312', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21743d20039', '2014-01-24 02:32:21', '\0', '2014-01-24 02:32:21', '17312', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c217472d003c', '2014-01-24 02:32:22', '\0', '2014-01-24 02:32:22', '17406', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21749ae003f', '2014-01-24 02:32:22', '\0', '2014-01-24 02:32:22', '17406', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21750b30042', '2014-01-24 02:32:24', '\0', '2014-01-24 02:32:24', '17312', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f43c2157f0143c21753040045', '2014-01-24 02:32:25', '\0', '2014-01-24 02:32:25', '17312', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f44bff7570144c06939fd0000', '2014-03-14 11:45:25', '\0', '2014-03-14 11:45:25', '11111', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f44bff7570144c07702bb0001', '2014-03-14 12:00:28', '\0', '2014-03-14 12:00:28', '1112', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f44bff7570144c077c6c50002', '2014-03-14 12:01:19', '\0', '2014-03-14 12:01:19', '1113', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f44bff7570144c079611a0003', '2014-03-14 12:03:04', '\0', '2014-03-14 12:03:29', '1115', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028960f44bff7570144c07bc0350004', '2014-03-14 12:05:39', '\0', '2014-03-14 12:05:39', 'dsfdf', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('4028961242c776d30142c78306ce0003', '2013-12-06 10:45:22', '\0', '2013-12-06 10:45:22', '28401', 'IES_SH');
INSERT INTO `location` VALUES ('4028961242c776d30142c78352aa0004', '2013-12-06 10:45:41', '\0', '2013-12-06 10:45:41', '202020', 'IES_WH');
INSERT INTO `location` VALUES ('4028961242c776d30142c78386e70001', '2013-12-06 10:45:55', '\0', '2013-12-06 10:45:55', '10101', 'IES_YZ');
INSERT INTO `location` VALUES ('4028961242c776d30142c78386e70002', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', '17205', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028961242c776d30142c78386e70003', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', '17408', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028961242c776d30142c78386e70004', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', '17409', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('4028961242c776d30142c78386e70005', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', 'A4301', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('4028961242c776d30142c78386e70006', '2014-01-24 11:11:55', '\0', '2014-01-24 11:12:08', 'A4308', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('ff5a32e7c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17100', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff5c0255c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17101', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff5ede7cc42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17102', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff5f537cc42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17103', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff5fe792c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17104', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff605845c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17105', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff612981c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17106', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff63ce07c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17107', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff652093c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17108', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff67c2f7c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17109', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff6858c7c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17110', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff6b7777c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17111', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff6bfe30c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17112', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff6cccd6c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17113', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff72ab71c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17114', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff7489e5c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17115', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff77033dc42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17116', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff77f494c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17117', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff786988c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17118', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff7ad337c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17119', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff80e1e5c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17120', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff83b8fdc42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17121', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff870d19c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17122', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff8af98cc42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17123', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff8cc1d9c42011e299cafa163e30ed85', '2013-05-24 11:21:17', '\0', '2013-05-24 11:21:17', '17124', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff8ea671c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17206', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff91bfb0c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17207', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff9259c8c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17208', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff94884bc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17209', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff9729edc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17301', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff9818e9c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17302', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff9add55c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17303', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff9e82f6c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17304', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff9f02aac42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17305', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ff9fb11bc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17306', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffa24127c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17307', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffa6932cc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17308', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffa88f1ec42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17309', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffa9db2ec42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17310', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffac797cc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17311', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffae89aec42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17312', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffb0dd20c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17401', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffb40235c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17402', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffb48a0ec42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17403', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffb5a99dc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17404', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffb9bb4dc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17405', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffbde98cc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17406', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffc1cb6cc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17407', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffc28f0cc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '17410', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffc343a5c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '18303', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffc3b2b4c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '18304', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffc49235c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28301', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffc4feafc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28302', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffc8cb6ac42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28303', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffc9ab45c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28304', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffcbb586c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28305', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffd195e6c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28306', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffd3d649c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28401', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffd65ac5c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28402', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffd7b57dc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28403', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffdc7956c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28404', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffe020fbc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28405', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffe5950ac42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28406', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffe9709bc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28407', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('ffeceb07c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28408', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('fff02382c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', '28409', 'Augmentum Shanghai');
INSERT INTO `location` VALUES ('fff1584dc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4101', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('fff60e13c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4102', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('fff686ffc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4103', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('fff76cf4c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4104', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('fffa3ec7c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4105', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('fffa838cc42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4106', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('fffb3880c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4107', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('fffc9183c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4201', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('fffd0813c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4202', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('fffd85d5c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4203', 'Augmentum Wuhan');
INSERT INTO `location` VALUES ('fffdf575c42011e299cafa163e30ed85', '2013-05-24 11:21:18', '\0', '2013-05-24 11:21:18', 'A4204', 'Augmentum Wuhan');

-- ----------------------------
-- Table structure for `machine`
-- ----------------------------
DROP TABLE IF EXISTS `machine`;
CREATE TABLE `machine` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `address` varchar(128) DEFAULT NULL,
  `configuration` varchar(512) DEFAULT NULL,
  `specification` varchar(512) DEFAULT NULL,
  `sub_type` varchar(32) NOT NULL,
  `asset_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `asset_id` (`asset_id`),
  KEY `FK3131444761AEBBBE` (`asset_id`),
  CONSTRAINT `FK3131444761AEBBBE` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of machine
-- ----------------------------
INSERT INTO `machine` VALUES ('4028960f446dee5101446df7571a00b1', '2014-02-26 11:32:10', '\0', '2014-02-26 11:32:10', '00-10-5C-E5-6A-BC', '', '', 'Desktop', '4028960f446dee5101446df7562000b0');
INSERT INTO `machine` VALUES ('4028960f446dee5101446df758ee00b3', '2014-02-26 11:32:10', '\0', '2014-02-26 11:32:10', '00-10-5C-DD-6B-E5', '', '', 'Desktop', '4028960f446dee5101446df7584300b2');
INSERT INTO `machine` VALUES ('4028960f446dee5101446df75af200b5', '2014-02-26 11:32:11', '\0', '2014-02-26 11:32:11', '00-05-9A-3C-78-00', '', '', 'Desktop', '4028960f446dee5101446df75a5600b4');
INSERT INTO `machine` VALUES ('4028960f446dee5101446df75cc700b7', '2014-02-26 11:32:11', '\0', '2014-02-26 11:32:11', '00-0D-87-90-30-57', '', '', 'Desktop', '4028960f446dee5101446df75c4a00b6');
INSERT INTO `machine` VALUES ('4028960f446dee5101446df7602200b9', '2014-02-26 11:32:12', '\0', '2014-02-26 11:32:12', '00-10-5C-E8-2E-3B', '', '', 'Desktop', '4028960f446dee5101446df75f8600b8');
INSERT INTO `machine` VALUES ('4028960f446dee5101446df7625500bb', '2014-02-26 11:32:13', '\0', '2014-02-26 11:32:13', '00-15-5C-E5-67-5C', '', '', 'Desktop', '4028960f446dee5101446df7618900ba');

-- ----------------------------
-- Table structure for `monitor`
-- ----------------------------
DROP TABLE IF EXISTS `monitor`;
CREATE TABLE `monitor` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `detail` varchar(512) DEFAULT NULL,
  `size` varchar(32) DEFAULT NULL,
  `asset_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `asset_id` (`asset_id`),
  KEY `FK49B0BD5A61AEBBBE` (`asset_id`),
  CONSTRAINT `FK49B0BD5A61AEBBBE` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monitor
-- ----------------------------
INSERT INTO `monitor` VALUES ('4028960f446dee5101446df7659100bd', '2014-02-26 11:32:13', '\0', '2014-02-26 11:32:13', '', '', '4028960f446dee5101446df764b600bc');
INSERT INTO `monitor` VALUES ('4028960f446dee5101446df7675600bf', '2014-02-26 11:32:14', '\0', '2014-02-26 11:32:14', '', '', '4028960f446dee5101446df766aa00be');
INSERT INTO `monitor` VALUES ('4028960f446dee5101446df76ac100c1', '2014-02-26 11:32:15', '\0', '2014-02-26 11:32:15', '', '', '4028960f446dee5101446df768bd00c0');
INSERT INTO `monitor` VALUES ('4028960f446dee5101446df76ca500c3', '2014-02-26 11:32:15', '\0', '2014-02-26 11:32:15', '', '', '4028960f446dee5101446df76c0900c2');
INSERT INTO `monitor` VALUES ('4028960f446dee5101446df76ea900c5', '2014-02-26 11:32:16', '\0', '2014-02-26 11:32:16', '', '', '4028960f446dee5101446df76dfd00c4');
INSERT INTO `monitor` VALUES ('4028960f446dee5101446df771b600c7', '2014-02-26 11:32:16', '\0', '2014-02-26 11:32:16', '', '', '4028960f446dee5101446df7711a00c6');
INSERT INTO `monitor` VALUES ('4028960f446dee5101446df773d900c9', '2014-02-26 11:32:17', '\0', '2014-02-26 11:32:17', '', '', '4028960f446dee5101446df7731d00c8');
INSERT INTO `monitor` VALUES ('4028960f446dee5101446df777f000cb', '2014-02-26 11:32:18', '\0', '2014-02-26 11:32:18', '', '', '4028960f446dee5101446df7775300ca');
INSERT INTO `monitor` VALUES ('4028960f44be5b7b0144bec424e40002', '2014-03-14 04:05:29', '\0', '2014-03-14 04:05:29', null, '', '4028960f44be5b7b0144bec423ea0000');
INSERT INTO `monitor` VALUES ('4028960f44be5b7b0144bec430bb0005', '2014-03-14 04:05:32', '\0', '2014-03-14 04:09:06', null, '', '4028960f44be5b7b0144bec4308d0003');

-- ----------------------------
-- Table structure for `operation_log`
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `operation` varchar(512) DEFAULT NULL,
  `operation_object` varchar(32) DEFAULT NULL,
  `operation_object_id` varchar(32) DEFAULT NULL,
  `operator_id` varchar(32) NOT NULL,
  `operator_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operation_log
-- ----------------------------
INSERT INTO `operation_log` VALUES ('4028960f44be5b7b0144bec424b50001', '2014-03-14 04:05:29', '\0', '2014-03-14 04:05:29', 'create asset', 'Asset', '4028960f44be5b7b0144bec423ea0000', 'T00690', 'Raymond Bao');
INSERT INTO `operation_log` VALUES ('4028960f44be5b7b0144bec430bb0004', '2014-03-14 04:05:32', '\0', '2014-03-14 04:05:32', 'create asset', 'Asset', '4028960f44be5b7b0144bec423ea0000', 'T00690', 'Raymond Bao');
INSERT INTO `operation_log` VALUES ('4028960f44bec6130144bec773bc0000', '2014-03-14 04:09:06', '\0', '2014-03-14 04:09:06', 'update asset [Status: AVAILABLE---IN_USE] [Memo: ---NULL] [WarrantyTime: NULL---] [User: NULL---Selena Shen]', 'Asset', '4028960f44be5b7b0144bec4308d0003', 'T00690', 'Raymond Bao');
INSERT INTO `operation_log` VALUES ('4028960f44bec6130144bec861f20004', '2014-03-14 04:10:07', '\0', '2014-03-14 04:10:07', 'create asset', 'Asset', '4028960f44bec6130144bec861660003', 'T00690', 'Raymond Bao');

-- ----------------------------
-- Table structure for `other_assets`
-- ----------------------------
DROP TABLE IF EXISTS `other_assets`;
CREATE TABLE `other_assets` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `detail` varchar(512) DEFAULT NULL,
  `asset_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `asset_id` (`asset_id`),
  KEY `FK1A43DF9261AEBBBE` (`asset_id`),
  CONSTRAINT `FK1A43DF9261AEBBBE` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of other_assets
-- ----------------------------

-- ----------------------------
-- Table structure for `page_size`
-- ----------------------------
DROP TABLE IF EXISTS `page_size`;
CREATE TABLE `page_size` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `category_flag` int(11) NOT NULL,
  `is_default_value` bit(1) DEFAULT NULL,
  `page_size_value` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page_size
-- ----------------------------
INSERT INTO `page_size` VALUES ('1001', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '10', '', '10');
INSERT INTO `page_size` VALUES ('1002', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '10', '\0', '20');
INSERT INTO `page_size` VALUES ('1003', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '10', '\0', '30');
INSERT INTO `page_size` VALUES ('1004', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '10', '\0', '50');
INSERT INTO `page_size` VALUES ('101', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '1', '', '10');
INSERT INTO `page_size` VALUES ('102', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '1', '\0', '20');
INSERT INTO `page_size` VALUES ('103', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '1', '\0', '30');
INSERT INTO `page_size` VALUES ('104', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '1', '\0', '50');
INSERT INTO `page_size` VALUES ('1101', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '11', '', '10');
INSERT INTO `page_size` VALUES ('1102', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '11', '\0', '20');
INSERT INTO `page_size` VALUES ('1103', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '11', '\0', '30');
INSERT INTO `page_size` VALUES ('1104', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '11', '\0', '50');
INSERT INTO `page_size` VALUES ('1201', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '12', '', '10');
INSERT INTO `page_size` VALUES ('1202', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '12', '\0', '20');
INSERT INTO `page_size` VALUES ('1203', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '12', '\0', '30');
INSERT INTO `page_size` VALUES ('1204', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '12', '\0', '50');
INSERT INTO `page_size` VALUES ('1301', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '13', '', '10');
INSERT INTO `page_size` VALUES ('1302', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '13', '\0', '20');
INSERT INTO `page_size` VALUES ('1303', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '13', '\0', '30');
INSERT INTO `page_size` VALUES ('1304', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '13', '\0', '50');
INSERT INTO `page_size` VALUES ('201', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '2', '', '10');
INSERT INTO `page_size` VALUES ('202', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '2', '\0', '20');
INSERT INTO `page_size` VALUES ('203', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '2', '\0', '30');
INSERT INTO `page_size` VALUES ('204', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '2', '\0', '50');
INSERT INTO `page_size` VALUES ('301', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '3', '', '10');
INSERT INTO `page_size` VALUES ('302', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '3', '\0', '20');
INSERT INTO `page_size` VALUES ('303', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '3', '\0', '30');
INSERT INTO `page_size` VALUES ('304', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '3', '\0', '50');
INSERT INTO `page_size` VALUES ('401', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '4', '', '10');
INSERT INTO `page_size` VALUES ('402', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '4', '\0', '20');
INSERT INTO `page_size` VALUES ('403', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '4', '\0', '30');
INSERT INTO `page_size` VALUES ('404', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '4', '\0', '50');
INSERT INTO `page_size` VALUES ('501', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '5', '', '10');
INSERT INTO `page_size` VALUES ('502', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '5', '\0', '20');
INSERT INTO `page_size` VALUES ('503', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '5', '\0', '30');
INSERT INTO `page_size` VALUES ('504', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '5', '\0', '50');
INSERT INTO `page_size` VALUES ('601', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '6', '', '10');
INSERT INTO `page_size` VALUES ('602', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '6', '\0', '20');
INSERT INTO `page_size` VALUES ('603', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '6', '\0', '30');
INSERT INTO `page_size` VALUES ('604', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '6', '\0', '50');
INSERT INTO `page_size` VALUES ('701', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '7', '', '10');
INSERT INTO `page_size` VALUES ('702', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '7', '\0', '20');
INSERT INTO `page_size` VALUES ('703', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '7', '\0', '30');
INSERT INTO `page_size` VALUES ('704', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '7', '\0', '50');
INSERT INTO `page_size` VALUES ('801', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '8', '', '10');
INSERT INTO `page_size` VALUES ('802', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '8', '\0', '20');
INSERT INTO `page_size` VALUES ('803', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '8', '\0', '30');
INSERT INTO `page_size` VALUES ('804', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '8', '\0', '50');
INSERT INTO `page_size` VALUES ('901', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '9', '', '10');
INSERT INTO `page_size` VALUES ('902', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '9', '\0', '20');
INSERT INTO `page_size` VALUES ('903', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '9', '\0', '30');
INSERT INTO `page_size` VALUES ('904', '2013-11-28 09:14:30', '\0', '2013-11-28 09:14:30', '9', '\0', '50');

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `project_code` varchar(32) NOT NULL,
  `project_name` varchar(64) DEFAULT NULL,
  `customer_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKED904B19DF0D5E76` (`customer_id`),
  CONSTRAINT `FKED904B19DF0D5E76` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('4028960f446dee5101446df754d800ae', '2014-02-26 11:32:09', '\0', '2014-02-26 11:32:09', '00900_005', 'ITD', '4028960f446dee5101446df753af00ad');
INSERT INTO `project` VALUES ('4028960f446dee5101446df7794700cc', '2014-02-26 11:32:18', '\0', '2014-02-26 11:32:18', '01121_041', 'PremierOne R2 2011', '4028960f446dee5101446df753af00ad');

-- ----------------------------
-- Table structure for `property_template`
-- ----------------------------
DROP TABLE IF EXISTS `property_template`;
CREATE TABLE `property_template` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `asset_type` varchar(32) NOT NULL,
  `description` varchar(300) DEFAULT NULL,
  `en_name` varchar(32) NOT NULL,
  `is_required` bit(1) DEFAULT NULL,
  `position` varchar(32) NOT NULL,
  `proeperty_type` varchar(32) NOT NULL,
  `sequence` int(11) NOT NULL,
  `value` varchar(512) DEFAULT NULL,
  `zh_name` varchar(32) NOT NULL,
  `creator_id` varchar(32) NOT NULL,
  `customer_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB035DFA4DF0D5E76` (`customer_id`),
  KEY `FKB035DFA4B665835E` (`creator_id`),
  CONSTRAINT `FKB035DFA4B665835E` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKB035DFA4DF0D5E76` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of property_template
-- ----------------------------
INSERT INTO `property_template` VALUES ('4028960f446dee5101446e05db1f00e5', '2014-02-26 11:48:01', '', '2014-02-27 00:29:12', 'DEVICE', 'test_property', 'test_property', '\0', 'sortableLeft', 'inputType', '0', 'test_property', 'test_property', '4028960f446dee5101446df33f3c0000', '4028960f446dee5101446e02b8ab00e1');
INSERT INTO `property_template` VALUES ('4028960f446e3dd201446e3f2ae30000', '2014-02-26 12:50:21', '\0', '2014-03-13 03:14:25', 'DEVICE', 'second_property', 'second_property', '', 'sortableRight', 'dateType', '3', '2014-02-27', 'second_property', '4028960f446dee5101446df33f3c0000', '4028960f446dee5101446e02b8ab00e1');
INSERT INTO `property_template` VALUES ('4028960f4471ed08014471ee06d30000', '2014-02-27 06:00:17', '\0', '2014-02-27 06:00:17', 'SOFTWARE', 'test_software_property', 'test_software_property', '', 'sortableLeft', 'inputType', '0', 'test_software_property', 'test_software_property', '4028960f446dee5101446df33f3c0000', '4028960f446dee5101446e02b8ab00e1');
INSERT INTO `property_template` VALUES ('4028960f44b9251b0144b969a00e0000', '2014-03-13 03:08:31', '\0', '2014-03-13 03:14:25', 'DEVICE', 'input', 'input', '', 'sortableLeft', 'inputType', '0', 'input', 'input', '4028960f446dee5101446df33f3c0000', '4028960f446dee5101446e02b8ab00e1');
INSERT INTO `property_template` VALUES ('4028960f44b9251b0144b969a02d0001', '2014-03-13 03:08:31', '\0', '2014-03-13 03:14:25', 'DEVICE', 'select', 'select', '', 'sortableLeft', 'selectType', '1', '1#2#3', 'select', '4028960f446dee5101446df33f3c0000', '4028960f446dee5101446e02b8ab00e1');
INSERT INTO `property_template` VALUES ('4028960f44b9251b0144b969a02d0002', '2014-03-13 03:08:31', '\0', '2014-03-13 03:14:25', 'DEVICE', 'date', 'date', '', 'sortableRight', 'dateType', '4', '2014-03-15', 'date', '4028960f446dee5101446df33f3c0000', '4028960f446dee5101446e02b8ab00e1');
INSERT INTO `property_template` VALUES ('4028960f44b9251b0144b969a02d0003', '2014-03-13 03:08:31', '\0', '2014-03-13 03:14:25', 'DEVICE', 'area', 'area', '', 'sortableLeft', 'textAreaType', '2', 'area', 'area', '4028960f446dee5101446df33f3c0000', '4028960f446dee5101446e02b8ab00e1');

-- ----------------------------
-- Table structure for `purchase_item`
-- ----------------------------
DROP TABLE IF EXISTS `purchase_item`;
CREATE TABLE `purchase_item` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `customer_code` varchar(32) NOT NULL,
  `customer_name` varchar(128) NOT NULL,
  `data_site` varchar(32) NOT NULL,
  `delivery_date` date DEFAULT NULL,
  `entity_site` varchar(32) NOT NULL,
  `final_quantity` int(11) NOT NULL,
  `is_used` bit(1) DEFAULT NULL,
  `item_description` varchar(512) DEFAULT NULL,
  `item_name` varchar(64) NOT NULL,
  `item_real_name` varchar(64) DEFAULT NULL,
  `item_type` varchar(32) NOT NULL,
  `po_no` varchar(32) NOT NULL,
  `process_type` varchar(32) NOT NULL,
  `project_code` varchar(32) DEFAULT NULL,
  `project_name` varchar(64) DEFAULT NULL,
  `used_quantity` int(11) DEFAULT NULL,
  `vendor_name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of purchase_item
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `role_name` varchar(75) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('40289609412595090141259513500001', '2013-09-16 15:03:48', '\0', '2013-12-06 04:17:33', 'IT');
INSERT INTO `role` VALUES ('40289609412595090141259513600002', '2013-09-16 15:03:48', '\0', '2013-12-06 04:17:34', 'SYSTEM_ADMIN');
INSERT INTO `role` VALUES ('40289609412595090141259513600003', '2013-09-16 15:03:48', '\0', '2013-12-06 04:17:32', 'EMPLOYEE');
INSERT INTO `role` VALUES ('40289609412595090141259513600004', '2013-09-16 15:03:48', '\0', '2013-12-06 04:17:32', 'MANAGER');
INSERT INTO `role` VALUES ('40289609412595090141259513600005', '2013-09-16 15:03:48', '\0', '2013-12-06 04:17:32', 'SPECIAL_ROLE');

-- ----------------------------
-- Table structure for `role_authority`
-- ----------------------------
DROP TABLE IF EXISTS `role_authority`;
CREATE TABLE `role_authority` (
  `role_id` varchar(32) NOT NULL,
  `authority_id` varchar(32) NOT NULL,
  KEY `FKFBF4E6BAB63EBB7F` (`role_id`),
  KEY `FKFBF4E6BA7FCA8C75` (`authority_id`),
  CONSTRAINT `FKFBF4E6BA7FCA8C75` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FKFBF4E6BAB63EBB7F` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_authority
-- ----------------------------
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600003', '4028961242c61fda0142c61ff20b0000');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600003', '4028961242c61fda0142c61ff2e60001');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600003', '4028961242c61fda0142c61ff3340002');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600003', '4028961242c61fda0142c61ff3920003');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600005', '4028961242c61fda0142c61ff20b0000');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600005', '4028961242c61fda0142c61ff2e60001');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600005', '4028961242c61fda0142c61ff3340002');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600005', '4028961242c61fda0142c61ff4f90004');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600004', '4028961242c61fda0142c61ff20b0000');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600004', '4028961242c61fda0142c61ff2e60001');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600004', '4028961242c61fda0142c61ff3340002');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600004', '4028961242c61fda0142c61ff4f90004');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600004', '4028961242c61fda0142c61ff69f0005');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600004', '4028961242c61fda0142c61ff6fd0006');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513500001', '4028961242c61fda0142c61ff3340002');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513500001', '4028961242c61fda0142c61ff4f90004');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513500001', '4028961242c61fda0142c61ff69f0005');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513500001', '4028961242c61fda0142c61ff75b0007');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513500001', '4028961242c61fda0142c61ff9000008');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513500001', '4028961242c61fda0142c61ff96e0009');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513500001', '4028961242c61fda0142c61ff9cc000a');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513500001', '4028961242c61fda0142c61ffa29000b');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600002', '4028961242c61fda0142c61ff3340002');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600002', '4028961242c61fda0142c61ff4f90004');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600002', '4028961242c61fda0142c61ff69f0005');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600002', '4028961242c61fda0142c61ff6fd0006');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600002', '4028961242c61fda0142c61ff75b0007');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600002', '4028961242c61fda0142c61ff9000008');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600002', '4028961242c61fda0142c61ff96e0009');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600002', '4028961242c61fda0142c61ff9cc000a');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600002', '4028961242c61fda0142c61ffd75000c');
INSERT INTO `role_authority` VALUES ('40289609412595090141259513600002', '4028961242c61fda0142c61ffde2000d');

-- ----------------------------
-- Table structure for `scheduler_task`
-- ----------------------------
DROP TABLE IF EXISTS `scheduler_task`;
CREATE TABLE `scheduler_task` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  `is_running` bit(1) NOT NULL,
  `timer_type` varchar(32) NOT NULL,
  `trigger_value` varchar(32) NOT NULL,
  `creator_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3B776A89B665835E` (`creator_id`),
  CONSTRAINT `FK3B776A89B665835E` FOREIGN KEY (`creator_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of scheduler_task
-- ----------------------------

-- ----------------------------
-- Table structure for `software`
-- ----------------------------
DROP TABLE IF EXISTS `software`;
CREATE TABLE `software` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `additional_info` varchar(255) DEFAULT NULL,
  `license_key` varchar(256) DEFAULT NULL,
  `managerVisible` bit(1) NOT NULL,
  `max_use_num` int(11) DEFAULT NULL,
  `software_expired_time` datetime DEFAULT NULL,
  `version` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of software
-- ----------------------------
INSERT INTO `software` VALUES ('4028960f4471dca1014471e19f980000', '2014-02-27 05:46:55', '\0', '2014-02-27 05:46:55', 'test_additional_info', '111111111111', '\0', '0', null, '1.0');
INSERT INTO `software` VALUES ('4028960f4471ed080144721bc60c0001', '2014-02-27 06:50:26', '\0', '2014-02-27 06:50:26', 'test_additional_info', '111111111111', '\0', '0', null, '1.0');

-- ----------------------------
-- Table structure for `special_role`
-- ----------------------------
DROP TABLE IF EXISTS `special_role`;
CREATE TABLE `special_role` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `customer_code` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of special_role
-- ----------------------------
INSERT INTO `special_role` VALUES ('4028960f44ba714a0144ba72df300001', '2014-03-13 07:57:44', '\0', '2014-03-13 07:57:44', '00100', 'T04013', 'Jimmy Wu');

-- ----------------------------
-- Table structure for `todo`
-- ----------------------------
DROP TABLE IF EXISTS `todo`;
CREATE TABLE `todo` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `received_time` datetime DEFAULT NULL,
  `returned_time` datetime DEFAULT NULL,
  `task` varchar(255) DEFAULT NULL,
  `asset_id` varchar(32) DEFAULT NULL,
  `assigner_id` varchar(32) DEFAULT NULL,
  `returner_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK36684661AEBBBE` (`asset_id`),
  KEY `FK366846CC08D7AE` (`assigner_id`),
  KEY `FK366846BEB9C0D` (`returner_id`),
  CONSTRAINT `FK36684661AEBBBE` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`),
  CONSTRAINT `FK366846BEB9C0D` FOREIGN KEY (`returner_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK366846CC08D7AE` FOREIGN KEY (`assigner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of todo
-- ----------------------------

-- ----------------------------
-- Table structure for `transfer_log`
-- ----------------------------
DROP TABLE IF EXISTS `transfer_log`;
CREATE TABLE `transfer_log` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `action` varchar(64) NOT NULL,
  `time` datetime NOT NULL,
  `asset_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK80419CF061AEBBBE` (`asset_id`),
  KEY `FK80419CF05B697F5F` (`user_id`),
  CONSTRAINT `FK80419CF05B697F5F` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK80419CF061AEBBBE` FOREIGN KEY (`asset_id`) REFERENCES `asset` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of transfer_log
-- ----------------------------
INSERT INTO `transfer_log` VALUES ('4028960f448c2a9501448c31cc0a0000', '2014-03-04 08:24:37', '\0', '2014-03-04 08:24:37', 'ReturnToCustomer', '2014-03-04 08:24:37', '4028960f4485f67e014487c2caad0014', '4028960f446dee5101446df33f3c0000');
INSERT INTO `transfer_log` VALUES ('4028960f448c2a9501448c31cc780001', '2014-03-04 08:24:37', '\0', '2014-03-04 08:24:37', 'ReturnToCustomer', '2014-03-04 08:24:37', '4028960f4473472b014473983d71002c', '4028960f446dee5101446df33f3c0000');
INSERT INTO `transfer_log` VALUES ('4028960f44b5da910144b5dc3f400001', '2014-03-12 10:35:14', '\0', '2014-03-12 10:35:14', 'Assign', '2014-03-12 10:35:14', '4028960f44b583d00144b58d9bfd0001', '4028960f446dee5101446df33f3c0000');
INSERT INTO `transfer_log` VALUES ('4028960f44b5da910144b6033eb40002', '2014-03-12 11:17:49', '\0', '2014-03-12 11:17:49', 'Assign', '2014-03-12 11:17:49', '4028960f44b567b00144b57c4dbd0002', '4028960f446dee5101446df33f3c0000');
INSERT INTO `transfer_log` VALUES ('4028960f44bec6130144bec77a530001', '2014-03-14 04:09:07', '\0', '2014-03-14 04:09:07', 'Assign', '2014-03-14 04:09:07', '4028960f44be5b7b0144bec4308d0003', '4028960f446dee5101446df33f3c0000');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `userId` varchar(32) NOT NULL,
  `user_name` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('4028960f446dee5101446df33f3c0000', '2014-02-26 11:27:41', '\0', '2014-03-18 00:33:51', 'T00690', 'Raymond Bao');
INSERT INTO `user` VALUES ('4028960f446dee5101446df7559300af', '2014-02-26 11:32:09', '\0', '2014-03-18 00:33:51', 'T04301', 'John Li');
INSERT INTO `user` VALUES ('4028960f44968e740144969109d00000', '2014-03-06 08:44:50', '\0', '2014-03-18 00:33:51', 'T03306', 'Selena Shen');
INSERT INTO `user` VALUES ('4028960f44b0313a0144b065e45b0000', '2014-03-11 09:07:51', '\0', '2014-03-11 09:07:51', 'T00245', 'Berton Wu');
INSERT INTO `user` VALUES ('4028960f44b5da910144b5dc331b0000', '2014-03-12 10:35:11', '\0', '2014-03-12 10:35:11', 'T04300', 'Geoffrey Zhao');
INSERT INTO `user` VALUES ('4028960f44bec6130144bec85ffe0002', '2014-03-14 04:10:06', '\0', '2014-03-14 04:10:06', 'WT00775', 'Aaliyah Li');
INSERT INTO `user` VALUES ('4028960f44cf05220144cf15e0ca0000', '2014-03-17 08:08:41', '\0', '2014-03-18 00:33:51', 'T03061', 'Judith Jiang');
INSERT INTO `user` VALUES ('4028960f44cf22480144cf2345fc0000', '2014-03-17 08:23:19', '\0', '2014-03-17 08:23:19', 'WT00720', 'Sally Fu');

-- ----------------------------
-- Table structure for `user_custom_column`
-- ----------------------------
DROP TABLE IF EXISTS `user_custom_column`;
CREATE TABLE `user_custom_column` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `sequence` int(11) DEFAULT NULL,
  `showDefault` bit(1) NOT NULL,
  `custom_column_id` varchar(32) DEFAULT NULL,
  `property_template_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK10A572701DFBBF94` (`property_template_id`),
  KEY `FK10A572706D6B4009` (`custom_column_id`),
  KEY `FK10A572705B697F5F` (`user_id`),
  CONSTRAINT `FK10A572701DFBBF94` FOREIGN KEY (`property_template_id`) REFERENCES `property_template` (`id`),
  CONSTRAINT `FK10A572705B697F5F` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK10A572706D6B4009` FOREIGN KEY (`custom_column_id`) REFERENCES `custom_column` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_custom_column
-- ----------------------------
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835b520000', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '1001', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835b910001', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '501', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835b910002', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '301', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835b910003', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '1101', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835b910004', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '1201', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835b910005', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '1301', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10006', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '901', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10007', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '401', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10008', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '601', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10009', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '201', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1000a', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '101', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1000b', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '801', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1000c', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '1', '', '701', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1000d', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '302', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1000e', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '802', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1000f', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '602', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10010', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '902', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10011', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '702', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10012', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '1202', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10013', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '202', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10014', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '1302', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10015', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '402', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10016', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '1002', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10017', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '1102', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10018', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '102', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10019', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '2', '', '502', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1001a', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '1303', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1001b', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '105', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1001c', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '405', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1001d', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '1205', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1001e', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '803', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1001f', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '505', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10020', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '903', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10021', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '\0', '1103', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10022', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '1003', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10023', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '305', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10024', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '205', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10025', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '703', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10026', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '3', '', '605', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10027', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '', '1104', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10028', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '\0', '303', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10029', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '\0', '503', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1002a', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '\0', '1209', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1002b', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '', '1304', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1002c', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '', '904', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1002d', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '', '804', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1002e', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '\0', '103', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba1002f', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '\0', '203', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10030', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '\0', '403', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10031', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '\0', '603', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10032', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '4', '', '1004', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10033', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '\0', '504', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10034', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '\0', '404', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10035', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '\0', '204', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10036', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '\0', '604', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835ba10037', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '\0', '304', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb00038', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '', '1305', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb00039', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '', '805', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb0003a', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '\0', '1204', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb0003b', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '\0', '1105', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb0003c', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '', '905', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb0003d', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '\0', '104', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb0003e', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '5', '', '1005', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb0003f', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '6', '\0', '306', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb00040', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '6', '\0', '606', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb00041', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '6', '\0', '206', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb00042', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '6', '\0', '506', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb00043', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '6', '\0', '906', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb00044', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '6', '\0', '1106', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb00045', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '6', '', '806', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb00046', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '6', '\0', '1206', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bb00047', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '6', '\0', '106', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00048', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '6', '\0', '406', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00049', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '7', '\0', '907', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0004a', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '7', '\0', '407', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0004b', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '7', '\0', '307', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0004c', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '7', '', '1107', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0004d', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '7', '\0', '507', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0004e', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '7', '\0', '107', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0004f', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '7', '\0', '607', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00050', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '7', '\0', '207', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00051', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '7', '\0', '1207', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00052', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '8', '', '908', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00053', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '8', '\0', '508', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00054', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '8', '\0', '1108', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00055', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '8', '\0', '208', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00056', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '8', '\0', '108', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00057', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '8', '\0', '1208', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00058', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '8', '\0', '308', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00059', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '8', '\0', '408', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0005a', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '8', '\0', '608', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0005b', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '9', '\0', '1109', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0005c', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '9', '\0', '609', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0005d', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '9', '\0', '509', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0005e', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '9', '\0', '409', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0005f', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '9', '\0', '109', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00060', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '9', '\0', '1203', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00061', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '9', '\0', '209', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00062', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '9', '\0', '309', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00063', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '9', '\0', '909', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00064', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '10', '\0', '310', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00065', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '10', '\0', '1210', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00066', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '10', '\0', '610', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00067', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '10', '\0', '1110', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00068', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '10', '\0', '410', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00069', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '10', '\0', '110', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0006a', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '10', '\0', '210', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0006b', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '10', '\0', '510', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0006c', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '10', '\0', '910', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0006d', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '11', '\0', '411', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0006e', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '11', '\0', '1111', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0006f', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '11', '\0', '211', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00070', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '11', '\0', '111', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00071', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '11', '\0', '611', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00072', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '11', '\0', '1211', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00073', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '11', '\0', '311', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00074', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '11', '\0', '511', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00075', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '12', '\0', '412', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00076', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '12', '\0', '1212', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00077', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '12', '\0', '612', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00078', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '12', '\0', '512', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00079', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '12', '\0', '112', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0007a', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '12', '\0', '212', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0007b', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '12', '\0', '312', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0007c', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '13', '\0', '513', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0007d', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '13', '\0', '613', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0007e', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '13', '\0', '213', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0007f', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '13', '\0', '1213', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00080', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '13', '\0', '113', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00081', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '13', '\0', '413', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00082', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '13', '\0', '313', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00083', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '14', '\0', '314', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00084', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '14', '\0', '514', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00085', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '14', '\0', '1214', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00086', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '14', '\0', '414', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00087', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '14', '\0', '214', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00088', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '14', '\0', '114', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00089', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '14', '\0', '614', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0008a', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '15', '\0', '315', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0008b', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '15', '\0', '615', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0008c', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '15', '\0', '515', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0008d', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '15', '\0', '115', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0008e', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '15', '\0', '215', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc0008f', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '15', '\0', '1215', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00090', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '15', '\0', '415', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00091', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '16', '\0', '316', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00092', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '16', '\0', '416', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bc00093', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '16', '\0', '1216', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bcf0094', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '16', '\0', '616', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bcf0095', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '16', '\0', '116', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf0096', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '16', '\0', '516', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf0097', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '16', '\0', '216', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf0098', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '17', '\0', '217', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf0099', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '17', '\0', '1217', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf009a', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '17', '\0', '517', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf009b', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '17', '\0', '417', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf009c', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '17', '\0', '617', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf009d', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '17', '\0', '317', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf009e', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '17', '\0', '117', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf009f', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '18', '\0', '118', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00a0', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '18', '\0', '218', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00a1', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '18', '\0', '418', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00a2', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '18', '\0', '518', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00a3', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '18', '\0', '318', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00a4', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '18', '\0', '1218', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00a5', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '18', '\0', '618', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00a6', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '19', '\0', '319', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00a7', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '19', '\0', '619', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00a8', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '19', '\0', '419', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00a9', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '19', '\0', '119', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00aa', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '19', '\0', '219', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00ab', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '19', '\0', '1219', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00ac', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '19', '\0', '519', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00ad', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '20', '\0', '120', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00ae', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '20', '\0', '1220', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bdf00af', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '20', '\0', '620', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00b0', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '20', '\0', '220', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00b1', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '20', '\0', '420', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00b2', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '20', '\0', '520', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00b3', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '20', '\0', '320', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00b4', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '21', '\0', '521', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00b5', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '21', '\0', '121', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00b6', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '21', '\0', '621', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00b7', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '21', '\0', '321', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00b8', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '21', '\0', '421', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00b9', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '21', '\0', '1221', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00ba', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '21', '\0', '221', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00bb', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '22', '\0', '522', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00bc', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '22', '\0', '622', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00bd', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '22', '\0', '422', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00be', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '22', '\0', '322', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00bf', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '22', '\0', '222', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00c0', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '23', '\0', '323', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00c1', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '23', '\0', '423', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00c2', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '23', '\0', '223', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00c3', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '23', '\0', '623', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00c4', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '24', '\0', '624', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00c5', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '24', '\0', '224', null, '4028960f446dee5101446df33f3c0000');
INSERT INTO `user_custom_column` VALUES ('4028960f44ba81e30144ba835bef00c6', '2014-03-13 08:16:14', '\0', '2014-03-13 08:16:14', '25', '\0', '225', null, '4028960f446dee5101446df33f3c0000');

-- ----------------------------
-- Table structure for `user_page_size`
-- ----------------------------
DROP TABLE IF EXISTS `user_page_size`;
CREATE TABLE `user_page_size` (
  `user_id` varchar(32) NOT NULL,
  `page_size_id` varchar(32) NOT NULL,
  KEY `FK92165C3DF9B52E58` (`page_size_id`),
  KEY `FK92165C3D5B697F5F` (`user_id`),
  CONSTRAINT `FK92165C3D5B697F5F` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK92165C3DF9B52E58` FOREIGN KEY (`page_size_id`) REFERENCES `page_size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_page_size
-- ----------------------------
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '1001');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '101');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '1101');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '1201');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '1301');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '201');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '301');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '401');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '501');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '601');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '701');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '801');
INSERT INTO `user_page_size` VALUES ('4028960f446dee5101446df33f3c0000', '901');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  KEY `FK143BF46AB63EBB7F` (`role_id`),
  KEY `FK143BF46A5B697F5F` (`user_id`),
  CONSTRAINT `FK143BF46A5B697F5F` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK143BF46AB63EBB7F` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('4028960f446dee5101446df7559300af', '40289609412595090141259513600002');
INSERT INTO `user_role` VALUES ('4028960f44968e740144969109d00000', '40289609412595090141259513500001');
INSERT INTO `user_role` VALUES ('4028960f44968e740144969109d00000', '40289609412595090141259513600002');
INSERT INTO `user_role` VALUES ('4028960f446dee5101446df33f3c0000', '40289609412595090141259513500001');
INSERT INTO `user_role` VALUES ('4028960f446dee5101446df33f3c0000', '40289609412595090141259513600002');
INSERT INTO `user_role` VALUES ('4028960f44cf05220144cf15e0ca0000', '40289609412595090141259513500001');
