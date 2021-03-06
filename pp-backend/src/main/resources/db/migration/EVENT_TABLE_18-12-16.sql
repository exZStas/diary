CREATE TABLE IF NOT EXISTS `EVENT` (
   `ID` int(11) NOT NULL AUTO_INCREMENT,
   `USER_ID` int(11) NOT NULL,
   `NAME` varchar(45) NOT NULL,
   `DESCRIPTION` varchar(200) DEFAULT NULL,
   `CATEGORY` varchar(45) NOT NULL,
   `COMPLEXITY` bit(1) NOT NULL,
   `START_TIME` timestamp(6) NOT NULL,
   `END_TIME` timestamp(6) NOT NULL,
   `DURATION` int(11) NOT NULL,
   `STICKER` varchar(45) DEFAULT NULL,
   `DONE_STATUS` varchar(9) NOT NULL DEFAULT 'active',
   PRIMARY KEY (`ID`,`USER_ID`),
   KEY `USER_ID_idx` (`USER_ID`),
   CONSTRAINT `USER_ID` FOREIGN KEY (`USER_ID`) REFERENCES `USER` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
 )