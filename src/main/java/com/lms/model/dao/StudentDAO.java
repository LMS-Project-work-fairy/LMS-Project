package com.lms.model.dao;

import com.lms.common.QueryUtil;
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

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                return rset.getInt(1) > 0;
            }
        }

        return false;
    }


}