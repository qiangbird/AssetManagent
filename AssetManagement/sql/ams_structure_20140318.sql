-- MySQL dump 10.13  Distrib 5.1.53, for Win32 (ia32)
--
-- Host: localhost    Database: ams
-- ------------------------------------------------------
-- Server version	5.1.53-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audit_file`
--

DROP TABLE IF EXISTS `audit_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `custom_column`
--

DROP TABLE IF EXISTS `custom_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer_group`
--

DROP TABLE IF EXISTS `customer_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customized_filter`
--

DROP TABLE IF EXISTS `customized_filter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customized_property`
--

DROP TABLE IF EXISTS `customized_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customized_view`
--

DROP TABLE IF EXISTS `customized_view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customized_view_item`
--

DROP TABLE IF EXISTS `customized_view_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device`
--

DROP TABLE IF EXISTS `device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `device_subtype`
--

DROP TABLE IF EXISTS `device_subtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `device_subtype` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `subtype_name` varchar(64) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inconsistent`
--

DROP TABLE IF EXISTS `inconsistent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `room` varchar(32) NOT NULL,
  `site` varchar(32) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `machine`
--

DROP TABLE IF EXISTS `machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `monitor`
--

DROP TABLE IF EXISTS `monitor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operation_log`
--

DROP TABLE IF EXISTS `operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `other_assets`
--

DROP TABLE IF EXISTS `other_assets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `page_size`
--

DROP TABLE IF EXISTS `page_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `property_template`
--

DROP TABLE IF EXISTS `property_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchase_item`
--

DROP TABLE IF EXISTS `purchase_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` varchar(32) NOT NULL,
  `created_time` datetime NOT NULL,
  `is_expired` bit(1) NOT NULL DEFAULT b'0',
  `updated_time` datetime NOT NULL,
  `role_name` varchar(75) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_name` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role_authority`
--

DROP TABLE IF EXISTS `role_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_authority` (
  `role_id` varchar(32) NOT NULL,
  `authority_id` varchar(32) NOT NULL,
  KEY `FKFBF4E6BAB63EBB7F` (`role_id`),
  KEY `FKFBF4E6BA7FCA8C75` (`authority_id`),
  CONSTRAINT `FKFBF4E6BA7FCA8C75` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FKFBF4E6BAB63EBB7F` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `scheduler_task`
--

DROP TABLE IF EXISTS `scheduler_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `software`
--

DROP TABLE IF EXISTS `software`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `special_role`
--

DROP TABLE IF EXISTS `special_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `todo`
--

DROP TABLE IF EXISTS `todo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transfer_log`
--

DROP TABLE IF EXISTS `transfer_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_custom_column`
--

DROP TABLE IF EXISTS `user_custom_column`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_page_size`
--

DROP TABLE IF EXISTS `user_page_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_page_size` (
  `user_id` varchar(32) NOT NULL,
  `page_size_id` varchar(32) NOT NULL,
  KEY `FK92165C3DF9B52E58` (`page_size_id`),
  KEY `FK92165C3D5B697F5F` (`user_id`),
  CONSTRAINT `FK92165C3D5B697F5F` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK92165C3DF9B52E58` FOREIGN KEY (`page_size_id`) REFERENCES `page_size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  KEY `FK143BF46AB63EBB7F` (`role_id`),
  KEY `FK143BF46A5B697F5F` (`user_id`),
  CONSTRAINT `FK143BF46A5B697F5F` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK143BF46AB63EBB7F` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-03-18  9:12:00
