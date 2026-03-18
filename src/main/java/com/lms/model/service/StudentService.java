package com.lms.model.service;

import com.lms.model.dao.StudentDAO;
import com.lms.model.dto.CourseDTO;
import com.lms.model.dto.EnrollmentDTO;
import com.lms.model.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO;
    private final Connection connection;

    public StudentService(Connection connection) {
        this.studentDAO = new StudentDAO(connection);
        this.connection = connection;
    }

    public List<CourseDTO> findClass() {
        try {
            return studentDAO.findClass();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addClass(EnrollmentDTO enroll) {
        try {
            return studentDAO.addClass(enroll); //enroll에 담긴 정보를 바탕으로 DAO에 전달
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // MySQL의 중복 키 에러 번호
                System.out.println("이미 수강 신청한 과목입니다!"); //이미 신청하거나 없는 과목 신청 시 종료되지 않고 계속 돌게함.
                return -1;
            } else if (e.getErrorCode() == 1452) { // 외래키 위반 (없는 과목 번호)
                System.out.println("존재하지 않는 과목 번호입니다. 강의 목록을 확인해주세요.");
                return -2;
            } else {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public List<EnrollmentDTO> enrollView(String studentId) {
        try {
            return studentDAO.enrollView(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int deleteClass(EnrollmentDTO enroll) {
        try {
            return studentDAO.deleteClass(enroll);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
