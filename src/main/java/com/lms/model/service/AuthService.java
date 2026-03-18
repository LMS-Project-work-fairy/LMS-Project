package com.lms.model.service;

import com.lms.common.JDBCTemplate;
import com.lms.model.dao.StudentDAO;
import com.lms.model.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthService {

    public int registerStudent(StudentDTO student) {

        Connection connection = JDBCTemplate.getConnection();
        StudentDAO studentDAO = new StudentDAO(connection);

        try {
            if (studentDAO.existsByStudentId(student.getStudentId())) {
                return 0;
            }

            int result = studentDAO.save(student);

            if (result > 0) {
                connection.commit();
            } else {
                connection.rollback();
            }

            return result;

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("롤백 중 오류 발생", ex);
            }
            throw new RuntimeException("학생 회원가입 중 오류 발생", e);

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Connection 종료 중 오류 발생", e);
            }
        }
    }
}