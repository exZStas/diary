CREATE TABLE `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` varchar(45) NOT NULL,
  `LAST_NAME` varchar(45) NOT NULL,
  `PASSWORD` varchar(45) NOT NULL,
  `GENDER` varchar(1) DEFAULT NULL,
  `STUDY_GROUP` varchar(10) NOT NULL,
  `BIRTHDAY` date DEFAULT NULL,
  `EMAIL` varchar(45) NOT NULL,
  `REGISTRATION_ID` varchar(45) NOT NULL,
  `REGISTRATION_APPROVED` bit(1) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `REGISTRATION_ID_UNIQUE` (`REGISTRATION_ID`);
)