CREATE TABLE `tbl_user` (
                            `uid` tinyint(20) NOT NULL AUTO_INCREMENT,
                            `user_name` varchar(20) DEFAULT NULL,
                            `password` varchar(30) DEFAULT NULL,
                            `email` varchar(30) DEFAULT NULL,
                            `address` varchar(50) DEFAULT NULL,
                            `phone_number` varchar(30) DEFAULT NULL,
                            `hobby` varchar(50) DEFAULT NULL,
                            `user_type` char(2) DEFAULT NULL,
                            `sign` varchar(255) not null,
                            `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            `update_time` datetime NOT NULL  DEFAULT  CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

CREATE TABLE tbl_userPhoto(
                              id INT PRIMARY KEY AUTO_INCREMENT,
                              uid tinyint NOT NULL,
                              photo mediumtext,
                              FOREIGN KEY (uid) REFERENCES tbl_user(uid)
);

CREATE TABLE tbl_pm(
                       pm_id tinyint NOT NULL AUTO_INCREMENT,
                       pm_name VARCHAR(20) UNIQUE NOT NULL,
                       password VARCHAR(30) NOT NULL,
                       email VARCHAR(30) NOT null,
                       address VARCHAR(50),
                       phone_number VARCHAR(30),
                       hobby VARCHAR(50),
                       company VARCHAR(50),
                       date DATE NOT NULL,
                       PRIMARY KEY (pm_id)
);

CREATE TABLE tbl_pmPhoto(
                            id tinyint PRIMARY KEY AUTO_INCREMENT,
                            pm_id INT NOT NULL,
                            photo mediumtext,
                            FOREIGN KEY (pm_id) REFERENCES tbl_pm(pm_id)
);

CREATE TABLE tbl_topic(
                          topic_id INT NOT NULL AUTO_INCREMENT,
                          pm_id INT NOT NULL,
                          topic_name VARCHAR(50),
                          content VARCHAR(500),
                          date DATE NOT NULL,
                          create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
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
                          uid tinyint NOT NULL,
                          date DATE NOT NULL,
                          PRIMARY KEY (id, topic_id, uid),
                          FOREIGN KEY (uid) REFERENCES tbl_user(uid),
                          FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id)
);

CREATE TABLE tbl_collects(
                             id INT NOT NULL AUTO_INCREMENT,
                             topic_id INT NOT NULL,
                             uid tinyint NOT NULL,
                             date DATE NOT NULL,
                             PRIMARY KEY (id, topic_id, uid),
                             FOREIGN KEY (uid) REFERENCES tbl_user(uid),
                             FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id)
);


CREATE TABLE tbl_comment(
                            id INT NOT NULL AUTO_INCREMENT,
                            topic_id INT NOT NULL,
                            uid tinyint NOT NULL,
                            content VARCHAR(100) NOT NULL,
                            date DATE NOT NULL,
                            PRIMARY KEY (id, topic_id, uid),
                            FOREIGN KEY (uid) REFERENCES tbl_user(uid),
                            FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id)
);

CREATE TABLE tbl_reply(
                          id INT NOT NULL AUTO_INCREMENT,
                          comment_id INT NOT NULL, -- 来表示该回复挂在的根评论id
                          from_uid tinyint NOT NULL,
                          to_uid tinyint NOT NULL,
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


