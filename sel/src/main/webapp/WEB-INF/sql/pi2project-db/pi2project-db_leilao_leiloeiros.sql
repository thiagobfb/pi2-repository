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
-- Table structure for table `leilao_leiloeiros`
--

DROP TABLE IF EXISTS `leilao_leiloeiros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leilao_leiloeiros` (
  `leilao_id` bigint(20) NOT NULL,
  `participante_id` bigint(20) NOT NULL,
  KEY `FK_mdxm4rxd1x51f9o2wli870f1a` (`participante_id`),
  KEY `FK_eyrhekax3u4vhlkc48sft0ppb` (`leilao_id`),
  CONSTRAINT `FK_eyrhekax3u4vhlkc48sft0ppb` FOREIGN KEY (`leilao_id`) REFERENCES `tb_leilao` (`leilao_id`),
  CONSTRAINT `FK_mdxm4rxd1x51f9o2wli870f1a` FOREIGN KEY (`participante_id`) REFERENCES `tb_participante` (`participante_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leilao_leiloeiros`
--

LOCK TABLES `leilao_leiloeiros` WRITE;
/*!40000 ALTER TABLE `leilao_leiloeiros` DISABLE KEYS */;
/*!40000 ALTER TABLE `leilao_leiloeiros` ENABLE KEYS */;
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
