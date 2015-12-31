DROP DATABASE IF EXISTS WebShop;

CREATE DATABASE IF NOT EXISTS WebShop CHARACTER SET utf8 DEFAULT COLLATE utf8_bin;

CREATE TABLE `WebShop`.`User` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(40) NOT NULL UNIQUE,
  `password` VARCHAR(45) NOT NULL,
  `country` VARCHAR(50) NOT NULL,
  `state` VARCHAR(50) NOT NULL,
  `county` VARCHAR(50) NULL,
  `postcode` VARCHAR(10) NOT NULL,
  `city` VARCHAR(50) NOT NULL,
  `streetname` VARCHAR(50) NOT NULL,
  `housenumber` VARCHAR(10) NOT NULL,
  `latitude` DOUBLE NULL,
  `longitude` DOUBLE NULL,
  `categoryRead` BIT NOT NULL,
  `categoryWrite` BIT NOT NULL,
  `categoryDelete` BIT NOT NULL,
  `itemRead` BIT NOT NULL,
  `itemWrite` BIT NOT NULL,
  `itemDelete` BIT NOT NULL,
  `itemCommentRead` BIT NOT NULL,
  `itemCommentWrite` BIT NOT NULL,
  `itemCommentDelete` BIT NOT NULL,
  `userPromote` BIT NOT NULL,
  `userDemote` BIT NOT NULL,
  `userDelete` BIT NOT NULL,
  `createTimestamp` DATETIME NOT NULL,
  PRIMARY KEY (`idUser`));
  
CREATE TABLE `WebShop`.`Category` (
  `idCategory` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL UNIQUE,
  `description` VARCHAR(150) NULL,
  `createUser` INT NOT NULL,
  `createTimestamp` DATETIME NOT NULL,
  `updateUser` INT NOT NULL,
  `updateTimestamp` DATETIME NULL,
  PRIMARY KEY (`idCategory`),
  CONSTRAINT `fk_category_createUser`
    FOREIGN KEY (`createUser`)
    REFERENCES `WebShop`.`User` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_category_updateUser`
    FOREIGN KEY (`updateUser`)
    REFERENCES `WebShop`.`User` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


CREATE TABLE `WebShop`.`Item` (
  `idItem` INT NOT NULL AUTO_INCREMENT,
  `idCategory` INT NOT NULL,
  `title` VARCHAR(50) NOT NULL UNIQUE,
  `description` VARCHAR(150) NULL,
  `price` DOUBLE NOT NULL,
  `createUser` INT NOT NULL,
  `createTimestamp` DATETIME NOT NULL,
  `updateUser` INT NOT NULL,
  `updateTimestamp` DATETIME NULL,
  PRIMARY KEY (`idItem`),
  CONSTRAINT `fk_item_idCategory`
    FOREIGN KEY (`idCategory`)
    REFERENCES `WebShop`.`Category` (`idCategory`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_item_createUser`
    FOREIGN KEY (`createUser`)
    REFERENCES `WebShop`.`User` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_item_updateUser`
    FOREIGN KEY (`updateUser`)
    REFERENCES `WebShop`.`User` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
CREATE TABLE `WebShop`.`ItemComment` (
  `idItemComment` INT NOT NULL AUTO_INCREMENT,
  `idItem` INT NOT NULL,
  `text` VARCHAR(250) NOT NULL,
  `rating` INT NOT NULL,
  `createUser` INT NOT NULL,
  `createTimestamp` DATETIME NOT NULL,
  `updateUser` INT NOT NULL,
  `updateTimestamp` DATETIME NULL,
  PRIMARY KEY (`idItemComment`),
  CONSTRAINT `fk_itemComment_idItem`
    FOREIGN KEY (`idItem`)
    REFERENCES `WebShop`.`Item` (`idItem`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_itemComment_createUser`
    FOREIGN KEY (`createUser`)
    REFERENCES `WebShop`.`User` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_itemComment_updateUser`
    FOREIGN KEY (`updateUser`)
    REFERENCES `WebShop`.`User` (`idUser`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

CREATE USER 'webshopUser'@'localhost' IDENTIFIED BY 'webshop';
GRANT USAGE ON *.* TO 'webshopUser'@'localhost' IDENTIFIED BY 'webshop';

GRANT ALL PRIVILEGES ON WebShop.* TO 'webshopUser'@'localhost';
FLUSH PRIVILEGES;

INSERT INTO `WebShop`.`User` (`username`, `password`, `country`, `state`, `county`, `postcode`, `city`, `streetname`, `housenumber`, `latitude`, `longitude`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `userPromote`, `userDemote`, `userDelete`, `createTimestamp`) VALUES ('superadmin', 'supersecret', 'Österreich', 'Tirol', 'Innsbruck-Stadt', '6020', 'Innsbruck', 'Technikerstraße', '15', 47.26428145, 11.3442061494943, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, NOW());
INSERT INTO `WebShop`.`User` (`username`, `password`, `country`, `state`, `county`, `postcode`, `city`, `streetname`, `housenumber`, `latitude`, `longitude`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `userPromote`, `userDemote`, `userDelete`, `createTimestamp`) VALUES ('admin', 'secret', 'Österreich', 'Tirol', 'Innsbruck-Stadt', '6020', 'Innsbruck', 'Technikerstraße', '15', 47.26428145, 11.3442061494943, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, NOW());
INSERT INTO `WebShop`.`User` (`username`, `password`, `country`, `state`, `county`, `postcode`, `city`, `streetname`, `housenumber`, `latitude`, `longitude`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `userPromote`, `userDemote`, `userDelete`, `createTimestamp`) VALUES ('user', 'password', 'Österreich', 'Tirol', 'Innsbruck-Stadt', '6020', 'Innsbruck', 'Technikerstraße', '15', 47.26428145, 11.3442061494943, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, NOW());
INSERT INTO `WebShop`.`User` (`username`, `password`, `country`, `state`, `county`, `postcode`, `city`, `streetname`, `housenumber`, `latitude`, `longitude`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `userPromote`, `userDemote`, `userDelete`, `createTimestamp`) VALUES ('guest', 'password', 'Österreich', 'Tirol', 'Innsbruck-Stadt', '6020', 'Innsbruck', 'Technikerstraße', '15', 47.26428145, 11.3442061494943, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, NOW());
INSERT INTO `WebShop`.`User` (`username`, `password`, `country`, `state`, `county`, `postcode`, `city`, `streetname`, `housenumber`, `latitude`, `longitude`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `userPromote`, `userDemote`, `userDelete`, `createTimestamp`) VALUES ('bernhard', 'secret', 'Österreich', 'Tirol', 'Innsbruck-Stadt', '6020', 'Innsbruck', 'Technikerstraße', '15', 47.26428145, 11.3442061494943, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, NOW());
INSERT INTO `WebShop`.`User` (`username`, `password`, `country`, `state`, `county`, `postcode`, `city`, `streetname`, `housenumber`, `latitude`, `longitude`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `userPromote`, `userDemote`, `userDelete`, `createTimestamp`) VALUES ('mario', 'secret', 'Österreich', 'Tirol', 'Innsbruck-Stadt', '6020', 'Innsbruck', 'Technikerstraße', '15', 47.26428145, 11.3442061494943, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, NOW());
INSERT INTO `WebShop`.`User` (`username`, `password`, `country`, `state`, `county`, `postcode`, `city`, `streetname`, `housenumber`, `latitude`, `longitude`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `userPromote`, `userDemote`, `userDelete`, `createTimestamp`) VALUES ('john', 'password', 'Österreich', 'Tirol', 'Innsbruck-Stadt', '6020', 'Innsbruck', 'Technikerstraße', '15', 47.26428145, 11.3442061494943, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, NOW());
INSERT INTO `WebShop`.`User` (`username`, `password`, `country`, `state`, `county`, `postcode`, `city`, `streetname`, `housenumber`, `latitude`, `longitude`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `userPromote`, `userDemote`, `userDelete`, `createTimestamp`) VALUES ('fridolin', 'password', 'Österreich', 'Tirol', 'Innsbruck-Stadt', '6020', 'Innsbruck', 'Technikerstraße', '15', 47.26428145, 11.3442061494943, 1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, NOW());
