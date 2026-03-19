-- 교수 더미 데이터
INSERT INTO `교수`
(`professor_id`, `professor_name`, `professor_no`, `professor_address`, `professor_email`, `professor_phone`, `professor_pw`)
VALUES
('P1001', '김교수', '700101-1234567', '서울시 강남구', 'kimprof@lms.com', '010-1111-1111', 'pass1234'),
('P1002', '이교수', '720202-2234567', '서울시 서초구', 'leeprof@lms.com', '010-2222-2222', 'pass1234');

-- 학생 더미 데이터
INSERT INTO `학생`
(`student_id`, `student_name`, `student_no`, `student_address`, `student_email`, `student_phone`, `student_pw`, `professor_id`)
VALUES
(20230001, '홍길동', '030101-3123456', '서울시 송파구', 'hong@lms.com', '010-3333-3333', 'student1', 'P1001'),
(20230002, '김철수', '030202-3123456', '서울시 마포구', 'kimcs@lms.com', '010-4444-4444', 'student2', 'P1001'),
(20230003, '이영희', '030303-4123456', '서울시 성북구', 'leeyh@lms.com', '010-5555-5555', 'student3', 'P1002');

-- 강의 더미 데이터
INSERT INTO `강의`
(`class_no`, `class_name`, `class_point`, `class_time`, `class_room`, `class_type`,`class_task`, `professor_id`)
VALUES
('C101', '자바프로그래밍', 3.0, '월1-3', 'A101', '전공', null,'P1001'),
('C102', '데이터베이스',   3.0, '화2-4', 'B201', '전공', null,'P1002'),
('C103', '웹개발기초',     2.0, '수1-2', 'C301', '교양', null,'P1001');

-- 수강 더미 데이터
INSERT INTO `수강`
(`student_id`, `class_no`, `enroll_date`, `score`, `status`)
VALUES
(20230001, 'C101', '2026-03-17 09:00:00', 4.50, TRUE),
(20230001, 'C102', '2026-03-17 09:10:00', 3.80, TRUE),
(20230002, 'C101', '2026-03-17 09:20:00', 3.20, TRUE),
(20230003, 'C103', '2026-03-17 09:30:00', NULL, TRUE);