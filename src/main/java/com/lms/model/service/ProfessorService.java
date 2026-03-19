package com.lms.model.service;

import com.lms.common.JDBCTemplate;
import com.lms.model.dao.CourseDAO;
import com.lms.model.dto.EnrollmentCourseDTO;


import java.sql.Connection;
import java.util.List;

public class ProfessorService {

    // ==============================================================
    // [교수 기능 1] 담당 과목 조회
    // ==============================================================
    public List<EnrollmentCourseDTO> selectCoursesByProfId(String profId) {
        Connection conn = JDBCTemplate.getConnection();


        CourseDAO courseDAO = new CourseDAO(conn);
        List<EnrollmentCourseDTO> courseList = courseDAO.selectCoursesByProfId(conn, profId);

        JDBCTemplate.close(conn);
        return courseList;
    }

    // ==============================================================
    // [교수 기능 2] 수강 학생 확인
    // ==============================================================
    public List<EnrollmentCourseDTO> selectStudentsByClassNo(String classNo) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO courseDAO = new CourseDAO(conn);

        List<EnrollmentCourseDTO> studentList = courseDAO.selectStudentsByClassNo(conn, classNo);

        JDBCTemplate.close(conn);
        return studentList;
    }

    // ==============================================================
    // [교수 기능 3] 과제 등록
    // ==============================================================
    public int updateClassTask(String classNo, String professorId, String classTask) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO courseDAO = new CourseDAO(conn);

        int result = courseDAO.updateClassTask(conn, classNo, professorId, classTask);

        if (result > 0) {
            JDBCTemplate.commit(conn);
        } else {
            JDBCTemplate.rollback(conn);
        }

        JDBCTemplate.close(conn);
        return result;
    }

    // ==============================================================
    // [교수 기능 4] 성적 관리
    // ==============================================================
    public int updateStudentScore(String classNo, String studentId, double score) {
        Connection conn = JDBCTemplate.getConnection();
        CourseDAO courseDAO = new CourseDAO(conn);

        int result = courseDAO.updateStudentScore(conn, classNo, studentId, score);

        if (result > 0) {
            JDBCTemplate.commit(conn);
        } else {
            JDBCTemplate.rollback(conn);
        }

        JDBCTemplate.close(conn);
        return result;
    }
}