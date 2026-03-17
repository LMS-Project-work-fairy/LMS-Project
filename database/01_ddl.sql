-- 자식 테이블부터 삭제
DROP TABLE IF EXISTS `수강`;
DROP TABLE IF EXISTS `강의`;
DROP TABLE IF EXISTS `학생`;
DROP TABLE IF EXISTS `교수`;

CREATE TABLE `교수`
(
    `professor_id`      VARCHAR(10) NOT NULL COMMENT '교수번호',
    `professor_name`    VARCHAR(20) NOT NULL COMMENT '이름',
    `professor_no`      VARCHAR(15) NOT NULL COMMENT '주민등록번호',
    `professor_address` VARCHAR(100) NOT NULL COMMENT '주소',
    `professor_email`   VARCHAR(30) NOT NULL COMMENT '이메일',
    `professor_phone`   VARCHAR(15) NOT NULL COMMENT '전화번호',
    `professor_pw`      VARCHAR(50) NOT NULL COMMENT '비밀번호',
    PRIMARY KEY (`professor_id`)
)
COMMENT = '교수';

CREATE TABLE `학생`
(
    `student_id`      VARCHAR(10) NOT NULL COMMENT '학번',
    `student_name`    VARCHAR(20) NOT NULL COMMENT '이름',
    `student_no`      VARCHAR(15) NOT NULL COMMENT '주민등록번호',
    `student_address` VARCHAR(100) NOT NULL COMMENT '주소',
    `student_email`   VARCHAR(30) NOT NULL COMMENT '이메일',
    `student_phone`   VARCHAR(15) NOT NULL COMMENT '전화번호',
    `student_pw`      VARCHAR(255) NOT NULL COMMENT '비밀번호',
    `professor_id`    VARCHAR(10) NOT NULL COMMENT '지도교수',
    PRIMARY KEY (`student_id`),
    CONSTRAINT `FK_학생_교수`
        FOREIGN KEY (`professor_id`) REFERENCES `교수` (`professor_id`)
)
COMMENT = '학생';

CREATE TABLE `강의`
(
    `class_no`       VARCHAR(10) NOT NULL COMMENT '강의번호',
    `class_name`     VARCHAR(20) NOT NULL COMMENT '강의명',
    `class_point`    DECIMAL(3,1) NOT NULL COMMENT '학점',
    `class_time`     VARCHAR(15) COMMENT '시간표',
    `class_room`     VARCHAR(10) NOT NULL COMMENT '강의실',
    `class_type`     VARCHAR(10) NOT NULL COMMENT '분류',
    `professor_id`   VARCHAR(10) NOT NULL COMMENT '교수번호',
    PRIMARY KEY (`class_no`),
    CONSTRAINT `FK_강의_교수`
        FOREIGN KEY (`professor_id`) REFERENCES `교수` (`professor_id`)
)
COMMENT = '강의';

CREATE TABLE `수강`
(
    `student_id`     BIGINT NOT NULL COMMENT '학번',
    `class_no`       VARCHAR(10) NOT NULL COMMENT '강의번호',
    `enroll_date`    DATETIME COMMENT '수강신청일',
    `score`          DECIMAL(4,2) COMMENT '성적',
    `status`         BOOLEAN COMMENT '수강상태',
    PRIMARY KEY (`student_id`, `class_no`),
    CONSTRAINT `FK_수강_학생`
        FOREIGN KEY (`student_id`) REFERENCES `학생` (`student_id`),
    CONSTRAINT `FK_수강_강의`
        FOREIGN KEY (`class_no`) REFERENCES `강의` (`class_no`)
)
COMMENT = '수강';