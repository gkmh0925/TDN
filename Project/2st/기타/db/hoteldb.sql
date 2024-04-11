-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.11.6-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- hotel 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `hotel` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `hotel`;

-- 테이블 hotel.admin 구조 내보내기
CREATE TABLE IF NOT EXISTS `admin` (
  `adminId` varchar(45) DEFAULT 'admin',
  `adminPw` varchar(45) DEFAULT '1234',
  `log` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 hotel.order 구조 내보내기
CREATE TABLE IF NOT EXISTS `order` (
  `OrderNum` int(11) NOT NULL AUTO_INCREMENT,
  `OrderDate` date DEFAULT NULL,
  `CancelDate` date DEFAULT NULL,
  `AddMemberCnt` int(11) DEFAULT NULL,
  `PetCnt` int(11) DEFAULT NULL,
  `Breakfast` tinyint(4) DEFAULT NULL,
  `CheckOut` date DEFAULT NULL,
  `CheckIn` date DEFAULT NULL,
  `ROOM_RoomNum` int(11) DEFAULT NULL,
  `USER_UserNum` int(11) DEFAULT NULL,
  PRIMARY KEY (`OrderNum`),
  KEY `ROOM_RoomNum` (`ROOM_RoomNum`),
  KEY `USER_UserNum` (`USER_UserNum`),
  CONSTRAINT `ROOM_RoomNum` FOREIGN KEY (`ROOM_RoomNum`) REFERENCES `room` (`RoomNum`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `USER_UserNum` FOREIGN KEY (`USER_UserNum`) REFERENCES `user` (`UserNum`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 hotel.room 구조 내보내기
CREATE TABLE IF NOT EXISTS `room` (
  `RoomNum` int(11) NOT NULL AUTO_INCREMENT,
  `Rating` varchar(50) DEFAULT NULL,
  `Bath` tinyint(4) DEFAULT NULL,
  `Terrace` tinyint(4) DEFAULT NULL,
  `View` varchar(15) DEFAULT NULL,
  `BasicPersonnel` int(11) DEFAULT NULL,
  `MaxPersonnel` int(11) DEFAULT NULL,
  `AddPrice` int(11) DEFAULT NULL,
  `Price` int(11) DEFAULT NULL,
  PRIMARY KEY (`RoomNum`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

-- 테이블 hotel.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `UserNum` int(11) NOT NULL AUTO_INCREMENT,
  `UserId` varchar(45) DEFAULT NULL,
  `UserPw` varchar(45) DEFAULT NULL,
  `UserName` varchar(30) DEFAULT NULL,
  `UserPhNum1` char(3) DEFAULT NULL,
  `UserPhNum2` char(4) DEFAULT NULL,
  `UserPhNum3` char(4) DEFAULT NULL,
  `UserEmail` varchar(45) DEFAULT NULL,
  `UserSsn1` char(6) DEFAULT NULL,
  `UserSsn2` char(7) DEFAULT NULL,
  PRIMARY KEY (`UserNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- 내보낼 데이터가 선택되어 있지 않습니다.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
