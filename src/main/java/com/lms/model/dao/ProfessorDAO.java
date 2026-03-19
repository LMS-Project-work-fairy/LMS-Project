package com.lms.model.dao;


import com.lms.common.QueryUtil;
import com.lms.model.dto.CourseDTO;
import com.lms.model.dto.ProfessorDTO;
import com.mysql.cj.util.DnsSrv;
import com.lms.common.JDBCTemplate;
import com.lms.common.QueryUtil;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    private final Connection connection;

    public ProfessorDAO(Connection connection) {
        this.connection = connection;
    }


    // 교수 회원가입 정보 저장 
    public String save(ProfessorDTO newprofessor) throws SQLException {

        String query = QueryUtil.getQuery("professor.save");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset = pstmt.executeQuery();

            pstmt.setString(1, newprofessor.getProfessorId());
            pstmt.setString(2, newprofessor.getProfessorName());
            pstmt.setString(3, newprofessor.getProfessorNo());
            pstmt.setString(4, newprofessor.getProfessorAddress());
            pstmt.setString(5, newprofessor.getProfessorEmail());
            pstmt.setString(6, newprofessor.getProfessorPhone());
            pstmt.setString(7, newprofessor.getProfessorPw());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "SUCCESS";

            }
        }

        return null;
    }

    //교수 로그인 메소드
    public LoginUserDTO loginProfessor(Connection con, LoginRequestDTO request) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        LoginUserDTO loginUser = null;

        String qeury = QueryUtil.getQuery("loginProfessor");

        try {
            pstmt = con.prepareStatement(qeury);
            pstmt.setString(1, request.getUserId());
            pstmt.setString(2, request.getPassword());

            rset = pstmt.executeQuery();

            if (rset.next()) {
                loginUser = new LoginUserDTO();
                loginUser.setRole("PROFESSOR");
                loginUser.setUserId(rset.getString("professor_id"));
                loginUser.setUserName(rset.getString("professor_name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("교수 로그인 조회 실패", e);
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }

        return loginUser;
    }

    // 교수 기능-담당 과목 조회
    public List<CourseDTO> selectCoursesByProfId(Connection con, String profId) {
        List<CourseDTO> courseList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = QueryUtil.getQuery("selectCoursesByProfId");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, profId);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                CourseDTO course = new CourseDTO();
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

    // 교수 기능-수강 학생 확인
    public List<CourseDTO> selectStudentsByClassNo(Connection con, String classNo) {
        List<CourseDTO> studentList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String query = QueryUtil.getQuery("selectStudentsByClassNo");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, classNo);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                CourseDTO course = new CourseDTO(); // 만능 상자(CourseDTO) 활용!
                course.setStudentId(rset.getString("student_id"));
                course.setStudentName(rset.getString("student_name"));
                course.setEnrollDate(rset.getString("enroll_date"));
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

    // 교수 기능 - 과제 등록
    public int updateClassTask(Connection con, String classNo, String professorId, String classTask) {
        int result = 0;
        PreparedStatement pstmt = null;
        String query = QueryUtil.getQuery("updateClassTask");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, classTask);
            pstmt.setString(2, classNo);
            pstmt.setString(3, professorId);

            result = pstmt.executeUpdate(); // 성공하면 1 반환
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
    }

    // 교수 기능 - 성적 관리
    public int updateStudentScore(Connection con, String classNo, String studentId, double score) {
        int result = 0;
        PreparedStatement pstmt = null;
        String query = QueryUtil.getQuery("updateStudentScore");

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setDouble(1, score);
            pstmt.setString(2, classNo);
            pstmt.setString(3, studentId);

            result = pstmt.executeUpdate(); // 성공하면 1 반환
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
    }
}
