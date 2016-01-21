DROP DATABASE IF EXISTS FA;

CREATE DATABASE IF NOT EXISTS FA CHARACTER SET utf8 DEFAULT COLLATE utf8_bin;

CREATE TABLE `FA`.`User` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(40) NOT NULL UNIQUE,
  `password` VARCHAR(45) NOT NULL,
  `createTimestamp` DATETIME NOT NULL,
  PRIMARY KEY (`idUser`));
  
CREATE TABLE `FA`.`AccountType` (
  `idAccountType` INT NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(40) NOT NULL UNIQUE,
  `createTimestamp` DATETIME NOT NULL,
  PRIMARY KEY (`idAccountType`));
  
CREATE TABLE `FA`.`Account` (
  `idAccount` INT NOT NULL AUTO_INCREMENT,
  `accountType` INT NOT NULL,
  `owner` INT NOT NULL,
  `accountNumber` VARCHAR(10) NOT NULL UNIQUE,
  `lowerLimit` DOUBLE NOT NULL,
  `balance` DOUBLE NOT NULL,
  `createUser` INT NOT NULL,
  `createTimestamp` DATETIME NOT NULL,
  `updateUser` INT NOT NULL,
  `updateTimestamp` DATETIME NOT NULL,
  PRIMARY KEY (`idAccount`),
  UNIQUE KEY `uk_accountType_owner` (`accountType`, `owner`),
  CONSTRAINT `fk_account_accountType`
    FOREIGN KEY (`accountType`)
    REFERENCES `FA`.`Account` (`idAccount`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_account_owner`
    FOREIGN KEY (`owner`)
    REFERENCES `FA`.`User` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE TABLE `FA`.`Operation` (
  `idOperation` INT NOT NULL AUTO_INCREMENT,
  `fromAccount` INT NOT NULL,
  `toAccount` INT NOT NULL,
  `amount` DOUBLE NOT NULL,
  `createUser` INT NOT NULL,
  `createTimestamp` DATETIME NOT NULL,
  `updateUser` INT NOT NULL,
  `updateTimestamp` DATETIME NOT NULL,
  PRIMARY KEY (`idOperation`),
  CONSTRAINT `fk_operation_fromAccount`
    FOREIGN KEY (`fromAccount`)
    REFERENCES `FA`.`Account` (`idAccount`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_operation_toAccount`
  	FOREIGN KEY (`toAccount`)
    REFERENCES `FA`.`Account` (`idAccount`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE USER 'faUser'@'localhost' IDENTIFIED BY 'faPassword';
GRANT USAGE ON *.* TO 'faUser'@'localhost' IDENTIFIED BY 'faPassword';

GRANT ALL PRIVILEGES ON FA.* TO 'faUser'@'localhost';
FLUSH PRIVILEGES;

INSERT INTO `FA`.`User` (`username`, `password`, `createTimestamp`) VALUES ('admin', 'secret', NOW());
INSERT INTO `FA`.`User` (`username`, `password`, `createTimestamp`) VALUES ('user', 'password', NOW());
INSERT INTO `FA`.`User` (`username`, `password`, `createTimestamp`) VALUES ('guest', 'guest', NOW());

INSERT INTO `FA`.`AccountType` (`label`, `createTimestamp`) VALUES ('Account', NOW());
INSERT INTO `FA`.`AccountType` (`label`, `createTimestamp`) VALUES ('Bankbook', NOW());

INSERT INTO `FA`.`Account` (`accountType`, `owner`, `accountNumber`, `lowerLimit`, `balance`, `createUser`, `createTimestamp`, `updateUser`, `updateTimestamp`) VALUES (1, 1, 'A441262943', -10000.0, 0.0, 1, NOW(), 1, NOW());
INSERT INTO `FA`.`Account` (`accountType`, `owner`, `accountNumber`, `lowerLimit`, `balance`, `createUser`, `createTimestamp`, `updateUser`, `updateTimestamp`) VALUES (1, 2, 'A872540888', -1000.0, 0.0, 2, NOW(), 2, NOW());
