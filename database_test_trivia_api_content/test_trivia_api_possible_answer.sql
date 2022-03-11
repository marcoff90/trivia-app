-- MySQL dump 10.13  Distrib 8.0.28, for macos11 (x86_64)
--
-- Host: localhost    Database: test_trivia_api
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `possible_answer`
--

DROP TABLE IF EXISTS `possible_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `possible_answer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `answer` varchar(500) DEFAULT NULL,
  `question_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK79ihrslxbbveihwcw9bemvm0g` (`question_id`),
  CONSTRAINT `FK79ihrslxbbveihwcw9bemvm0g` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66310 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `possible_answer`
--

LOCK TABLES `possible_answer` WRITE;
/*!40000 ALTER TABLE `possible_answer` DISABLE KEYS */;
INSERT INTO `possible_answer` VALUES (1,'Microsoft',1),(2,'Apple',1),(3,'Google',1),(4,'Yahoo',1),(5,'2004',2),(6,'2005',2),(7,'2003',2),(8,'2006',2),(9,'Jens',3),(10,'Loki',3),(11,'Tyr',3),(12,'Snotra',3),(13,'Red, black and yellow',4),(14,'Red, yellow and green',4),(15,'Red, black and green',4),(16,'Red, purple and blue',4),(17,'Lesotho',5),(18,'Swaziland',5),(19,'Botswana',5),(20,'Zimbabwe',5),(21,'False',6),(22,'True',6),(23,'A mall with high vacancy rates or low consumer foot traffic',7),(24,'A mall with no stores',7),(25,'A mall that has been condemed',7),(26,'A mall after business hours',7),(27,'91.28 m/s^2',8),(28,'110  m/s^2',8),(29,'9.42  m/s^2',8),(30,'4.4  m/s^2',8),(31,'Ad hominem',9),(32,'Post hoc ergo propter hoc',9),(33,'Tu quoque',9),(34,'Argumentum ad populum',9),(35,'Bobby Darin',10),(36,'Louis Armstrong',10),(37,'Frank Sinatra',10),(38,'Dean Martin',10),(39,'32',11),(40,'50',11),(41,'31',11),(42,'21',11),(43,'Black',12),(44,'White',12),(45,'Red',12),(46,'Green',12),(47,'Kirby\'s Adventure',13),(48,'Kirby Super Star',13),(49,'Kirby\'s Dream Land 2',13),(50,'Kirby\'s Dream Land',13),(51,'True',14),(52,'False',14),(53,'Diamond/Pearl',15),(54,'Ruby/Sapphire',15),(55,'Black/White',15),(56,'X/Y',15),(57,'17',16),(58,'18',16),(59,'15',16),(60,'16',16),(61,'A\' + B\'',17),(62,'A\'B + B\'A',17),(63,'A\'B\'',17),(64,'AB\' + AB',17),(65,'Caterpie',18),(66,'Charmander',18),(67,'Pikachu',18),(68,'Pidgey',18),(69,'True',19),(70,'False',19),(79,'Another',22),(80,'Humanity Has Declined',22),(81,'Assassination Classroom',22),(82,'Danganronpa: The Animation',22),(87,'Naruto',24),(88,'Ten Ten',24),(89,'Sasuke',24),(90,'Kakashi',24),(91,'False',25),(92,'True',25),(93,'Romantic',26),(94,'Classic',26),(95,'Baroque',26),(96,'Renaissance',26),(97,'Germany',27),(98,'USA',27),(99,'Russia',27),(100,'China',27),(101,'True',28),(102,'False',28),(103,'Bones',29),(104,'A-1 Pictures',29),(105,'Shaft',29),(106,'Madhouse',29),(111,'Gdańsk',31),(112,'Warsaw',31),(113,'Zakopane',31),(114,'Poznań',31),(115,'Guyana',32),(116,'Senegal',32),(117,'Liberia',32),(118,'Somalia',32),(123,'True',34),(124,'False',34),(125,'212°F',35),(126,'200°F',35),(127,'181°F',35),(128,'178°F',35),(137,'Silence',39),(138,'Farts',39),(139,'People talking',39),(140,'Cricket chirps',39),(143,'Korean',41),(144,'Kenyan',41),(145,'Kazakhstan',41),(146,'Kuwaiti',41),(153,'Lung Cancer',44),(154,'Prostate Cancer',44),(155,'Brain Cancer',44),(156,'Testicular Cancer',44),(165,'Johnny Cash',47),(166,'Bob Dylan',47),(167,'Willie Nelson',47),(168,'Kris Kristofferson',47),(181,'Quahog',51),(182,'Springfield',51),(183,'South Park',51),(184,'Langley Falls',51),(197,'Bombardier Billy Wells',55),(198,'Freddie Mills',55),(199,'Terry Spinks',55),(200,'Don Cockell',55),(201,'THat Part',56),(202,'Neva CHange',56),(203,'Big Body',56),(204,'Blank Face',56),(205,'Rhode Island ',57),(206,'Maine',57),(207,'Vermont',57),(208,'Massachusetts',57),(209,'Electricity',58),(210,'Gasoline',58),(211,'Diesel',58),(212,'Nuclear',58),(217,'Bryan Adams',60),(218,'Smash Mouth',60),(219,'Oasis',60),(220,'Air Supply',60),(221,'False',61),(222,'True',61),(223,'False',62),(224,'True',62),(225,'False',63),(226,'True',63),(241,'William McKinley',68),(242,'Lyndon Johnson',68),(243,'Chester Arthur',68),(244,'Franklin Roosevelt',68),(245,'Kyoko Ito',69),(246,'Yoko Ono',69),(247,'Kyary Pamyu Pamyu',69),(248,'LiSA',69),(257,'Tsumo',72),(258,'Ron',72),(259,'Rīchi',72),(260,'Kan',72),(261,'1,000,000',73),(262,'10',73),(263,'1,000,000,000',73),(264,'1000',73),(265,'Theodore Harold Maiman',74),(266,'Nikola Tesla',74),(267,'Jack Kilby',74),(268,'Edith Clarke',74),(329,'Lamborghini',91),(330,'Ferrari',91),(331,'Pagani',91),(332,'Bugatti',91),(365,'Kiernan Shipka',101),(366,'Ariel Winter',101),(367,'Emma Watson',101),(368,'Bonnie Wright',101),(381,'9',105),(382,'1',105),(383,'6',105),(384,'10',105),(409,'Warlock',112),(410,'Wizard',112),(411,'Sorceror',112),(412,'Cleric',112),(433,'Harpies',118),(434,'Mermaids',118),(435,'Nymph',118),(436,'Lilith',118),(455,'Horse-Riding',124),(456,'Cycling',124),(457,'Swimming',124),(458,'Running',124),(467,'1',127),(468,'2',127),(469,'3',127),(470,'4',127),(485,'Tarrasque',132),(486,'Beholder',132),(487,'Displacer Beast',132),(488,'Lich',132),(523,'West Germany',142),(524,'Soviet Union',142),(525,'Portugal',142),(526,'Brazil',142),(877,'April 23rd, 1564',242),(878,'July 4th, 1409',242),(879,'September 29th, 1699',242),(880,'December 1st, 1750',242),(1623,'Gimli Glider',444),(1624,'Gimli Microlight',444),(1625,'Gimli Chaser',444),(1626,'Gimli Superb',444),(1763,'Boeing 747-8',483),(1764,'Airbus A350-1000',483),(1765,'Airbus A330-200',483),(1766,'Boeing 787-10',483);
/*!40000 ALTER TABLE `possible_answer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-11 16:49:38
