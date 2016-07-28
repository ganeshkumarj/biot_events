-- MySQL dump 10.13  Distrib 5.6.21, for Win32 (x86)
--
-- Host: localhost    Database: beacon
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
-- Table structure for table `bi_hs_message_user`
--

DROP TABLE IF EXISTS `bi_hs_message_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_hs_message_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL,
  `user_code` varchar(15) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mu_hs_u_id` (`user_code`),
  KEY `fk_mu_hs_m_id` (`message_id`),
  CONSTRAINT `fk_mu_hs_m_id` FOREIGN KEY (`message_id`) REFERENCES `bi_tr_message` (`id`),
  CONSTRAINT `fk_mu_hs_u_id` FOREIGN KEY (`user_code`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_hs_message_user`
--

LOCK TABLES `bi_hs_message_user` WRITE;
/*!40000 ALTER TABLE `bi_hs_message_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_hs_message_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_hs_reward_user`
--

DROP TABLE IF EXISTS `bi_hs_reward_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_hs_reward_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reward_enc` varchar(500) DEFAULT NULL,
  `user_code` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mu_hs_u_id` (`user_code`),
  CONSTRAINT `fk_re_hs_u_id` FOREIGN KEY (`user_code`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_hs_reward_user`
--

LOCK TABLES `bi_hs_reward_user` WRITE;
/*!40000 ALTER TABLE `bi_hs_reward_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_hs_reward_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_lk_event_profile`
--

DROP TABLE IF EXISTS `bi_lk_event_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_lk_event_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) DEFAULT NULL,
  `profile_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ep_event_id` (`event_id`),
  KEY `fk_ep_p_id` (`profile_id`),
  CONSTRAINT `fk_ep_eve_id` FOREIGN KEY (`event_id`) REFERENCES `bi_tr_event` (`id`),
  CONSTRAINT `fk_ep_p_id` FOREIGN KEY (`profile_id`) REFERENCES `bi_ma_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_lk_event_profile`
--

LOCK TABLES `bi_lk_event_profile` WRITE;
/*!40000 ALTER TABLE `bi_lk_event_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_lk_event_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_lk_event_user`
--

DROP TABLE IF EXISTS `bi_lk_event_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_lk_event_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) DEFAULT NULL,
  `user_code` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_eu_eve_id` (`event_id`),
  KEY `fk_eu_u_id` (`user_code`),
  CONSTRAINT `fk_eu_eve_id` FOREIGN KEY (`event_id`) REFERENCES `bi_tr_event` (`id`),
  CONSTRAINT `fk_eu_u_id` FOREIGN KEY (`user_code`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_lk_event_user`
--

LOCK TABLES `bi_lk_event_user` WRITE;
/*!40000 ALTER TABLE `bi_lk_event_user` DISABLE KEYS */;
INSERT INTO `bi_lk_event_user` VALUES (1,1,'GBS03578'),(2,1,'GBS02315'),(3,2,'GBS03578'),(4,3,'GBS03578'),(6,2,'GBS02315'),(7,1,'GBS02286'),(8,4,'GBS02315'),(9,4,'GBS03578');
/*!40000 ALTER TABLE `bi_lk_event_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_lk_message_profile`
--

DROP TABLE IF EXISTS `bi_lk_message_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_lk_message_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL,
  `profile_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mp_msg_id` (`message_id`),
  KEY `fk_mp_p_id` (`profile_id`),
  CONSTRAINT `fk_mp_msg_id` FOREIGN KEY (`message_id`) REFERENCES `bi_tr_message` (`id`),
  CONSTRAINT `fk_mp_p_id` FOREIGN KEY (`profile_id`) REFERENCES `bi_ma_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_lk_message_profile`
--

LOCK TABLES `bi_lk_message_profile` WRITE;
/*!40000 ALTER TABLE `bi_lk_message_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_lk_message_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_lk_message_user`
--

DROP TABLE IF EXISTS `bi_lk_message_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_lk_message_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL,
  `user_code` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mu_msg_id` (`message_id`),
  KEY `fk_mu_u_id` (`user_code`),
  CONSTRAINT `fk_mu_msg_id` FOREIGN KEY (`message_id`) REFERENCES `bi_tr_message` (`id`),
  CONSTRAINT `fk_mu_u_id` FOREIGN KEY (`user_code`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_lk_message_user`
--

LOCK TABLES `bi_lk_message_user` WRITE;
/*!40000 ALTER TABLE `bi_lk_message_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_lk_message_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_lk_profile_device`
--

DROP TABLE IF EXISTS `bi_lk_profile_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_lk_profile_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `profile_id` int(11) DEFAULT NULL,
  `device_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pd_p_id` (`profile_id`),
  KEY `fk_pd_d_id` (`device_id`),
  CONSTRAINT `fk_pd_d_id` FOREIGN KEY (`device_id`) REFERENCES `bi_ma_device` (`id`),
  CONSTRAINT `fk_pd_p_id` FOREIGN KEY (`profile_id`) REFERENCES `bi_ma_profile` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_lk_profile_device`
--

LOCK TABLES `bi_lk_profile_device` WRITE;
/*!40000 ALTER TABLE `bi_lk_profile_device` DISABLE KEYS */;
INSERT INTO `bi_lk_profile_device` VALUES (1,1,9262),(2,2,9262),(3,3,9262),(4,4,9262),(5,5,9262),(6,1,9263),(7,2,9263),(8,3,9263),(9,4,9263),(10,5,9263),(11,1,9264),(12,2,9264),(13,3,9264),(14,4,9264),(15,5,9264),(16,1,9265),(17,2,9265),(18,3,9265),(19,4,9265),(20,5,9265);
/*!40000 ALTER TABLE `bi_lk_profile_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_lk_user_profile`
--

DROP TABLE IF EXISTS `bi_lk_user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_lk_user_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(15) DEFAULT NULL,
  `profile_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_up_u_id` (`user_code`),
  KEY `fk_up_p_id` (`profile_id`),
  CONSTRAINT `fk_up_p_id` FOREIGN KEY (`profile_id`) REFERENCES `bi_ma_profile` (`id`),
  CONSTRAINT `fk_up_u_id` FOREIGN KEY (`user_code`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_lk_user_profile`
--

LOCK TABLES `bi_lk_user_profile` WRITE;
/*!40000 ALTER TABLE `bi_lk_user_profile` DISABLE KEYS */;
INSERT INTO `bi_lk_user_profile` VALUES (1,'GBS02286',1),(2,'GBS03578',1),(3,'GBS02412',2),(4,'GBS03922',2),(5,'GBS03682',3),(6,'GBS02276',3),(7,'GBS02315',4),(8,'GBS02404',5),(9,'GBS03681',5);
/*!40000 ALTER TABLE `bi_lk_user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_ma_device`
--

DROP TABLE IF EXISTS `bi_ma_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_ma_device` (
  `id` int(11) NOT NULL,
  `locationmap` varchar(100) DEFAULT NULL,
  `location` varchar(100) DEFAULT NULL,
  `owner` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_owner_device` (`owner`),
  CONSTRAINT `fk_owner_device` FOREIGN KEY (`owner`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_ma_device`
--

LOCK TABLES `bi_ma_device` WRITE;
/*!40000 ALTER TABLE `bi_ma_device` DISABLE KEYS */;
INSERT INTO `bi_ma_device` VALUES (9262,'location/9262.png','Sella Entrance','GBS02412'),(9263,'location/9263.png','Sella Wealth Management','GBS02286'),(9264,'location/9264.png','Sella Architecture','GBS02276'),(9265,'location/9265.png','Sella Old Branch','GBS03681'),(22845,'location/22845.png','Sella Branch','GBS02315'),(23636,'location/23636.png','Sella Ground Floor Entrance','GBS02412');
/*!40000 ALTER TABLE `bi_ma_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_ma_profile`
--

DROP TABLE IF EXISTS `bi_ma_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_ma_profile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `owner` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_owner_profile` (`owner`),
  CONSTRAINT `fk_owner_profile` FOREIGN KEY (`owner`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_ma_profile`
--

LOCK TABLES `bi_ma_profile` WRITE;
/*!40000 ALTER TABLE `bi_ma_profile` DISABLE KEYS */;
INSERT INTO `bi_ma_profile` VALUES (1,'Racolta','GBS02286'),(2,'HR','GBS02412'),(3,'Architecture','GBS02276'),(4,'Guest','GBS02315'),(5,'Sella Branch','GBS03681');
/*!40000 ALTER TABLE `bi_ma_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_ma_user`
--

DROP TABLE IF EXISTS `bi_ma_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_ma_user` (
  `code` varchar(15) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `image_url` varchar(300) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `password` varchar(20) NOT NULL,
  `manager` varchar(15) DEFAULT NULL,
  `internal` varchar(1) DEFAULT 'N',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_ma_user`
--

LOCK TABLES `bi_ma_user` WRITE;
/*!40000 ALTER TABLE `bi_ma_user` DISABLE KEYS */;
INSERT INTO `bi_ma_user` VALUES ('ADMIN','Gamification','users/','2015-09-04','adminbiotiot',NULL,'Y'),('GAMIFICATION','Gamification','users/','2015-09-04','gamification',NULL,'Y'),('GBS02276','Murali','users/','1981-07-21','gbs02276',NULL,'N'),('GBS02286','Premkumar','users/','1981-07-21','gbs02286',NULL,'N'),('GBS02315','Balakrishnan','users/','1981-07-21','gbs02315',NULL,'N'),('GBS02404','Giada Bono','users/','1981-07-21','gbs02404',NULL,'N'),('GBS02412','Chris','users/','1981-07-21','gbs02412',NULL,'N'),('GBS03578','Ganeshkumar','users/','1981-07-21','gbs03578',NULL,'N'),('GBS03681','Padma','users/','1981-07-21','gbs03681',NULL,'N'),('GBS03682','Godwin','users/','1981-07-21','gbs03682',NULL,'N'),('GBS03922','Srinivasagopal','users/','1981-07-21','gbs03922',NULL,'N');
/*!40000 ALTER TABLE `bi_ma_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_ma_user_location_challenge`
--

DROP TABLE IF EXISTS `bi_ma_user_location_challenge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_ma_user_location_challenge` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL,
  `challenge_code` varchar(50) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_device_id_challenge` (`device_id`),
  CONSTRAINT `fk_device_id_challenge` FOREIGN KEY (`device_id`) REFERENCES `bi_ma_device` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_ma_user_location_challenge`
--

LOCK TABLES `bi_ma_user_location_challenge` WRITE;
/*!40000 ALTER TABLE `bi_ma_user_location_challenge` DISABLE KEYS */;
INSERT INTO `bi_ma_user_location_challenge` VALUES (1,9265,'IOT_OFF_PEAK_CHAL','1970-01-01 18:00:00','1970-01-01 19:00:00'),(2,9262,'IOT_NEW_BIE_CHAL',NULL,NULL),(3,9263,'IOT_NEW_BIE_CHAL',NULL,NULL),(4,9264,'IOT_NEW_BIE_CHAL',NULL,NULL),(5,9265,'IOT_NEW_BIE_CHAL',NULL,NULL),(6,9263,'IOT_BUSINESS_VISIT_CHAL',NULL,NULL),(7,9265,'IOT_BRANCH_VISIT_CHAL',NULL,NULL),(8,9262,'IOT_EXPERIENCE_FEEDBACK_CHAL',NULL,NULL),(9,9263,'IOT_EXPERIENCE_FEEDBACK_CHAL',NULL,NULL),(10,9264,'IOT_EXPERIENCE_FEEDBACK_CHAL',NULL,NULL),(11,9265,'IOT_EXPERIENCE_FEEDBACK_CHAL',NULL,NULL),(12,9265,'IOT_DETAIL_FEEDBACK_CHAL',NULL,NULL);
/*!40000 ALTER TABLE `bi_ma_user_location_challenge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_tr_daily_log`
--

DROP TABLE IF EXISTS `bi_tr_daily_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_tr_daily_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(15) NOT NULL,
  `first_in` datetime NOT NULL,
  `last_out` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_daily_log_user` (`user_code`),
  CONSTRAINT `fk_daily_log_user` FOREIGN KEY (`user_code`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_tr_daily_log`
--

LOCK TABLES `bi_tr_daily_log` WRITE;
/*!40000 ALTER TABLE `bi_tr_daily_log` DISABLE KEYS */;
INSERT INTO `bi_tr_daily_log` VALUES (1,'GBS02315','2016-02-24 11:34:36','2016-02-24 11:39:27'),(2,'GBS03578','2016-02-24 11:39:44','2016-02-24 12:24:09'),(3,'GBS02286','2016-02-24 12:24:35','2016-02-24 16:37:26');
/*!40000 ALTER TABLE `bi_tr_daily_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_tr_event`
--

DROP TABLE IF EXISTS `bi_tr_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_tr_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL,
  `created` varchar(15) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_eve_created` (`created`),
  CONSTRAINT `fk_eve_created` FOREIGN KEY (`created`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_tr_event`
--

LOCK TABLES `bi_tr_event` WRITE;
/*!40000 ALTER TABLE `bi_tr_event` DISABLE KEYS */;
INSERT INTO `bi_tr_event` VALUES (1,'Discussion about mifid','GBS02286','About mifid','TIM-BOB','2016-02-24 12:00:00','2016-02-24 15:00:00'),(2,'Discussion about assicurativo','GBS02286','About assicurativo','Kaplan-Norton','2016-02-24 16:00:00','2016-02-24 17:00:00'),(3,'Discussion about raccolta','GBS02286','About raccolta','Kaplan-Norton','2016-02-24 10:00:00','2016-02-24 12:00:00'),(4,'Discussion about completed','GBS02286','About raccolta','Kaplan-Norton','2016-02-24 10:00:00','2016-02-24 11:00:00');
/*!40000 ALTER TABLE `bi_tr_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_tr_marked_event`
--

DROP TABLE IF EXISTS `bi_tr_marked_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_tr_marked_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `event_id` int(11) DEFAULT NULL,
  `user_code` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_marked_eve_id` (`event_id`),
  KEY `fk_marked_u_id` (`user_code`),
  CONSTRAINT `fk_marked_eve_id` FOREIGN KEY (`event_id`) REFERENCES `bi_tr_event` (`id`),
  CONSTRAINT `fk_marked_u_id` FOREIGN KEY (`user_code`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_tr_marked_event`
--

LOCK TABLES `bi_tr_marked_event` WRITE;
/*!40000 ALTER TABLE `bi_tr_marked_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_tr_marked_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_tr_message`
--

DROP TABLE IF EXISTS `bi_tr_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_tr_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interval_desc` varchar(100) DEFAULT NULL,
  `created` varchar(15) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_msg_created` (`created`),
  CONSTRAINT `fk_msg_created` FOREIGN KEY (`created`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_tr_message`
--

LOCK TABLES `bi_tr_message` WRITE;
/*!40000 ALTER TABLE `bi_tr_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_tr_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_tr_message_content`
--

DROP TABLE IF EXISTS `bi_tr_message_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_tr_message_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) NOT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `image_url` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_msg_content_message_id` (`message_id`),
  CONSTRAINT `fk_msg_content_message_id` FOREIGN KEY (`message_id`) REFERENCES `bi_tr_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_tr_message_content`
--

LOCK TABLES `bi_tr_message_content` WRITE;
/*!40000 ALTER TABLE `bi_tr_message_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_tr_message_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_tr_user_current_location`
--

DROP TABLE IF EXISTS `bi_tr_user_current_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_tr_user_current_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(15) NOT NULL,
  `device_id` int(11) NOT NULL,
  `checkin` datetime DEFAULT NULL,
  `checkout` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_code_location` (`user_code`),
  KEY `fk_device_id_location` (`device_id`),
  CONSTRAINT `fk_device_id_location` FOREIGN KEY (`device_id`) REFERENCES `bi_ma_device` (`id`),
  CONSTRAINT `fk_user_code_location` FOREIGN KEY (`user_code`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_tr_user_current_location`
--

LOCK TABLES `bi_tr_user_current_location` WRITE;
/*!40000 ALTER TABLE `bi_tr_user_current_location` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_tr_user_current_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_tr_user_device`
--

DROP TABLE IF EXISTS `bi_tr_user_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_tr_user_device` (
  `user_code` varchar(15) NOT NULL,
  `user_device_id` varchar(50) NOT NULL,
  `active` tinyint(1) NOT NULL,
  KEY `fk_usr_device_code` (`user_code`),
  CONSTRAINT `fk_usr_device_code` FOREIGN KEY (`user_code`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_tr_user_device`
--

LOCK TABLES `bi_tr_user_device` WRITE;
/*!40000 ALTER TABLE `bi_tr_user_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_tr_user_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bi_tr_user_feedback`
--

DROP TABLE IF EXISTS `bi_tr_user_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bi_tr_user_feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(15) NOT NULL,
  `device_id` int(11) NOT NULL,
  `question` varchar(2000) NOT NULL,
  `answer` varchar(2000) NOT NULL,
  `insertion_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_feedback` (`user_code`),
  KEY `fk_device_code_feedback` (`device_id`),
  CONSTRAINT `fk_device_code_feedback` FOREIGN KEY (`device_id`) REFERENCES `bi_ma_device` (`id`),
  CONSTRAINT `fk_user_feedback` FOREIGN KEY (`user_code`) REFERENCES `bi_ma_user` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bi_tr_user_feedback`
--

LOCK TABLES `bi_tr_user_feedback` WRITE;
/*!40000 ALTER TABLE `bi_tr_user_feedback` DISABLE KEYS */;
/*!40000 ALTER TABLE `bi_tr_user_feedback` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-30 20:17:17
