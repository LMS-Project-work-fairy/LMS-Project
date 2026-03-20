-- 교수 더미 데이터
INSERT INTO `교수`
(
    `professor_id`,
    `professor_name`,
    `professor_no`,
    `professor_address`,
    `professor_email`,
    `professor_phone`,
    `professor_pw`
)
VALUES
('P1001', '김교수', '700101-1234567', '서울시 강남구', 'kimprof@lms.com', '010-1111-1111', 'pass1234'),
('P1002', '이교수', '720202-2234567', '서울시 서초구', 'leeprof@lms.com', '010-2222-2222', 'pass1234'),
('P1003', '박교수', '750303-1234567', '서울시 송파구', 'parkprof@lms.com', '010-3333-3333', 'pass1234');


-- 강의 더미 데이터
INSERT INTO `강의`
(
    `class_no`,
    `class_name`,
    `class_point`,
    `class_time`,
    `class_room`,
    `class_type`,
    `professor_id`,
    `class_task`,
    `class_capacity`
)
VALUES
('C101', '자바프로그래밍', 3.0, '월1-3', 'A101', '전공', 'P1001', '자바 기초 문법 정리', 30),
('C102', '데이터베이스',   3.0, '화2-4', 'B201', '전공', 'P1002', 'ERD 설계 초안 제출', 35),
('C103', '웹개발기초',     2.0, '수1-2', 'C301', '교양', 'P1001', 'HTML/CSS 실습', 40),
('C104', '자료구조',       3.0, '목1-3', 'D101', '전공', 'P1003', '스택과 큐 구현', 25);


-- 학생 더미 데이터
INSERT INTO `학생`
(
    `student_id`,
    `student_name`,
    `student_no`,
    `student_address`,
    `student_email`,
    `student_phone`,
    `student_pw`,
    `professor_id`
)
VALUES
('20230001', '홍길동', '030101-3123456', '서울시 송파구', 'hong@lms.com', '010-4444-1111', 'student1', 'P1001'),
('20230002', '김철수', '030202-3123456', '서울시 마포구', 'kimcs@lms.com', '010-4444-2222', 'student2', 'P1001'),
('20230003', '이영희', '030303-4123456', '서울시 성북구', 'leeyh@lms.com', '010-4444-3333', 'student3', 'P1002'),
('20230004', '박민수', '030404-3123456', '서울시 강서구', 'parkms@lms.com', '010-4444-4444', 'student4', 'P1003');


-- 메시지 더미 데이터
INSERT INTO `메시지`
(
    `user_id`,
    `student_id`,
    `professor_id`,
    `receiver_id`,
    `content`,
    `user_name`
)
VALUES
('20230001', '20230001', NULL, 'P1001', '교수님, 과제 제출 기한 문의드립니다.', '홍길동'),
('20230002', '20230002', NULL, 'P1001', '수강 신청 관련 상담 요청드립니다.', '김철수'),
('P1002', NULL, 'P1002', '20230003', '중간고사 범위를 공지합니다.', '이교수'),
('P1003', NULL, 'P1003', '20230004', '자료구조 과제 피드백 확인 바랍니다.', '박교수');


-- 수강 더미 데이터
INSERT INTO `수강`
(
    `student_id`,
    `class_no`,
    `enroll_date`,
    `score`,
    `status`
)
VALUES
('20230001', 'C101', '2026-03-17 09:00:00', 4.50, true),
('20230001', 'C102', '2026-03-17 09:10:00', 3.80, true),
('20230002', 'C101', '2026-03-17 09:20:00', 3.20, true),
('20230003', 'C102', '2026-03-17 09:30:00', NULL, true),
('20230003', 'C103', '2026-03-17 09:40:00', NULL, true),
('20230004', 'C104', '2026-03-17 09:50:00', NULL, true);