package com.lms.model.dao;

import com.lms.common.QueryUtil;
import com.lms.model.dto.CourseDTO;
import com.lms.model.dto.EnrollmentDTO;
import com.lms.model.dto.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private final Connection connection;

    public StudentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<CourseDTO> findClass() throws SQLException {
        List<CourseDTO> courseList = new ArrayList<>();
        String query = QueryUtil.getQuery("course.findClass");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                CourseDTO course = new CourseDTO();
                        course.setClassNo(rset.getString("class_no"));
                course.setClassName(rset.getString("class_name"));
                course.setClassTime(rset.getString("class_time"));
                course.setProfessorName(rset.getString("professor_name"));
                courseList.add(course);
            }
            return courseList;
        }
    }

    public int addClass(EnrollmentDTO enroll) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.addClass");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, enroll.getStudentId());
            pstmt.setString(2, enroll.getClassNo());

            return pstmt.executeUpdate();
            //insert, update, delete는 db의 상태를 바꾸기 때문에 executeUpdate 사용
            //insert에 성공하면 1이 반환됨(1행이 추가되므로)
        }
    }

    public List<EnrollmentDTO> enrollView(String studentId) throws SQLException {
        List<EnrollmentDTO> enrollList = new ArrayList<>();
        String query = QueryUtil.getQuery("enrollment.myEnrollView");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                EnrollmentDTO enroll = new EnrollmentDTO();

                enroll.setStudentId(rset.getString("student_id"));
                enroll.setStudentName(rset.getString("student_name")); // DTO에 이 필드가 있어야 함!
                enroll.setClassName(rset.getString("class_name"));   // DTO에 이 필드가 있어야 함!
                enroll.setClassNo(rset.getString("class_no"));
                enroll.setProfessorName(rset.getString("professor_name"));
                enroll.setEnrollDate(rset.getString("enroll_date"));

                enrollList.add(enroll);
            }
            return enrollList;
        }

    }

    public int deleteClass(EnrollmentDTO enroll) throws SQLException {
        String query = QueryUtil.getQuery("enrollment.deleteClass");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, enroll.getStudentId());
            pstmt.setString(2, enroll.getClassNo());

            return pstmt.executeUpdate();
        }
    }
}
