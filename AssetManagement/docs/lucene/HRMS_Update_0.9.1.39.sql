use hrms;

DROP table if exists raw_employee_annual_leave; 
CREATE TABLE `raw_employee_annual_leave` (
  `ies_id` varchar(36) DEFAULT NULL,
  `employee_id` varchar(10) DEFAULT NULL,
  `annual_days` float DEFAULT NULL,
  `is_extended_annual_leave` varchar(1) DEFAULT NULL,
  `extended_annual_days` float DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP table if exists employee_annual_leave4; 
CREATE TABLE `employee_annual_leave4` (
  `annual_leave_id` int(11) NOT NULL AUTO_INCREMENT,
  `person_id` int(11) NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `extended_annual_days` float DEFAULT NULL,
  `expired_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `ies_id` varchar(36) NOT NULL,
  `annual_days` int(10) unsigned DEFAULT NULL,
  `is_extended_annual_leave` bit(1) DEFAULT NULL,
  PRIMARY KEY (`annual_leave_id`),
  KEY `FKD7BDFAEC47B12656` (`person_id`),
  CONSTRAINT `FKD7BDFAEC47B12656` FOREIGN KEY (`person_id`) REFERENCES `employee` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
DROP table if exists edu_type; 
CREATE TABLE `edu_type` (
  `edu_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `edu_type_cn` varchar(30) NOT NULL,
  `edu_type_en` varchar(30) NOT NULL,
  `expired_date` datetime DEFAULT NULL,
  PRIMARY KEY (`edu_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP table if exists edu_level_type; 
CREATE TABLE `edu_level_type` (
  `edu_level_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `edu_level_type_cn` varchar(30) NOT NULL,
  `edu_level_type_en` varchar(30) NOT NULL,
  `expired_date` datetime DEFAULT NULL,
  PRIMARY KEY (`edu_level_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table education_info add column edu_type_id int(11) default null;
alter table education_info add column is_yiye tinyint(1) defalut '0';
--alter table education_info add column edu_level_type_id int(11) default null;

alter table education_info add foreign key(edu_type_id) references edu_type(edu_type_id) on delete set null on update set null;
--alter table education_info add foreign key(edu_level_type_id) references edu_level_type(edu_level_type_id) on delete set null on update set null;

INSERT INTO `permission`(pageName_cn, pageName_en, categoryName_cn, categoryName_en,groupName_cn, groupName_en, code, expired_date) VALUES 
	('教育类型','Education Type','基本信息管理','Basic Info','基础信息','Basic Properties','EduType',NULL);

--INSERT INTO `permission`(pageName_cn, pageName_en, categoryName_cn, categoryName_en,groupName_cn, groupName_en, code, expired_date) VALUES ('学历类型','Education Level Type','基本信息管理','Basic Info','基础信息','Basic Properties','EduLevelType',NULL);

INSERT INTO edu_type(edu_type_cn,edu_type_en) values('民办教育','Civilian-run Education');
INSERT INTO edu_type(edu_type_cn,edu_type_en) values('成人自考','Adult Self-taught');
INSERT INTO edu_type(edu_type_cn,edu_type_en) values('网络教育','Web Education');
INSERT INTO edu_type(edu_type_cn,edu_type_en) values('三级学院','Tertiary Colleges');
INSERT INTO edu_type(edu_type_cn,edu_type_en) values('在职攻读','In-service Study');
INSERT INTO edu_type(edu_type_cn,edu_type_en) values('业余大学','Spare-Time University');

select * from edu_type;
--INSERT INTO edu_level_type(edu_level_type_cn,edu_level_type_en) values('双学士','Double Bachelor');
--INSERT INTO edu_level_type(edu_level_type_cn,edu_level_type_en) values('工商管理硕士','MBA');
--INSERT INTO edu_level_type(edu_level_type_cn,edu_level_type_en) values('辅修',' Minor Major');
--INSERT INTO edu_level_type(edu_level_type_cn,edu_level_type_en) values('肄业','YiYe');
--select * from edu_level_type;

alter table edu_level add column edu_grade;


select * from edu_level;
update edu_level set edu_grade=13  where edu_level_id = 1 and edu_level_en= 'Postdoctoral';
update edu_level set edu_grade=12 where edu_level_id=2 and edu_level_en='Doctor';
update edu_level set edu_grade=11 where edu_level_id=12 and edu_level_en='MBA';
update edu_level set edu_grade=10 where edu_level_id=3 and edu_level_en='Master';
update edu_level set edu_grade=9 where edu_level_id=5 and edu_level_en='Double Bachelor';
update edu_level set edu_grade=8 where edu_level_id=4 and edu_level_en='Bachelor';
update edu_level set edu_grade=7 where edu_level_id=6 and edu_level_en='Diploma';
update edu_level set edu_grade=6 where edu_level_id=7 and edu_level_en='Technical Secondary School';
update edu_level set edu_grade=5 where edu_level_id=8 and edu_level_en='Senior High School';
update edu_level set edu_grade=4 where edu_level_id=9 and edu_level_en='Junior High School';
insert into edu_level(edu_level_cn,edu_level_en, edu_grade) values('小学','Primary School',3);
update edu_level set edu_grade=2 where edu_level_id=13 and edu_level_en='Minor Major';
update edu_level set edu_grade=1 where edu_level_id=11 and edu_level_en='Other';
update edu_level set expired_date= now() where edu_level_id=10 and edu_level_en='YiYe';




--update edu_level set edu_grade=10 where edu_level_id=2 and edu_level_en='Doctor';
--update edu_level set edu_grade=10 where edu_level_id=2 and edu_level_en='Master';
--update edu_level set edu_grade=8 where edu_level_id=4 and edu_level_en='Bachelor';
--update edu_level set edu_grade=8 where edu_level_id=5 and edu_level_en='Double Bachelor';
--update edu_level set edu_grade=7 where edu_level_id=6 and edu_level_en='Diploma';
--update edu_level set edu_grade=6 where edu_level_id=7 and edu_level_en='Technical Secondary School';
--update edu_level set edu_grade=5 where edu_level_id=8 and edu_level_en='Senior High School';
--update edu_level set edu_grade=4 where edu_level_id=9 and edu_level_en='Junior High School';
--update edu_level set edu_grade=2 where edu_level_id=10 and edu_level_en='YiYe';
--update edu_level set edu_grade=1 where edu_level_id=11 and edu_level_en='Other';
--update edu_level set edu_grade=9 where edu_level_id=12 and edu_level_en='MBA';
--update edu_level set edu_grade=3 where edu_level_id=13 and edu_level_en='Minor Major';

--定时器
INSERT INTO timer_service_configuration value(0,'HighestEduUpdateService','2013-07-18 06:30:00','100800',true,'最高学历修改信息','',now(),null);

