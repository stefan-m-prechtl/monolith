-- Workflow DB
CREATE DATABASE IF NOT EXISTS workflowdb;
USE workflowdb;

CREATE TABLE IF NOT EXISTS t_workflow (
  id int(11) NOT NULL AUTO_INCREMENT,
  objid binary(16) NOT NULL,
  name varchar(25) NOT NULL,
  description varchar(255) NULL,
  first_state_objid binary(16) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY wf_objid_UNIQUE (objid),
  UNIQUE KEY wf_name_UNIQUE (name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS t_state (
  id int(11) NOT NULL AUTO_INCREMENT,
  objid binary(16) NOT NULL,
  name varchar(25) NOT NULL,
  description varchar(255) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY state_objid_UNIQUE (objid),
  UNIQUE KEY state_name_UNIQUE (name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;  

CREATE TABLE IF NOT EXISTS t_transition (
  id int(11) NOT NULL AUTO_INCREMENT,
  objid binary(16) NOT NULL,
  workflow_objid binary(16) NOT NULL,
  from_state_id int(11) NOT NULL,
  to_state_id int(11) NOT NULL,
  description varchar(255) NULL,
  PRIMARY KEY (id),
  UNIQUE KEY workflow_from_to_UNIQUE (workflow_objid,from_state_id,to_state_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;  

-- Item DB
CREATE DATABASE IF NOT EXISTS itemdb;
USE itemdb;

CREATE TABLE IF NOT EXISTS t_priority (
  id int(11) NOT NULL AUTO_INCREMENT,
  objid binary(16) NOT NULL,
  name varchar(25) NOT NULL,
  value int(11) DEFAULT NULL,
  description varchar(50) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY priority_objid_UNIQUE (objid),
  UNIQUE KEY priority_caption_UNIQUE (name)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS t_item (
   id int(11) NOT NULL AUTO_INCREMENT,
  objid binary(16) NOT NULL,
  title varchar(25) NOT NULL,
  content varchar(255) DEFAULT NULL,
  project_objid binary(16) NOT NULL,
  creator_user_objid binary(16) NOT NULL,
  fk_priorityid int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY item_objid_UNIQUE (objid),
  UNIQUE KEY item_project_name_UNIQUE (project_objid,title),
  KEY fk_priority_idx (fk_priorityid),
  CONSTRAINT fk_priority FOREIGN KEY (fk_priorityid) REFERENCES t_priority (id)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Project DB
CREATE DATABASE IF NOT EXISTS projectdb;
USE projectdb;

CREATE TABLE IF NOT EXISTS t_project (
  id int(11) NOT NULL AUTO_INCREMENT,
  objid binary(16) NOT NULL,
  name varchar(25) NOT NULL,
  description varchar(50) NOT NULL,
  owner_user_objid binary(16) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY project_objid_UNIQUE (objid),
  UNIQUE KEY project_name_UNIQUE (name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Rollen DB
CREATE DATABASE IF NOT EXISTS roledb;
USE roledb;

CREATE TABLE IF NOT EXISTS t_role (
  id int(11) NOT NULL AUTO_INCREMENT,
  objid binary(16) NOT NULL,
  name varchar(25) NOT NULL,
  description varchar(50) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY role_objid_UNIQUE (objid),
  UNIQUE KEY role_name_UNIQUE (name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Benutzer DB
CREATE DATABASE IF NOT EXISTS userdb;
USE userdb;

CREATE TABLE IF NOT EXISTS t_user (
  id int(11) NOT NULL AUTO_INCREMENT,
  objid binary(16) NOT NULL,
  login varchar(10) NOT NULL,
  firstname varchar(20) NOT NULL,
  lastname varchar(30) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY user_objid_UNIQUE (objid),
  UNIQUE KEY user_login_UNIQUE (login)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
