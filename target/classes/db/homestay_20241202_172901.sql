-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: homestay
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `house_tag`
--

DROP TABLE IF EXISTS `house_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `house_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `house_id` bigint DEFAULT NULL COMMENT '房源ID',
  `tag_id` bigint DEFAULT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_house_tag` (`house_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `house_tag`
--

LOCK TABLES `house_tag` WRITE;
/*!40000 ALTER TABLE `house_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `house_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_admin`
--

DROP TABLE IF EXISTS `sys_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态(0:禁用 1:正常)',
  `department` varchar(50) DEFAULT NULL COMMENT '部门',
  `position` varchar(50) DEFAULT NULL COMMENT '职位',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  `remark` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_admin`
--

LOCK TABLES `sys_admin` WRITE;
/*!40000 ALTER TABLE `sys_admin` DISABLE KEYS */;
INSERT INTO `sys_admin` VALUES (1,'admin001','password123','Admin One','admin1@example.com','13800138001','http://example.com/avatar1.jpg',1,'IT','System Administrator','2024-11-30 18:46:38','2024-11-30 18:46:38',0,'Initial admin account'),(2,'admin002','password456','Admin Two','admin2@example.com','13800138002','http://example.com/avatar2.jpg',1,'HR','HR Manager','2024-11-30 18:46:38','2024-11-30 18:46:38',0,'HR department admin'),(3,'admin003','password789','Admin Three','admin3@example.com','13800138003','http://example.com/avatar3.jpg',0,'Finance','Financial Officer','2024-11-30 18:46:38','2024-11-30 18:46:38',0,'Disabled account for testing');
/*!40000 ALTER TABLE `sys_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_merchant`
--

DROP TABLE IF EXISTS `sys_merchant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_merchant` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商家ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `status` tinyint NOT NULL DEFAULT '2' COMMENT '状态(0:禁用 1:正常 2:待审核)',
  `business_name` varchar(100) NOT NULL COMMENT '商家名称',
  `business_license` varchar(50) NOT NULL COMMENT '营业执照号',
  `business_address` varchar(255) NOT NULL COMMENT '商家地址',
  `contact_person` varchar(50) NOT NULL COMMENT '联系人',
  `audit_remark` varchar(255) DEFAULT NULL COMMENT '审核备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  `id_card` varchar(1000) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_business_license` (`business_license`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2000004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商家表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_merchant`
--

LOCK TABLES `sys_merchant` WRITE;
/*!40000 ALTER TABLE `sys_merchant` DISABLE KEYS */;
INSERT INTO `sys_merchant` VALUES (2000001,'merchant001','$2a$10$n1A.Um4WD8Iv3SQQtiN/JODqXLSICVk7RKvpiQlOnEA749W4bVFE6','Merchant One','merchant1@example.com','13900139001','http://localhost:8080/upload/merchant_avatar/2024/11/30/ac69f0eb99254609abc87139b22d0b6c.jpg',1,'Store A','BL123456789','123 Main St, City','John Doe','Pending approval','2024-11-30 18:46:38','2024-11-30 18:51:58',0,'ID0012345678'),(2000002,'merchant002','merchantpassword456','Merchant Two','merchant2@example.com','13900139002','http://example.com/avatar2.jpg',1,'Store B','BL987654321','456 Elm St, City','Jane Smith','Approved','2024-11-30 18:46:38','2024-11-30 18:46:38',0,'ID9876543210'),(2000003,'merchant003','merchantpassword789','Merchant Three','merchant3@example.com','13900139003','http://example.com/avatar3.jpg',0,'Store C','BL111223344','789 Pine St, City','Alice Johnson','Rejected','2024-11-30 18:46:38','2024-11-30 18:46:38',0,'ID1029384756');
/*!40000 ALTER TABLE `sys_merchant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像URL',
  `gender` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '居住地址',
  `bio` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '个人简介',
  `interests` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '兴趣爱好',
  `preferences` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '偏好设置',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态(0:禁用 1:正常)',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最后登录IP',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:未删除 1:已删除)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  UNIQUE KEY `uk_phone` (`phone`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=5000004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (5000001,'user001','$2a$10$g4FFu789OiJJGcjKWkDuoOnrtO5ZqOqKgf0g2RzAvC9ZF4wBCWsUy','User One','user1@example.com','13700137001','http://localhost:8080/upload/avatar/2024/12/01/1e26ab9fe5174e9884ea12c8fc5e24bf.jpg','other','1990-01-01 00:00:00','123 Maple St, City','I am a tech enthusiast','Reading, Coding','Dark Mode',1,'2024-11-01 12:00:00','192.168.1.1','2024-11-30 18:46:38','2024-12-01 12:31:05',7,0),(5000002,'user002','userpassword456','User Two','user2@example.com','13700137002','http://example.com/avatar2.jpg','Female','1992-02-02 00:00:00','456 Oak St, City','Love traveling and photography','Photography, Traveling','Light Mode',1,'2024-11-05 14:30:00','192.168.1.2','2024-11-30 18:46:38','2024-11-30 18:46:38',0,0),(5000003,'user003','userpassword789','User Three','user3@example.com','13700137003','http://example.com/avatar3.jpg','Male','1994-03-03 00:00:00','789 Birch St, City','Gamer and movie lover','Gaming, Movies','Dark Mode',0,'2024-10-28 16:45:00','192.168.1.3','2024-11-30 18:46:38','2024-11-30 18:46:38',0,0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_admin`
--

DROP TABLE IF EXISTS `t_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_admin` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `department` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '部门',
  `position` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '职位',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态(0:待审核 1:正常 2:禁用)',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_admin`
--

LOCK TABLES `t_admin` WRITE;
/*!40000 ALTER TABLE `t_admin` DISABLE KEYS */;
INSERT INTO `t_admin` VALUES (1,'admin1','{bcrypt}$2a$10$rDjyD8F6yO.pxgkCnVU4YehzuR1wuQI0QzLXx.tMqo1.H8Yvq2vPW','张管理','admin1@example.com','13800138001','运营部','运营经理',1,NULL,'2024-11-14 10:16:15','2024-11-14 10:16:15',0),(2,'admin2','{bcrypt}$2a$10$rDjyD8F6yO.pxgkCnVU4YehzuQI0QzLXx.tMqo1.H8Yvq2vPW','李运营','admin2@example.com','13800138002','客服部','客服主管',0,NULL,'2024-11-14 10:16:15','2024-11-14 10:16:15',0),(3,'admin3','{bcrypt}$2a$10$rDjyD8F6yO.pxgkCnVU4YehzuQI0QzLXx.tMqo1.H8Yvq2vPW','王客服','admin3@example.com','13800138003','技术部','技术经理',0,NULL,'2024-11-14 10:16:15','2024-11-14 10:16:15',0);
/*!40000 ALTER TABLE `t_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_all_order`
--

DROP TABLE IF EXISTS `t_all_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_all_order` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单ID',
  `order_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单编号',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `house_id` bigint NOT NULL COMMENT '房源ID',
  `house_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房源名称',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户姓名',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系电话',
  `check_in` date NOT NULL COMMENT '入住日期',
  `check_out` date NOT NULL COMMENT '离店日期',
  `nights` int NOT NULL COMMENT '入住天数',
  `guests` int NOT NULL COMMENT '入住人数',
  `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `status` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:未删除 1:已删除)',
  PRIMARY KEY (`id`),
  KEY `idx_user_name` (`user_name`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_check_in` (`check_in`),
  KEY `idx_order_no` (`order_no`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_all_order`
--

LOCK TABLES `t_all_order` WRITE;
/*!40000 ALTER TABLE `t_all_order` DISABLE KEYS */;
INSERT INTO `t_all_order` VALUES ('1','ORD001',1001,5,'海景房A',5000001,'张三','13800138000','2024-12-01','2024-12-03',2,2,300.00,'PAID','2024-11-29 10:39:25','2024-11-29 10:39:59','2024-12-01 15:33:22',0),('10','ORD010',2000001,5,'House J',5000001,'User 10','1234567899','2024-05-11','2024-05-13',2,4,2100.00,'PAID','2024-05-11 12:30:00','2024-05-15 15:00:00','2024-12-01 15:33:22',0),('100','ORD001',2000001,101,'House A',5000001,'User 1','1234567890','2024-01-01','2024-01-03',2,3,1500.00,'PAID','2024-01-01 12:00:00','2024-01-05 12:00:00','2024-12-01 15:33:22',0),('11','ORD011',2000001,111,'House K',5000001,'User 11','1234567890','2024-06-01','2024-06-03',2,2,1750.00,'PAID','2024-06-01 08:00:00','2024-06-05 09:00:00','2024-12-01 15:33:22',0),('12','ORD012',2000001,112,'House L',5000001,'User 12','1234567891','2024-06-12','2024-06-14',2,3,1850.00,'PAID','2024-06-12 10:00:00','2024-06-15 12:00:00','2024-12-01 15:33:22',0),('13','ORD013',2000001,113,'House M',5000001,'User 13','1234567892','2024-07-01','2024-07-05',4,2,2200.00,'PAID','2024-07-01 07:30:00','2024-07-07 08:00:00','2024-12-01 15:33:22',0),('14','ORD014',2000001,114,'House N',5000001,'User 14','1234567893','2024-07-10','2024-07-12',2,3,2100.00,'PAID','2024-07-10 14:30:00','2024-07-15 16:00:00','2024-12-01 15:33:22',0),('15','ORD015',2000001,115,'House O',5000001,'User 15','1234567894','2024-08-02','2024-08-04',2,3,2500.00,'PAID','2024-08-02 09:00:00','2024-08-05 11:30:00','2024-12-01 15:33:22',0),('16','ORD016',2000001,116,'House P',5000001,'User 16','1234567895','2024-08-15','2024-08-17',3,2,2400.00,'PAID','2024-08-15 10:30:00','2024-08-18 12:00:00','2024-12-01 15:33:22',0),('17','ORD017',2000001,117,'House Q',5000001,'User 17','1234567896','2024-09-01','2024-09-03',2,1,1700.00,'PAID','2024-09-01 12:00:00','2024-09-05 13:00:00','2024-12-01 15:33:22',0),('18','ORD018',2000001,118,'House R',1018,'User 18','1234567897','2024-09-10','2024-09-12',2,3,2300.00,'PAID','2024-09-10 16:00:00','2024-09-15 17:30:00','2024-12-01 15:33:22',0),('19','ORD019',2000001,119,'House S',5000001,'User 19','1234567898','2024-10-05','2024-10-07',2,2,1600.00,'PAID','2024-10-05 14:00:00','2024-10-10 15:00:00','2024-12-01 15:33:22',0),('2','ORD001',2000001,222,'海景房A',5000001,'张三','13800138000','2024-12-01','2024-12-03',2,2,300.00,'PAID','2024-11-29 10:39:59','2024-11-29 10:39:59','2024-12-01 15:33:22',0),('20','ORD020',2000001,120,'House T',5000001,'User 20','1234567899','2024-10-20','2024-10-22',2,4,2800.00,'PAID','2024-10-20 13:30:00','2024-10-25 16:30:00','2024-12-01 15:33:22',0),('21','ORD021',2000001,121,'House U',5000001,'User 21','1234567890','2024-11-01','2024-11-03',2,2,2100.00,'PAID','2024-11-01 09:00:00','2024-11-05 10:00:00','2024-12-01 15:33:22',0),('22','ORD022',2000001,122,'House V',5000001,'User 22','1234567891','2024-11-15','2024-11-17',2,3,2300.00,'PAID','2024-11-15 14:00:00','2024-11-20 15:30:00','2024-12-01 15:33:22',0),('244','ORD002',2000001,102,'House B',5000001,'User 2','1234567891','2024-01-10','2024-01-12',2,2,1800.00,'PAID','2024-01-10 13:00:00','2024-01-15 14:00:00','2024-12-01 15:33:22',0),('3','ORD003',2000001,103,'House C',5000001,'User 3','1234567892','2024-02-01','2024-02-05',4,2,2200.00,'PAID','2024-02-01 12:30:00','2024-02-05 16:00:00','2024-12-01 15:33:22',0),('4','ORD004',2000001,104,'House D',5000001,'User 4','1234567893','2024-02-15','2024-02-18',3,4,2400.00,'PAID','2024-02-15 08:00:00','2024-02-20 10:30:00','2024-12-01 15:33:22',0),('5','ORD005',2000001,105,'House E',5000001,'User 5','1234567894','2024-03-05','2024-03-07',2,1,1300.00,'PAID','2024-03-05 10:00:00','2024-03-10 12:00:00','2024-12-01 15:33:22',0),('6','ORD006',2000001,106,'House F',1006,'User 6','1234567895','2024-03-12','2024-03-14',2,3,1700.00,'PAID','2024-03-12 14:00:00','2024-03-15 16:00:00','2024-12-01 15:33:22',0),('7','ORD007',2000001,107,'House G',1007,'User 7','1234567896','2024-04-02','2024-04-05',3,2,1600.00,'PAID','2024-04-02 09:00:00','2024-04-10 11:00:00','2024-12-01 15:33:22',0),('8','ORD008',2000001,108,'House H',1008,'User 8','1234567897','2024-04-20','2024-04-22',2,4,2000.00,'PAID','2024-04-20 17:00:00','2024-04-23 18:30:00','2024-12-01 15:33:22',0),('9','ORD009',2000001,109,'House I',1009,'User 9','1234567898','2024-05-05','2024-05-07',2,3,1900.00,'PAID','2024-05-05 13:00:00','2024-05-10 14:30:00','2024-12-01 15:33:22',0),('ORD202312010001','',2000001,1,'亚龙湾温泉别墅',1,'周八','13800138006','2023-12-24','2023-12-26',2,6,5176.00,'PAID','2024-11-14 10:11:25','2023-12-23 16:40:00','2024-12-01 15:33:22',0),('ORD202312010002','',2000001,2,'三里屯SOHO公寓',2,'吴九','13800138007','2023-12-25','2023-12-28',3,2,2397.00,'PAID','2024-11-14 10:11:25','2023-12-24 09:15:00','2024-12-01 15:33:22',0),('ORD202312020001','',2000001,3,'钱塘江观景公寓',3,'郑十','13800138008','2023-12-30','2024-01-01',2,2,1376.00,'PAID','2024-11-14 10:11:25','2023-12-29 11:20:00','2024-12-01 15:33:22',0),('ORD202401010001','',2000001,4,'椰梦长廊海景公寓',4,'张三','13800138001','2024-01-01','2024-01-03',2,2,1376.00,'PAID','2024-11-14 10:11:25','2024-01-01 10:30:00','2024-12-01 15:33:22',0),('ORD202401010002','',2000001,5,'外滩江景套房',5,'李四','13800138002','2024-01-05','2024-01-07',2,3,2576.00,'PAID','2024-11-14 10:11:25','2024-01-01 14:20:00','2024-12-01 15:33:22',0),('ORD202401020001','',2000001,6,'故宫旁四合院',8,'王五','13800138003','2024-01-10','2024-01-12',2,4,3976.00,'PAID','2024-11-14 10:11:25',NULL,'2024-12-01 15:33:22',0),('ORD202401020002','',2000001,0,'宽窄巷子民宿',6,'赵六','13800138004','2024-01-15','2024-01-16',1,2,468.00,'PAID','2024-11-14 10:11:25',NULL,'2024-12-01 15:33:22',0),('ORD202401020004','',2000001,0,'西湖湖景别墅',7,'孙七','13800138005','2024-01-20','2024-01-22',2,4,4176.00,'PAID','2024-11-14 10:11:25',NULL,'2024-12-01 15:33:22',0),('ORD202401020023','1',2000001,8,'广西科技大学',1,'周九','18589826376','2024-11-29','2024-11-29',3,2,2131.00,'3','2024-11-29 10:35:28',NULL,'2024-12-01 15:29:02',0);
/*!40000 ALTER TABLE `t_all_order` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_update_trend_and_income` AFTER INSERT ON `t_all_order` FOR EACH ROW BEGIN

    -- 只处理已支付的订单

    IF NEW.status = 'PAID' THEN

        -- 根据支付时间来提取年份和月份，如果支付时间为空，则使用创建时间

        SET @order_year = IFNULL(YEAR(NEW.pay_time), YEAR(NEW.create_time));

        SET @order_month = LPAD(IFNULL(MONTH(NEW.pay_time), MONTH(NEW.create_time)), 2, '0');



        -- 更新 t_merchant_trend_data 表

        IF EXISTS (

            SELECT 1 FROM t_merchant_trend_data

            WHERE merchant_id = NEW.merchant_id AND years = @order_year AND months = @order_month

        ) THEN

            -- 如果商家趋势数据已存在，则更新

            UPDATE t_merchant_trend_data

            SET

                all_order = all_order + 1, -- 订单数增加

                all_price = all_price + NEW.total_amount, -- 收入增加

                update_time = NOW()

            WHERE merchant_id = NEW.merchant_id AND years = @order_year AND months = @order_month;

        ELSE

            -- 如果商家趋势数据不存在，则插入新记录

            INSERT INTO t_merchant_trend_data (

                merchant_id, years, months, all_order, all_price, create_time, update_time

            ) VALUES (

                         NEW.merchant_id, @order_year, @order_month, 1, NEW.total_amount, NOW(), NOW()

                     );

        END IF;



        -- 更新 t_merchant_income 表

        IF EXISTS (

            SELECT 1 FROM t_merchant_income

            WHERE merchant_id = NEW.merchant_id

        ) THEN

            -- 如果商家收入记录已存在，则更新

            UPDATE t_merchant_income

            SET

                all_income = all_income + NEW.total_amount, -- 收入增加

                update_time = NOW()

            WHERE merchant_id = NEW.merchant_id;

        ELSE

            -- 如果商家收入记录不存在，则插入新记录

            INSERT INTO t_merchant_income (

                merchant_id, all_income, create_time, update_time

            ) VALUES (

                         NEW.merchant_id, NEW.total_amount, NOW(), NOW()

                     );

        END IF;

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `t_booking`
--

DROP TABLE IF EXISTS `t_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_booking` (
  `id` varchar(20) NOT NULL COMMENT '预订ID',
  `house_id` bigint NOT NULL COMMENT '房源ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `check_in_time` datetime NOT NULL COMMENT '入住时间',
  `check_out_time` datetime NOT NULL COMMENT '退房时间',
  `guest_count` int NOT NULL DEFAULT '1' COMMENT '入住人数',
  `contact_name` varchar(50) NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(20) NOT NULL COMMENT '联系人电话',
  `special_requests` text COMMENT '特殊要求',
  `total_price` decimal(10,2) NOT NULL COMMENT '订单总价',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '订单状态(0:待支付 1:已支付 2:已取消 3:已完成)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='预订表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_booking`
--

LOCK TABLES `t_booking` WRITE;
/*!40000 ALTER TABLE `t_booking` DISABLE KEYS */;
INSERT INTO `t_booking` VALUES ('202403210001',1,1,'2024-03-25 14:00:00','2024-03-27 12:00:00',2,'张三','13800138001','希望有加床服务',1288.00,1,'2024-11-17 09:42:34','2024-11-17 09:42:34',0,0),('202403210002',2,2,'2024-03-28 14:00:00','2024-03-30 12:00:00',3,'李四','13800138002','需要接送机服务',2588.00,1,'2024-11-17 09:42:34','2024-11-17 09:42:34',0,0),('202403210003',3,3,'2024-04-01 14:00:00','2024-04-03 12:00:00',2,'王五','13800138003',NULL,1688.00,0,'2024-11-17 09:42:34','2024-11-17 09:42:34',0,0),('202403210004',4,4,'2024-04-05 14:00:00','2024-04-07 12:00:00',4,'赵六','13800138004','需要婴儿床',3288.00,0,'2024-11-17 09:42:34','2024-11-17 09:42:34',0,0),('202403210005',5,5,'2024-04-10 14:00:00','2024-04-12 12:00:00',2,'孙七','13800138005',NULL,1988.00,2,'2024-11-17 09:42:34','2024-11-17 09:42:34',0,0),('202403210006',6,6,'2024-03-15 14:00:00','2024-03-17 12:00:00',3,'周八','13800138006','需���准备接待晚餐',2688.00,3,'2024-11-17 09:42:34','2024-11-17 09:42:34',0,0);
/*!40000 ALTER TABLE `t_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_cities`
--

DROP TABLE IF EXISTS `t_cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_cities` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '城市编码',
  `name` varchar(50) NOT NULL COMMENT '城市名称',
  `level` tinyint NOT NULL DEFAULT '1' COMMENT '行政级别：1-省份，2-直辖市',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='城市表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_cities`
--

LOCK TABLES `t_cities` WRITE;
/*!40000 ALTER TABLE `t_cities` DISABLE KEYS */;
INSERT INTO `t_cities` VALUES (1,'BJ','北京市',2,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(2,'SH','上海市',2,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(3,'TJ','天津市',2,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(4,'CQ','重庆市',2,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(5,'GD','广东省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(6,'JS','江苏省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(7,'ZJ','浙江省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(8,'SC','四川省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(9,'FJ','福建省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(10,'HN','湖南省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(11,'HB','湖北省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(12,'SD','山东省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(13,'AH','安徽省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(14,'HE','河北省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(15,'SX','山西省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(16,'JX','江西省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(17,'GX','广西壮族自治区',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(18,'YN','云南省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(19,'GZ','贵州省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(20,'NM','内蒙古自治区',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(21,'LN','辽宁省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(22,'JL','吉林省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(23,'HL','黑龙江省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(24,'SN','陕西省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(25,'GS','甘肃省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(26,'QH','青海省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(27,'XJ','新疆维吾尔自治区',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(28,'XZ','西藏自治区',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(29,'NX','宁夏回族自治区',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(30,'HI','海南省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(31,'TW','台湾省',1,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(32,'HK','香港特别行政区',2,'2024-11-29 10:26:07','2024-11-30 05:51:02',1),(33,'MO','澳门特别行政区',2,'2024-11-29 10:26:07','2024-11-30 05:51:02',1);
/*!40000 ALTER TABLE `t_cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_districts`
--

DROP TABLE IF EXISTS `t_districts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_districts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL COMMENT '地区编码',
  `name` varchar(50) NOT NULL COMMENT '地区名称',
  `city_code` varchar(50) NOT NULL COMMENT '所属城市编码',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` int DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_code` (`code`),
  KEY `idx_city_code` (`city_code`),
  CONSTRAINT `fk_city_code` FOREIGN KEY (`city_code`) REFERENCES `t_cities` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='地区表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_districts`
--

LOCK TABLES `t_districts` WRITE;
/*!40000 ALTER TABLE `t_districts` DISABLE KEYS */;
INSERT INTO `t_districts` VALUES (1,'BJ-CP','昌平区','BJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(2,'BJ-HD','海淀区','BJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(3,'BJ-CY','朝阳区','BJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(4,'BJ-DC','东城区','BJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(5,'BJ-XC','西城区','BJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(6,'BJ-FT','丰台区','BJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(7,'BJ-SY','顺义区','BJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(8,'SH-PD','浦东新区','SH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(9,'SH-XH','徐汇区','SH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(10,'SH-JA','静安区','SH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(11,'SH-HP','黄浦区','SH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(12,'SH-BS','宝山区','SH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(13,'SH-MH','闵行区','SH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(14,'SH-PT','普陀区','SH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(15,'TJ-HD','河东区','TJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(16,'TJ-HX','河西区','TJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(17,'TJ-NK','南开区','TJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(18,'TJ-HB','河北区','TJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(19,'TJ-HQ','红桥区','TJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(20,'CQ-YZ','渝中区','CQ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(21,'CQ-JB','江北区','CQ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(22,'CQ-NA','南岸区','CQ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(23,'CQ-SP','沙坪坝区','CQ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(24,'CQ-BB','北碚区','CQ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(25,'GD-GZ','广州市','GD','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(26,'GD-SZ','深圳市','GD','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(27,'GD-DG','东莞市','GD','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(28,'GD-FS','佛山市','GD','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(29,'GD-ZH','珠海市','GD','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(30,'GD-HZ','惠州市','GD','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(31,'GD-ZS','中山市','GD','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(32,'JS-NJ','南京市','JS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(33,'JS-SZ','苏州市','JS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(34,'JS-WX','无锡市','JS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(35,'JS-XZ','徐州市','JS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(36,'JS-NT','南通市','JS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(37,'JS-YZ','扬州市','JS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(38,'JS-ZJ','镇江市','JS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(39,'ZJ-HZ','杭州市','ZJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(40,'ZJ-NB','宁波市','ZJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(41,'ZJ-WZ','温州市','ZJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(42,'ZJ-JX','嘉兴市','ZJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(43,'ZJ-SX','绍兴市','ZJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(44,'SC-CD','成都市','SC','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(45,'SC-MY','绵阳市','SC','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(46,'SC-DY','德阳市','SC','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(47,'SC-MS','眉山市','SC','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(48,'SC-LS','乐山市','SC','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(49,'SC-YB','宜宾市','SC','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(50,'SC-ZG','自贡市','SC','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(51,'FJ-FZ','福州市','FJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(52,'FJ-XM','厦门市','FJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(53,'FJ-QZ','泉州市','FJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(54,'FJ-ZZ','漳州市','FJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(55,'FJ-PT','莆田市','FJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(56,'HN-CS','长沙市','HN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(57,'HN-ZZ','株洲市','HN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(58,'HN-YY','岳阳市','HN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(59,'HN-CZ','常德市','HN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(60,'HN-YZ','永州市','HN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(61,'LN-SY','沈阳市','LN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(62,'LN-DL','大连市','LN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(63,'LN-AS','鞍山市','LN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(64,'LN-FS','抚顺市','LN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(65,'LN-BX','本溪市','LN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(66,'JL-CC','长春市','JL','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(67,'JL-JL','吉林市','JL','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(68,'JL-SP','四平市','JL','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(69,'JL-BC','白城市','JL','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(70,'JL-YJ','延吉市','JL','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(71,'HL-HEB','哈尔滨市','HL','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(72,'HL-QQH','齐齐哈尔市','HL','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(73,'HL-JMS','佳木斯市','HL','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(74,'HL-MDJ','牡丹江市','HL','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(75,'HL-DQ','大庆市','HL','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(76,'SN-XA','西安市','SN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(77,'SN-BJ','宝鸡市','SN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(78,'SN-XY','咸阳市','SN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(79,'SN-WN','渭南市','SN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(80,'SN-YL','榆林市','SN','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(81,'GS-LZ','兰州市','GS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(82,'GS-JYG','嘉峪关市','GS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(83,'GS-BY','白银市','GS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(84,'GS-TS','天水市','GS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(85,'GS-WW','武威市','GS','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(86,'QH-XN','西宁市','QH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(87,'QH-HX','海西州','QH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(88,'QH-HB','海北州','QH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(89,'QH-HN','海南州','QH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(90,'QH-GL','果洛州','QH','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(91,'XJ-WLQ','乌鲁木齐市','XJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(92,'XJ-KLY','克拉玛依市','XJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(93,'XJ-TL','吐鲁番市','XJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(94,'XJ-HM','哈密市','XJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(95,'XJ-KS','喀什市','XJ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(96,'XZ-LS','拉萨市','XZ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(97,'XZ-RKZ','日喀则市','XZ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(98,'XZ-CDD','昌都市','XZ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(99,'XZ-LZ','林芝市','XZ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(100,'XZ-NQ','那曲市','XZ','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(101,'NX-YC','银川市','NX','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(102,'NX-SZ','石嘴山市','NX','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(103,'NX-WZ','吴忠市','NX','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(104,'NX-GY','固原市','NX','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(105,'NX-ZW','中卫市','NX','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(106,'HI-HK','海口市','HI','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(107,'HI-SY','三亚市','HI','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(108,'HI-SZ','三沙市','HI','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(109,'HI-DA','儋州市','HI','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(110,'HI-WC','文昌市','HI','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(111,'TW-TB','台北市','TW','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(112,'TW-KH','高雄市','TW','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(113,'TW-TC','台中市','TW','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(114,'TW-TN','台南市','TW','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(115,'TW-TY','桃园市','TW','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(116,'HK-CW','中西区','HK','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(117,'HK-WC','湾仔区','HK','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(118,'HK-KC','九龙城区','HK','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(119,'HK-ST','沙田区','HK','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(120,'HK-YL','元朗区','HK','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(121,'MO-MP','澳门半岛','MO','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(122,'MO-TK','氹仔','MO','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL),(123,'MO-CL','路环','MO','2024-11-29 10:26:07','2024-11-30 05:51:13',1,NULL,NULL);
/*!40000 ALTER TABLE `t_districts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_facility`
--

DROP TABLE IF EXISTS `t_facility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_facility` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '设施ID',
  `name` varchar(50) NOT NULL COMMENT '设施名称',
  `icon` varchar(100) DEFAULT NULL COMMENT '设施图标',
  `description` varchar(255) DEFAULT NULL COMMENT '设施描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设施信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_facility`
--

LOCK TABLES `t_facility` WRITE;
/*!40000 ALTER TABLE `t_facility` DISABLE KEYS */;
INSERT INTO `t_facility` VALUES (1,'WiFi','Connection','免费高速WiFi'),(2,'空调','WindPower','中央空调'),(3,'停车位','Van','免费专属停车位'),(4,'厨房','KnifeFork','配备厨具的开放式厨房'),(5,'洗衣机','Box','滚筒洗衣机'),(6,'电视','Monitor','智能电视'),(7,'暖气','Sunny','地暖/暖气'),(8,'热水','WaterCup','24小时热水'),(9,'电梯','UploadOne','电梯直达'),(10,'游泳池','Swimming','私家泳池'),(11,'健身房','Football','健身设备'),(12,'阳台','TakeawayBox','观景阳台'),(13,'烘干机','Box','烘干机'),(14,'门禁','Lock','智能门禁'),(15,'冰箱','Box','大容量冰箱');
/*!40000 ALTER TABLE `t_facility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_facility_dict`
--

DROP TABLE IF EXISTS `t_facility_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_facility_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '设施名称',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '图标',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态(0:禁用 1:启用)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='设施字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_facility_dict`
--

LOCK TABLES `t_facility_dict` WRITE;
/*!40000 ALTER TABLE `t_facility_dict` DISABLE KEYS */;
INSERT INTO `t_facility_dict` VALUES (1,'WiFi','wifi',1,1,'2024-11-21 21:04:24','2024-11-21 21:04:24'),(2,'空调','ac',2,1,'2024-11-21 21:04:24','2024-11-21 21:04:24'),(3,'电视','tv',3,1,'2024-11-21 21:04:24','2024-11-21 21:04:24'),(4,'洗衣机','washer',4,1,'2024-11-21 21:04:24','2024-11-21 21:04:24'),(5,'冰箱','fridge',5,1,'2024-11-21 21:04:24','2024-11-21 21:04:24'),(6,'热水器','water-heater',6,1,'2024-11-21 21:04:24','2024-11-21 21:04:24'),(7,'厨房','kitchen',7,1,'2024-11-21 21:04:24','2024-11-21 21:04:24'),(8,'阳台','balcony',8,1,'2024-11-21 21:04:24','2024-11-21 21:04:24'),(9,'电梯','elevator',9,1,'2024-11-21 21:04:24','2024-11-21 21:04:24'),(10,'停车位','parking',10,1,'2024-11-21 21:04:24','2024-11-21 21:04:24');
/*!40000 ALTER TABLE `t_facility_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_favorite`
--

DROP TABLE IF EXISTS `t_favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `house_id` bigint NOT NULL COMMENT '房源ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_house` (`user_id`,`house_id`),
  KEY `idx_house_id` (`house_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收藏表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_favorite`
--

LOCK TABLES `t_favorite` WRITE;
/*!40000 ALTER TABLE `t_favorite` DISABLE KEYS */;
INSERT INTO `t_favorite` VALUES (1,1,1,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(2,1,3,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(3,1,5,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(4,2,2,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(5,2,4,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(6,2,6,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(7,3,1,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(8,3,2,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(9,3,3,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(10,4,4,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(11,4,5,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(12,4,6,'2024-11-17 09:42:52','2024-11-17 09:42:52',0,0),(13,1000,1,'2024-11-22 15:12:30','2024-11-22 15:12:30',0,1),(14,1000,13,'2024-11-22 15:15:46','2024-11-22 15:15:46',0,0),(15,1000,12,'2024-11-22 18:19:35','2024-11-22 18:19:35',0,0),(16,7,18,'2024-11-30 13:28:41','2024-11-30 13:28:41',0,0),(17,5000001,1,'2024-11-30 19:02:07','2024-11-30 19:02:07',0,0);
/*!40000 ALTER TABLE `t_favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_house`
--

DROP TABLE IF EXISTS `t_house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_house` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '房源ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房源名称',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房源类型',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '房源地址',
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所在城市',
  `district` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所在地区',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '详细位置',
  `price` decimal(10,2) NOT NULL COMMENT '价格/晚',
  `max_guests` int NOT NULL COMMENT '最大入住人数',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '房源描述',
  `category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '房源分类',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态(0:下架 1:上架)',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `merchant_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商家名称',
  `facilities` json DEFAULT NULL COMMENT '设施列表(JSON数组)',
  `images` json DEFAULT NULL COMMENT '图片列表(JSON数组)',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '房源主图',
  `rating` decimal(2,1) DEFAULT '5.0' COMMENT '评分',
  `features` json DEFAULT NULL COMMENT '特色标签(JSON数组)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:未删除 1:已删除)',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '房源标题',
  `review_count` int DEFAULT '0' COMMENT '评价数量',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_status` (`status`),
  KEY `idx_city` (`city`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_house`
--

LOCK TABLES `t_house` WRITE;
/*!40000 ALTER TABLE `t_house` DISABLE KEYS */;
INSERT INTO `t_house` VALUES (1,'海景度假别墅','单人间','海南省三亚市海棠区滨海大道','三亚','海棠区','近海棠湾沙滩',1288.00,6,'270°一线海景房,独立泳池,私人沙滩','单人间',1,2000001,'阳光度假酒店','[\"wifi\", \"停车场\", \"厨房\", \"洗衣机\", \"空调\", \"游泳池\"]','[\"http://example.com/villa1.jpg\", \"http://example.com/villa2.jpg\"]','http://example.com/villa_main.jpg',4.8,'[\"海景\", \"泳池\", \"度假\"]','2024-11-14 10:07:20','2024-11-30 18:49:19',0,NULL,0),(2,'海景度假别墅','单人间','北京朝阳区近海棠湾沙滩','北京','朝阳区','近海棠湾沙滩',1288.00,6,'270°一线海景房,独立泳池,私人沙滩','单人间',1,2000001,'阳光度假酒店','[\"wifi\", \"停车场\", \"厨房\", \"洗衣机\", \"空调\", \"游泳池\"]','[\"http://example.com/villa1.jpg\", \"http://example.com/villa2.jpg\"]','http://localhost:8080/upload/house/2024/11/29/b746dcf22af84ed08f7568e2652dbccc.png',4.8,'[\"海景\", \"泳池\", \"度假\"]','2024-11-14 10:08:50','2024-11-30 18:49:19',1,'撒大四的',0),(3,'椰梦长廊海景公寓','单人间','海南省三亚市三亚湾路128号','三亚','吉阳区','三亚湾椰梦长廊',688.00,4,'一线海景房,步行2分钟到沙滩,近三亚湾商圈','单人间',1,2000001,'椰子湾度假公寓','[\"wifi\", \"停车场\", \"厨房\", \"洗衣机\", \"空调\", \"电梯\"]','[\"http://example.com/sanya1.jpg\", \"http://example.com/sanya2.jpg\"]','http://example.com/sanya_main.jpg',4.9,'[\"海景\", \"沙滩\", \"商圈\"]','2024-11-14 10:08:50','2024-11-30 18:49:19',0,NULL,0),(4,'亚龙湾温泉别墅','别墅','海南省三亚市亚龙湾国家旅游度假区','三亚','天涯区','亚龙湾',2588.00,8,'独栋温泉别墅,私家泳池,管家服务','豪华别墅',1,2000001,'亚龙湾度假村','[\"wifi\", \"停车场\", \"厨房\", \"洗衣机\", \"空调\", \"泳池\", \"温泉\", \"管家\"]','[\"http://example.com/yalong1.jpg\", \"http://example.com/yalong2.jpg\"]','http://example.com/yalong_main.jpg',4.8,'[\"温泉\", \"泳池\", \"奢华\"]','2024-11-14 10:08:50','2024-11-30 18:49:19',0,NULL,0),(5,'故宫旁四合院','四合院','北京市东城区景山前街','北京','东城区','故宫博物院附近',1988.00,6,'百年老宅,典型北京四合院,近故宫天安门','特色民宿',1,2000001,'京城文化民宿','[\"WiFi\", \"暖气\", \"茶室\", \"庭院\", \"空调\"]','[\"http://example.com/beijing1.jpg\", \"http://example.com/beijing2.jpg\"]','http://example.com/beijing_main.jpg',4.7,'[\"文化\", \"古韵\", \"地标\"]','2024-11-14 10:08:50','2024-11-30 19:47:42',0,NULL,0),(6,'三里屯SOHO公寓','公寓','北京市朝阳区三里屯','北京','朝阳区','三里屯商圈',799.00,2,'时尚公寓,俯瞰三里屯,近地铁','商务公寓',1,2000001,'城市精品公寓','[\"WiFi\", \"健身房\", \"停车场\", \"电梯\", \"智能门锁\"]','[\"http://example.com/sanlitun1.jpg\", \"http://example.com/sanlitun2.jpg\"]','http://example.com/sanlitun_main.jpg',4.6,'[\"时尚\", \"商圈\", \"交通\"]','2024-11-14 10:08:50','2024-11-30 19:41:19',0,NULL,0),(7,'外滩江景套房','公寓','上海市黄浦区中山东一路','上海','黄浦区','外滩',1288.00,3,'270°外滩江景房,近南京路步行街','豪华公寓',1,2000001,'浦江夜景公寓','[\"WiFi\", \"停车场\", \"健身房\", \"空调\", \"智能家电\"]','[\"http://example.com/waitan1.jpg\", \"http://example.com/waitan2.jpg\"]','http://example.com/waitan_main.jpg',4.9,'[\"江景\", \"地标\", \"夜景\"]','2024-11-14 10:08:50','2024-11-30 19:41:18',0,NULL,0),(8,'田子坊老洋房','洋房','上海市黄浦区泰康路','上海','黄浦区','田子坊',1588.00,4,'百年石库门建筑,近地铁,文艺氛围浓厚','特色民宿',1,2000001,'老上海文化民宿','[\"WiFi\", \"庭院\", \"茶室\", \"麻将室\", \"空调\"]','[\"http://example.com/tianzifang1.jpg\", \"http://example.com/tianzifang2.jpg\"]','http://example.com/tianzifang_main.jpg',4.7,'[\"文艺\", \"怀旧\", \"特色\"]','2024-11-14 10:08:50','2024-11-30 19:41:19',0,NULL,0),(9,'宽窄巷子民宿','民居','成都市青羊区宽窄巷子','成都','青羊区','宽窄巷子',468.00,4,'老成都民居,近太古里,春熙路商圈','特色民宿',1,2000001,'蓉城民宿','[\"wifi\", \"麻将室\", \"茶室\", \"空调\", \"电视\"]','[\"http://example.com/chengdu1.jpg\", \"http://example.com/chengdu2.jpg\"]','http://example.com/chengdu_main.jpg',4.8,'[\"文化\", \"美食\", \"休闲\"]','2024-11-14 10:08:50','2024-11-30 18:49:19',0,NULL,0),(10,'天府新区复式公寓','公寓','成都市天府新区天府大道','成都','天府新区','天府软件园',528.00,3,'现代简约风格,近环球中心,软件园','商务公寓',1,2000001,'天府商务公寓','[\"wifi\", \"健身房\", \"停车场\", \"电梯\", \"智能门锁\"]','[\"http://example.com/tianfu1.jpg\", \"http://example.com/tianfu2.jpg\"]','http://example.com/tianfu_main.jpg',4.6,'[\"商务\", \"现代\", \"便利\"]','2024-11-14 10:08:50','2024-11-30 18:49:19',0,NULL,0),(11,'西湖湖景别墅','别墅','杭州市西湖区北山街道','杭州','西湖区','西湖景区',2088.00,6,'临湖独栋别墅,近断桥残雪','豪华别墅',1,1009,'西湖度假别墅','[\"wifi\", \"泳池\", \"花园\", \"厨房\", \"停车场\", \"管家\"]','[\"http://example.com/xihu1.jpg\", \"http://example.com/xihu2.jpg\"]','http://example.com/xihu_main.jpg',4.9,'[\"湖景\", \"度假\", \"奢华\"]','2024-11-14 10:08:50','2024-11-14 10:08:50',0,NULL,0),(12,'钱塘江观景公寓','公寓','杭州市滨江区江南大道','杭州','滨江区','钱塘江畔',688.00,2,'江景房,近阿里巴巴总部,适合商务出行','商务公寓',1,1010,'钱塘公寓','[\"wifi\", \"健身房\", \"停车场\", \"电梯\", \"办公桌\"]','[\"http://example.com/qiantang1.jpg\", \"http://example.com/qiantang2.jpg\"]','http://example.com/qiantang_main.jpg',4.7,'[\"江景\", \"商务\", \"科技\"]','2024-11-14 10:08:50','2024-11-14 10:08:50',0,NULL,0),(13,'装修中的海景房','公寓','三亚市海棠区海棠北路','三亚','海棠区','海棠湾',888.00,4,'正在装修中,预计下月开放预订','精品公寓',0,1001,'椰子湾度假公寓','[\"wifi\", \"停车场\", \"厨房\", \"洗衣机\", \"空调\"]','[\"http://example.com/temp1.jpg\", \"http://example.com/temp2.jpg\"]','http://example.com/temp_main.jpg',0.0,'[\"海景\", \"新装修\"]','2024-11-14 10:08:50','2024-11-14 10:08:50',0,NULL,0),(14,'待验收别墅','别墅','北京市顺义区后沙峪','北京市','顺义区','温泉度假区',1688.00,6,'正在进行最终验收,即将开放','度假别墅',0,1003,'京城文化民宿','[\"wifi\", \"泳池\", \"温泉\", \"停车场\", \"花园\"]','[\"http://example.com/pending1.jpg\", \"http://example.com/pending2.jpg\"]','http://example.com/pending_main.jpg',0.0,'[\"温泉\", \"度假\"]','2024-11-14 10:08:50','2024-11-30 14:21:57',0,NULL,0),(17,'真好啊','BaseType','北京市昌平区广西科技大学','北京市','BJ-CP','广西科技大学',100.00,2,'非常好','BOUTIQUE',1,7,'merc','[\"WiFi\"]','[\"http://localhost:8080/upload/house/2024/11/30/be55e18b15464d8f9019fb7b0fa9676a.jpg\"]','http://localhost:8080/upload/house/2024/11/30/98a8ca06e5ec42a1b1fac100945a3b19.jpg',0.0,'[]','2024-11-30 12:47:05','2024-11-30 19:47:42',1,'好东西',0),(18,'你非常好','基础类型','北京市昌平区广西科技大学','北京市','BJ-CP','广西科技大学',100.00,2,'你好','精品房源',1,7,'merc','[\"WiFi\"]','[\"http://localhost:8080/upload/house/2024/11/30/f3e44e9ca0c04be392e2e3f6de53e6ca.jpg\"]','http://localhost:8080/upload/house/2024/11/30/01d0a0108b2449f3b32beb8e6b63427b.jpg',0.0,'[\"cooking\", \"scenic\"]','2024-11-30 13:00:35','2024-11-30 19:47:42',0,'你好',0);
/*!40000 ALTER TABLE `t_house` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_house_booking`
--

DROP TABLE IF EXISTS `t_house_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_house_booking` (
  `id` int NOT NULL AUTO_INCREMENT,
  `house_id` bigint NOT NULL COMMENT '对应房源ID',
  `status` int NOT NULL DEFAULT '0' COMMENT '预订状态: 0 未预订, 1 已预订',
  `booking_start` datetime NOT NULL COMMENT '预订开始时间',
  `booking_end` datetime NOT NULL COMMENT '预订结束时间',
  `user_name` varchar(255) NOT NULL COMMENT '预订用户姓名',
  `phone` varchar(20) NOT NULL COMMENT '预订人手机号',
  `email` varchar(255) NOT NULL COMMENT '预订人邮箱',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录最后更新时间',
  PRIMARY KEY (`id`),
  KEY `t_house_booking__fk_house_id` (`house_id`),
  KEY `idx_booking_start` (`booking_start`),
  KEY `idx_status` (`status`),
  CONSTRAINT `t_house_booking__fk_house_id` FOREIGN KEY (`house_id`) REFERENCES `t_house` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='房源预订表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_house_booking`
--

LOCK TABLES `t_house_booking` WRITE;
/*!40000 ALTER TABLE `t_house_booking` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_house_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_house_comment`
--

DROP TABLE IF EXISTS `t_house_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_house_comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `house_id` varchar(100) NOT NULL COMMENT '房源ID',
  `user_id` varchar(100) NOT NULL COMMENT '用户ID',
  `order_id` varchar(100) NOT NULL COMMENT '订单ID',
  `rating` tinyint NOT NULL COMMENT '评分(1-5星)',
  `content` text NOT NULL COMMENT '评论内容',
  `images` json DEFAULT NULL COMMENT '评论图片列表',
  `reply` text COMMENT '商家回复',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `status` tinyint DEFAULT '1' COMMENT '状态(0:待审核 1:已发布 2:已隐藏)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='房源评论表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_house_comment`
--

LOCK TABLES `t_house_comment` WRITE;
/*!40000 ALTER TABLE `t_house_comment` DISABLE KEYS */;
INSERT INTO `t_house_comment` VALUES (1,'5','5000001','202411229513',5,'asdas  sasa da','[]',NULL,NULL,1,'2024-12-01 15:34:50','2024-12-01 15:36:35'),(2,'1','5000001','ORD001',5,'房间非常干净整洁,位置也很好,老板服务态度很好,下次还会来住!','[\"comment/img1.jpg\", \"comment/img2.jpg\"]',NULL,NULL,1,'2024-12-02 17:04:03','2024-12-02 17:04:03'),(3,'1','5000002','ORD002',4,'整体不错,就是价格稍微贵了点,其他都挺满意的。','[\"comment/img3.jpg\"]',NULL,NULL,1,'2024-12-02 17:04:03','2024-12-02 17:04:03'),(4,'2','5000003','ORD003',3,'位置还行,但是隔音效果不是很好,晚上有点吵。',NULL,NULL,NULL,1,'2024-12-02 17:04:03','2024-12-02 17:04:03'),(5,'3','5000004','ORD004',2,'失望,房间描述与实际不符,设施较旧,需要更新了。','[\"comment/img4.jpg\", \"comment/img5.jpg\"]',NULL,NULL,1,'2024-12-02 17:04:03','2024-12-02 17:04:03'),(6,'3','5000005','ORD005',1,'非常不满意,卫生状况差,服务态度也不好。','[\"comment/img6.jpg\"]',NULL,NULL,1,'2024-12-02 17:04:03','2024-12-02 17:04:03'),(7,'4','5000006','ORD006',5,'这个价位能住到这样的房子很值得,推荐给大家!',NULL,NULL,NULL,0,'2024-12-02 17:04:03','2024-12-02 17:04:03'),(8,'4','5000007','ORD007',4,'房东人很好,入住体验不错。',NULL,NULL,NULL,0,'2024-12-02 17:04:03','2024-12-02 17:04:03'),(9,'5','5000008','ORD008',2,'房间有异味,空调制冷效果不好。',NULL,'非常抱歉给您带来不愉快的入住体验,我们已经安排工作人员处理相关问题,欢迎您再次光临。','2024-12-02 17:04:03',1,'2024-12-02 17:04:03','2024-12-02 17:04:03');
/*!40000 ALTER TABLE `t_house_comment` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_after_comment_insert` AFTER INSERT ON `t_house_comment` FOR EACH ROW BEGIN
    DECLARE v_merchant_id BIGINT;
    DECLARE v_house_name VARCHAR(100);
    DECLARE v_user_nickname VARCHAR(50);
    DECLARE v_message_id VARCHAR(100);

    -- 获取房源相关信息
    SELECT h.merchant_id, h.name, u.nickname
    INTO v_merchant_id, v_house_name, v_user_nickname
    FROM t_house h
             LEFT JOIN sys_user u ON u.id = NEW.user_id
    WHERE h.id = NEW.house_id;

    -- 生成消息ID (格式: MSG + 年月日时分秒 + 6位随机数)
    SET v_message_id = CONCAT(
            'MSG',
            DATE_FORMAT(NOW(), '%Y%m%d%H%i%s'),
            LPAD(FLOOR(RAND() * 1000000), 6, '0')
                       );

    -- 1. 插入商家消息通知
    INSERT INTO t_merchant_message (
        merchant_id,
        title,
        content,
        type,
        priority,
        sender,
        send_id,
        is_read
    ) VALUES (
                 v_merchant_id,
                 CASE
                     WHEN NEW.rating >= 4 THEN CONCAT('收到新的好评 - ', v_house_name)
                     WHEN NEW.rating <= 2 THEN CONCAT('收到新的差评 - ', v_house_name)
                     ELSE CONCAT('收到新的评价 - ', v_house_name)
                     END,
                 CONCAT(
                         '用户 ', IFNULL(v_user_nickname, '匿名用户'),
                         ' 对房源 "', v_house_name,
                         '" 发表了', NEW.rating, '星评价: ',
                         LEFT(NEW.content, 100),
                         IF(LENGTH(NEW.content) > 100, '...', '')
                 ),
                 'REVIEW_NOTIFICATION',
                 CASE
                     WHEN NEW.rating <= 2 THEN 'high'
                     ELSE 'normal'
                     END,
                 'system',
                 v_message_id,
                 0
             );

    -- 2. 更新房源评分统计
    INSERT INTO t_house_rating_stats (
        house_id,
        total_rating,
        average_rating,
        rating_1_count,
        rating_2_count,
        rating_3_count,
        rating_4_count,
        rating_5_count
    )
    VALUES (
               NEW.house_id,
               1,
               NEW.rating,
               IF(NEW.rating = 1, 1, 0),
               IF(NEW.rating = 2, 1, 0),
               IF(NEW.rating = 3, 1, 0),
               IF(NEW.rating = 4, 1, 0),
               IF(NEW.rating = 5, 1, 0)
           )
    ON DUPLICATE KEY UPDATE
                         total_rating = total_rating + 1,
                         average_rating = ROUND((average_rating * total_rating + NEW.rating) / (total_rating + 1), 1),
                         rating_1_count = rating_1_count + IF(NEW.rating = 1, 1, 0),
                         rating_2_count = rating_2_count + IF(NEW.rating = 2, 1, 0),
                         rating_3_count = rating_3_count + IF(NEW.rating = 3, 1, 0),
                         rating_4_count = rating_4_count + IF(NEW.rating = 4, 1, 0),
                         rating_5_count = rating_5_count + IF(NEW.rating = 5, 1, 0);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `t_house_comment_like`
--

DROP TABLE IF EXISTS `t_house_comment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_house_comment_like` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '点赞ID',
  `comment_id` bigint NOT NULL COMMENT '评论ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_comment_user` (`comment_id`,`user_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论点赞表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_house_comment_like`
--

LOCK TABLES `t_house_comment_like` WRITE;
/*!40000 ALTER TABLE `t_house_comment_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_house_comment_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_house_comment_report`
--

DROP TABLE IF EXISTS `t_house_comment_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_house_comment_report` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '举报ID',
  `comment_id` bigint NOT NULL COMMENT '评论ID',
  `user_id` bigint NOT NULL COMMENT '举报用户ID',
  `reason` varchar(50) NOT NULL COMMENT '举报原因',
  `description` text COMMENT '详细说明',
  `status` tinyint DEFAULT '0' COMMENT '处理状态(0:待处理 1:已处理 2:已驳回)',
  `handle_note` text COMMENT '处理说明',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_comment_id` (`comment_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论举报表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_house_comment_report`
--

LOCK TABLES `t_house_comment_report` WRITE;
/*!40000 ALTER TABLE `t_house_comment_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_house_comment_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_house_facility`
--

DROP TABLE IF EXISTS `t_house_facility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_house_facility` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `house_id` bigint NOT NULL COMMENT '房源ID',
  `facility_id` bigint NOT NULL COMMENT '设施ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除，1-已删除',
  PRIMARY KEY (`id`),
  KEY `idx_house_id` (`house_id`),
  KEY `fk_facility_id` (`facility_id`),
  CONSTRAINT `fk_facility_id` FOREIGN KEY (`facility_id`) REFERENCES `t_facility` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房源设施关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_house_facility`
--

LOCK TABLES `t_house_facility` WRITE;
/*!40000 ALTER TABLE `t_house_facility` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_house_facility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_house_feature`
--

DROP TABLE IF EXISTS `t_house_feature`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_house_feature` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `house_id` bigint NOT NULL COMMENT '房源ID',
  `feature_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '特色标签名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_feature_name` (`feature_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房源特色标签关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_house_feature`
--

LOCK TABLES `t_house_feature` WRITE;
/*!40000 ALTER TABLE `t_house_feature` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_house_feature` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_house_image`
--

DROP TABLE IF EXISTS `t_house_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_house_image` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `house_id` bigint NOT NULL COMMENT '房源ID',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片URL',
  `image_type` int DEFAULT NULL COMMENT '图片类型(0:普通图片 1:封面图)',
  `is_cover` tinyint NOT NULL DEFAULT '0' COMMENT '是否为封面(0:否 1:是)',
  `sort` int NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_house_id` (`house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房源图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_house_image`
--

LOCK TABLES `t_house_image` WRITE;
/*!40000 ALTER TABLE `t_house_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_house_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_house_rating_stats`
--

DROP TABLE IF EXISTS `t_house_rating_stats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_house_rating_stats` (
  `house_id` bigint NOT NULL COMMENT '房源ID',
  `total_rating` int NOT NULL DEFAULT '0' COMMENT '总评分数',
  `average_rating` decimal(2,1) NOT NULL DEFAULT '0.0' COMMENT '平均评分',
  `rating_1_count` int NOT NULL DEFAULT '0' COMMENT '1星评价数',
  `rating_2_count` int NOT NULL DEFAULT '0' COMMENT '2星评价数',
  `rating_3_count` int NOT NULL DEFAULT '0' COMMENT '3星评价数',
  `rating_4_count` int NOT NULL DEFAULT '0' COMMENT '4星评价数',
  `rating_5_count` int NOT NULL DEFAULT '0' COMMENT '5星评价数',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='房源评分统计表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_house_rating_stats`
--

LOCK TABLES `t_house_rating_stats` WRITE;
/*!40000 ALTER TABLE `t_house_rating_stats` DISABLE KEYS */;
INSERT INTO `t_house_rating_stats` VALUES (1,2,4.7,0,0,0,1,1,'2024-12-02 17:04:03'),(2,1,3.0,0,0,1,0,0,'2024-12-02 17:04:03'),(3,2,1.7,1,1,0,0,0,'2024-12-02 17:04:03'),(4,2,4.7,0,0,0,1,1,'2024-12-02 17:04:03'),(5,1,1.0,0,1,0,0,1,'2024-12-02 17:04:03');
/*!40000 ALTER TABLE `t_house_rating_stats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_merchant_income`
--

DROP TABLE IF EXISTS `t_merchant_income`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_merchant_income` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '收入表ID',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `all_income` decimal(19,2) NOT NULL COMMENT '总收入',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商家总收入';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_merchant_income`
--

LOCK TABLES `t_merchant_income` WRITE;
/*!40000 ALTER TABLE `t_merchant_income` DISABLE KEYS */;
INSERT INTO `t_merchant_income` VALUES (1,1,15000.75,'2024-11-01 10:00:00','2024-11-01 15:00:00','2024-11-01 15:00:00'),(2,2,30000.50,'2024-10-20 09:30:00','2024-10-20 14:45:00','2024-10-20 14:45:00'),(3,3,22000.00,'2024-11-10 11:00:00','2024-11-10 16:30:00','2024-11-10 16:30:00'),(4,4,5000.20,'2024-09-15 13:00:00','2024-09-15 18:00:00','2024-09-15 18:00:00'),(5,5,12500.90,'2024-11-05 08:30:00','2024-11-05 12:00:00','2024-11-05 12:00:00'),(6,6,8000.00,'2024-08-25 16:00:00','2024-08-25 20:00:00','2024-08-25 20:00:00'),(7,2000001,62600.30,'2024-07-30 14:15:00','2024-07-30 19:45:00','2024-11-30 18:48:55'),(8,8,47000.00,'2024-10-01 09:00:00','2024-10-01 13:30:00','2024-10-01 13:30:00'),(9,9,600.00,'2024-11-29 10:39:25',NULL,'2024-11-30 18:48:55');
/*!40000 ALTER TABLE `t_merchant_income` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_merchant_message`
--

DROP TABLE IF EXISTS `t_merchant_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_merchant_message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息标题',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息内容',
  `type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '消息类型',
  `priority` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'normal' COMMENT '优先级',
  `sender` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发送者',
  `is_read` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已读',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `send_id` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_type` (`type`),
  KEY `idx_is_read` (`is_read`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商家消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_merchant_message`
--

LOCK TABLES `t_merchant_message` WRITE;
/*!40000 ALTER TABLE `t_merchant_message` DISABLE KEYS */;
INSERT INTO `t_merchant_message` VALUES (52,2000001,'新订单通知 #ORDER123','您有一个新的订单需要处理，订单号：ORDER123','ORDER_NOTIFICATION','high','system',0,'2024-11-08 17:04:10','2024-11-30 18:52:44',0,NULL),(53,2000001,'订单已支付 #ORDER124','订单ORDER124已成功支付，请及时处理','ORDER_NOTIFICATION','high','system',1,'2024-11-11 17:04:10','2024-11-30 18:52:44',0,NULL),(54,2000001,'订单取消提醒 #ORDER125','订单ORDER125已被用户取消','ORDER_NOTIFICATION','normal','system',0,'2024-11-05 17:04:10','2024-11-30 18:52:44',0,NULL),(55,2000001,'系统维护通知','系统将于今晚23:00-次日凌晨2:00进行例行维护','SYSTEM_NOTIFICATION','high','admin',0,'2024-11-23 17:04:10','2024-11-30 18:52:44',0,NULL),(56,2000001,'功能更新通知','平台新增商家数据分析功能，点击查看详情','SYSTEM_NOTIFICATION','normal','admin',1,'2024-11-13 17:04:10','2024-11-30 18:52:44',0,NULL),(57,2000001,'账户安全提醒','请定期更改您的账户密码，确保账户安全','SYSTEM_NOTIFICATION','low','system',0,'2024-11-01 17:04:10','2024-11-30 18:52:44',0,NULL),(58,2000001,'618活动预告','618购物节即将开始，请提前准备商品促销计划','PROMOTION_NOTIFICATION','high','marketing',1,'2024-10-30 17:04:10','2024-11-30 18:52:44',0,NULL),(59,2000001,'限时特惠活动','您的店铺已被选中参加下周限时特惠活动','PROMOTION_NOTIFICATION','normal','marketing',0,'2024-10-29 17:04:10','2024-11-30 18:52:44',0,NULL),(60,2000001,'节日营销活动','中秋节活动报名开始，点击参与','PROMOTION_NOTIFICATION','normal','marketing',0,'2024-11-24 17:04:10','2024-11-30 18:52:44',0,NULL),(61,2000001,'新的用户评价','您收到一条新的用户评价，请及时查看并回复','REVIEW_NOTIFICATION','normal','system',0,'2024-11-10 17:04:10','2024-11-30 18:52:44',0,NULL),(62,2000001,'用户评价提醒','您有3条用户评价待回复','REVIEW_NOTIFICATION','normal','system',1,'2024-11-10 17:04:10','2024-11-30 18:52:44',0,NULL),(63,2000001,'账户余额提醒','您的账户余额已不足1000元，请及时充值','ACCOUNT_NOTIFICATION','high','finance',0,'2024-11-24 17:04:10','2024-11-30 18:52:44',0,NULL),(64,2000001,'收入结算通知','上周期订单已完成结算，请查收','ACCOUNT_NOTIFICATION','normal','finance',1,'2024-11-04 17:04:10','2024-11-30 18:52:44',0,NULL),(65,7,'商品库存预警','商品\"精品双人房\"库存不足，请及时补充','PRODUCT_NOTIFICATION','high','system',0,'2024-11-10 17:04:10','2024-11-26 17:11:07',0,NULL),(66,7,'商品下架通知','您的商品\"豪华套房\"已自动下架，原因：价格异常','PRODUCT_NOTIFICATION','high','system',1,'2024-11-12 17:04:10','2024-11-26 17:11:07',0,NULL),(67,7,'订单提醒 #ORDER126','订单即将超时，请尽快处理','ORDER_NOTIFICATION','high','system',0,'2024-11-02 17:04:10','2024-11-26 17:11:07',0,NULL),(68,7,'订单完成 #ORDER127','订单ORDER127已完成，用户评分：5星','ORDER_NOTIFICATION','normal','system',1,'2024-11-09 17:04:10','2024-11-26 17:11:07',0,NULL),(69,7,'系统升级通知','系统将进行版本升级，新增多项功能','SYSTEM_NOTIFICATION','normal','admin',0,'2024-11-10 17:04:10','2024-11-26 17:11:07',0,NULL),(70,7,'操作指南更新','平台操作指南已更新，请查看最新版本','SYSTEM_NOTIFICATION','low','admin',1,'2024-10-31 17:04:10','2024-11-26 17:11:07',0,NULL),(71,7,'双11活动预告','双11购物节活动规则已发布，请查看','PROMOTION_NOTIFICATION','high','marketing',0,'2024-11-01 17:04:10','2024-11-26 17:11:07',0,NULL),(72,1002,'店铺优惠券投放','系统自动为您投放了100张优惠券','PROMOTION_NOTIFICATION','normal','marketing',1,'2024-11-10 17:04:10','2024-11-26 17:04:10',0,NULL),(73,1001,'系统公告','平台新增直播功能，欢迎体验','SYSTEM_NOTIFICATION','normal','admin',0,'2024-11-19 17:04:10','2024-11-26 17:04:10',0,NULL),(74,1002,'订单退款通知 #ORDER128','订单ORDER128发起退款申请','ORDER_NOTIFICATION','high','system',0,'2024-11-09 17:04:10','2024-11-26 17:04:10',0,NULL),(75,1003,'新功能上线通知','商家后台新增数据分析功能','SYSTEM_NOTIFICATION','normal','admin',1,'2024-11-20 17:04:10','2024-11-26 17:04:10',0,NULL),(76,1001,'用户投诉提醒','收到用户投诉，请及时处理','COMPLAINT_NOTIFICATION','high','system',0,'2024-11-18 17:04:10','2024-11-26 17:04:10',0,NULL),(77,1002,'年度账单提醒','您的2023年度账单已生成','ACCOUNT_NOTIFICATION','normal','finance',1,'2024-11-05 17:04:10','2024-11-26 17:04:10',0,NULL),(78,1003,'商品促销提醒','您的商品已参与平台促销活动','PROMOTION_NOTIFICATION','normal','marketing',0,'2024-11-05 17:04:10','2024-11-26 17:04:10',0,NULL),(79,1001,'订单超时提醒 #ORDER129','订单ORDER129即将超时，请尽快处理','ORDER_NOTIFICATION','high','system',0,'2024-11-12 17:04:10','2024-11-26 17:04:10',0,NULL),(80,1002,'系统更新提醒','系统将于明日凌晨2点进行更新','SYSTEM_NOTIFICATION','high','admin',1,'2024-11-18 17:04:10','2024-11-26 17:04:10',0,NULL),(81,1003,'活动报名通知','新年活动报名开始，请及时参与','PROMOTION_NOTIFICATION','normal','marketing',0,'2024-10-28 17:04:10','2024-11-26 17:04:10',0,NULL),(82,1001,'账户异常提醒','检测到异常登录，请确认是否为本人操作','ACCOUNT_NOTIFICATION','high','system',0,'2024-11-24 17:04:10','2024-11-26 17:04:10',0,NULL),(83,1002,'订单评价提醒 #ORDER130','订单ORDER130收到新评价','REVIEW_NOTIFICATION','normal','system',1,'2024-11-15 17:04:10','2024-11-26 17:04:10',0,NULL),(84,1003,'库存预警通知','多个房型库存不足，请及时更新','PRODUCT_NOTIFICATION','high','system',0,'2024-11-03 17:04:10','2024-11-26 17:04:10',0,NULL),(85,1001,'促销活动结算','上月促销活动已完成结算','PROMOTION_NOTIFICATION','normal','finance',1,'2024-11-07 17:04:10','2024-11-26 17:04:10',0,NULL),(86,1002,'系统功能调整','部分功能位置调整，点击查看详情','SYSTEM_NOTIFICATION','normal','admin',0,'2024-10-28 17:04:10','2024-11-26 17:04:10',0,NULL),(87,1003,'订单统计报告','您的月度订单统计报告已生成','ORDER_NOTIFICATION','normal','system',1,'2024-10-31 17:04:10','2024-11-26 17:04:10',0,NULL),(88,1001,'商家认证提醒','请尽快完成商家年度认证','ACCOUNT_NOTIFICATION','high','admin',0,'2024-11-12 17:04:10','2024-11-26 17:04:10',0,NULL),(89,1002,'用户反馈通知','收到用户建议反馈，请查看','REVIEW_NOTIFICATION','normal','system',1,'2024-11-02 17:04:10','2024-11-26 17:04:10',0,NULL),(90,1003,'节日营销建议','春节营销方案建议已生成','PROMOTION_NOTIFICATION','normal','marketing',0,'2024-11-08 17:04:10','2024-11-26 17:04:10',0,NULL),(91,1001,'订单处理提醒 #ORDER131','新订单ORDER131等待处理','ORDER_NOTIFICATION','high','system',0,'2024-11-10 17:04:10','2024-11-26 17:04:10',0,NULL),(92,1002,'系统维护完成','系统维护已完成，新功能已上线','SYSTEM_NOTIFICATION','normal','admin',1,'2024-10-28 17:04:10','2024-11-26 17:04:10',0,NULL),(93,1003,'优惠券到期提醒','部分优惠券即将过期','PROMOTION_NOTIFICATION','normal','marketing',0,'2024-11-19 17:04:10','2024-11-26 17:04:10',0,NULL),(94,1001,'账期结算通知','本期账单已生成，请查收','ACCOUNT_NOTIFICATION','normal','finance',1,'2024-11-19 17:04:10','2024-11-26 17:04:10',0,NULL),(95,1002,'房态��新提醒','请及时更新节假日房态','PRODUCT_NOTIFICATION','high','system',0,'2024-11-09 17:04:10','2024-11-26 17:04:10',0,NULL),(96,1003,'订单取消通知 #ORDER132','订单ORDER132已被用户取消','ORDER_NOTIFICATION','normal','system',1,'2024-11-23 17:04:10','2024-11-26 17:04:10',0,NULL),(97,1001,'活动报名截止','五一活动报名即将截止','PROMOTION_NOTIFICATION','high','marketing',0,'2024-10-31 17:04:10','2024-11-26 17:04:10',0,NULL),(98,1002,'系统问题修复','之前反馈的系统问题已修复','SYSTEM_NOTIFICATION','normal','admin',1,'2024-11-26 17:04:10','2024-11-26 17:04:10',0,NULL),(99,1003,'订单改期申请 #ORDER133','订单ORDER133申请改期，请处理','ORDER_NOTIFICATION','high','system',0,'2024-11-13 17:04:10','2024-11-26 17:04:10',0,NULL),(100,1001,'营销数据分析','上月营销活动分析报告已生成','PROMOTION_NOTIFICATION','normal','marketing',1,'2024-11-22 17:04:10','2024-11-26 17:04:10',0,NULL),(101,1002,'发票申请提醒','新的发票申请待处理','ACCOUNT_NOTIFICATION','normal','finance',0,'2024-11-13 17:04:10','2024-11-26 17:04:10',0,NULL),(102,1003,'订单完成通知 #ORDER134','订单ORDER134已完成，等待评价','ORDER_NOTIFICATION','normal','system',1,'2024-11-03 17:04:10','2024-11-26 17:04:10',0,NULL),(103,2000001,'新的房源评价 - 海景度假别墅','您的房源\"海景度假别墅\"收到了新的评价，评分: 5星，内容: 房间非常干净整洁,位置也很好,老板服务态度很好,下次还会来住!...','REVIEW_NOTIFICATION','normal','system',0,'2024-12-02 17:04:03','2024-12-02 17:04:03',0,NULL),(104,2000001,'新的房源评价 - 海景度假别墅','您的房源\"海景度假别墅\"收到了新的评价，评分: 4星，内容: 整体不错,就是价格稍微贵了点,其他都挺满意的。...','REVIEW_NOTIFICATION','normal','system',0,'2024-12-02 17:04:03','2024-12-02 17:04:03',0,NULL),(105,2000001,'新的房源评价 - 海景度假别墅','您的房源\"海景度假别墅\"收到了新的评价，评分: 3星，内容: 位置还行,但是隔音效果不是很好,晚上有点吵。...','REVIEW_NOTIFICATION','normal','system',0,'2024-12-02 17:04:03','2024-12-02 17:04:03',0,NULL),(106,2000001,'新的房源评价 - 椰梦长廊海景公寓','您的房源\"椰梦长廊海景公寓\"收到了新的评价，评分: 2星，内容: 失望,房间描述与实际不符,设施较旧,需要更新了。...','REVIEW_NOTIFICATION','high','system',0,'2024-12-02 17:04:03','2024-12-02 17:04:03',0,NULL),(107,2000001,'新的房源评价 - 椰梦长廊海景公寓','您的房源\"椰梦长廊海景公寓\"收到了新的评价，评分: 1星，内容: 非常不满意,卫生状况差,服务态度也不好。...','REVIEW_NOTIFICATION','high','system',0,'2024-12-02 17:04:03','2024-12-02 17:04:03',0,NULL),(108,2000001,'新的房源评价 - 亚龙湾温泉别墅','您的房源\"亚龙湾温泉别墅\"收到了新的评价，评分: 5星，内容: 这个价位能住到这样的房子很值得,推荐给大家!...','REVIEW_NOTIFICATION','normal','system',0,'2024-12-02 17:04:03','2024-12-02 17:04:03',0,NULL),(109,2000001,'新的房源评价 - 亚龙湾温泉别墅','您的房源\"亚龙湾温泉别墅\"收到了新的评价，评分: 4星，内容: 房东人很好,入住体验不错。...','REVIEW_NOTIFICATION','normal','system',0,'2024-12-02 17:04:03','2024-12-02 17:04:03',0,NULL),(110,2000001,'新的房源评价 - 故宫旁四合院','您的房源\"故宫旁四合院\"收到了新的评价，评分: 2星，内容: 房间有异味,空调制冷效果不好。...','REVIEW_NOTIFICATION','high','system',0,'2024-12-02 17:04:03','2024-12-02 17:04:03',0,NULL),(112,2000001,'新订单通知 - 海景度假别墅','用户 User One 提交了一个新订单，订单ID: ORD202401010201，入住日期: 2024-01-25，退房日期: 2024-01-27，总金额: 1376.00元。','ORDER_NOTIFICATION','normal','system',0,'2024-12-02 17:28:32','2024-12-02 17:28:32',0,'5000001'),(113,2000001,'新订单通知 - 海景度假别墅','用户 User Two 提交了一个新订单，订单ID: ORD202401010012，入住日期: 2024-01-28，退房日期: 2024-01-30，总金额: 2576.00元。','ORDER_NOTIFICATION','normal','system',0,'2024-12-02 17:28:32','2024-12-02 17:28:32',0,'5000002');
/*!40000 ALTER TABLE `t_merchant_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_merchant_promotion`
--

DROP TABLE IF EXISTS `t_merchant_promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_merchant_promotion` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `name` varchar(100) NOT NULL COMMENT '活动名称',
  `type` varchar(20) NOT NULL COMMENT '活动类型',
  `discount` decimal(10,2) NOT NULL COMMENT '优惠力度',
  `threshold` decimal(10,2) DEFAULT NULL COMMENT '使用门槛',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `status` tinyint NOT NULL COMMENT '活动状态 (0--进行中 1---已结束  2--未开始）',
  `description` varchar(500) DEFAULT NULL COMMENT '活动描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  `target_users` varchar(100) DEFAULT NULL COMMENT '"VIP" "User"',
  PRIMARY KEY (`id`),
  KEY `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商家优惠活动表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_merchant_promotion`
--

LOCK TABLES `t_merchant_promotion` WRITE;
/*!40000 ALTER TABLE `t_merchant_promotion` DISABLE KEYS */;
INSERT INTO `t_merchant_promotion` VALUES (1,2000001,'开业大促','FULL_REDUCTION',50.00,200.00,'2024-03-01 00:00:00','2024-04-30 23:59:59',1,'满200减50','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(2,2000001,'春季特惠','FULL_REDUCTION',100.00,500.00,'2024-03-15 00:00:00','2024-05-15 23:59:59',1,'满500减100','2024-11-26 16:27:35','2024-11-30 18:52:41',1,'user'),(3,2000001,'周末狂欢','FULL_REDUCTION',30.00,150.00,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'周末满150减30','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(4,2000001,'新客特惠','FULL_REDUCTION',80.00,300.00,'2024-03-10 00:00:00','2024-06-10 23:59:59',1,'新客满300减80','2024-11-26 16:27:35','2024-11-30 18:52:41',1,'user'),(5,2000001,'会员日特惠','DISCOUNT',8.50,NULL,'2024-03-01 00:00:00','2024-03-31 23:59:59',1,'会员专享8.5折','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(6,2000001,'限时折扣','DISCOUNT',7.00,NULL,'2024-03-15 00:00:00','2024-04-15 23:59:59',1,'全场7折','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(7,2000001,'早鸟特惠','DISCOUNT',8.00,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'早市8折优惠','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(8,2000001,'夜间优惠','DISCOUNT',7.50,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'晚市7.5折','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(9,2000001,'新店开业','DIRECT_REDUCTION',20.00,NULL,'2024-03-01 00:00:00','2024-03-31 23:59:59',1,'下单立减20元','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(10,2000001,'午市特惠','DIRECT_REDUCTION',15.00,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'午市立减15元','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(11,2000001,'下午茶优惠','DIRECT_REDUCTION',10.00,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'下午茶时段立减10元','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(12,2000001,'618促销','FULL_REDUCTION',200.00,1000.00,'2024-06-01 00:00:00','2024-06-30 23:59:59',0,'618大促满1000减200','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(13,2000001,'暑期特惠','FULL_REDUCTION',150.00,800.00,'2024-07-01 00:00:00','2024-08-31 23:59:59',0,'暑期满800减150','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(14,2000001,'开学季','FULL_REDUCTION',120.00,600.00,'2024-09-01 00:00:00','2024-09-30 23:59:59',0,'开学季满600减120','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(15,2000001,'双11预热','FULL_REDUCTION',300.00,1500.00,'2024-11-01 00:00:00','2024-11-10 23:59:59',0,'双11预热满1500减300','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(16,2000001,'春节特惠','DISCOUNT',6.50,NULL,'2024-01-20 00:00:00','2024-02-20 23:59:59',2,'春节特惠6.5折','2024-11-26 16:27:35','2024-11-30 18:52:41',0,'user'),(17,7,'五一促销','DISCOUNT',7.50,NULL,'2024-05-01 00:00:00','2024-05-07 23:59:59',0,'五一黄金周7.5折','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(18,7,'中秋特惠','DISCOUNT',8.00,NULL,'2024-09-15 00:00:00','2024-09-21 23:59:59',0,'中秋节8折优惠','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(19,7,'国庆促销','DISCOUNT',7.00,NULL,'2024-10-01 00:00:00','2024-10-07 23:59:59',0,'国庆7折大促','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(20,7,'情人节特惠','FULL_REDUCTION',214.00,1000.00,'2024-02-14 00:00:00','2024-02-14 23:59:59',2,'情人节满1000减214','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(21,7,'圣诞特惠','FULL_REDUCTION',250.00,1000.00,'2024-12-24 00:00:00','2024-12-25 23:59:59',0,'圣诞节满1000减250','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(22,7,'店庆活动','FULL_REDUCTION',88.00,400.00,'2024-03-01 00:00:00','2024-03-31 23:59:59',1,'店庆满400减88','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(23,7,'会员专享','DISCOUNT',8.80,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'会员专享8.8折','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(24,7,'限时特价','DIRECT_REDUCTION',25.00,NULL,'2024-03-15 00:00:00','2024-04-15 23:59:59',1,'限时立减25元','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(25,7,'春季促销','FULL_REDUCTION',60.00,300.00,'2024-03-01 00:00:00','2024-05-31 23:59:59',1,'春季满300减60','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(26,7,'春季促销','FULL_REDUCTION',80.00,400.00,'2024-03-01 00:00:00','2024-05-31 23:59:59',1,'春季满400减80','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(27,7,'春季促销','FULL_REDUCTION',100.00,500.00,'2024-03-01 00:00:00','2024-05-31 23:59:59',1,'春季满500减100','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(28,7,'已结束活动','DISCOUNT',7.50,NULL,'2024-01-01 00:00:00','2024-02-29 23:59:59',2,'新年7.5折优惠','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(29,7,'未开始活动','FULL_REDUCTION',150.00,700.00,'2024-07-01 00:00:00','2024-07-31 23:59:59',0,'暑期满700减150','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(30,7,'进行中活动','DIRECT_REDUCTION',30.00,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'立减30元','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(31,7,'工作日特惠','DIRECT_REDUCTION',40.00,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'工作日立减40元','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(32,7,'周末特惠','DIRECT_REDUCTION',50.00,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'周末立减50元','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(33,7,'节假日特惠','DIRECT_REDUCTION',60.00,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'节假日立减60元','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(34,7,'春季折扣','DISCOUNT',8.50,NULL,'2024-03-01 00:00:00','2024-05-31 23:59:59',1,'春季8.5折','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(35,7,'夏季折扣','DISCOUNT',8.00,NULL,'2024-06-01 00:00:00','2024-08-31 23:59:59',0,'夏季8折','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(36,7,'秋季折扣','DISCOUNT',7.50,NULL,'2024-09-01 00:00:00','2024-11-30 23:59:59',0,'秋季7.5折','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(37,7,'冬季折扣','DISCOUNT',7.00,NULL,'2024-12-01 00:00:00','2024-12-31 23:59:59',0,'冬季7折','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(38,7,'豪华套餐优惠','FULL_REDUCTION',500.00,2000.00,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'满2000减500','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(39,7,'家庭套餐优惠','FULL_REDUCTION',300.00,1500.00,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'满1500减300','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(40,7,'商务套餐优惠','FULL_REDUCTION',400.00,1800.00,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'满1800减400','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(41,7,'早餐特惠','DISCOUNT',7.50,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'早餐7.5折','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(42,7,'午餐特惠','DISCOUNT',8.00,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'午餐8折','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(43,7,'晚餐特惠','DISCOUNT',8.50,NULL,'2024-03-01 00:00:00','2024-12-31 23:59:59',1,'晚餐8.5折','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(44,7,'元旦特惠','FULL_REDUCTION',201.00,1000.00,'2024-01-01 00:00:00','2024-01-03 23:59:59',2,'元旦满1000减201','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(45,7,'春节特惠','FULL_REDUCTION',888.00,3000.00,'2024-02-10 00:00:00','2024-02-17 23:59:59',2,'春节满3000减888','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(46,7,'元宵特惠','FULL_REDUCTION',150.00,800.00,'2024-02-24 00:00:00','2024-02-24 23:59:59',2,'元宵节满800减150','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(47,7,'春游套餐','FULL_REDUCTION',200.00,1000.00,'2024-03-01 00:00:00','2024-05-31 23:59:59',1,'春季出游满1000减200','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(48,7,'夏日清凉','FULL_REDUCTION',250.00,1200.00,'2024-06-01 00:00:00','2024-08-31 23:59:59',0,'夏季特惠满1200减250','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(49,7,'秋高气爽','FULL_REDUCTION',180.00,900.00,'2024-09-01 00:00:00','2024-11-30 23:59:59',0,'秋季特惠满900减180','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(50,7,'冬季温暖','FULL_REDUCTION',220.00,1100.00,'2024-12-01 00:00:00','2024-12-31 23:59:59',0,'冬季特惠满1100减220','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(51,7,'年终特惠','DISCOUNT',6.80,NULL,'2024-12-25 00:00:00','2024-12-31 23:59:59',0,'年终6.8折大促','2024-11-26 16:27:35','2024-11-29 09:54:19',0,'user'),(52,7,'挂科补偿','COMPENSATION',20.00,0.00,'2024-11-27 00:00:00','2024-11-29 23:59:59',0,'','2024-11-26 16:50:56','2024-11-29 09:54:19',0,'user');
/*!40000 ALTER TABLE `t_merchant_promotion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_merchant_trend_data`
--

DROP TABLE IF EXISTS `t_merchant_trend_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_merchant_trend_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '数据id',
  `merchant_id` bigint NOT NULL COMMENT '商家ID',
  `years` varchar(255) NOT NULL COMMENT '年份',
  `months` varchar(255) NOT NULL COMMENT '月份',
  `all_order` bigint NOT NULL COMMENT '订单数',
  `all_price` decimal(15,2) NOT NULL COMMENT '收入总数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商家趋势数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_merchant_trend_data`
--

LOCK TABLES `t_merchant_trend_data` WRITE;
/*!40000 ALTER TABLE `t_merchant_trend_data` DISABLE KEYS */;
INSERT INTO `t_merchant_trend_data` VALUES (1,1001,'2024','11',2,600.00,'2024-11-29 10:39:25','2024-11-29 10:39:59'),(3,2000001,'2024','11',4,9107.00,'2024-11-29 10:44:24','2024-11-30 18:52:36'),(6,2000001,'2024','01',2,3300.00,'2024-11-29 10:53:35','2024-11-30 18:52:36'),(7,2000001,'2024','02',2,4600.00,'2024-11-29 10:53:35','2024-11-30 18:52:36'),(8,2000001,'2024','03',2,3000.00,'2024-11-29 10:53:35','2024-11-30 18:52:36'),(9,2000001,'2024','04',2,3600.00,'2024-11-29 10:53:35','2024-11-30 18:52:36'),(10,2000001,'2024','05',2,4000.00,'2024-11-29 10:53:35','2024-11-30 18:52:36'),(11,2000001,'2024','06',2,3600.00,'2024-11-29 10:53:35','2024-11-30 18:52:36'),(12,2000001,'2024','07',2,4300.00,'2024-11-29 10:53:35','2024-11-30 18:52:36'),(13,2000001,'2024','08',2,4900.00,'2024-11-29 10:53:35','2024-11-30 18:52:36'),(14,2000001,'2024','09',2,4000.00,'2024-11-29 10:53:35','2024-11-30 18:52:36'),(15,2000001,'2024','10',2,4400.00,'2024-11-29 10:53:35','2024-11-30 18:52:36');
/*!40000 ALTER TABLE `t_merchant_trend_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_order_payment`
--

DROP TABLE IF EXISTS `t_order_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_order_payment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单ID',
  `payment_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付流水号',
  `payment_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付方式(ALIPAY-支付宝 WECHAT-微信支付)',
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '支付状态(0:待支付 1:支付成功 2:支付失败 3:已退款)',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `refund_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '退款原因',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  UNIQUE KEY `uk_payment_no` (`payment_no`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单支付表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_order_payment`
--

LOCK TABLES `t_order_payment` WRITE;
/*!40000 ALTER TABLE `t_order_payment` DISABLE KEYS */;
INSERT INTO `t_order_payment` VALUES (1,'ORD202401010001','PAY202401010001','ALIPAY',1376.00,1,'2024-01-01 10:30:00',NULL,NULL,NULL,'2024-11-18 18:01:30','2024-11-18 18:01:30',0),(2,'ORD202401010002','PAY202401010002','WECHAT',2576.00,1,'2024-01-01 14:20:00',NULL,NULL,NULL,'2024-11-18 18:01:30','2024-11-18 18:01:30',0),(3,'ORD202401020001','PAY202401020001','ALIPAY',3976.00,0,NULL,NULL,NULL,NULL,'2024-11-18 18:01:30','2024-11-18 18:01:30',0),(4,'ORD202401020002','PAY202401020002','WECHAT',468.00,0,NULL,NULL,NULL,NULL,'2024-11-18 18:01:30','2024-11-18 18:01:30',0),(5,'ORD202401030001','PAY202401030001','ALIPAY',4176.00,2,NULL,NULL,NULL,NULL,'2024-11-18 18:01:30','2024-11-18 18:01:30',0),(6,'ORD202312010001','PAY202312010001','WECHAT',5176.00,1,'2023-12-23 16:40:00',NULL,NULL,NULL,'2024-11-18 18:01:30','2024-11-18 18:01:30',0),(7,'ORD202312010002','PAY202312010002','ALIPAY',2397.00,3,'2023-12-24 09:15:00','2023-12-25 10:20:00',2397.00,'客户投诉服务质量','2024-11-18 18:01:30','2024-11-18 18:01:30',0);
/*!40000 ALTER TABLE `t_order_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_payment`
--

DROP TABLE IF EXISTS `t_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_payment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '支付ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单ID',
  `amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付方式(ALIPAY-支付宝 WECHAT-微信支付)',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付状态(PENDING-待支付 PAID-已支付 CANCELLED-已取消)',
  `qrcode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付二维码',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `expire_time` datetime NOT NULL COMMENT '二维码过期时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_id` (`order_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_payment`
--

LOCK TABLES `t_payment` WRITE;
/*!40000 ALTER TABLE `t_payment` DISABLE KEYS */;
INSERT INTO `t_payment` VALUES (1,'ORD202401010001',1376.00,'ALIPAY','PAID','https://example.com/qr/pay001.png','2024-01-01 10:30:00','2024-01-01 11:00:00','2024-11-18 17:56:49','2024-11-18 17:56:49',0),(2,'ORD202401010002',2576.00,'WECHAT','PAID','https://example.com/qr/pay002.png','2024-01-01 14:20:00','2024-01-01 15:00:00','2024-11-18 17:56:49','2024-11-18 17:56:49',0),(3,'ORD202401020001',3976.00,'ALIPAY','PENDING','https://example.com/qr/pay003.png',NULL,'2024-11-18 18:26:49','2024-11-18 17:56:49','2024-11-18 17:56:49',0),(4,'ORD202401020002',468.00,'WECHAT','PENDING','https://example.com/qr/pay004.png',NULL,'2024-11-18 18:26:49','2024-11-18 17:56:49','2024-11-18 17:56:49',0),(5,'ORD202401030001',4176.00,'ALIPAY','CANCELLED','https://example.com/qr/pay005.png',NULL,'2024-01-03 11:00:00','2024-11-18 17:56:49','2024-11-18 17:56:49',0),(6,'ORD202312010001',5176.00,'WECHAT','PAID','https://example.com/qr/pay006.png','2023-12-23 16:40:00','2023-12-23 17:00:00','2024-11-18 17:56:49','2024-11-18 17:56:49',0),(7,'ORD202312010002',2397.00,'ALIPAY','PAID','https://example.com/qr/pay007.png','2023-12-24 09:15:00','2023-12-24 10:00:00','2024-11-18 17:56:49','2024-11-18 17:56:49',0);
/*!40000 ALTER TABLE `t_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_refund`
--

DROP TABLE IF EXISTS `t_refund`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_refund` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '退款ID',
  `payment_id` bigint NOT NULL COMMENT '支付ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单ID',
  `amount` decimal(10,2) NOT NULL COMMENT '退款金额',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '退款原因',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '退款状态(PENDING-处理中 SUCCESS-成功 FAILED-失败)',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `idx_payment_id` (`payment_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退款信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_refund`
--

LOCK TABLES `t_refund` WRITE;
/*!40000 ALTER TABLE `t_refund` DISABLE KEYS */;
INSERT INTO `t_refund` VALUES (1,1,'ORD202401010001',1376.00,'房源已售罄','SUCCESS','2024-01-02 10:30:00','2024-11-18 17:56:55','2024-11-18 17:56:55',0),(2,2,'ORD202401010002',2576.00,'客户取消预订','SUCCESS','2024-01-02 14:20:00','2024-11-18 17:56:55','2024-11-18 17:56:55',0),(3,3,'ORD202401020001',3976.00,'系统故障取消','PENDING',NULL,'2024-11-18 17:56:55','2024-11-18 17:56:55',0),(4,4,'ORD202401020002',468.00,'支付渠道异常','FAILED',NULL,'2024-11-18 17:56:55','2024-11-18 17:56:55',0),(5,5,'ORD202312010001',2588.00,'提前退房','SUCCESS','2023-12-24 16:40:00','2024-11-18 17:56:55','2024-11-18 17:56:55',0),(6,6,'ORD202312010002',1198.50,'服务质量问题','SUCCESS','2023-12-25 09:15:00','2024-11-18 17:56:55','2024-11-18 17:56:55',0);
/*!40000 ALTER TABLE `t_refund` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_review`
--

DROP TABLE IF EXISTS `t_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_review` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `house_id` bigint NOT NULL COMMENT '房源ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评价内容',
  `rating` int NOT NULL COMMENT '评分(1-5)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '乐观锁版本号',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:未删除 1:已删除)',
  PRIMARY KEY (`id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='房源评价表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_review`
--

LOCK TABLES `t_review` WRITE;
/*!40000 ALTER TABLE `t_review` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_system_settings`
--

DROP TABLE IF EXISTS `t_system_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_system_settings` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `site_name` varchar(100) NOT NULL DEFAULT '民宿预订系统' COMMENT '网站名称',
  `site_desc` varchar(255) DEFAULT NULL COMMENT '网站描述',
  `logo` varchar(255) DEFAULT NULL COMMENT 'logo地址',
  `favicon` varchar(255) DEFAULT NULL COMMENT '网站图标地址',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `max_booking_days` int NOT NULL DEFAULT '30' COMMENT '最大预订天数',
  `min_advance_booking_days` int NOT NULL DEFAULT '1' COMMENT '最小提前预订天数',
  `max_advance_booking_days` int NOT NULL DEFAULT '90' COMMENT '最大提前预订天数',
  `check_in_time` varchar(5) NOT NULL DEFAULT '14:00' COMMENT '入住时间',
  `check_out_time` varchar(5) NOT NULL DEFAULT '12:00' COMMENT '退房时间',
  `deposit_rate` int NOT NULL DEFAULT '30' COMMENT '定金比例',
  `cancel_policy` int NOT NULL DEFAULT '1' COMMENT '取消政策',
  `refund_rules` json DEFAULT NULL COMMENT '退款规则',
  `email_notification` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用邮件通知',
  `sms_notification` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否启用短信通知',
  `notify_before_check_in` int NOT NULL DEFAULT '24' COMMENT '入住前通知时间(小时)',
  `notify_before_check_out` int NOT NULL DEFAULT '2' COMMENT '退房前通知时间(小时)',
  `maintenance` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否维护中',
  `maintenance_message` varchar(255) DEFAULT NULL COMMENT '维护信息',
  `registration_enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否开放注册',
  `default_user_role` varchar(20) NOT NULL DEFAULT 'user' COMMENT '默认用户角色',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_system_settings`
--

LOCK TABLES `t_system_settings` WRITE;
/*!40000 ALTER TABLE `t_system_settings` DISABLE KEYS */;
INSERT INTO `t_system_settings` VALUES (1,'民宿预订','提供优质的民宿预订服务',NULL,NULL,'400-123-4567','support@example.com','浙江省杭州市西湖区',30,1,90,'14:00','12:00',30,1,NULL,1,1,24,2,0,NULL,1,'user','2024-11-15 11:03:08','2024-11-15 11:03:08');
/*!40000 ALTER TABLE `t_system_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_trend_data`
--

DROP TABLE IF EXISTS `t_trend_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_trend_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` varchar(20) NOT NULL COMMENT '数据类型(USER:用户趋势 ORDER:订单趋势)',
  `date` date NOT NULL COMMENT '统计日期',
  `count` int NOT NULL COMMENT '数量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_type_date` (`type`,`date`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='趋势数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_trend_data`
--

LOCK TABLES `t_trend_data` WRITE;
/*!40000 ALTER TABLE `t_trend_data` DISABLE KEYS */;
INSERT INTO `t_trend_data` VALUES (1,'USER','2024-06-14',456,'2024-11-14 09:57:35','2024-11-14 09:57:35'),(2,'USER','2024-07-14',789,'2024-11-14 09:57:35','2024-11-14 09:57:35'),(3,'USER','2024-08-14',654,'2024-11-14 09:57:35','2024-11-14 09:57:35'),(4,'USER','2024-09-14',876,'2024-11-14 09:57:35','2024-11-14 09:57:35'),(5,'USER','2024-10-14',543,'2024-11-14 09:57:35','2024-11-14 09:57:35'),(6,'USER','2024-11-14',987,'2024-11-14 09:57:35','2024-11-14 09:57:35'),(7,'ORDER','2024-06-14',45,'2024-11-14 09:59:19','2024-11-14 09:59:19'),(8,'ORDER','2024-07-14',78,'2024-11-14 09:59:19','2024-11-14 09:59:19'),(9,'ORDER','2024-08-14',56,'2024-11-14 09:59:19','2024-11-14 09:59:19'),(10,'ORDER','2024-09-14',89,'2024-11-14 09:59:19','2024-11-14 09:59:19'),(11,'ORDER','2024-10-14',65,'2024-11-14 09:59:19','2024-11-14 09:59:19'),(12,'ORDER','2024-11-14',92,'2024-11-14 09:59:19','2024-11-14 09:59:19');
/*!40000 ALTER TABLE `t_trend_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_order`
--

DROP TABLE IF EXISTS `t_user_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_order` (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `house_id` bigint NOT NULL COMMENT '房源ID',
  `amount` decimal(10,2) NOT NULL COMMENT '订单金额',
  `check_in` date NOT NULL COMMENT '入住日期',
  `check_out` date NOT NULL COMMENT '退房日期',
  `nights` int NOT NULL COMMENT '入住天数',
  `guests` int NOT NULL COMMENT '入住人数',
  `contact_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '联系人电话',
  `special_requests` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '特殊要求',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '订单状态(0:待支付 1:已支付 2:已取消 3:已完成)',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `deleted` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除(0:未删除 1:已删除)',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_house_id` (`house_id`),
  KEY `idx_status` (`status`),
  KEY `idx_check_in` (`check_in`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_order`
--

LOCK TABLES `t_user_order` WRITE;
/*!40000 ALTER TABLE `t_user_order` DISABLE KEYS */;
INSERT INTO `t_user_order` VALUES ('202411220385',1000,1,2576.00,'2024-11-27','2024-11-29',2,1,'吴宏昊','18589826376','121323',3,NULL,'2024-11-22 16:26:35','2024-12-01 15:33:55',0,0),('202411229513',5000001,5,1376.00,'2024-11-27','2024-11-29',2,1,'吴宏昊','18589826376','我需要好吃的早餐',3,NULL,'2024-11-22 18:19:56','2024-12-01 15:33:55',0,0),('ORD202312010001',6,6,5176.00,'2023-12-24','2023-12-26',2,6,'周八','13800138006','需要准备接待晚餐',3,'2023-12-23 16:40:00','2024-11-18 18:01:26','2024-11-18 18:01:26',0,0),('ORD202312010002',7,7,2397.00,'2023-12-25','2023-12-28',3,2,'吴九','13800138007',NULL,3,'2023-12-24 09:15:00','2024-11-18 18:01:26','2024-11-18 18:01:26',0,0),('ORD202312020001',4,4,468.00,'2024-02-05','2024-02-06',1,2,'赵六','13800138004','需要婴儿床',0,NULL,'2024-11-18 18:01:26','2024-11-26 16:13:32',0,0),('ORD202401010001',1,1,1376.00,'2024-01-25','2024-01-27',2,2,'张三','13800138001','希望有加床服务',1,'2024-01-01 10:30:00','2024-11-18 18:01:26','2024-11-18 18:01:26',0,0),('ORD202401010002',2,2,2576.00,'2024-01-28','2024-01-30',2,3,'李四','13800138002','需要接送机服务',1,'2024-01-01 14:20:00','2024-11-18 18:01:26','2024-11-18 18:01:26',0,0),('ORD202401010012',5000002,2,2576.00,'2024-01-28','2024-01-30',2,3,'李四','13800138002',NULL,1,NULL,'2024-12-02 17:28:32','2024-12-02 17:28:32',0,0),('ORD202401010201',5000001,1,1376.00,'2024-01-25','2024-01-27',2,2,'张三','13800138001',NULL,1,NULL,'2024-12-02 17:28:32','2024-12-02 17:28:32',0,0),('ORD202401020001',3,3,3976.00,'2024-02-01','2024-02-03',2,2,'王五','13800138003',NULL,0,NULL,'2024-11-18 18:01:26','2024-11-18 18:01:26',0,0),('ORD202401030001',5,5,4176.00,'2024-02-10','2024-02-12',2,4,'孙七','13800138005',NULL,2,NULL,'2024-11-18 18:01:26','2024-11-18 18:01:26',0,0);
/*!40000 ALTER TABLE `t_user_order` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_after_order_insert` AFTER INSERT ON `t_user_order` FOR EACH ROW BEGIN
    DECLARE v_merchant_id BIGINT;
    DECLARE v_house_name VARCHAR(100);
    DECLARE v_user_nickname VARCHAR(50);

    -- 获取房源和用户相关信息
    SELECT h.merchant_id, h.name, u.nickname
    INTO v_merchant_id, v_house_name, v_user_nickname
    FROM t_house h
             LEFT JOIN sys_user u ON u.id = NEW.user_id
    WHERE h.id = NEW.house_id;

    -- 插入商家消息通知
    INSERT INTO t_merchant_message (
        merchant_id,
        title,
        content,
        type,
        priority,
        sender,
        send_id,    -- send_id 设置为下单用户的ID
        is_read
    ) VALUES (
                 v_merchant_id,
                 CONCAT('新订单通知 - ', v_house_name),
                 CONCAT(
                         '用户 ', IFNULL(v_user_nickname, '匿名用户'),
                         ' 提交了一个新订单，订单ID: ', NEW.id,
                         '，入住日期: ', DATE_FORMAT(NEW.check_in, '%Y-%m-%d'),
                         '，退房日期: ', DATE_FORMAT(NEW.check_out, '%Y-%m-%d'),
                         '，总金额: ', NEW.amount, '元。'
                 ),
                 'ORDER_NOTIFICATION',
                 'normal',
                 'system',
                 NEW.user_id,  -- 设置为下单用户的ID
                 0
             );
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `t_user_privacy`
--

DROP TABLE IF EXISTS `t_user_privacy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_privacy` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `profile_visibility` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'PUBLIC' COMMENT '个人资料可见性',
  `show_online_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '显示在线状态',
  `show_last_seen` tinyint(1) NOT NULL DEFAULT '1' COMMENT '显示最后在线时间',
  `allow_friend_requests` tinyint(1) NOT NULL DEFAULT '1' COMMENT '允许好友请求',
  `allow_messages` tinyint(1) NOT NULL DEFAULT '1' COMMENT '允许消息',
  `show_email` tinyint(1) NOT NULL DEFAULT '0' COMMENT '显示邮箱',
  `show_phone` tinyint(1) NOT NULL DEFAULT '0' COMMENT '显示手机号',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户隐私设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_privacy`
--

LOCK TABLES `t_user_privacy` WRITE;
/*!40000 ALTER TABLE `t_user_privacy` DISABLE KEYS */;
INSERT INTO `t_user_privacy` VALUES (1,1,'PUBLIC',1,1,1,1,0,0,'2024-11-19 10:55:16','2024-11-19 10:55:16'),(2,2,'FRIENDS',1,0,1,1,0,0,'2024-11-19 10:55:16','2024-11-19 10:55:16'),(3,3,'PRIVATE',0,0,0,1,0,0,'2024-11-19 10:55:16','2024-11-19 10:55:16');
/*!40000 ALTER TABLE `t_user_privacy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_user_settings`
--

DROP TABLE IF EXISTS `t_user_settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_user_settings` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `email_notification` tinyint(1) NOT NULL DEFAULT '1' COMMENT '邮件通知',
  `sms_notification` tinyint(1) NOT NULL DEFAULT '1' COMMENT '短信通知',
  `order_update_notification` tinyint(1) NOT NULL DEFAULT '1' COMMENT '订单更新通知',
  `promotion_notification` tinyint(1) NOT NULL DEFAULT '1' COMMENT '促销通知',
  `language` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'zh_CN' COMMENT '语言',
  `currency` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'CNY' COMMENT '货币',
  `timezone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Asia/Shanghai' COMMENT '时区',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户设置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_user_settings`
--

LOCK TABLES `t_user_settings` WRITE;
/*!40000 ALTER TABLE `t_user_settings` DISABLE KEYS */;
INSERT INTO `t_user_settings` VALUES (1,1,1,1,1,1,'zh_CN','CNY','Asia/Shanghai','2024-11-19 10:55:21','2024-11-19 10:55:21'),(2,2,1,0,1,0,'en_US','USD','America/New_York','2024-11-19 10:55:21','2024-11-19 10:55:21'),(3,3,0,1,1,0,'zh_CN','CNY','Asia/Shanghai','2024-11-19 10:55:21','2024-11-23 18:59:40'),(4,1000,1,0,1,1,'zh-CN','CNY','Asia/Shanghai','2024-11-23 19:32:24','2024-11-23 19:32:24'),(5,7,1,1,1,0,'zh-CN','CNY','Asia/Shanghai','2024-11-30 13:26:46','2024-11-30 13:26:46'),(6,5000001,1,1,1,0,'zh-CN','CNY','Asia/Shanghai','2024-11-30 18:59:27','2024-11-30 18:59:27');
/*!40000 ALTER TABLE `t_user_settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL COMMENT '标签类型',
  `text` varchar(100) DEFAULT NULL COMMENT '标签文本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-02 17:29:01
