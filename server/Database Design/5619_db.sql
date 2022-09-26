CREATE TABLE tbl_user(
	uid INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(20) UNIQUE NOT NULL,
	password VARCHAR(30) NOT NULL,
	email VARCHAR(30) NOT null,
	address VARCHAR(50),
	phoneNumber VARCHAR(30),
	hobby VARCHAR(50),
	-- userPhoto BINARY,
	date DATE NOT NULL,
	PRIMARY KEY (uid)
);

CREATE TABLE tbl_userPhoto(
	id INT PRIMARY KEY AUTO_INCREMENT,
	uid INT NOT NULL,
	photo BLOB,
	FOREIGN KEY (uid) REFERENCES tbl_user(uid)
);

CREATE TABLE tbl_pm(
	pm_id INT NOT NULL AUTO_INCREMENT,
	pmName VARCHAR(20) UNIQUE NOT NULL,
	password VARCHAR(30) NOT NULL,
	email VARCHAR(30) NOT null,
	address VARCHAR(50),
	phoneNumber VARCHAR(30),
	hobby VARCHAR(50),
	company VARCHAR(50),
	date DATE NOT NULL,
	PRIMARY KEY (pm_id)
);

CREATE TABLE tbl_pmPhoto(
	id INT PRIMARY KEY AUTO_INCREMENT,
	pm_id INT NOT NULL,
	photo BLOB,
	FOREIGN KEY (pm_id) REFERENCES tbl_pm(pm_id)
);

CREATE TABLE tbl_topic(
	topic_id INT NOT NULL AUTO_INCREMENT,
	pm_id INT NOT NULL,
	topicName VARCHAR(50),
	content VARCHAR(500),
	date DATE NOT NULL,
	PRIMARY KEY (topic_id, pm_id),
	FOREIGN KEY (pm_id) REFERENCES tbl_pm(pm_id)
);

CREATE TABLE tbl_topicPhoto(
	id INT PRIMARY KEY AUTO_INCREMENT,
	topic_id INT NOT NULL,
	photo BLOB,
	FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id)
);

CREATE TABLE tbl_likes(
	id INT NOT NULL AUTO_INCREMENT,
	topic_id INT NOT NULL,
	uid INT NOT NULL,
	date DATE NOT NULL,
	PRIMARY KEY (id, topic_id, uid),
	FOREIGN KEY (uid) REFERENCES tbl_user(uid),
	FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id)
);

CREATE TABLE tbl_collects(
	id INT NOT NULL AUTO_INCREMENT,
	topic_id INT NOT NULL,
	uid INT NOT NULL,
	date DATE NOT NULL,
	PRIMARY KEY (id, topic_id, uid),
	FOREIGN KEY (uid) REFERENCES tbl_user(uid),
	FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id)
);


CREATE TABLE tbl_comment(
	id INT NOT NULL AUTO_INCREMENT,
	topic_id INT NOT NULL,
	uid INT NOT NULL,
	content VARCHAR(100) NOT NULL,
	date DATE NOT NULL,
	PRIMARY KEY (id, topic_id, uid),
	FOREIGN KEY (uid) REFERENCES tbl_user(uid),
	FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id)
);

CREATE TABLE tbl_reply(
	id INT NOT NULL AUTO_INCREMENT,
	comment_id INT NOT NULL, -- 来表示该回复挂在的根评论id
	from_uid INT NOT NULL,
	to_uid INT NOT NULL,
	reply_type VARCHAR(7) NOT NULL CHECK(reply_type = 'reply' or reply_type = 'comment' ), -- 表示回复的类型，因为回复可以是针对评论的回复 (comment) 也可以是针对回复的回复 (reply)
	reply_id int NOT NULL,	-- 表示回复目标的 id,如果 reply_type 是 comment 的话,那么 reply_id = commit id,如果 reply_type 是 reply 的话,这表示这条回复的父回复.
	content VARCHAR(100) NOT NULL,
	date DATE NOT NULL,
	PRIMARY KEY (id, comment_id, from_uid),
	FOREIGN KEY (comment_id) REFERENCES tbl_comment(id),
	FOREIGN KEY (from_uid) REFERENCES tbl_user(uid),
	FOREIGN KEY (to_uid) REFERENCES tbl_user(uid)
);


SELECT * from tbl_user;
SELECT * from tbl_userPhoto;
SELECT * from tbl_pm;
SELECT * from tbl_pmPhoto;
SELECT * from tbl_topic;
SELECT * from tbl_topicPhoto;
SELECT * from tbl_likes;
SELECT * from tbl_collects;
SELECT * from tbl_comment;
SELECT * from tbl_reply;

DROP TABLE tbl_user;
DROP TABLE tbl_userPhoto;
DROP TABLE tbl_pm;
DROP TABLE tbl_pmPhoto;
DROP TABLE tbl_topic;
DROP TABLE tbl_topicPhoto;
DROP TABLE tbl_likes;
DROP TABLE tbl_collects;
DROP TABLE tbl_comment;
DROP TABLE tbl_reply;


INSERT INTO tbl_user VALUES( null, 'user2', 'abc123', '948936249@qq.com', 'china', null, null, '2022-01-19');
