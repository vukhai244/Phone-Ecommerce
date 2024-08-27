CREATE DATABASE IF NOT EXISTS phone_ecommerce_db;
USE phone_ecommerce_db;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: phone_ecommerce_db
-- ------------------------------------------------------
-- Server version	9.0.1

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `full_name` varchar(255) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `role` enum('CUSTOMER','ADMIN') NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'Nguyen Van A','nguyenvana','hashed_password_1','nguyenvana@email.com','0123456789','Ha Noi','CUSTOMER','2024-08-08 15:41:55','2024-08-08 15:41:55'),(2,'Tran Thi B','tranthib','hashed_password_2','tranthib@email.com','0987654321','Ho Chi Minh City','CUSTOMER','2024-08-08 15:41:55','2024-08-08 15:41:55'),(3,'Le Van C','levanc','hashed_password_3','levanc@email.com','0369852147','Da Nang','ADMIN','2024-08-08 15:41:55','2024-08-08 15:41:55'),(4,'Pham Thi D','phamthid','hashed_password_4','phamthid@email.com','0741852963','Can Tho','CUSTOMER','2024-08-08 15:41:55','2024-08-08 15:41:55'),(5,'Hoang Van E','hoangvane','hashed_password_5','hoangvane@email.com','0159753684','Hai Phong','ADMIN','2024-08-08 15:41:55','2024-08-08 15:41:55');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cart_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cart_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `account` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (1,1,'2024-08-08 15:42:44','2024-08-08 15:42:44'),(2,2,'2024-08-08 15:42:44','2024-08-08 15:42:44'),(3,3,'2024-08-08 15:42:44','2024-08-08 15:42:44'),(4,4,'2024-08-08 15:54:11','2024-08-08 15:54:11'),(5,5,'2024-08-09 18:27:11','2024-08-09 18:27:11');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `cart_item_id` bigint NOT NULL AUTO_INCREMENT,
  `cart_id` bigint DEFAULT NULL,
  `phone_id` bigint DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`cart_item_id`),
  KEY `cart_id` (`cart_id`),
  KEY `phone_id` (`phone_id`),
  CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`),
  CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`phone_id`) REFERENCES `phone` (`phone_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (6,4,5,6),(7,1,1,2),(8,1,2,1),(9,2,3,3),(10,2,4,1),(11,3,5,2),(12,3,4,1),(13,5,5,3);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) NOT NULL,
  `description` text,
  `parent_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`category_id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `category` (`category_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Electronics','All electronic items',NULL,'2024-08-08 15:48:37','2024-08-08 15:48:37'),(2,'Smartphones','All types of smartphones',1,'2024-08-08 15:48:37','2024-08-08 15:48:37'),(3,'Laptops','Various kinds of laptops',1,'2024-08-08 15:48:37','2024-08-08 15:48:37'),(4,'Accessories','Electronic accessories',1,'2024-08-08 15:48:37','2024-08-08 15:48:37'),(5,'Chargers','Phone and laptop chargers',4,'2024-08-08 15:48:37','2024-08-08 15:48:37'),(6,'Wireless Chargers','Chargers without cables',5,'2024-08-08 15:48:37','2024-08-08 15:48:37');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint NOT NULL,
  `phone_id` bigint NOT NULL,
  `quantity` int NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `phone_id` (`phone_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`phone_id`) REFERENCES `phone` (`phone_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,1,2,999),(2,1,2,1,899),(3,2,3,3,1999),(4,3,4,1,1499),(5,4,1,2,999),(6,8,1,2,999),(7,8,2,2,899);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `order_date` datetime NOT NULL,
  `total_amount` int NOT NULL,
  `status` varchar(50) NOT NULL,
  `shipping_address` varchar(255) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `account` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'2024-08-08 10:30:00',150000,'Processing','123 Main St, City, Country'),(2,2,'2024-08-08 11:00:00',250000,'CANCELLED','HaNoi'),(3,3,'2024-08-08 12:00:00',100000,'Delivered','789 Maple St, City, Country'),(4,1,'2024-08-08 13:00:00',300000,'Cancelled','123 Main St, City, Country'),(5,4,'2024-08-08 14:00:00',200000,'Processing','101 Oak St, City, Country'),(6,5,'2024-08-11 01:25:35',0,'PENDING','Ha Noi'),(7,5,'2024-08-11 01:27:35',0,'PENDING','Ha Noi'),(8,5,'2024-08-11 01:28:29',3796,'PENDING','Ha Noi');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone`
--

DROP TABLE IF EXISTS `phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phone` (
  `phone_id` bigint NOT NULL AUTO_INCREMENT,
  `phone_code` varchar(50) NOT NULL,
  `phone_name` varchar(255) NOT NULL,
  `image_urls` text,
  `description` text,
  `price` int NOT NULL,
  `brand` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `stock_quantity` int NOT NULL,
  `category_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`phone_id`),
  UNIQUE KEY `phone_code` (`phone_code`),
  KEY `idx_product_code` (`phone_code`),
  KEY `idx_category` (`category_id`),
  CONSTRAINT `phone_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone`
--

LOCK TABLES `phone` WRITE;
/*!40000 ALTER TABLE `phone` DISABLE KEYS */;
INSERT INTO `phone` VALUES (1,'P001','iPhone 13','image1.jpg,image2.jpg','Latest model of iPhone 13',999,'Apple','13',50,2,'2024-08-08 15:49:24','2024-08-08 15:49:24'),(2,'P002','Samsung Galaxy S21','image3.jpg,image4.jpg','Latest model of Samsung Galaxy S21',899,'Samsung','S21',30,2,'2024-08-08 15:49:24','2024-08-08 15:49:24'),(3,'P003','MacBook Pro','image5.jpg,image6.jpg','Latest model of MacBook Pro',1999,'Apple','Pro',20,3,'2024-08-08 15:49:24','2024-08-08 15:49:24'),(4,'P004','Dell XPS 13','image7.jpg,image8.jpg','Latest model of Dell XPS 13',1499,'Dell','XPS 13',25,3,'2024-08-08 15:49:24','2024-08-08 15:49:24'),(5,'P005','Sony WH-1000XM4','image9.jpg,image10.jpg','Latest noise-cancelling headphones from Sony',349,'Sony','WH-1000XM4',100,4,'2024-08-08 15:49:24','2024-08-08 15:49:24');
/*!40000 ALTER TABLE `phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone_category`
--

DROP TABLE IF EXISTS `phone_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phone_category` (
  `phone_id` bigint NOT NULL,
  `category_id` int NOT NULL,
  PRIMARY KEY (`phone_id`,`category_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `phone_category_ibfk_1` FOREIGN KEY (`phone_id`) REFERENCES `phone` (`phone_id`) ON DELETE CASCADE,
  CONSTRAINT `phone_category_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone_category`
--

LOCK TABLES `phone_category` WRITE;
/*!40000 ALTER TABLE `phone_category` DISABLE KEYS */;
INSERT INTO `phone_category` VALUES (1,1),(2,1),(1,2);
/*!40000 ALTER TABLE `phone_category` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-16 17:05:44
