drop database if exists MyBlog;

create database if not exists MyBlog;

create table if not exists `MyBlog`.`Posting` (
  `id` int not null auto_increment,
  `author` varchar(40) not null,
  `title` varchar(75) not null,
  `text` text not null,
  `keywords` varchar(100) null,
  `created` datetime not null,
  `updated` datetime not null,
  primary key (`id`));

create table if not exists `MyBlog`.`User` (
  `id` int not null auto_increment,
  `username` varchar(50) not null,
  `password` varchar(100) not null,
  `readPost` bit not null,
  `writePost` bit not null,
  `deletePost` bit not null,
  `readComment` bit not null,
  `writeComment` bit not null,
  `deleteComment` bit not null,
  primary key (`id`));

create table if not exists `MyBlog`.`Comment` (
  `id` int not null auto_increment,
  `idPosting` int not null,
  `idUser` int not null,
  `text` varchar(120) not null,
  `created` datetime not null,
  primary key (`id`),
  constraint `fk_posting`
    foreign key (`idPosting`)
    references `MyBlog`.`Posting` (`id`),
  constraint `fk_user`
    foreign key (`idUser`)
    references `MyBlog`.`User` (`id`));

create user 'myBlogUser'@'localhost' identified by 'blog';
grant usage on *.* to 'myBlogUser'@'localhost' identified by 'blog';

grant all privileges on MyBlog.* to 'myBlogUser'@'localhost';
flush privileges;

insert into `MyBlog`.`User`(`username`, `password`, `readPost`, `writePost`, `deletePost`, `readComment`, `writeComment`, `deleteComment`) values('admin', 'secret', 1, 1, 1, 1, 1, 1);
insert into `MyBlog`.`User`(`username`, `password`, `readPost`, `writePost`, `deletePost`, `readComment`, `writeComment`, `deleteComment`) values('author', 'password', 1, 1, 0, 1, 1, 0);
insert into `MyBlog`.`User`(`username`, `password`, `readPost`, `writePost`, `deletePost`, `readComment`, `writeComment`, `deleteComment`) values('user', 'password', 1, 0, 0, 1, 1, 0);
insert into `MyBlog`.`User`(`username`, `password`, `readPost`, `writePost`, `deletePost`, `readComment`, `writeComment`, `deleteComment`) values('guest', 'password', 1, 0, 0, 1, 0, 0);
