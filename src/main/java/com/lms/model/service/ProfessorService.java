package com.lms.model.service;

import com.lms.common.JDBCTemplate;
import com.lms.model.dao.ProfessorDAO;
import com.lms.model.dto.CourseDTO;

import java.sql.Connection;
import java.util.List;

public class ProfessorService {

    // 담당 과목 조회
    public List<CourseDTO> selectCoursesByProfId(String profId) {
        // 1. DB 연결 통로 열기
        Connection conn = JDBCTemplate.getConnection();

        // 2. DAO 부르기
        ProfessorDAO professorDAO = new ProfessorDAO(conn);

        // 3. DAO에게 결과 받기
        List<CourseDTO> courseList = professorDAO.selectCoursesByProfId(conn, profId);

        // 4. 통로 닫기
        JDBCTemplate.close(conn);

        return courseList;
    }

    // 수강 학생 확인
    public List<CourseDTO> selectStudentsByClassNo(String classNo) {
        Connection conn = JDBCTemplate.getConnection();
        ProfessorDAO professorDAO = new ProfessorDAO(conn);

        List<CourseDTO> studentList = professorDAO.selectStudentsByClassNo(conn, classNo);

        JDBCTemplate.close(conn);
        return studentList;
    }

    // 과제 등록
    public int updateClassTask(String classNo, String professorId, String classTask) {
        Connection conn = JDBCTemplate.getConnection();
        ProfessorDAO professorDAO = new ProfessorDAO(conn);

        int result = professorDAO.updateClassTask(conn, classNo, professorId, classTask);

        // 성공(1)하면 확정(Commit), 실패(0)하면 되돌리기(Rollback)
        if (result > 0) {
            JDBCTemplate.commit(conn);
        } else {
            JDBCTemplate.rollback(conn);
        }

        JDBCTemplate.close(conn);
        return result;
    }

    // 성적 관리
    public int updateStudentScore(String classNo, String studentId, double score) {
        Connection conn = JDBCTemplate.getConnection();
        ProfessorDAO professorDAO = new ProfessorDAO(conn);

        int result = professorDAO.updateStudentScore(conn, classNo, studentId, score);

        if (result > 0) {
            JDBCTemplate.commit(conn);
        } else {
            JDBCTemplate.rollback(conn);
        }

        JDBCTemplate.close(conn);
        return result;
    }
}
