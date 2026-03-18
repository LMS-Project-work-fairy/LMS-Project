package com.lms.model.service;

import com.lms.common.JDBCTemplate;
import com.lms.model.dao.ProfessorDAO;
import com.lms.model.dao.StudentDAO;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import com.lms.model.dto.ProfessorDTO;
import com.lms.model.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthService {

    private final ProfessorDAO professorDAO;
    private final StudentDAO studentDAO;

    public AuthService(StudentDAO studentDAO, ProfessorDAO professorDAO) {
        this.studentDAO = studentDAO;
        this.professorDAO = professorDAO;
    }

    public boolean insertProfessor(ProfessorDTO newProfessor) {
        String pw = newProfessor.getProfessorPw();

        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$";
        if (!pw.matches(regex)) {
            throw new RuntimeException("비밀번호는 영문, 숫자, 특수문자를 포함해 8자 이상이어야 합니다.");
        }

        try {
            String result = professorDAO.save(newProfessor);
            return result != null && !result.isEmpty();
        } catch (SQLException e) {
            throw new RuntimeException("교수 데이터 입력 중 오류 발생 🚨", e);
        }
    }

    public LoginUserDTO login(LoginRequestDTO request) {

        if (request == null) {
            return null;
        }

        Connection con = JDBCTemplate.getConnection();
        StudentDAO studentDAO = new StudentDAO(con);

        try {
            if ("STUDENT".equalsIgnoreCase(request.getRole())) {
                return studentDAO.loginStudent(request);
            } else if ("PROFESSOR".equalsIgnoreCase(request.getRole())) {
                return professorDAO.loginProfessor(con, request);
            }
            return null;
        } finally {
            JDBCTemplate.close(con);
        }
    }

    public int registerStudent(StudentDTO student) {

        String pw = student.getStudentPw();
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$";

        if (!pw.matches(regex)) {
            throw new RuntimeException("비밀번호는 영문, 숫자, 특수문자를 포함해 8자 이상이어야 합니다.");
        }

        Connection connection = JDBCTemplate.getConnection();

        try {
            StudentDAO studentDAO = new StudentDAO(connection);

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