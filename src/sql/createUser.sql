CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `objid` binary(16) NOT NULL,
  `login` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `objid_UNIQUE` (`objid`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;