# DB 생성
DROP DATABASE IF EXISTS jpaBoard;
CREATE DATABASE jpaBoard;
USE jpaBoard;


#게시물(article)
#-id
#-등록날짜
#-수정날짜
#-제목
#-내용

# 회원 테이블 생성
CREATE TABLE `article` (
    id BIGINT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    reg_date DATETIME NOT NULL,
    update_date DATETIME NOT NULL,
    title VARCHAR(150) NOT NULL,
    `body` TEXT NOT NULL
    `user_id` BIGINT UNSIGNED NOT NULL  #현재 회원에 대한 구분 번호
);


SHOW TABLES;

# 회원데이터 생성
INSERT INTO `article`
SET reg_date = NOW(),
update_date = NOW(),
title = '제목1',
`body` = '내용1',
`user_id` = 1;

# 회원데이터 생성
INSERT INTO `article`
SET reg_date = NOW(),
update_date = NOW(),
title = '제목2',
`body` = '내용2',
`user_id` = 2;

# 회원데이터 생성
INSERT INTO `article`
SET reg_date = NOW(),
update_date = NOW(),
title = '제목3',
`body` = '내용3',
`user_id` = 3;

# 회원데이터 생성
INSERT INTO `article`
SET reg_date = NOW(),
update_date = NOW(),
title = '제목4',
`body` = '내용4',
`user_id` = 4;

# 회원데이터 생성
INSERT INTO `article`
SET reg_date = NOW(),
update_date = NOW(),
title = '제목5',
`body` = '내용5',
`user_id` = 5;