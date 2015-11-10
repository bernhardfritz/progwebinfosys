DROP DATABASE IF EXISTS WebShop;

CREATE DATABASE IF NOT EXISTS WebShop CHARACTER SET utf8;

CREATE TABLE `WebShop`.`User` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(40) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `categoryRead` BIT NOT NULL,
  `categoryWrite` BIT NOT NULL,
  `categoryDelete` BIT NOT NULL,
  `itemRead` BIT NOT NULL,
  `itemWrite` BIT NOT NULL,
  `itemDelete` BIT NOT NULL,
  `itemCommentRead` BIT NOT NULL,
  `itemCommentWrite` BIT NOT NULL,
  `itemCommentDelete` BIT NOT NULL,
  `createTimestamp` DATETIME NOT NULL,
  PRIMARY KEY (`idUser`));
  
CREATE TABLE `WebShop`.`Category` (
  `idCategory` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(60) NOT NULL,
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
  `title` VARCHAR(50) NOT NULL,
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

INSERT INTO `WebShop`.`User` (`username`, `password`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `createTimestamp`) VALUES ('admin', 'secret', 1, 1, 1, 1, 1, 1, 1, 1, 1, NOW());
INSERT INTO `WebShop`.`User` (`username`, `password`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `createTimestamp`) VALUES ('user', 'password', 1, 0, 0, 1, 0, 0, 1, 1, 0, NOW());
INSERT INTO `WebShop`.`User` (`username`, `password`, `categoryRead`, `categoryWrite`, `categoryDelete`, `itemRead`, `itemWrite`, `itemDelete`, `itemCommentRead`, `itemCommentWrite`, `itemCommentDelete`, `createTimestamp`) VALUES ('guest', 'password', 1, 0, 0, 1, 0, 0, 1, 0, 0, NOW());
