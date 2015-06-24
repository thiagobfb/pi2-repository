CREATE DATABASE  IF NOT EXISTS `pi2project-db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pi2project-db`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: pi2project-db
-- ------------------------------------------------------
-- Server version	5.6.21-log

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
-- Table structure for table `lote_has_itens`
--

DROP TABLE IF EXISTS `lote_has_itens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lote_has_itens` (
  `lote_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_4w3mbx3rfnrsuqguj9wqntj97` (`item_id`),
  KEY `FK_hq2jy490rqaqms55usgkcse2q` (`lote_id`),
  CONSTRAINT `FK_4w3mbx3rfnrsuqguj9wqntj97` FOREIGN KEY (`item_id`) REFERENCES `tb_item` (`item_id`),
  CONSTRAINT `FK_hq2jy490rqaqms55usgkcse2q` FOREIGN KEY (`lote_id`) REFERENCES `tb_lote` (`lote_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lote_has_itens`
--

LOCK TABLES `lote_has_itens` WRITE;
/*!40000 ALTER TABLE `lote_has_itens` DISABLE KEYS */;
INSERT INTO `lote_has_itens` VALUES (3,2),(3,3),(3,6),(4,1);
/*!40000 ALTER TABLE `lote_has_itens` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-03 18:00:16
