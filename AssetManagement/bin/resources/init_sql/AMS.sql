/*
Navicat MySQL Data Transfer

Source Server         : conn_ams
Source Server Version : 50153
Source Host           : localhost:3306
Source Database       : ams

Target Server Type    : MYSQL
Target Server Version : 50153
File Encoding         : 65001

Date: 2013-12-11 16:15:45
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
  `share_assets` varchar(32) DEFAULT NULL,
  `groupId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK24217FDEE4674D1F` (`groupId`),
  CONSTRAINT `FK24217FDEE4674D1F` FOREIGN KEY (`groupId`) REFERENCES `customer_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------


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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customized_view
-- ----------------------------

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
  `search_condition` varchar(32) DEFAULT NULL,
  `used` bit(1) DEFAULT NULL,
  `value` varchar(64) DEFAULT NULL,
  `customized_view_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK47EFE22FBE0EFC4C` (`customized_view_id`),
  CONSTRAINT `FK47EFE22FBE0EFC4C` FOREIGN KEY (`customized_view_id`) REFERENCES `customized_view` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customized_view_item
-- ----------------------------

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
  `sequence` int(11) DEFAULT NULL,
  `sort_name` varchar(32) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `zh_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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
  `fina_quantity` int(11) NOT NULL,
  `is_used` bit(1) DEFAULT NULL,
  `item_description` varchar(512) DEFAULT NULL,
  `item_name` varchar(64) NOT NULL,
  `item_real_name` varchar(64) DEFAULT NULL,
  `po_no` varchar(32) NOT NULL,
  `process_type` varchar(32) NOT NULL,
  `project_code` varchar(32) DEFAULT NULL,
  `project_name` varchar(64) DEFAULT NULL,
  `used_auantity` int(11) DEFAULT NULL,
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
  `max_use_num` int(11) DEFAULT NULL,
  `software_expired_time` datetime DEFAULT NULL,
  `version` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of software
-- ----------------------------

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
  `project_code` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `user_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of special_role
-- ----------------------------

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
