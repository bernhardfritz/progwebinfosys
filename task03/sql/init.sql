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

create user 'myBlogUser'@'localhost' identified by 'blog';
grant usage on *.* to 'myBlogUser'@'localhost' identified by 'blog';

grant all privileges on MyBlog.* to 'myBlogUser'@'localhost';
flush privileges;
