package com.lms.model.dao;

import com.lms.common.JDBCTemplate;
import com.lms.common.QueryUtil;
import com.lms.model.dto.EnrollmentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    private final Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }

    // ==============================================================
    // [교수 기능 1] 담당 과목 조회
    // ==============================================================
    public List<EnrollmentDTO> selectCoursesByProfId(Connection con, String profId) {
        List<EnrollmentDTO> courseList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = QueryUtil.getQuery("selectCoursesByProfId");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, profId);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                EnrollmentDTO course = new EnrollmentDTO();
                course.setClassNo(rset.getString("class_no"));
                course.setClassName(rset.getString("class_name"));
                course.setClassPoint(rset.getDouble("class_point"));
                course.setClassTime(rset.getString("class_time"));
                course.setClassRoom(rset.getString("class_room"));
                course.setClassType(rset.getString("class_type"));
                course.setClassTask(rset.getString("class_task"));
                course.setProfessorId(rset.getString("professor_id"));
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }
        return courseList;
    }

    // ==============================================================
    // [교수 기능 2] 수강 학생 확인
    // ==============================================================
    public List<EnrollmentDTO> selectStudentsByClassNo(Connection con, String classNo) {
        List<EnrollmentDTO> studentList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = QueryUtil.getQuery("selectStudentsByClassNo");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, classNo);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                EnrollmentDTO course = new EnrollmentDTO();
                course.setStudentId(rset.getString("student_id"));
                course.setStudentName(rset.getString("student_name"));
                course.setScore(rset.getDouble("score"));
                course.setStatus(rset.getBoolean("status"));
                studentList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }
        return studentList;
    }

    // ==============================================================
    // [교수 기능 3] 과제 등록
    // ==============================================================
    public int updateClassTask(Connection con, String classNo, String professorId, String classTask) {
        int result = 0;
        PreparedStatement pstmt = null;
        String query = QueryUtil.getQuery("updateClassTask");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, classTask);
            pstmt.setString(2, classNo);
            pstmt.setString(3, professorId);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
    }

    // ==============================================================
    // [교수 기능 4] 성적 관리
    // ==============================================================
    public int updateStudentScore(Connection con, String classNo, String studentId, double score) {
        int result = 0;
        PreparedStatement pstmt = null;
        String query = QueryUtil.getQuery("updateStudentScore");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setDouble(1, score);
            pstmt.setString(2, classNo);
            pstmt.setString(3, studentId);

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
    }
}