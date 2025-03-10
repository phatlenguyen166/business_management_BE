CREATE DATABASE  IF NOT EXISTS `business_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `business_management`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: business_management
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attendance_details`
--

DROP TABLE IF EXISTS `attendance_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance_details` (
                                      `id` bigint NOT NULL AUTO_INCREMENT,
                                      `attendance_status` tinyint DEFAULT NULL,
                                      `check_in` datetime(6) DEFAULT NULL,
                                      `check_out` datetime(6) DEFAULT NULL,
                                      `working_date` date DEFAULT NULL,
                                      `user_id` bigint NOT NULL,
                                      PRIMARY KEY (`id`),
                                      KEY `FK92qrxbt3v4sbf7w9p3sj0wg9u` (`user_id`),
                                      CONSTRAINT `FK92qrxbt3v4sbf7w9p3sj0wg9u` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                      CONSTRAINT `attendance_details_chk_1` CHECK ((`attendance_status` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance_details`
--

LOCK TABLES `attendance_details` WRITE;
/*!40000 ALTER TABLE `attendance_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attendances`
--

DROP TABLE IF EXISTS `attendances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendances` (
                               `id` bigint NOT NULL AUTO_INCREMENT,
                               `attendance_status` tinyint DEFAULT NULL,
                               `check_in` datetime(6) DEFAULT NULL,
                               `check_out` datetime(6) DEFAULT NULL,
                               `working_date` date DEFAULT NULL,
                               `user_id` bigint NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK8o39cn3ghqwhccyrrqdesttr8` (`user_id`),
                               CONSTRAINT `FK8o39cn3ghqwhccyrrqdesttr8` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                               CONSTRAINT `attendances_chk_1` CHECK ((`attendance_status` between 0 and 2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendances`
--

LOCK TABLES `attendances` WRITE;
/*!40000 ALTER TABLE `attendances` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendances` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill_details`
--

DROP TABLE IF EXISTS `bill_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_details` (
                                `id` bigint NOT NULL AUTO_INCREMENT,
                                `quantity` int NOT NULL,
                                `sub_price` decimal(19,4) NOT NULL,
                                `bill_id` bigint NOT NULL,
                                `product_id` bigint NOT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FKfwm4sko9p82ndh6belyxx12bj` (`bill_id`),
                                KEY `FK4iagdr0uhsq4tj0ag99nmmya1` (`product_id`),
                                CONSTRAINT `FK4iagdr0uhsq4tj0ag99nmmya1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
                                CONSTRAINT `FKfwm4sko9p82ndh6belyxx12bj` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_details`
--

LOCK TABLES `bill_details` WRITE;
/*!40000 ALTER TABLE `bill_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `bill_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bills` (
                         `id` bigint NOT NULL,
                         `address` varchar(255) DEFAULT NULL,
                         `created_at` datetime(6) DEFAULT NULL,
                         `total_price` decimal(19,4) NOT NULL,
                         `updated_at` datetime(6) DEFAULT NULL,
                         `customer_id` bigint NOT NULL,
                         `user_id` bigint NOT NULL,
                         PRIMARY KEY (`id`),
                         KEY `FKoy9sc2dmxj2qwjeiiilf3yuxp` (`customer_id`),
                         KEY `FKk8vs7ac9xknv5xp18pdiehpp1` (`user_id`),
                         CONSTRAINT `FKk8vs7ac9xknv5xp18pdiehpp1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                         CONSTRAINT `FKoy9sc2dmxj2qwjeiiilf3yuxp` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contracts`
--

DROP TABLE IF EXISTS `contracts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contracts` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `base_salary` decimal(19,4) NOT NULL,
                             `end_date` date DEFAULT NULL,
                             `expiry_date` date DEFAULT NULL,
                             `start_date` date DEFAULT NULL,
                             `status` int NOT NULL,
                             `seniority_level` bigint NOT NULL,
                             `user_id` bigint NOT NULL,
                             PRIMARY KEY (`id`),
                             KEY `FKa685dj982h13eolw5pkd65wll` (`seniority_level`),
                             KEY `FKq3v8dxlubujug7dxvpauig94n` (`user_id`),
                             CONSTRAINT `FKa685dj982h13eolw5pkd65wll` FOREIGN KEY (`seniority_level`) REFERENCES `seniority_levels` (`id`),
                             CONSTRAINT `FKq3v8dxlubujug7dxvpauig94n` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contracts`
--

LOCK TABLES `contracts` WRITE;
/*!40000 ALTER TABLE `contracts` DISABLE KEYS */;
INSERT INTO `contracts` VALUES (2,8000000.0000,'2026-12-31','2026-12-31','2024-03-05',1,2,3),(3,15000000.0000,'2026-12-31','2026-12-31','2024-03-05',1,5,4),(4,12000000.0000,'2026-12-31','2026-12-31','2024-03-05',1,8,5),(5,18000000.0000,'2026-12-31','2026-12-31','2024-03-05',1,11,6),(8,1111000000.0000,'2025-12-31','2026-12-31','2024-03-05',1,3,2);
/*!40000 ALTER TABLE `contracts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) DEFAULT NULL,
                             `phone_number` varchar(255) DEFAULT NULL,
                             `address` varchar(255) DEFAULT NULL,
                             `created_at` datetime(6) DEFAULT NULL,
                             `email` varchar(255) DEFAULT NULL,
                             `status` int NOT NULL,
                             `updated_at` datetime(6) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Nguyễn Văn A','0912345678','123 Đường Trần Hưng Đạo, Quận 1, TP.HCM','2025-03-10 14:43:52.000000','nguyenvana@example.com',1,'2025-03-10 14:43:52.000000'),(2,'Trần Thị B','0987654321','456 Đường Lý Thái Tổ, Quận 3, TP.HCM','2025-03-10 14:43:52.000000','tranthib@example.com',1,'2025-03-10 14:43:52.000000'),(3,'Lê Minh C','0901122334','789 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM','2025-03-10 14:43:52.000000','leminhc@example.com',1,'2025-03-10 14:43:52.000000'),(4,'Phạm Văn D','0923456789','321 Đường Võ Văn Kiệt, Quận 10, TP.HCM','2025-03-10 14:43:52.000000','phamvand@example.com',1,'2025-03-10 14:43:52.000000'),(5,'Hoàng Thị E','0934567890','567 Đường Cộng Hòa, Quận Tân Bình, TP.HCM','2025-03-10 14:43:52.000000','hoangthie@example.com',1,'2025-03-10 14:43:52.000000'),(6,'Đặng Văn F','0945678901','678 Đường Phạm Văn Đồng, TP.Thủ Đức, TP.HCM','2025-03-10 14:43:52.000000','dangvanf@example.com',1,'2025-03-10 14:43:52.000000'),(7,'Bùi Thị G','0956789012','789 Đường Nguyễn Trãi, Quận 3, TP.HCM','2025-03-10 14:43:52.000000','buithig@example.com',1,'2025-03-10 14:43:52.000000'),(8,'Ngô Văn H','0967890123','890 Đường Hai Bà Trưng, Quận 1, TP.HCM','2025-03-10 14:43:52.000000','ngovanh@example.com',1,'2025-03-10 14:43:52.000000'),(9,'Dương Thị I','0978901234','101 Đường Lê Lợi, Quận 1, TP.HCM','2025-03-10 14:43:52.000000','duongthii@example.com',1,'2025-03-10 14:43:52.000000'),(10,'Vũ Minh K','0989012345','202 Đường Điện Biên Phủ, Quận Bình Thạnh, TP.HCM','2025-03-10 14:43:52.000000','vuminhk@example.com',1,'2025-03-10 14:43:52.000000');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_receipt_details`
--

DROP TABLE IF EXISTS `good_receipt_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_receipt_details` (
                                        `id` bigint NOT NULL AUTO_INCREMENT,
                                        `input_price` decimal(19,4) NOT NULL,
                                        `quantity` int NOT NULL,
                                        `goodreceipt_id` bigint NOT NULL,
                                        `product_id` bigint NOT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `FKds2up0fhjemb8ug5v28eb35r4` (`goodreceipt_id`),
                                        KEY `FKi25t5ni7hi6e6fvmiagxfc0jy` (`product_id`),
                                        CONSTRAINT `FKds2up0fhjemb8ug5v28eb35r4` FOREIGN KEY (`goodreceipt_id`) REFERENCES `good_receipts` (`id`),
                                        CONSTRAINT `FKi25t5ni7hi6e6fvmiagxfc0jy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipt_details`
--

LOCK TABLES `good_receipt_details` WRITE;
/*!40000 ALTER TABLE `good_receipt_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_receipt_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_receipts`
--

DROP TABLE IF EXISTS `good_receipts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_receipts` (
                                 `id` bigint NOT NULL AUTO_INCREMENT,
                                 `created_at` datetime(6) DEFAULT NULL,
                                 `total_price` decimal(19,4) NOT NULL,
                                 `updated_at` datetime(6) DEFAULT NULL,
                                 `supplier_id` bigint NOT NULL,
                                 `user_id` bigint DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `FK16hdoccmb55lsi1hf2rs12rl9` (`supplier_id`),
                                 KEY `FKf5qtokynne3a2n7mn566ohxyh` (`user_id`),
                                 CONSTRAINT `FK16hdoccmb55lsi1hf2rs12rl9` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`),
                                 CONSTRAINT `FKf5qtokynne3a2n7mn566ohxyh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipts`
--

LOCK TABLES `good_receipts` WRITE;
/*!40000 ALTER TABLE `good_receipts` DISABLE KEYS */;
/*!40000 ALTER TABLE `good_receipts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holidays`
--

DROP TABLE IF EXISTS `holidays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holidays` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `description` mediumtext,
                            `end_date` date DEFAULT NULL,
                            `start_date` date DEFAULT NULL,
                            `status` int NOT NULL,
                            `name` varchar(255) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holidays`
--

LOCK TABLES `holidays` WRITE;
/*!40000 ALTER TABLE `holidays` DISABLE KEYS */;
/*!40000 ALTER TABLE `holidays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_requests`
--

DROP TABLE IF EXISTS `leave_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leave_requests` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `description` mediumtext,
                                  `end_date` date NOT NULL,
                                  `leave_reason` tinyint NOT NULL,
                                  `send_date` datetime(6) DEFAULT NULL,
                                  `start_date` date NOT NULL,
                                  `status` int NOT NULL,
                                  `title` varchar(255) NOT NULL,
                                  `updated_at` datetime(6) DEFAULT NULL,
                                  `user_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FKh6s8bo5d59oy52b6nxfguf4yx` (`user_id`),
                                  CONSTRAINT `FKh6s8bo5d59oy52b6nxfguf4yx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                  CONSTRAINT `leave_requests_chk_1` CHECK ((`leave_reason` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_requests`
--

LOCK TABLES `leave_requests` WRITE;
/*!40000 ALTER TABLE `leave_requests` DISABLE KEYS */;
/*!40000 ALTER TABLE `leave_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payrolls`
--

DROP TABLE IF EXISTS `payrolls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payrolls` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `gross_salary` decimal(19,4) NOT NULL,
                            `maternity_benefit` decimal(19,4) NOT NULL,
                            `maternity_leaves` int NOT NULL,
                            `meal_allowance` decimal(19,4) NOT NULL,
                            `net_salary` decimal(19,4) NOT NULL,
                            `paid_leaves` int NOT NULL,
                            `pay_period` date DEFAULT NULL,
                            `penalties` decimal(19,4) NOT NULL,
                            `sick_leaves` int NOT NULL,
                            `tax` decimal(19,4) NOT NULL,
                            `working_days` int NOT NULL,
                            `user_id` bigint NOT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FKldxx1r63qa4adw90ohy7ga8o` (`user_id`),
                            CONSTRAINT `FKldxx1r63qa4adw90ohy7ga8o` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payrolls`
--

LOCK TABLES `payrolls` WRITE;
/*!40000 ALTER TABLE `payrolls` DISABLE KEYS */;
/*!40000 ALTER TABLE `payrolls` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `created_at` datetime(6) DEFAULT NULL,
                            `image` varchar(255) DEFAULT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `price` decimal(19,4) NOT NULL,
                            `quantity` int NOT NULL,
                            `status` int NOT NULL,
                            `updated_at` datetime(6) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2025-03-10 14:45:15.000000',NULL,'Sách Lập Trình Java',299000.0000,50,1,'2025-03-10 14:45:15.000000'),(2,'2025-03-10 14:45:15.000000',NULL,'Sách Python Cơ Bản',199000.0000,30,1,'2025-03-10 14:45:15.000000'),(3,'2025-03-10 14:45:15.000000',NULL,'Sách Dữ Liệu Lớn',399000.0000,20,1,'2025-03-10 14:45:15.000000'),(4,'2025-03-10 14:45:15.000000',NULL,'Sách Khoa Học Máy Tính',249000.0000,25,1,'2025-03-10 14:45:15.000000'),(5,'2025-03-10 14:45:15.000000',NULL,'Sách Thiết Kế UX/UI',349000.0000,40,1,'2025-03-10 14:45:15.000000'),(6,'2025-03-10 14:45:15.000000',NULL,'Sách AI và Machine Learning',499000.0000,15,1,'2025-03-10 14:45:15.000000'),(7,'2025-03-10 14:45:15.000000',NULL,'Sách Nhập Môn CNTT',159000.0000,60,1,'2025-03-10 14:45:15.000000'),(8,'2025-03-10 14:45:15.000000',NULL,'Sách Cấu Trúc Dữ Liệu',279000.0000,35,1,'2025-03-10 14:45:15.000000'),(9,'2025-03-10 14:45:15.000000',NULL,'Sách Mạng Máy Tính',319000.0000,45,1,'2025-03-10 14:45:15.000000'),(10,'2025-03-10 14:45:15.000000',NULL,'Sách An Ninh Mạng',459000.0000,20,1,'2025-03-10 14:45:15.000000'),(11,'2025-03-10 14:45:15.000000',NULL,'Sách Phát Triển Web',229000.0000,55,1,'2025-03-10 14:45:15.000000'),(12,'2025-03-10 14:45:15.000000',NULL,'Sách JavaScript Chuyên Sâu',319000.0000,33,1,'2025-03-10 14:45:15.000000'),(13,'2025-03-10 14:45:15.000000',NULL,'Sách React.js và Redux',349000.0000,25,1,'2025-03-10 14:45:15.000000'),(14,'2025-03-10 14:45:15.000000',NULL,'Sách Node.js và Express',299000.0000,22,1,'2025-03-10 14:45:15.000000'),(15,'2025-03-10 14:45:15.000000',NULL,'Sách Spring Boot',359000.0000,28,1,'2025-03-10 14:45:15.000000'),(16,'2025-03-10 14:45:15.000000',NULL,'Sách Lập Trình Mobile',279000.0000,30,1,'2025-03-10 14:45:15.000000'),(17,'2025-03-10 14:45:15.000000',NULL,'Sách Data Science',499000.0000,18,1,'2025-03-10 14:45:15.000000'),(18,'2025-03-10 14:45:15.000000',NULL,'Sách Kỹ Thuật Lập Trình',239000.0000,40,1,'2025-03-10 14:45:15.000000'),(19,'2025-03-10 14:45:15.000000',NULL,'Sách Hệ Điều Hành',289000.0000,23,1,'2025-03-10 14:45:15.000000'),(20,'2025-03-10 14:45:15.000000',NULL,'Sách SQL và Database',319000.0000,37,1,'2025-03-10 14:45:15.000000');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `description` varchar(255) DEFAULT NULL,
                         `name` varchar(50) NOT NULL,
                         `status` int NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Quản trị viên hệ thống, có toàn quyền quản lý','ADMIN',1),(2,'Nhân viên làm việc trong hệ thống','EMPLOYEE',1),(3,'Quản lý nhân sự, chịu trách nhiệm về hồ sơ nhân sự','HR_MANAGER',1),(4,'Quản lý kho, giám sát hàng tồn kho, nhập xuất hàng hóa','WAREHOUSE_MANAGER',1),(5,'Quản lý kinh doanh, chịu trách nhiệm về chiến lược kinh doanh','BUSINESS_MANAGER',1),(6,'Test','Test1',1),(7,'Test','1',1),(8,'','testUpsss',0);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seniority_levels`
--

DROP TABLE IF EXISTS `seniority_levels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seniority_levels` (
                                    `id` bigint NOT NULL AUTO_INCREMENT,
                                    `description` mediumtext NOT NULL,
                                    `level_name` varchar(100) NOT NULL,
                                    `salary_coefficient` float NOT NULL,
                                    `status` int NOT NULL,
                                    `role_id` bigint NOT NULL,
                                    PRIMARY KEY (`id`),
                                    KEY `FK42hqucc3nfs0pvgu51i74oq13` (`role_id`),
                                    CONSTRAINT `FK42hqucc3nfs0pvgu51i74oq13` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
                                    CONSTRAINT `seniority_levels_chk_2` CHECK ((`salary_coefficient` <= 5))
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seniority_levels`
--

LOCK TABLES `seniority_levels` WRITE;
/*!40000 ALTER TABLE `seniority_levels` DISABLE KEYS */;
INSERT INTO `seniority_levels` VALUES (1,'Nhân viên mới vào làm, đang trong giai đoạn thử việc','Thử việc',0.8,1,2),(2,'Nhân viên đã hoàn thành thử việc và làm việc dưới 1 năm.','Nhân viên bậc 1',1,1,2),(3,'Nhân viên đã làm việc từ 1 đến 3 năm trong công ty','Nhân viên bậc 2',1.3,1,2),(4,'Nhân viên đã gắn bó với công ty từ 3 năm trở lên.','Nhân viên bậc 3',1.5,1,2),(5,'Quản lý nhân sự mới được bổ nhiệm.','Quản lý nhân sự bậc 1',1,1,3),(6,'Quản lý nhân sự đã có kinh nghiệm từ 1-3 năm.','Quản lý nhân sự bậc 2',1.2,1,3),(7,'Quản lý nhân sự đã làm việc trên 3 năm.','Quản lý nhân sự bậc 3',1.5,1,3),(8,'Quản lý kho mới được bổ nhiệm.','Quản lý kho bậc 1',1,1,4),(9,'Quản lý kho đã có kinh nghiệm từ 1-3 năm.','Quản lý kho bậc 2',1.2,1,4),(10,'Quản lý kho đã làm việc trên 3 năm.','Quản lý kho bậc 3',1.5,1,4),(11,'Quản lý kho mới được bổ nhiệm.','Quản lý kinh doanh bậc 1',1,1,5),(12,'Quản lý kho đã có kinh nghiệm từ 1-3 năm.','Quản lý kinh doanh kho bậc 2',1.4,1,5),(13,'Quản lý kho đã làm việc trên 3 năm.','Quản lý kinh doanh kho bậc 3',1.8,1,5);
/*!40000 ALTER TABLE `seniority_levels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers` (
                             `id` bigint NOT NULL AUTO_INCREMENT,
                             `address` varchar(255) DEFAULT NULL,
                             `created_at` datetime(6) DEFAULT NULL,
                             `name` varchar(255) DEFAULT NULL,
                             `phone_number` varchar(255) DEFAULT NULL,
                             `status` int NOT NULL,
                             `updated_at` datetime(6) DEFAULT NULL,
                             `percentage` double NOT NULL,
                             `email` varchar(255) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (11,'123 Đường ABC, TP.HCM','2025-03-10 22:38:26.000000','Công ty A','0987654321',1,'2025-03-10 22:38:26.000000',5,'contact@companya.com'),(12,'456 Đường XYZ, Hà Nội','2025-03-10 22:38:26.000000','Công ty B','0971122334',1,'2025-03-10 22:38:26.000000',7.5,'info@companyb.com'),(13,'789 Đường LMN, Đà Nẵng','2025-03-10 22:38:26.000000','Công ty C','0903344556',1,'2025-03-10 22:38:26.000000',6.2,'support@companyc.com'),(14,'111 Đường PQR, Hải Phòng','2025-03-10 22:38:26.000000','Công ty D','0912345678',1,'2025-03-10 22:38:26.000000',4.8,'hello@companyd.com'),(15,'222 Đường UVW, Cần Thơ','2025-03-10 22:38:26.000000','Công ty E','0938765432',1,'2025-03-10 22:38:26.000000',8.1,'sales@companye.com'),(16,'333 Đường STU, Bình Dương','2025-03-10 22:38:26.000000','Công ty F','0956781234',1,'2025-03-10 22:38:26.000000',9,'contact@companyf.com'),(17,'444 Đường RST, Đồng Nai','2025-03-10 22:38:26.000000','Công ty G','0923456789',1,'2025-03-10 22:38:26.000000',3.6,'admin@companyg.com'),(18,'555 Đường NOP, Vũng Tàu','2025-03-10 22:38:26.000000','Công ty H','0919876543',1,'2025-03-10 22:38:26.000000',5.5,'info@companyh.com'),(19,'666 Đường HIJ, Nha Trang','2025-03-10 22:38:26.000000','Công ty I','0945678901',1,'2025-03-10 22:38:26.000000',7.2,'support@companyi.com'),(20,'777 Đường EFG, Huế','2025-03-10 22:38:26.000000','Công ty J','0967890123',1,'2025-03-10 22:38:26.000000',6.8,'hello@companyj.com');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tokens`
--

DROP TABLE IF EXISTS `tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tokens` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `access_token` varchar(255) DEFAULT NULL,
                          `created_at` datetime(6) DEFAULT NULL,
                          `refresh_token` varchar(255) DEFAULT NULL,
                          `updated_at` datetime(6) DEFAULT NULL,
                          `username` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `UK7nq3j9mbmotv8kv3nv9kbcb7c` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tokens`
--

LOCK TABLES `tokens` WRITE;
/*!40000 ALTER TABLE `tokens` DISABLE KEYS */;
INSERT INTO `tokens` VALUES (1,'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBRE1JTiIsImlhdCI6MTc0MTE4NDE5NCwiZXhwIjoxNzQ0Nzg0MTk0fQ.Em9Eg0yPj4F8MlT8o0bRZRxzN02LgFwBQJ5rnjy-NSk','2025-03-05 21:16:30.531138','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBRE1JTiIsImlhdCI6MTc0MTE4NDE5NCwiZXhwIjoxNzQyMzkzNzk0fQ.-r2Y0RgXlQOU1KmcLROzvrJtdjcrKY6oVsy5P5Dp34Y','2025-03-05 21:16:34.067015','ADMIN');
/*!40000 ALTER TABLE `tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `address` varchar(255) DEFAULT NULL,
                         `created_at` datetime(6) DEFAULT NULL,
                         `date_of_birth` date DEFAULT NULL,
                         `email` varchar(255) DEFAULT NULL,
                         `full_name` varchar(255) DEFAULT NULL,
                         `gender` tinyint DEFAULT NULL,
                         `last_login` datetime(6) DEFAULT NULL,
                         `password` varchar(255) DEFAULT NULL,
                         `phone_number` varchar(255) DEFAULT NULL,
                         `status` int NOT NULL,
                         `updated_at` datetime(6) DEFAULT NULL,
                         `username` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         CONSTRAINT `users_chk_1` CHECK ((`gender` between 0 and 2))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (2,'Hà Nội, Việt Nam','2025-03-05 21:00:35.000173','1990-05-15','an.nguyen@example.com','Nguyễn Văn An',0,NULL,'$2a$10$NFNUzoBf7f59miXiM2blcuTD9EuI3vL5WYrDkx6NR4hCRbjmna182','0987654321',1,NULL,'ADMIN'),(3,'303 Điện Biên Phủ, Quận Bình Thạnh, TP.HCM','2025-03-05 21:00:46.305184','2000-12-01','khanh.buivan@example.com','Test',0,NULL,'$2a$10$mcCg/6lmmsbPbHiWWiwEPOM0w6MnTCEeNqSt/32M4B9pp98wl/S1C','0945678901',1,'2025-03-06 18:23:40.414087','EMPLOYEE'),(4,'Đà Nẵng, Việt Nam','2025-03-05 21:01:13.014281','1988-09-10','tuan.le@example.com','Lê Minh Tuấn',0,NULL,'$2a$10$VEsW6l63BLabq2XkGmWYxuVd7M/PVVVd44o8NcW0Y6.O213FOqBCy','0965432109',1,NULL,'HR_MANAGER'),(5,'Hải Phòng, Việt Nam','2025-03-05 21:01:23.772907','1992-11-25','huy.pham@example.com','Phạm Quang Huy',0,NULL,'$2a$10$WWub6z22bRw8lYBKHMbHxujIRI.R6GLVNU8whAYNiXemPQB3.i7Gm','0954321098',1,NULL,'WAREHOUSE_MANAGER'),(6,'Cần Thơ, Việt Nam','2025-03-05 21:01:31.739038','1993-04-30','lan.do@example.com','Đỗ Thị Lan',1,NULL,'$2a$10$7u0rWM7puzgWD6GQWyUzsuuJfXJYzTZsAyONEkZCe/hX8uuxPxQcK','0943210987',1,NULL,'BUSINESS_MANAGER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-10 22:39:37
