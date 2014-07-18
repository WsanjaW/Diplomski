CREATE DATABASE  IF NOT EXISTS `njt_baza` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `njt_baza`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: 127.1.1.0    Database: njt_baza
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `korisnik` (
  `idKorisnik` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) DEFAULT NULL,
  `prezime` varchar(45) DEFAULT NULL,
  `jmbg` varchar(13) DEFAULT NULL,
  `nazivBicikla` varchar(45) DEFAULT NULL,
  `tipBicikla` set('MTB','Road','Hibrid') DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `aktivan` bit(1) DEFAULT b'0',
  `aktivacionikod` varchar(9) DEFAULT NULL,
  `mestoID` int(11) DEFAULT NULL,
  PRIMARY KEY (`idKorisnik`),
  KEY `fk_mesto_idx` (`mestoID`),
  CONSTRAINT `fk_mesto` FOREIGN KEY (`mestoID`) REFERENCES `mesto` (`idMesto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Sanja','Katanic','1234567890','Stiv','MTB','sanjak_5@hotmail.com','sanja','sanja','',NULL,1),(2,'Mirko','Katanic','3435346565','sss','MTB','sanjak_5@hotmail.com','mirko','mirko','\0',NULL,1),(3,'Vesna','Katanic','1234567890','aaa','MTB','sanjak_5@hotmail.com','veca','veca','\0',NULL,1),(4,'a','a','a','a','Hibrid','sanjak_5@hotmail.com','a','a','\0',NULL,2),(5,'b','b','b','b','Hibrid','sanjak_5@hotmail.com','b','b','\0',NULL,2),(6,'c','c','c','c','Road','sanjak_5@hotmail.com','c','c','\0',NULL,1),(7,'d','d','d','d','Hibrid','sanjak_5@hotmail.com','d','d','\0','UDjedJrmE',2),(8,'f','f','fds','f','MTB','sanjak_5@hotmail.com','f','f','','TOd7crHO6',1),(9,'g','g','g','g','MTB','katanic3410i@fon.bg.ac.rs','g','g','\0','LFi4tvouL',1),(10,'h','h','h','h','MTB','h','h','h','\0','Ky2XUwDqF',1);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnikputovanje`
--

DROP TABLE IF EXISTS `korisnikputovanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `korisnikputovanje` (
  `idKorisnik` int(11) NOT NULL,
  `idPutovanje` int(11) NOT NULL,
  PRIMARY KEY (`idKorisnik`,`idPutovanje`),
  KEY `fk_Korisnik_has_Putovanje_Putovanje1_idx` (`idPutovanje`),
  KEY `fk_Korisnik_has_Putovanje_Korisnik1_idx` (`idKorisnik`),
  CONSTRAINT `fk_Korisnik_has_Putovanje_Korisnik1` FOREIGN KEY (`idKorisnik`) REFERENCES `korisnik` (`idKorisnik`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Korisnik_has_Putovanje_Putovanje1` FOREIGN KEY (`idPutovanje`) REFERENCES `putovanje` (`idPutovanje`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnikputovanje`
--

LOCK TABLES `korisnikputovanje` WRITE;
/*!40000 ALTER TABLE `korisnikputovanje` DISABLE KEYS */;
/*!40000 ALTER TABLE `korisnikputovanje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mesto` (
  `idMesto` int(11) NOT NULL AUTO_INCREMENT,
  `pttBroj` varchar(45) DEFAULT NULL,
  `naziv` varchar(45) NOT NULL,
  `lon` decimal(2,0) DEFAULT NULL,
  `lat` decimal(2,0) DEFAULT NULL,
  PRIMARY KEY (`idMesto`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesto`
--

LOCK TABLES `mesto` WRITE;
/*!40000 ALTER TABLE `mesto` DISABLE KEYS */;
INSERT INTO `mesto` VALUES (1,'11000','Beograd',NULL,NULL),(2,'21000','Novi Sad',NULL,NULL);
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mestoputovanje`
--

DROP TABLE IF EXISTS `mestoputovanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mestoputovanje` (
  `idMesto` int(11) NOT NULL,
  `idPutovanje` int(11) NOT NULL,
  PRIMARY KEY (`idMesto`,`idPutovanje`),
  KEY `fk_Mesto_has_Putovanje_Putovanje1_idx` (`idPutovanje`),
  KEY `fk_Mesto_has_Putovanje_Mesto1_idx` (`idMesto`),
  CONSTRAINT `fk_Mesto_has_Putovanje_Mesto1` FOREIGN KEY (`idMesto`) REFERENCES `mesto` (`idMesto`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Mesto_has_Putovanje_Putovanje1` FOREIGN KEY (`idPutovanje`) REFERENCES `putovanje` (`idPutovanje`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mestoputovanje`
--

LOCK TABLES `mestoputovanje` WRITE;
/*!40000 ALTER TABLE `mestoputovanje` DISABLE KEYS */;
/*!40000 ALTER TABLE `mestoputovanje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `putovanje`
--

DROP TABLE IF EXISTS `putovanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `putovanje` (
  `idPutovanje` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(245) NOT NULL,
  `datumOd` date DEFAULT NULL,
  `datumDo` date DEFAULT NULL,
  `biciklistaId` int(11) NOT NULL,
  PRIMARY KEY (`idPutovanje`),
  KEY `fk_biciklista_idx` (`biciklistaId`),
  CONSTRAINT `fk_biciklista` FOREIGN KEY (`biciklistaId`) REFERENCES `korisnik` (`idKorisnik`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `putovanje`
--

LOCK TABLES `putovanje` WRITE;
/*!40000 ALTER TABLE `putovanje` DISABLE KEYS */;
INSERT INTO `putovanje` VALUES (1,'putovanje1','0014-07-17','0014-07-18',1),(2,'putovanje9','0014-07-24','0014-07-31',1),(3,'fp','0014-07-17','0014-07-17',1),(4,'kp','0014-07-17','0014-07-17',8),(5,'putovanje2','0014-07-18','0014-07-18',1);
/*!40000 ALTER TABLE `putovanje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trek`
--

DROP TABLE IF EXISTS `trek`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trek` (
  `idPutovanje` int(11) NOT NULL,
  `idTrek` int(11) NOT NULL AUTO_INCREMENT,
  `naziv` varchar(245) DEFAULT NULL,
  `kilometraza` double DEFAULT '0',
  `vreme` double DEFAULT '0',
  `prosecnaBrzina` double DEFAULT '0',
  `ukupanUspon` double DEFAULT '0',
  PRIMARY KEY (`idTrek`,`idPutovanje`),
  KEY `fk_putovanje_idx` (`idPutovanje`),
  CONSTRAINT `fk_putovanje` FOREIGN KEY (`idPutovanje`) REFERENCES `putovanje` (`idPutovanje`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trek`
--

LOCK TABLES `trek` WRITE;
/*!40000 ALTER TABLE `trek` DISABLE KEYS */;
INSERT INTO `trek` VALUES (1,1,'t1',NULL,NULL,NULL,NULL),(2,1,'t71',NULL,NULL,NULL,NULL),(3,1,'rr',NULL,NULL,NULL,NULL),(4,1,'pp',NULL,NULL,NULL,NULL),(5,1,'p1t1',NULL,NULL,NULL,NULL),(1,2,'t2',NULL,NULL,NULL,NULL),(5,2,'p1t2',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `trek` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wp`
--

DROP TABLE IF EXISTS `wp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wp` (
  `idWP` int(11) NOT NULL AUTO_INCREMENT,
  `idTrek` int(11) NOT NULL,
  `idPutovanje` int(11) NOT NULL,
  `lon` double DEFAULT NULL,
  `lat` double DEFAULT NULL,
  `elev` double DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`idWP`,`idTrek`,`idPutovanje`),
  KEY `fk_put_idx` (`idPutovanje`,`idTrek`),
  CONSTRAINT `fk_put` FOREIGN KEY (`idPutovanje`, `idTrek`) REFERENCES `trek` (`idPutovanje`, `idTrek`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wp`
--

LOCK TABLES `wp` WRITE;
/*!40000 ALTER TABLE `wp` DISABLE KEYS */;
/*!40000 ALTER TABLE `wp` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-18 23:23:20
