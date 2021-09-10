-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema library_service
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema library_service
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `library_service` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `library_service` ;

-- -----------------------------------------------------
-- Table `library_service`.`authors`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_service`.`authors` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `lastname` VARCHAR(255) NOT NULL,
  `firstname` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 45
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `library_service`.`clients`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_service`.`clients` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(255) NOT NULL,
  `lastname` VARCHAR(255) NOT NULL,
  `birthday` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 50
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `library_service`.`publishing_houses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_service`.`publishing_houses` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `library_service`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_service`.`roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `library_service`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_service`.`users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `firstname` VARCHAR(255) NOT NULL,
  `lastname` VARCHAR(255) NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `users_roles_id_fk` (`role_id` ASC) VISIBLE,
  CONSTRAINT `users_roles_id_fk`
    FOREIGN KEY (`role_id`)
    REFERENCES `library_service`.`roles` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 37
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `library_service`.`books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_service`.`books` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `year` VARCHAR(255) NOT NULL,
  `id_client` BIGINT NOT NULL DEFAULT '0',
  `id_author` BIGINT NOT NULL,
  `id_user` BIGINT NOT NULL DEFAULT '0',
  `id_publishing_house` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `books_authors_id_fk` (`id_author` ASC) VISIBLE,
  INDEX `books_publishing_house_id_fk` (`id_publishing_house` ASC) VISIBLE,
  INDEX `books_clients_id_fk` (`id_client` ASC) VISIBLE,
  INDEX `books_users_id_fk` (`id_user` ASC) VISIBLE,
  CONSTRAINT `books_authors_id_fk`
    FOREIGN KEY (`id_author`)
    REFERENCES `library_service`.`authors` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `books_clients_id_fk`
    FOREIGN KEY (`id_client`)
    REFERENCES `library_service`.`clients` (`id`)
    ON UPDATE CASCADE,
  CONSTRAINT `books_publishing_house_id_fk`
    FOREIGN KEY (`id_publishing_house`)
    REFERENCES `library_service`.`publishing_houses` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `books_users_id_fk`
    FOREIGN KEY (`id_user`)
    REFERENCES `library_service`.`users` (`id`)
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `library_service`.`accounting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_service`.`accounting` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `accounting_date` VARCHAR(255) NOT NULL,
  `accounting_status` VARCHAR(255) NOT NULL,
  `book_id` BIGINT NOT NULL,
  `client_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `accounting_books_id_fk` (`book_id` ASC) VISIBLE,
  INDEX `accounting_users_id_fk` (`user_id` ASC) VISIBLE,
  INDEX `accounting_clients_id_fk` (`client_id` ASC) VISIBLE,
  CONSTRAINT `accounting_books_id_fk`
    FOREIGN KEY (`book_id`)
    REFERENCES `library_service`.`books` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `accounting_clients_id_fk`
    FOREIGN KEY (`client_id`)
    REFERENCES `library_service`.`clients` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `accounting_users_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `library_service`.`users` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 27
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `roles` VALUES (1,'NONE'),(2,'ADMIN');

INSERT IGNORE INTO `authors` VALUES (40,'Толстой','Лев'),(41,'Кэрролл','Льюис'),(42,'Каэлье','Пауло '),(43,'Кристи','Агата'),(44,'Керуак','Джек');

INSERT IGNORE INTO `clients` VALUES (1,'','',''),(44,'Сергей','Семенов','2021-08-10'),(45,'Иван','Петров','2021-08-12'),(46,'Иван','Иванов','2021-08-20'),(47,'Максим','Андреев','2021-08-05'),(48,'Иван','Иванов','2021-08-11'),(49,'Сергей ','Сапронов','2021-08-06');

INSERT IGNORE INTO `publishing_houses` VALUES (20,'Печатный дом','Пушкина 19'),(22,'Печатный дом','Пушкина 222'),(23,'Печатный дом','Пушкина 22'),(24,'Дом','Фрунзенская 34'),(25,'Издательство №30','Семенова 234'),(26,'Печаточка','Петропавловская 236');

INSERT IGNORE INTO `users` VALUES (1,'','','','',1),(35,'maksim@mail.ru','1234','Maksim','Chuyashkou',1),(36,'aleksandr@gmail.com','1234','Aleksandr','Kopikov',1);

INSERT IGNORE INTO `books` VALUES (19,'Анна Каренина','2021-08-11',1,40,1,20),(20,'Алиса в стране чудес','2021-08-06',1,41,1,22),(21,'Алхимик','2021-08-12',1,42,1,23),(22,'10 Негретят','2021-08-19',1,43,1,24),(23,'Война и мир','2021-08-04',1,40,1,22),(24,'В дороге','2021-08-13',1,44,1,25);

INSERT IGNORE INTO `accounting` VALUES (21,'2021-08-31 22:27:56','issued',19,44,35),(22,'2021-08-31 22:28:15','returned',19,44,35),(23,'2021-08-31 22:28:53','issued',19,48,35),(24,'2021-08-31 22:29:01','returned',19,48,35),(25,'2021-08-31 22:32:04','issued',19,49,36),(26,'2021-08-31 22:32:29','returned',19,49,35);
