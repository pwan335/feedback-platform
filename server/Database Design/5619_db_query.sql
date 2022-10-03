CREATE TABLE tbl_user(
	uid BIGINT(20) NOT NULL AUTO_INCREMENT,
	user_name VARCHAR(20) UNIQUE NOT NULL,
	password VARCHAR(30) NOT NULL,
	email VARCHAR(30) NOT null,
	address VARCHAR(50),
	phone_number VARCHAR(30),
	hobby VARCHAR(50),
	avatar VARCHAR(50), -- default file path: ?
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (uid)
);

-- CREATE TABLE tbl_userPhoto(
-- 	id INT PRIMARY KEY AUTO_INCREMENT,
-- 	uid INT NOT NULL,
-- 	photo BLOB,
-- 	FOREIGN KEY (uid) REFERENCES tbl_user(uid)
-- );

CREATE TABLE tbl_pm(
	pm_id BIGINT(20) NOT NULL AUTO_INCREMENT,
	pm_name VARCHAR(20) UNIQUE NOT NULL,
	password VARCHAR(30) NOT NULL,
	email VARCHAR(30) NOT null,
	address VARCHAR(50),
	phone_number VARCHAR(30),
	hobby VARCHAR(50),
	avatar VARCHAR(50), -- default file path: ?
	company VARCHAR(50),
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (pm_id)
);

-- CREATE TABLE tbl_pmPhoto(
-- 	id INT PRIMARY KEY AUTO_INCREMENT,
-- 	pm_id INT NOT NULL,
-- 	photo BLOB,
-- 	FOREIGN KEY (pm_id) REFERENCES tbl_pm(pm_id)
-- );

CREATE TABLE tbl_topic(
	topic_id BIGINT(20) NOT NULL AUTO_INCREMENT,
	pm_id BIGINT(20) NOT NULL,
	topic_name VARCHAR(50),
	content text NOT NULL,
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (topic_id, pm_id),
	FOREIGN KEY (pm_id) REFERENCES tbl_pm(pm_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tbl_topicPhoto(
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	topic_id BIGINT(20) NOT NULL,
	photo VARCHAR(50), -- file path
	FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tbl_likes(
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	topic_id BIGINT(20) NOT NULL,
	uid BIGINT(20) NOT NULL,
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (topic_id, uid),
	KEY(id),
	FOREIGN KEY (uid) REFERENCES tbl_user(uid) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tbl_collects(
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	topic_id BIGINT(20) NOT NULL,
	uid BIGINT(20) NOT NULL,
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (topic_id, uid),
	KEY(id),
	FOREIGN KEY (uid) REFERENCES tbl_user(uid) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE tbl_comment(
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	topic_id BIGINT(20) NOT NULL,
	uid BIGINT(20) NOT NULL,
	content text NOT NULL,
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id, topic_id, uid),
	FOREIGN KEY (uid) REFERENCES tbl_user(uid) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (topic_id) REFERENCES tbl_topic(topic_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE tbl_reply(
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	comment_id BIGINT(20) NOT NULL, -- 来表示该回复挂在的根评论id
	from_uid BIGINT(20) NOT NULL,
	to_uid BIGINT(20) NOT NULL,
	reply_type VARCHAR(7) NOT NULL CHECK(reply_type = 'reply' or reply_type = 'comment' ), -- 表示回复的类型，因为回复可以是针对评论的回复 (comment) 也可以是针对回复的回复 (reply)
	reply_id BIGINT(20) NOT NULL,	-- 表示回复目标的 id,如果 reply_type 是 comment 的话,那么 reply_id = commit id,如果 reply_type 是 reply 的话,这表示这条回复的父回复.
	content text NOT NULL,
	create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id, comment_id, from_uid),
	FOREIGN KEY (comment_id) REFERENCES tbl_comment(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (from_uid) REFERENCES tbl_user(uid) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (to_uid) REFERENCES tbl_user(uid) ON DELETE CASCADE ON UPDATE CASCADE
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

DROP TABLE tbl_reply;
DROP TABLE tbl_comment;
DROP TABLE tbl_collects;
DROP TABLE tbl_likes;
DROP TABLE tbl_topicPhoto;
DROP TABLE tbl_topic;
-- DROP TABLE tbl_pmPhoto;
DROP TABLE tbl_pm;
-- DROP TABLE tbl_userPhoto;
DROP TABLE tbl_user;


INSERT INTO tbl_user VALUES( null, 'user2', 'abc123', '948936249@qq.com', 'china', null, null, null, '2022-01-19 22:22:22', '2022-01-19 22:22:22');

INSERT INTO tbl_pm VALUES(null, 'pm1', 'abc123', '948936249@qq.com', 'China', '13812345678', 'Basketball, movie', null, 'Tencent', '2022-01-19 22:22:22', '2022-01-19 22:22:22');

INSERT INTO tbl_topic VALUES(null, 1, 'The best way to study', 'read mroe, listen more and speak more!', '2022-01-19 10:15:00');
INSERT INTO tbl_topic VALUES(null, 1, 'The best way to study2', 'read mroe, listen more and speak more!', '2022-01-20 11:11:11');
INSERT INTO tbl_topic VALUES(null, 1, 'Test1', 'test1,test1,test1', '2022-09-28 17:08:11');

INSERT INTO tbl_comment VALUES(null, 2, 1, 'very good!', '2022-01-19 11:11:11');
INSERT INTO tbl_comment VALUES(null, 2, 1, 'very good222!', '2022-01-19 22:22:22');
INSERT INTO tbl_comment VALUES(null, 3, 1, 'test comment1', '2022-09-28 17:10:11');

INSERT INTO tbl_reply VALUES(null, 3, 1, 1, 'comment', 1, 'reply test1', '2022-01-19 22:22:22');

INSERT INTO tbl_collects VALUES(null, 2, 1, '2022-09-19 22:22:22');
INSERT INTO tbl_likes VALUES(null, 2, 1, '2022-09-19 22:22:22');

-- 根据topicName 来搜索话题
SELECT * FROM tbl_topic WHERE topicName LIKE '%study2%'
-- 查询用户是否收藏该话题
SELECT * from tbl_collects WHERE topic_id = 2 and uid = 1;
-- 查询该话题的评论数，包括一级评论表和二级表
SELECT topic_id, COUNT(*) FROM tbl_comment WHERE topic_id = 2;
SELECT COUNT(*) FROM tbl_reply WHERE comment_id IN
	(SELECT id FROM tbl_comment WHERE topic_id = 2);
-- 查询用户是否点赞该话题
SELECT IFNULL(id, FALSE) from tbl_likes WHERE topic_id = 1 and uid = 1;
-- 根据时间排序最新话题
SELECT * FROM tbl_topic ORDER BY date DESC;

-- SELECT *
-- FROM tbl_topic JOIN tbl_comment USING (topic_id)
-- 			JOIN tbl_reply t2 ON (t1.id = t2.comment_id)

-- 话题总评论数：一级评论+二级评论
SELECT topic_id, IFNULL(SUM(comment + IFNULL(reply, 0)),0) AS total_comments
FROM
	(SELECT topic.topic_id, comment , reply -- , SUM(rep.reply + com.comment) AS total_comments
	FROM
		(tbl_topic topic
	LEFT JOIN
		(SELECT topic_id, COUNT(*) AS comment
			FROM tbl_comment
			GROUP BY topic_id) com USING(topic_id))
	LEFT JOIN
		(SELECT topic_id, COUNT(*) AS reply  -- 话题二级评论的数量
			FROM tbl_comment com JOIN tbl_reply rep on com.id = rep.comment_id
			GROUP BY topic_id) rep
	ON rep.topic_id = com.topic_id) total
GROUP BY topic_id;
	
-- 总点赞数
SELECT tbl_topic.topic_id, IFNULL(likes,0) AS likeNum
FROM 
	tbl_topic 
LEFT JOIN
	(SELECT topic_id, COUNT(*) AS likes
	FROM tbl_likes
	GROUP BY topic_id) tb_like USING(topic_id)
WHERE topic_id = 3;

-- 总收藏数
SELECT COUNT(*) AS collects
FROM tbl_collects
GROUP BY topic_id
HAVING topic_id = 2;

-- 封装话题信息：(topic_id, topicName, content, date, pm_id, pmName, photo) / (collectNum, collectState, commentNum, likeNum, likeState )
SELECT  topic_id, topicName, content, tbl_topic.date, pmName, photo
FROM 
	tbl_topic LEFT JOIN tbl_pm USING(pm_id)
						LEFT JOIN tbl_pmPhoto USING(pm_id)
WHERE topicName LIKE '%study%'
