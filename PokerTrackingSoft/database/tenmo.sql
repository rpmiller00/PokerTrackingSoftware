BEGIN TRANSACTION;

DROP TABLE IF EXISTS entry, tracker_user;
DROP SEQUENCE IF EXISTS seq_user_id, seq_entry_id;


CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tracker_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(20),
	CONSTRAINT PK_tracker_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);


CREATE SEQUENCE seq_entry_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE entry (
	entry_id int NOT NULL DEFAULT nextval('seq_entry_id'),
	user_id int NOT NULL,
	amount decimal(13, 2) NOT NULL,
	game_size varchar(55) NOT NULL,
	game_type varchar(55) NOT NULL,
	location  varchar(55) NOT NULL,
	CONSTRAINT PK_entry PRIMARY KEY (entry_id),
	CONSTRAINT FK_entry_user FOREIGN KEY (user_id) REFERENCES tracker_user (user_id)
);
	




COMMIT;
