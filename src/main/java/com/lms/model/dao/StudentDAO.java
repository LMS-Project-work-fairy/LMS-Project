package com.lms.model.dao;

import com.lms.common.JDBCTemplate;
import com.lms.common.QueryUtil;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import com.lms.model.dto.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {

    private final Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public LoginUserDTO loginStudent(LoginRequestDTO request) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        LoginUserDTO loginUser = null;

        String query = QueryUtil.getQuery("loginStudent");

        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, request.getUserId());
            pstmt.setString(2, request.getPassword());

            rset = pstmt.executeQuery();

            if (rset.next()) {
                loginUser = new LoginUserDTO();
                loginUser.setRole("STUDENT");
                loginUser.setUserId(rset.getString("student_id"));
                loginUser.setUserName(rset.getString("student_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("학생 로그인 조회 실패", e);
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }

        return loginUser;
    }

    public int save(StudentDTO student) throws SQLException {
        String query = QueryUtil.getQuery("students.save");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, student.getStudentId());
            pstmt.setString(2, student.getStudentPw());
            pstmt.setString(3, student.getStudentNo());
            pstmt.setString(4, student.getStudentName());
            pstmt.setString(5, student.getStudentAddress());
            pstmt.setString(6, student.getStudentEmail());
            pstmt.setString(7, student.getStudentPhone());

            return pstmt.executeUpdate();
        }
    }

    public boolean existsByStudentId(String studentId) throws SQLException {
        String query = QueryUtil.getQuery("students.existsByStudentId");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    return rset.getInt(1) > 0;
                }
            }
        }

        return false;
    }
}