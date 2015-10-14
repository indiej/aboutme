CREATE DATABASE IF NOT EXISTS aboutme;
GRANT USAGE ON *.* TO 'aboutme-user' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON aboutme.* TO 'aboutme-user';
FLUSH PRIVILEGES;

USE aboutme;
CREATE TABLE IF NOT EXISTS MyAttribute (
	id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	type VARCHAR(15) NOT NULL,
	name VARCHAR(40) CHARACTER SET utf8 NOT NULL,
	startDate DATETIME,
	endDate DATETIME,
	picture VARCHAR(128),
	lastModBy VARCHAR(64),
	description TEXT CHARACTER SET utf8
);

INSERT INTO MyAttribute (type, name, startDate, endDate, description)
SELECT * FROM (SELECT 'Identity', 'Joe Developer', '12-01-14', '12-01-17', 'Wannabe Code Ninja') AS tmp
WHERE NOT EXISTS (
	SELECT 1 FROM MyAttribute WHERE type='Identity'
) LIMIT 1;

