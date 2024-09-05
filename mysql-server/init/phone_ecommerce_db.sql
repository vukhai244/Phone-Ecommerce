CREATE DATABASE IF NOT EXISTS phone_ecommerce_db;
USE phone_ecommerce_db;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: phone_ecommerce_db
-- ------------------------------------------------------
-- Server version	8.0.37

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
  `user_id` char(36) NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('6ba7b819-9dad-11d1-80b4-00c04fd430c8','Nguyen Van A','nguyenvana','hashed_password_1','nguyenvana@email.com','0123456789','Ha Noi','CUSTOMER','2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b81a-9dad-11d1-80b4-00c04fd430c8','Tran Thi B','tranthib','hashed_password_2','tranthib@email.com','0987654321','Ho Chi Minh City','CUSTOMER','2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b81b-9dad-11d1-80b4-00c04fd430c8','Le Van C','levanc','hashed_password_3','levanc@email.com','0369852147','Da Nang','ADMIN','2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b81c-9dad-11d1-80b4-00c04fd430c8','Pham Thi D','phamthid','hashed_password_4','phamthid@email.com','0741852963','Can Tho','CUSTOMER','2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b81d-9dad-11d1-80b4-00c04fd430c8','Hoang Van E','hoangvane','hashed_password_5','hoangvane@email.com','0159753684','Hai Phong','ADMIN','2024-09-05 12:31:04','2024-09-05 12:31:04');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cart_id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`cart_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `account` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES ('6ba7b81e-9dad-11d1-80b4-00c04fd430c8','6ba7b819-9dad-11d1-80b4-00c04fd430c8','2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b81f-9dad-11d1-80b4-00c04fd430c8','6ba7b81a-9dad-11d1-80b4-00c04fd430c8','2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b820-9dad-11d1-80b4-00c04fd430c8','6ba7b81b-9dad-11d1-80b4-00c04fd430c8','2024-09-05 12:31:04','2024-09-05 12:31:04');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_item` (
  `cart_item_id` char(36) NOT NULL,
  `cart_id` char(36) DEFAULT NULL,
  `phone_id` char(36) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`cart_item_id`),
  KEY `cart_id` (`cart_id`),
  KEY `phone_id` (`phone_id`),
  CONSTRAINT `cart_item_ibfk_1` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`cart_id`),
  CONSTRAINT `cart_item_ibfk_2` FOREIGN KEY (`phone_id`) REFERENCES `phone` (`phone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES ('6ba7b821-9dad-11d1-80b4-00c04fd430c8','6ba7b81e-9dad-11d1-80b4-00c04fd430c8','6ba7b814-9dad-11d1-80b4-00c04fd430c8',2),('6ba7b822-9dad-11d1-80b4-00c04fd430c8','6ba7b81e-9dad-11d1-80b4-00c04fd430c8','6ba7b815-9dad-11d1-80b4-00c04fd430c8',1);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Category`
--

DROP TABLE IF EXISTS `Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Category` (
  `category_id` char(36) NOT NULL,
  `category_name` varchar(100) NOT NULL,
  `description` text,
  `parent_id` char(36) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`category_id`),
  KEY `FK5s5t2pfpxo0vnd1ihc43721ty` (`parent_id`),
  CONSTRAINT `category_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `Category` (`category_id`) ON DELETE SET NULL,
  CONSTRAINT `FK5s5t2pfpxo0vnd1ihc43721ty` FOREIGN KEY (`parent_id`) REFERENCES `Category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Category`
--

LOCK TABLES `Category` WRITE;
/*!40000 ALTER TABLE `Category` DISABLE KEYS */;
INSERT INTO `Category` VALUES ('550e8400-e29b-41d4-a716-446655440000','Smartphones','All types of smartphones','f47ac10b-58cc-4372-a567-0e02b2c3d479','2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b810-9dad-11d1-80b4-00c04fd430c8','Laptops','Various kinds of laptops','f47ac10b-58cc-4372-a567-0e02b2c3d479','2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b811-9dad-11d1-80b4-00c04fd430c8','Accessories','Electronic accessories','f47ac10b-58cc-4372-a567-0e02b2c3d479','2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b812-9dad-11d1-80b4-00c04fd430c8','Chargers','Phone and laptop chargers','6ba7b811-9dad-11d1-80b4-00c04fd430c8','2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b813-9dad-11d1-80b4-00c04fd430c8','Wireless Chargers','Chargers without cables','6ba7b812-9dad-11d1-80b4-00c04fd430c8','2024-09-05 12:31:04','2024-09-05 12:31:04'),('f47ac10b-58cc-4372-a567-0e02b2c3d479','Electronics','All electronic items',NULL,'2024-09-05 12:31:04','2024-09-05 12:31:04');
/*!40000 ALTER TABLE `Category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` char(36) NOT NULL,
  `order_id` char(36) NOT NULL,
  `phone_id` char(36) NOT NULL,
  `quantity` int NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `phone_id` (`phone_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`phone_id`) REFERENCES `phone` (`phone_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` char(36) NOT NULL,
  `user_id` char(36) NOT NULL,
  `order_date` datetime NOT NULL,
  `total_amount` int NOT NULL,
  `status` varchar(50) NOT NULL,
  `shipping_address` varchar(255) NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `account` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone`
--

DROP TABLE IF EXISTS `phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phone` (
  `phone_id` char(36) NOT NULL,
  `phone_code` varchar(50) NOT NULL,
  `phone_name` varchar(255) NOT NULL,
  `image_urls` text,
  `description` text,
  `price` int NOT NULL,
  `brand` varchar(100) DEFAULT NULL,
  `model` varchar(100) DEFAULT NULL,
  `stock_quantity` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`phone_id`),
  UNIQUE KEY `phone_code` (`phone_code`),
  KEY `idx_product_code` (`phone_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone`
--

LOCK TABLES `phone` WRITE;
/*!40000 ALTER TABLE `phone` DISABLE KEYS */;
INSERT INTO `phone` VALUES ('0e9fbaea-7af4-4c37-9aed-c636e3712400','P0010677','Ngockhai9ii','ngockhai.jpg','Đồng hồ thông minh',99900000,'Apple','Watch Series 6',100,'2024-09-05 21:02:07','2024-09-05 21:02:07'),('1ac1d860-9b66-4a15-a4fa-907c1f81fd00','P0010660777','Ngockhai9ii','ngockhai.jpg','Đồng hồ thông minh',99900000,'Apple','Watch Series 6',0,'2024-09-06 01:54:44','2024-09-06 01:54:44'),('628ac492-2978-4cf5-b2da-174b806da27e','P00106777','Ngockhai9ii','ngockhai.jpg','Đồng hồ thông minh',99900000,'Apple','Watch Series 6',100,'2024-09-05 21:06:33','2024-09-05 21:06:33'),('6ba7b814-9dad-11d1-80b4-00c04fd430c8','P001','iPhone 13','image1.jpg,image2.jpg','Latest model of iPhone 13',999,'Apple','13',50,'2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b815-9dad-11d1-80b4-00c04fd430c8','P002','Samsung Galaxy S21','image3.jpg,image4.jpg','Latest model of Samsung Galaxy S21',899,'Samsung','S21',30,'2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b816-9dad-11d1-80b4-00c04fd430c8','P003','MacBook Pro','image5.jpg,image6.jpg','Latest model of MacBook Pro',1999,'Apple','Pro',20,'2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b817-9dad-11d1-80b4-00c04fd430c8','P004','Dell XPS 13','image7.jpg,image8.jpg','Latest model of Dell XPS 13',1499,'Dell','XPS 13',25,'2024-09-05 12:31:04','2024-09-05 12:31:04'),('6ba7b818-9dad-11d1-80b4-00c04fd430c8','P005','Sony WH-1000XM4','image9.jpg,image10.jpg','Latest noise-cancelling headphones from Sony',349,'Sony','WH-1000XM4',100,'2024-09-05 12:31:04','2024-09-05 12:31:04'),('70661690-bb22-47ef-a5ec-239bd3f1f16e','P001067','Ngockhai9ii','ngockhai.jpg','Đồng hồ thông minh',99900000,'Apple','Watch Series 6',100,'2024-09-05 20:56:40','2024-09-05 20:56:40'),('7212f31b-6f53-498e-9bd3-9b6ae1ca052b','P00106','Ngockhai9ii',NULL,'Đồng hồ thông minh',99900000,'Apple','Watch Series 6',100,'2024-09-05 20:50:45','2024-09-05 20:50:45'),('ee597c18-cf2e-40c2-a5b3-53555bc288ae','P001066777','Ngockhai9ii','ngockhai.jpg','Đồng hồ thông minh',99900000,'Apple','Watch Series 6',100,'2024-09-05 21:13:53','2024-09-05 21:13:53');
/*!40000 ALTER TABLE `phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone_category`
--

DROP TABLE IF EXISTS `phone_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `phone_category` (
  `phone_id` char(36) NOT NULL,
  `category_id` char(36) NOT NULL,
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
INSERT INTO `phone_category` VALUES ('6ba7b814-9dad-11d1-80b4-00c04fd430c8','550e8400-e29b-41d4-a716-446655440000'),('1ac1d860-9b66-4a15-a4fa-907c1f81fd00','6ba7b812-9dad-11d1-80b4-00c04fd430c8'),('ee597c18-cf2e-40c2-a5b3-53555bc288ae','6ba7b812-9dad-11d1-80b4-00c04fd430c8'),('6ba7b814-9dad-11d1-80b4-00c04fd430c8','f47ac10b-58cc-4372-a567-0e02b2c3d479'),('6ba7b815-9dad-11d1-80b4-00c04fd430c8','f47ac10b-58cc-4372-a567-0e02b2c3d479');
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

-- Dump completed on 2024-09-06  2:40:47
