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
-- Table structure for table `tb_lote`
--

DROP TABLE IF EXISTS `tb_lote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_lote` (
  `lote_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lote_valor_total` double DEFAULT NULL,
  `lote_status` varchar(255) DEFAULT NULL,
  `lote_valor_lance` double DEFAULT NULL,
  `lote_valor` double DEFAULT NULL,
  `arrematante_fk` bigint(20) DEFAULT NULL,
  `comitente_fk` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`lote_id`),
  KEY `FK_2bmr2l6r49u7ayqb9bq32n8y` (`arrematante_fk`),
  KEY `FK_4n88hp775m0u1vteyvaqbnv71` (`comitente_fk`),
  CONSTRAINT `FK_2bmr2l6r49u7ayqb9bq32n8y` FOREIGN KEY (`arrematante_fk`) REFERENCES `tb_participante` (`participante_id`),
  CONSTRAINT `FK_4n88hp775m0u1vteyvaqbnv71` FOREIGN KEY (`comitente_fk`) REFERENCES `tb_participante` (`participante_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_lote`
--

LOCK TABLES `tb_lote` WRITE;
/*!40000 ALTER TABLE `tb_lote` DISABLE KEYS */;
INSERT INTO `tb_lote` VALUES (3,NULL,'NAO_LEILOADO',NULL,8454.2,NULL,4),(4,NULL,'NAO_LEILOADO',NULL,2225.5,NULL,1);
/*!40000 ALTER TABLE `tb_lote` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-06-03 18:00:15
