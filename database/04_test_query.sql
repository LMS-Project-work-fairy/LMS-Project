-- 학생별 수강 목록 조회
SELECT s.student_name, c.class_name, e.enroll_date, e.score, e.status
FROM `수강` e
JOIN `학생` s ON e.student_id = s.student_id
JOIN `강의` c ON e.class_no = c.class_no;

-- 교수별 강의 목록 조회
SELECT p.professor_name, c.class_name, c.class_time, c.class_room
FROM `강의` c
JOIN `교수` p ON c.professor_id = p.professor_id;

-- 학생 + 지도교수 조회
SELECT s.student_name, p.professor_name
FROM `학생` s
JOIN `교수` p ON s.professor_id = p.professor_id;