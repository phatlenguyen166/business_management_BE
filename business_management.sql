-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: business_management
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `attendances`
--
CREATE DATABASE IF NOT EXISTS business_management;
USE business_management;

DROP TABLE IF EXISTS `attendances`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendances`
(
    `id`                bigint NOT NULL AUTO_INCREMENT,
    `attendance_status` tinyint     DEFAULT NULL,
    `check_in`          datetime(6) DEFAULT NULL,
    `check_out`         datetime(6) DEFAULT NULL,
    `working_date`      date        DEFAULT NULL,
    `user_id`           bigint NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK8o39cn3ghqwhccyrrqdesttr8` (`user_id`),
    CONSTRAINT `FK8o39cn3ghqwhccyrrqdesttr8` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `attendances_chk_1` CHECK ((`attendance_status` between 0 and 2))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendances`
--

LOCK TABLES `attendances` WRITE;
/*!40000 ALTER TABLE `attendances`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `attendances`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill_details`
--

DROP TABLE IF EXISTS `bill_details`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill_details`
(
    `id`         bigint         NOT NULL AUTO_INCREMENT,
    `quantity`   int            NOT NULL,
    `sub_price`  decimal(19, 4) NOT NULL,
    `bill_id`    bigint         NOT NULL,
    `product_id` bigint         NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKfwm4sko9p82ndh6belyxx12bj` (`bill_id`),
    KEY `FK4iagdr0uhsq4tj0ag99nmmya1` (`product_id`),
    CONSTRAINT `FK4iagdr0uhsq4tj0ag99nmmya1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
    CONSTRAINT `FKfwm4sko9p82ndh6belyxx12bj` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_details`
--

LOCK TABLES `bill_details` WRITE;
/*!40000 ALTER TABLE `bill_details`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `bill_details`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bills`
(
    `id`          bigint         NOT NULL,
    `address`     varchar(255) DEFAULT NULL,
    `created_at`  datetime(6)  DEFAULT NULL,
    `total_price` decimal(19, 4) NOT NULL,
    `updated_at`  datetime(6)  DEFAULT NULL,
    `customer_id` bigint         NOT NULL,
    `user_id`     bigint         NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKoy9sc2dmxj2qwjeiiilf3yuxp` (`customer_id`),
    KEY `FKk8vs7ac9xknv5xp18pdiehpp1` (`user_id`),
    CONSTRAINT `FKk8vs7ac9xknv5xp18pdiehpp1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FKoy9sc2dmxj2qwjeiiilf3yuxp` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `bills`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contracts`
--

DROP TABLE IF EXISTS `contracts`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contracts`
(
    `id`                   bigint         NOT NULL AUTO_INCREMENT,
    `base_salary`          decimal(19, 4) NOT NULL,
    `end_date`             date DEFAULT NULL,
    `expiry_date`          date DEFAULT NULL,
    `standard_working_day` int            NOT NULL,
    `start_date`           date DEFAULT NULL,
    `status`               int            NOT NULL,
    `seniority_level`      bigint         NOT NULL,
    `user_id`              bigint         NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKa685dj982h13eolw5pkd65wll` (`seniority_level`),
    KEY `FKq3v8dxlubujug7dxvpauig94n` (`user_id`),
    CONSTRAINT `FKa685dj982h13eolw5pkd65wll` FOREIGN KEY (`seniority_level`) REFERENCES `seniority_levels` (`id`),
    CONSTRAINT `FKq3v8dxlubujug7dxvpauig94n` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contracts`
--

LOCK TABLES `contracts` WRITE;
/*!40000 ALTER TABLE `contracts`
    DISABLE KEYS */;
INSERT INTO `contracts`
VALUES (1, 20000000.0000, '2026-12-31', '2026-12-31', 26, '2024-03-05', 1, 1, 2),
       (2, 8000000.0000, '2026-12-31', '2026-12-31', 26, '2024-03-05', 1, 2, 3),
       (3, 15000000.0000, '2026-12-31', '2026-12-31', 26, '2024-03-05', 1, 5, 4),
       (4, 12000000.0000, '2026-12-31', '2026-12-31', 26, '2024-03-05', 1, 8, 5),
       (5, 18000000.0000, '2026-12-31', '2026-12-31', 26, '2024-03-05', 1, 11, 6);
/*!40000 ALTER TABLE `contracts`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customers`
(
    `id`           bigint NOT NULL,
    `name`         varchar(255) DEFAULT NULL,
    `phone_number` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `customers`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_receipt_details`
--

DROP TABLE IF EXISTS `good_receipt_details`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_receipt_details`
(
    `id`           bigint         NOT NULL AUTO_INCREMENT,
    `input_price`  decimal(19, 4) NOT NULL,
    `quantity`     int            NOT NULL,
    `goodreipt_id` bigint         NOT NULL,
    `product_id`   bigint         NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKds2up0fhjemb8ug5v28eb35r4` (`goodreipt_id`),
    KEY `FKi25t5ni7hi6e6fvmiagxfc0jy` (`product_id`),
    CONSTRAINT `FKds2up0fhjemb8ug5v28eb35r4` FOREIGN KEY (`goodreipt_id`) REFERENCES `good_receipts` (`id`),
    CONSTRAINT `FKi25t5ni7hi6e6fvmiagxfc0jy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipt_details`
--

LOCK TABLES `good_receipt_details` WRITE;
/*!40000 ALTER TABLE `good_receipt_details`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `good_receipt_details`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `good_receipts`
--

DROP TABLE IF EXISTS `good_receipts`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `good_receipts`
(
    `id`          bigint         NOT NULL AUTO_INCREMENT,
    `created_at`  datetime(6) DEFAULT NULL,
    `total_price` decimal(19, 4) NOT NULL,
    `updated_at`  datetime(6) DEFAULT NULL,
    `supplier_id` bigint         NOT NULL,
    `user_id`     bigint      DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FK16hdoccmb55lsi1hf2rs12rl9` (`supplier_id`),
    KEY `FKf5qtokynne3a2n7mn566ohxyh` (`user_id`),
    CONSTRAINT `FK16hdoccmb55lsi1hf2rs12rl9` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`),
    CONSTRAINT `FKf5qtokynne3a2n7mn566ohxyh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipts`
--

LOCK TABLES `good_receipts` WRITE;
/*!40000 ALTER TABLE `good_receipts`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `good_receipts`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holidays`
--

DROP TABLE IF EXISTS `holidays`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `holidays`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `description` mediumtext,
    `end_date`    date DEFAULT NULL,
    `start_date`  date DEFAULT NULL,
    `status`      int    NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holidays`
--

LOCK TABLES `holidays` WRITE;
/*!40000 ALTER TABLE `holidays`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `holidays`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_requests`
--

DROP TABLE IF EXISTS `leave_requests`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leave_requests`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `created_at`   datetime(6) DEFAULT NULL,
    `end_date`     date        DEFAULT NULL,
    `leave_reason` mediumtext,
    `start_date`   date        DEFAULT NULL,
    `status`       int    NOT NULL,
    `updated_at`   datetime(6) DEFAULT NULL,
    `user_id`      bigint      DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKh6s8bo5d59oy52b6nxfguf4yx` (`user_id`),
    CONSTRAINT `FKh6s8bo5d59oy52b6nxfguf4yx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_requests`
--

LOCK TABLES `leave_requests` WRITE;
/*!40000 ALTER TABLE `leave_requests`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `leave_requests`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payrolls`
--

DROP TABLE IF EXISTS `payrolls`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payrolls`
(
    `id`                bigint         NOT NULL AUTO_INCREMENT,
    `gross_salary`      decimal(19, 4) NOT NULL,
    `maternity_benefit` decimal(19, 4) NOT NULL,
    `maternity_leaves`  int            NOT NULL,
    `meal_allowance`    decimal(19, 4) NOT NULL,
    `net_salary`        decimal(19, 4) NOT NULL,
    `paid_leaves`       int            NOT NULL,
    `pay_period`        date DEFAULT NULL,
    `penalties`         decimal(19, 4) NOT NULL,
    `sick_leaves`       int            NOT NULL,
    `tax`               decimal(19, 4) NOT NULL,
    `working_days`      int            NOT NULL,
    `user_id`           bigint         NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FKldxx1r63qa4adw90ohy7ga8o` (`user_id`),
    CONSTRAINT `FKldxx1r63qa4adw90ohy7ga8o` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payrolls`
--

LOCK TABLES `payrolls` WRITE;
/*!40000 ALTER TABLE `payrolls`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `payrolls`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products`
(
    `id`         bigint         NOT NULL AUTO_INCREMENT,
    `created_at` datetime(6)  DEFAULT NULL,
    `image`      varchar(255) DEFAULT NULL,
    `name`       varchar(255) DEFAULT NULL,
    `price`      decimal(19, 4) NOT NULL,
    `quantity`   int            NOT NULL,
    `status`     int            NOT NULL,
    `updated_at` datetime(6)  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `products`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    `name`        varchar(255) DEFAULT NULL,
    `status`      int    NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles`
    DISABLE KEYS */;
INSERT INTO `roles`
VALUES (1, 'Quản trị viên hệ thống, có toàn quyền quản lý', 'ADMIN', 1),
       (2, 'Nhân viên làm việc trong hệ thống', 'EMPLOYEE', 1),
       (3, 'Quản lý nhân sự, chịu trách nhiệm về hồ sơ nhân sự', 'HR_MANAGER', 1),
       (4, '	Quản lý kho, giám sát hàng tồn kho, nhập xuất hàng hóa', 'WAREHOUSE_MANAGER', 1),
       (5, 'Quản lý kinh doanh, chịu trách nhiệm về chiến lược kinh doanh', 'BUSINESS_MANAGER', 1);
/*!40000 ALTER TABLE `roles`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seniority_levels`
--

DROP TABLE IF EXISTS `seniority_levels`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seniority_levels`
(
    `id`                 bigint       NOT NULL AUTO_INCREMENT,
    `description`        mediumtext   NOT NULL,
    `level_name`         varchar(100) NOT NULL,
    `salary_coefficient` float        NOT NULL,
    `status`             int          NOT NULL,
    `role_id`            bigint       NOT NULL,
    PRIMARY KEY (`id`),
    KEY `FK42hqucc3nfs0pvgu51i74oq13` (`role_id`),
    CONSTRAINT `FK42hqucc3nfs0pvgu51i74oq13` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
    CONSTRAINT `seniority_levels_chk_2` CHECK ((`salary_coefficient` <= 5))
) ENGINE = InnoDB
  AUTO_INCREMENT = 18
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seniority_levels`
--

LOCK TABLES `seniority_levels` WRITE;
/*!40000 ALTER TABLE `seniority_levels`
    DISABLE KEYS */;
INSERT INTO `seniority_levels`
VALUES (1, 'Nhân viên mới vào làm, đang trong giai đoạn thử việc', 'Thử việc', 0.8, 1, 2),
       (2, 'Nhân viên đã hoàn thành thử việc và làm việc dưới 1 năm.', 'Nhân viên bậc 1', 1, 1, 2),
       (3, 'Nhân viên đã làm việc từ 1 đến 3 năm trong công ty', 'Nhân viên bậc 2', 1.3, 1, 2),
       (4, 'Nhân viên đã gắn bó với công ty từ 3 năm trở lên.', 'Nhân viên bậc 3', 1.5, 1, 2),
       (5, 'Quản lý nhân sự mới được bổ nhiệm.', 'Quản lý nhân sự bậc 1', 1, 1, 3),
       (6, 'Quản lý nhân sự đã có kinh nghiệm từ 1-3 năm.', 'Quản lý nhân sự bậc 2', 1.2, 1, 3),
       (7, 'Quản lý nhân sự đã làm việc trên 3 năm.', 'Quản lý nhân sự bậc 3', 1.5, 1, 3),
       (8, 'Quản lý kho mới được bổ nhiệm.', 'Quản lý kho bậc 1', 1, 1, 4),
       (9, 'Quản lý kho đã có kinh nghiệm từ 1-3 năm.', 'Quản lý kho bậc 2', 1.2, 1, 4),
       (10, 'Quản lý kho đã làm việc trên 3 năm.', 'Quản lý kho bậc 3', 1.5, 1, 4),
       (11, 'Quản lý kho mới được bổ nhiệm.', 'Quản lý kinh doanh bậc 1', 1, 1, 5),
       (12, 'Quản lý kho đã có kinh nghiệm từ 1-3 năm.', 'Quản lý kinh doanh kho bậc 2', 1.4, 1, 5),
       (13, 'Quản lý kho đã làm việc trên 3 năm.', 'Quản lý kinh doanh kho bậc 3', 1.8, 1, 5);
/*!40000 ALTER TABLE `seniority_levels`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `suppliers`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `address`      varchar(255) DEFAULT NULL,
    `created_at`   datetime(6)  DEFAULT NULL,
    `name`         varchar(255) DEFAULT NULL,
    `phone_number` varchar(255) DEFAULT NULL,
    `status`       int    NOT NULL,
    `updated_at`   datetime(6)  DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `suppliers`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tokens`
--

DROP TABLE IF EXISTS `tokens`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tokens`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `access_token`  varchar(255) DEFAULT NULL,
    `created_at`    datetime(6)  DEFAULT NULL,
    `refresh_token` varchar(255) DEFAULT NULL,
    `updated_at`    datetime(6)  DEFAULT NULL,
    `username`      varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK7nq3j9mbmotv8kv3nv9kbcb7c` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tokens`
--

LOCK TABLES `tokens` WRITE;
/*!40000 ALTER TABLE `tokens`
    DISABLE KEYS */;
INSERT INTO `tokens`
VALUES (1,
        'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBRE1JTiIsImlhdCI6MTc0MTE4NDE5NCwiZXhwIjoxNzQ0Nzg0MTk0fQ.Em9Eg0yPj4F8MlT8o0bRZRxzN02LgFwBQJ5rnjy-NSk',
        '2025-03-05 21:16:30.531138',
        'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBRE1JTiIsImlhdCI6MTc0MTE4NDE5NCwiZXhwIjoxNzQyMzkzNzk0fQ.-r2Y0RgXlQOU1KmcLROzvrJtdjcrKY6oVsy5P5Dp34Y',
        '2025-03-05 21:16:34.067015', 'ADMIN');
/*!40000 ALTER TABLE `tokens`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users`
(
    `id`            bigint NOT NULL AUTO_INCREMENT,
    `address`       varchar(255) DEFAULT NULL,
    `created_at`    datetime(6)  DEFAULT NULL,
    `date_of_birth` date         DEFAULT NULL,
    `email`         varchar(255) DEFAULT NULL,
    `full_name`     varchar(255) DEFAULT NULL,
    `gender`        tinyint      DEFAULT NULL,
    `last_login`    datetime(6)  DEFAULT NULL,
    `password`      varchar(255) DEFAULT NULL,
    `phone_number`  varchar(255) DEFAULT NULL,
    `status`        int    NOT NULL,
    `updated_at`    datetime(6)  DEFAULT NULL,
    `username`      varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `users_chk_1` CHECK ((`gender` between 0 and 2))
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users`
    DISABLE KEYS */;
INSERT INTO `users`
VALUES (2, 'Hà Nội, Việt Nam', '2025-03-05 21:00:35.000173', '1990-05-15', 'an.nguyen@example.com', 'Nguyễn Văn An', 0,
        NULL, '$2a$10$NFNUzoBf7f59miXiM2blcuTD9EuI3vL5WYrDkx6NR4hCRbjmna182', '0987654321', 1, NULL, 'ADMIN'),
       (3, 'TP. Hồ Chí Minh, Việt Nam', '2025-03-05 21:00:46.305184', '1995-07-20', 'hoa.tran@example.com',
        'Trần Thị Hoa', 1, NULL, '$2a$10$BrZzi3u.aS1rubkuTSEJauwgwWSdR9muRBTfUSqaiAdK9b2HcfkTy', '0976543210', 1, NULL,
        'EMPLOYEE'),
       (4, 'Đà Nẵng, Việt Nam', '2025-03-05 21:01:13.014281', '1988-09-10', 'tuan.le@example.com', 'Lê Minh Tuấn', 0,
        NULL, '$2a$10$VEsW6l63BLabq2XkGmWYxuVd7M/PVVVd44o8NcW0Y6.O213FOqBCy', '0965432109', 1, NULL, 'HR_MANAGER'),
       (5, 'Hải Phòng, Việt Nam', '2025-03-05 21:01:23.772907', '1992-11-25', 'huy.pham@example.com', 'Phạm Quang Huy',
        0, NULL, '$2a$10$WWub6z22bRw8lYBKHMbHxujIRI.R6GLVNU8whAYNiXemPQB3.i7Gm', '0954321098', 1, NULL,
        'WAREHOUSE_MANAGER'),
       (6, 'Cần Thơ, Việt Nam', '2025-03-05 21:01:31.739038', '1993-04-30', 'lan.do@example.com', 'Đỗ Thị Lan', 1, NULL,
        '$2a$10$7u0rWM7puzgWD6GQWyUzsuuJfXJYzTZsAyONEkZCe/hX8uuxPxQcK', '0943210987', 1, NULL, 'BUSINESS_MANAGER');
/*!40000 ALTER TABLE `users`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2025-03-05 21:28:58
