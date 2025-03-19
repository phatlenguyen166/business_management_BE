CREATE DATABASE  IF NOT EXISTS `business_management` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `business_management`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: business_management
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
                                      `attendance_status` enum('ABSENT','ON_LEAVE','PRESENT') DEFAULT NULL,
                                      `check_in` time(6) DEFAULT NULL,
                                      `check_out` time(6) DEFAULT NULL,
                                      `leave_type_enum` enum('HOLIDAY','MATERNITY_LEAVE','PAID_LEAVE','SICK_LEAVE') DEFAULT NULL,
                                      `working_day` date DEFAULT NULL,
                                      `attendance_id` bigint NOT NULL,
                                      `holiday_id` bigint DEFAULT NULL,
                                      PRIMARY KEY (`id`),
                                      KEY `FK1d74vxn7muwg3w6matb0tkf9c` (`attendance_id`),
                                      KEY `FK9otly272s6xvcvno3urlwihlf` (`holiday_id`),
                                      CONSTRAINT `FK1d74vxn7muwg3w6matb0tkf9c` FOREIGN KEY (`attendance_id`) REFERENCES `attendances` (`id`),
                                      CONSTRAINT `FK9otly272s6xvcvno3urlwihlf` FOREIGN KEY (`holiday_id`) REFERENCES `holidays` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance_details`
--

LOCK TABLES `attendance_details` WRITE;
/*!40000 ALTER TABLE `attendance_details` DISABLE KEYS */;
INSERT INTO `attendance_details` VALUES (1,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-01',1,1),(2,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-02',1,NULL),(3,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-03',1,NULL),(4,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-06',1,NULL),(5,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-07',1,NULL),(6,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-08',1,NULL),(7,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-09',1,NULL),(8,'ON_LEAVE',NULL,NULL,'PAID_LEAVE','2025-01-10',1,NULL),(9,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-13',1,NULL),(10,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-14',1,NULL),(11,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-15',1,NULL),(12,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-16',1,NULL),(13,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-17',1,NULL),(14,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-20',1,NULL),(15,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-21',1,NULL),(16,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-22',1,NULL),(17,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-23',1,NULL),(18,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-24',1,NULL),(19,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-27',1,NULL),(20,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-28',1,NULL),(21,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-29',1,2),(22,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-30',1,2),(23,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-31',1,2),(24,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-01',2,1),(25,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-02',2,NULL),(26,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-03',2,NULL),(27,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-06',2,NULL),(28,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-07',2,NULL),(29,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-08',2,NULL),(30,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-09',2,NULL),(31,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-10',2,NULL),(32,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-13',2,NULL),(33,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-14',2,NULL),(34,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-15',2,NULL),(35,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-16',2,NULL),(36,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-17',2,NULL),(37,'ON_LEAVE',NULL,NULL,'PAID_LEAVE','2025-01-20',2,NULL),(38,'ON_LEAVE',NULL,NULL,'PAID_LEAVE','2025-01-21',2,NULL),(39,'ON_LEAVE',NULL,NULL,'PAID_LEAVE','2025-01-22',2,NULL),(40,'ON_LEAVE',NULL,NULL,'PAID_LEAVE','2025-01-23',2,NULL),(41,'ON_LEAVE',NULL,NULL,'PAID_LEAVE','2025-01-24',2,NULL),(42,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-27',2,NULL),(43,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-28',2,NULL),(44,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-29',2,2),(45,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-30',2,2),(46,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-31',2,2),(47,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-01',3,1),(48,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-02',3,NULL),(49,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-03',3,NULL),(50,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-06',3,NULL),(51,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-07',3,NULL),(52,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-08',3,NULL),(53,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-09',3,NULL),(54,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-10',3,NULL),(55,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-13',3,NULL),(56,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-14',3,NULL),(57,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-15',3,NULL),(58,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-16',3,NULL),(59,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-17',3,NULL),(60,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-20',3,NULL),(61,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-21',3,NULL),(62,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-22',3,NULL),(63,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-23',3,NULL),(64,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-24',3,NULL),(65,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-27',3,NULL),(66,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-28',3,NULL),(67,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-29',3,2),(68,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-30',3,2),(69,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-31',3,2),(70,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-01',4,1),(71,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-02',4,NULL),(72,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-03',4,NULL),(73,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-06',4,NULL),(74,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-07',4,NULL),(75,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-08',4,NULL),(76,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-09',4,NULL),(77,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-10',4,NULL),(78,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-13',4,NULL),(79,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-14',4,NULL),(80,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-15',4,NULL),(81,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-16',4,NULL),(82,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-17',4,NULL),(83,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-20',4,NULL),(84,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-21',4,NULL),(85,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-22',4,NULL),(86,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-23',4,NULL),(87,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-24',4,NULL),(88,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-01-27',4,NULL),(89,'ON_LEAVE',NULL,NULL,'PAID_LEAVE','2025-01-28',4,NULL),(90,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-29',4,2),(91,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-30',4,2),(92,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-01-31',4,2),(93,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-02-03',5,2),(94,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-04',5,NULL),(95,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-05',5,NULL),(96,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-06',5,NULL),(97,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-07',5,NULL),(98,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-10',5,NULL),(99,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-11',5,NULL),(100,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-12',5,NULL),(101,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-13',5,NULL),(102,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-14',5,NULL),(103,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-17',5,NULL),(104,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-18',5,NULL),(105,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-19',5,NULL),(106,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-20',5,NULL),(107,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-21',5,NULL),(108,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-24',5,NULL),(109,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-25',5,NULL),(110,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-26',5,NULL),(111,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-27',5,NULL),(112,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-28',5,NULL),(113,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-02-03',6,2),(114,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-04',6,NULL),(115,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-05',6,NULL),(116,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-06',6,NULL),(117,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-07',6,NULL),(118,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-10',6,NULL),(119,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-11',6,NULL),(120,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-12',6,NULL),(121,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-13',6,NULL),(122,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-14',6,NULL),(123,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-17',6,NULL),(124,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-18',6,NULL),(125,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-19',6,NULL),(126,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-20',6,NULL),(127,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-21',6,NULL),(128,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-24',6,NULL),(129,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-25',6,NULL),(130,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-26',6,NULL),(131,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-27',6,NULL),(132,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-28',6,NULL),(133,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-02-03',7,2),(134,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-04',7,NULL),(135,'ON_LEAVE',NULL,NULL,'MATERNITY_LEAVE','2025-02-05',7,NULL),(136,'ON_LEAVE',NULL,NULL,'MATERNITY_LEAVE','2025-02-06',7,NULL),(137,'ON_LEAVE',NULL,NULL,'MATERNITY_LEAVE','2025-02-07',7,NULL),(138,'ON_LEAVE',NULL,NULL,'MATERNITY_LEAVE','2025-02-10',7,NULL),(139,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-11',7,NULL),(140,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-12',7,NULL),(141,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-13',7,NULL),(142,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-14',7,NULL),(143,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-17',7,NULL),(144,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-18',7,NULL),(145,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-19',7,NULL),(146,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-20',7,NULL),(147,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-21',7,NULL),(148,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-24',7,NULL),(149,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-25',7,NULL),(150,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-26',7,NULL),(151,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-27',7,NULL),(152,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-28',7,NULL),(153,'ON_LEAVE',NULL,NULL,'HOLIDAY','2025-02-03',8,2),(154,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-04',8,NULL),(155,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-05',8,NULL),(156,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-06',8,NULL),(157,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-07',8,NULL),(158,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-10',8,NULL),(159,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-11',8,NULL),(160,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-12',8,NULL),(161,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-13',8,NULL),(162,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-14',8,NULL),(163,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-17',8,NULL),(164,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-18',8,NULL),(165,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-19',8,NULL),(166,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-20',8,NULL),(167,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-21',8,NULL),(168,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-24',8,NULL),(169,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-25',8,NULL),(170,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-26',8,NULL),(171,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-27',8,NULL),(172,'PRESENT','08:30:00.000000','17:30:00.000000',NULL,'2025-02-28',8,NULL);
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
                               `month_of_year` varchar(255) DEFAULT NULL,
                               `total_holiday_leaves` int NOT NULL,
                               `total_maternity_leaves` int NOT NULL,
                               `total_paid_leaves` int NOT NULL,
                               `total_sick_leaves` int NOT NULL,
                               `total_unpaid_leaves` int NOT NULL,
                               `total_working_days` int NOT NULL,
                               `user_id` bigint DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FK8o39cn3ghqwhccyrrqdesttr8` (`user_id`),
                               CONSTRAINT `FK8o39cn3ghqwhccyrrqdesttr8` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendances`
--

LOCK TABLES `attendances` WRITE;
/*!40000 ALTER TABLE `attendances` DISABLE KEYS */;
INSERT INTO `attendances` VALUES (1,'2025-01',4,0,1,0,0,18,3),(2,'2025-01',4,0,5,0,0,14,4),(3,'2025-01',4,0,0,0,0,19,5),(4,'2025-01',4,0,1,0,0,18,6),(5,'2025-02',1,0,0,0,0,19,3),(6,'2025-02',1,0,0,0,0,19,4),(7,'2025-02',1,4,0,0,0,15,5),(8,'2025-02',1,0,0,0,0,19,6);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_details`
--

LOCK TABLES `bill_details` WRITE;
/*!40000 ALTER TABLE `bill_details` DISABLE KEYS */;
INSERT INTO `bill_details` VALUES (1,1,120000.0000,1,1),(2,2,99000.0000,1,2),(3,1,120000.0000,2,1),(4,2,99000.0000,2,2);
/*!40000 ALTER TABLE `bill_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bills` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
INSERT INTO `bills` VALUES (1,'234 Nguyen Trai, quan 5, TPHCM','2025-03-19 22:34:12.350620',318000.0000,'2025-03-19 22:34:12.452106',2,5),(2,'234 Nguyen Trai, quan 5, TPHCM','2025-03-19 22:34:26.366559',318000.0000,'2025-03-19 22:34:26.377500',2,5);
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
                                        `good_receipt_id` bigint NOT NULL,
                                        `product_id` bigint NOT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `FKds2up0fhjemb8ug5v28eb35r4` (`good_receipt_id`),
                                        KEY `FKi25t5ni7hi6e6fvmiagxfc0jy` (`product_id`),
                                        CONSTRAINT `FKds2up0fhjemb8ug5v28eb35r4` FOREIGN KEY (`good_receipt_id`) REFERENCES `good_receipts` (`id`),
                                        CONSTRAINT `FKi25t5ni7hi6e6fvmiagxfc0jy` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipt_details`
--

LOCK TABLES `good_receipt_details` WRITE;
/*!40000 ALTER TABLE `good_receipt_details` DISABLE KEYS */;
INSERT INTO `good_receipt_details` VALUES (1,108000.0000,10,1,1),(2,84150.0000,15,1,2);
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
                                 `user_id` bigint DEFAULT NULL,
                                 PRIMARY KEY (`id`),
                                 KEY `FKf5qtokynne3a2n7mn566ohxyh` (`user_id`),
                                 CONSTRAINT `FKf5qtokynne3a2n7mn566ohxyh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `good_receipts`
--

LOCK TABLES `good_receipts` WRITE;
/*!40000 ALTER TABLE `good_receipts` DISABLE KEYS */;
INSERT INTO `good_receipts` VALUES (1,'2025-03-12 15:26:01.407869',2342250.0000,'2025-03-12 15:26:01.453898',5);
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holidays`
--

LOCK TABLES `holidays` WRITE;
/*!40000 ALTER TABLE `holidays` DISABLE KEYS */;
INSERT INTO `holidays` VALUES (1,'Tết Dương lịch - Ngày đầu năm mới theo lịch Gregory','2025-01-01','2025-01-01',1,'Tết Dương lịch'),(2,'Tết Nguyên Đán - Kỳ nghỉ lễ quan trọng nhất trong năm, kéo dài 7 ngày','2025-02-03','2025-01-28',1,'Tết Nguyên Đán');
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
                                  `leave_reason` enum('HOLIDAY','MATERNITY_LEAVE','PAID_LEAVE','SICK_LEAVE') NOT NULL,
                                  `send_date` datetime(6) DEFAULT NULL,
                                  `start_date` date NOT NULL,
                                  `status` int NOT NULL,
                                  `title` varchar(255) NOT NULL,
                                  `updated_at` datetime(6) DEFAULT NULL,
                                  `user_id` bigint DEFAULT NULL,
                                  `total_day_leave` bigint NOT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FKh6s8bo5d59oy52b6nxfguf4yx` (`user_id`),
                                  CONSTRAINT `FKh6s8bo5d59oy52b6nxfguf4yx` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_requests`
--

LOCK TABLES `leave_requests` WRITE;
/*!40000 ALTER TABLE `leave_requests` DISABLE KEYS */;
INSERT INTO `leave_requests` VALUES (1,'Nghỉ để giải quyết việc gia đình','2025-01-12','PAID_LEAVE','2025-01-05 08:30:00.000000','2025-01-10',1,'Nghỉ phép cá nhân','2025-01-05 08:30:00.000000',3,0),(2,'Ốm cần nghỉ ngơi','2025-02-16','SICK_LEAVE','2025-02-14 10:00:00.000000','2025-02-15',1,'Nghỉ bệnh','2025-02-14 10:00:00.000000',3,0),(3,'Đi du lịch cùng gia đình','2025-01-25','PAID_LEAVE','2025-01-15 14:00:00.000000','2025-01-20',1,'Nghỉ phép du lịch','2025-01-15 14:00:00.000000',4,0),(4,'Nghỉ sinh con','2025-02-10','MATERNITY_LEAVE','2025-01-30 09:00:00.000000','2025-02-05',1,'Nghỉ thai sản','2025-01-30 09:00:00.000000',5,0),(5,'Về quê thăm người thân','2025-02-02','PAID_LEAVE','2025-01-22 16:30:00.000000','2025-01-28',1,'Nghỉ phép thăm gia đình','2025-01-22 16:30:00.000000',6,0);
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
                            `allowance` decimal(19,4) NOT NULL,
                            `standard_working_days` int NOT NULL,
                            `attendance_id` bigint NOT NULL,
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `UK6rnl02upmdwog20grcubko3vk` (`attendance_id`),
                            KEY `FKldxx1r63qa4adw90ohy7ga8o` (`user_id`),
                            CONSTRAINT `FKe2vymg8kl12b8svk7ec2y1dn8` FOREIGN KEY (`attendance_id`) REFERENCES `attendances` (`id`),
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
                            `supplier_id` bigint DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FK6i174ixi9087gcvvut45em7fd` (`supplier_id`),
                            CONSTRAINT `FK6i174ixi9087gcvvut45em7fd` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'2024-02-15 11:30:45.000000','https://picsum.photos/200?random=1','Đắc Nhân Tâm',120000.0000,8,1,'2025-03-19 22:34:26.378644',1),(2,'2024-02-10 09:15:20.000000','https://picsum.photos/200?random=2','Nhà Giả Kim',99000.0000,11,1,'2025-03-19 22:34:26.381644',2),(3,'2024-01-05 14:10:00.000000','https://picsum.photos/200?random=3','Tôi Thấy Hoa Vàng Trên Cỏ Xanh',85000.0000,0,1,'2024-02-20 10:45:12.000000',3),(4,'2024-03-01 10:20:30.000000','https://picsum.photos/200?random=4','Quẳng Gánh Lo Đi & Vui Sống',110000.0000,0,1,'2024-03-08 12:00:00.000000',4),(5,'2024-02-18 09:45:00.000000','https://picsum.photos/200?random=5','Bí Mật Của May Mắn',150000.0000,0,1,'2024-03-06 11:30:15.000000',5),(6,'2024-01-22 13:50:10.000000','https://picsum.photos/200?random=6','Dám Nghĩ Lớn',180000.0000,0,1,'2024-02-28 15:20:30.000000',6),(7,'2023-12-30 08:25:45.000000','https://picsum.photos/200?random=7','Muôn Kiếp Nhân Sinh',145000.0000,0,1,'2024-02-27 17:15:20.000000',7),(8,'2023-11-15 10:40:30.000000','https://picsum.photos/200?random=8','Hiểu Về Trái Tim',97000.0000,0,1,'2024-02-25 09:35:10.000000',8),(9,'2023-10-28 12:05:15.000000','https://picsum.photos/200?random=9','Cà Phê Cùng Tony',132000.0000,0,1,'2024-02-23 14:10:00.000000',9),(10,'2023-09-18 11:20:10.000000','https://picsum.photos/200?random=10','Người Giàu Có Nhất Thành Babylon',119000.0000,0,1,'2024-02-21 10:00:45.000000',10);
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
INSERT INTO `suppliers` VALUES (1,'Hà Nội, Việt Nam','2024-01-01 12:00:00.000000','Nhà Xuất Bản Kim Đồng','+84-24-3851-4352',1,'2024-03-10 14:20:30.000000',10,'contact@kimdong.vn'),(2,'TP.HCM, Việt Nam','2023-11-15 09:15:00.000000','NXB Trẻ','+84-28-3930-0700',1,'2024-02-28 16:45:12.000000',15,'info@nxbtre.vn'),(3,'Hà Nội, Việt Nam','2024-02-05 08:50:30.000000','Nhà Xuất Bản Giáo Dục','+84-24-3869-2694',1,'2024-03-01 10:10:00.000000',20,'support@giaoduc.vn'),(4,'TP.HCM, Việt Nam','2024-02-10 07:45:20.000000','Nhà Sách Fahasa','+84-28-3829-2180',1,'2024-03-05 13:55:12.000000',18,'contact@fahasa.com'),(5,'TP.HCM, Việt Nam','2023-12-20 15:22:10.000000','Tiki Trading','+84-28-7300-9999',1,'2024-02-15 09:10:30.000000',12,'info@tiki.vn'),(6,'TP.HCM, Việt Nam','2023-12-01 11:30:00.000000','Vinabook','+84-28-7301-8886',1,'2024-02-25 14:40:20.000000',25,'support@vinabook.com'),(7,'Hà Nội, Việt Nam','2023-11-25 10:00:00.000000','Nhà Xuất Bản Văn Học','+84-24-3944-3631',1,'2024-03-01 12:30:00.000000',30,'sales@vanhoc.vn'),(8,'TP.HCM, Việt Nam','2023-10-10 15:45:12.000000','NXB Tổng Hợp TP.HCM','+84-28-3829-7140',1,'2024-03-02 14:55:30.000000',22,'info@nxbtonghop.vn'),(9,'Hà Nội, Việt Nam','2023-09-15 14:22:40.000000','Nhà Xuất Bản Đại Học Quốc Gia','+84-24-3754-7505',1,'2024-03-03 11:12:50.000000',28,'sales@dhqg.vn'),(10,'TP.HCM, Việt Nam','2023-08-30 09:30:00.000000','Nhà Sách Phương Nam','+84-28-7303-1666',1,'2024-02-28 10:45:00.000000',35,'info@phuongnambook.vn');
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

-- Dump completed on 2025-03-19 22:34:55
