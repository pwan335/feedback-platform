CREATE TABLE tbl_user(
	uid INT PRIMARY KEY auto_increment,
	username VARCHAR(20) UNIQUE NOT NULL,
	password VARCHAR(30) NOT NULL,
	email VARCHAR(30) NOT null,
	address VARCHAR(50),
	phoneNumber VARCHAR(30),
	hobby VARCHAR(50),
	-- userPhoto BINARY,
	date DATE NOT NULL
);

SELECT * from tbl_user;

DROP TABLE tbl_user;

INSERT INTO tbl_user VALUES( null, 'user2', 'abc123', '948936249@qq.com', 'china', null, null, '2022-01-19');