CREATE DATABASE IF NOT EXISTS roledb;
USE roledb;

CREATE TABLE IF NOT EXISTS t_role (
  id int(11) NOT NULL AUTO_INCREMENT,
  objid binary(16) NOT NULL,
  name varchar(25) NOT NULL,
  description varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_objid_UNIQUE` (`objid`),
  UNIQUE KEY `role_name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE DATABASE IF NOT EXISTS userdb;
USE userdb;

CREATE TABLE IF NOT EXISTS t_user (
  id int(11) NOT NULL AUTO_INCREMENT,
  objid binary(16) NOT NULL,
  login varchar(10) NOT NULL,
  firstname varchar(20) NOT NULL,
  lastname varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_objid_UNIQUE` (`objid`),
  UNIQUE KEY `user_login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
