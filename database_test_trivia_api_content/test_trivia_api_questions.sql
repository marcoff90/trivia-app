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
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `correct_answer` varchar(500) DEFAULT NULL,
  `difficulty` varchar(255) DEFAULT NULL,
  `question` varchar(500) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `correct_answer_id` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nv168ew28d2nc8efey0cs2613` (`question`),
  KEY `FKqb2mhy1x3tikx9013l5eb0wfr` (`category_id`),
  CONSTRAINT `FKqb2mhy1x3tikx9013l5eb0wfr` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16893 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'Microsoft','easy','Which company did Gabe Newell work at before founding Valve Corporation?','multiple',1,1),(2,'2004','medium','When did the website \"Facebook\" launch?','multiple',2,5),(3,'Jens','hard','Which of the following is NOT a god in Norse Mythology.','multiple',3,9),(4,'Red, black and yellow','medium','What colour hair does the main character of the Yu-Gi-Oh! original anime series have?','multiple',4,13),(5,'Lesotho','hard','Which country is completely landlocked by South Africa?','multiple',5,17),(6,'False','easy','The Sun rises from the North.','boolean',2,21),(7,'A mall with high vacancy rates or low consumer foot traffic','medium','What is a dead mall?','multiple',2,23),(8,'91.28 m/s^2','hard','In the original \"Super Mario Bros.\", what is the acceleration of Mario if he was in free fall?','multiple',1,27),(9,'Ad hominem','medium','Which logical fallacy means to attack the character of your opponent rather than their arguments?','multiple',2,31),(10,'Bobby Darin','medium','Which famous singer was portrayed by actor Kevin Spacey in the 2004 biographical film \"Beyond the Sea\"?','multiple',6,35),(11,'32','hard','How many sonatas did Ludwig van Beethoven write?','multiple',7,39),(12,'Black','medium','What is the highest belt you can get in Taekwondo?','multiple',8,43),(13,'Kirby\'s Adventure','easy','Which Kirby game first introduced Copy Abilities?','multiple',1,47),(14,'True','medium','Arriva is owned by the Deutsche Bahn.','boolean',9,51),(15,'Diamond/Pearl','easy','Which Pokemon generation did the fan-named \"Masuda Method\" first appear in? ','multiple',1,53),(16,'17','medium','In the game Pokemon Conquest, how many kingdoms make up the region of Ransei?','multiple',1,57),(17,'A\' + B\'','hard','According to DeMorgan\'s Theorem, the Boolean expression (AB)\' is equivalent to:','multiple',10,61),(18,'Caterpie','easy','What was Ash Ketchum\'s second Pokemon?','multiple',4,65),(19,'True','hard','Only one country in the world starts with the letter Q.','boolean',5,69),(22,'Another','medium','Which anime did Seiji Kishi NOT direct?','multiple',4,79),(24,'Naruto','easy','Who is the main character with yellow hair in the anime Naruto?','multiple',4,87),(25,'False','medium','In World War II, Hawker Typhoons served in the Pacific theater.','boolean',7,91),(26,'Romantic','hard','Pianist Frederic Chopin was a composer of which musical era?','multiple',7,93),(27,'Germany','medium','Which of the following countries was the first to send an object into space?','multiple',7,97),(28,'True','medium','You could walk from Norway to North Korea while only passing through Russia.','boolean',5,101),(29,'Bones','medium','Which animation studio animated the 2016 anime \"Mob Psycho 100\"?','multiple',4,103),(31,'Gdańsk','easy','What is the Polish city known to Germans as Danzig?','multiple',5,111),(32,'Guyana','hard','Which is not a country in Africa?','multiple',5,115),(34,'True','easy','The mitochondria is the powerhouse of the cell.','boolean',2,123),(35,'212°F','medium','At what temperature does water boil?','multiple',10,125),(39,'Silence','medium','The 1952 musical composition 4\'33\", composed by prolific American composer John Cage, is mainly comprised of what sound?','multiple',11,137),(41,'Korean','easy','The \"K\" in \"K-Pop\" stands for which word?','multiple',11,143),(44,'Lung Cancer','easy','In \"Breaking Bad\", Walter White is a high school teacher diagnosed with which form of cancer?','multiple',6,153),(47,'Johnny Cash','easy','Who had a 1969 top 5 hit with the song,  \'A Boy Named Sue\'?','multiple',11,165),(51,'Quahog','medium','Adam West was the mayor of which cartoon town?','multiple',6,181),(55,'Bombardier Billy Wells','hard','Which boxer was famous for striking the gong in the introduction to J. Arthur Rank films?','multiple',6,197),(56,'THat Part','medium','What song on ScHoolboy Q\'s album Black Face LP featured Kanye West?','multiple',11,201),(57,'Rhode Island ','medium','Which state of the United States is the smallest?','multiple',5,205),(58,'Electricity','easy','Automobiles produced by Telsa Motors operate on which form of energy?','multiple',9,209),(60,'Bryan Adams','medium','Who provided a majority of the songs and lyrics for \"Spirit: Stallion of the Cimarron\"?','multiple',6,217),(61,'False','easy','A universal set, or a set that contains all sets, exists.','boolean',10,221),(62,'False','medium','\"Rich Uncle Pennybags\" from the board game \"Monopoly\" wears a monocle.','boolean',12,223),(63,'False','easy','Tupac Shakur died due to complications from being stabbed in 1996.','boolean',3,225),(68,'William McKinley','medium','Which of the following Presidents of the United States was assassinated?','multiple',7,241),(69,'Kyoko Ito','easy','What is the name of Rivers Cuomo\'s wife?','multiple',11,245),(72,'Tsumo','hard','What do you declare in Rīchi Mahjong when you\'ve drawn your winning tile?','multiple',12,257),(73,'1,000,000','hard','How many zeptometres are inside one femtometre?','multiple',10,261),(74,'Theodore Harold Maiman','hard','Who built the first laser?','multiple',10,265),(91,'Lamborghini','medium','Which car manufacturer created the \"Aventador\"?','multiple',9,329),(101,'Kiernan Shipka','medium','Who out of these actresses is the youngest?','multiple',3,365),(105,'9','hard','How many rooms are there, not including the hallways and the set of stairs, in the board game \"Clue\"?','multiple',12,381),(112,'Warlock','hard','Which character class in Dungeons and Dragons 5th edition gains it\'s powers from making a pact with a being of higher power?','multiple',12,409),(118,'Harpies','easy','What mytological creatures have women\'s faces and vultures\' bodies?','multiple',3,433),(124,'Horse-Riding','easy','Which of the following sports is not part of the triathlon?','multiple',8,455),(127,'1','hard','In Canadian football, scoring a rouge is worth how many points?','multiple',8,467),(132,'Tarrasque','hard','What is the most challenging monster in the Dungeons & Dragons 5th Edition Monster Manual?','multiple',12,485),(142,'West Germany','easy','What team did England beat to win in the 1966 World Cup final?','multiple',8,523),(242,'April 23rd, 1564','medium','What is generally considered to be William Shakespeare\'s birth date?','multiple',3,877),(444,'Gimli Glider','medium','What nickname was given to Air Canada Flight 143 after it ran out of fuel and glided to safety in 1983?','multiple',9,1623),(483,'Boeing 747-8','medium','Which of the following passenger jets is the longest?','multiple',9,1763);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-11 16:49:39
