package com.lms.model.dao;

import com.lms.common.JDBCTemplate;
import com.lms.common.QueryUtil;
import com.lms.controller.StudentController;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import com.lms.model.dto.StudentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import java.util.Properties;


public class StudentDAO {

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
                course.setClassType(rset.getString("class_type"));
                course.setClassRoom(rset.getString("class_room"));
                course.setClassPoint(rset.getString("class_point"));
                course.setProfessorId(rset.getString("professor_name"));
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
                enroll.setClassNo(rset.getString("class_no"));

                String detailInfo = (("학생명: " + rset.getString("student_name") +
                        ("\n강의명: " + rset.getString("class_name") + " (" + rset.getString("class_type") + ")")+
                        ("\n강의실: " + rset.getString("class_room") + " (" + rset.getString("class_time") + ")") +
                        ("\n교수명: " + rset.getString("professor_name")) +
                        ("\n신청일: " + rset.getString("enroll_date")) +
                        ("\n수강신청 인원: " + rset.getString("current_count"))));

                enroll.setEnrollDate(detailInfo);

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

    public List<CourseDTO> taskView(EnrollmentDTO enroll) throws SQLException {
        String query = QueryUtil.getQuery("course.taskCheck");
        List<CourseDTO> taskList = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, enroll.getStudentId());
            pstmt.setString(2, enroll.getClassNo());

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                CourseDTO course = new CourseDTO();

                course.setClassName(rset.getString("class_name"));
                String taskContent = rset.getString("class_task"); // DTO에 이 필드가 있어야 함!
                course.setClassNo(enroll.getClassNo()); // 이미 알고 있는 번호 세팅

                if (taskContent == null || taskContent.isEmpty()) {
                    course.setClassTask("현재 등록된 과제가 없습니다.");
                } else {
                    course.setClassTask(taskContent);
                }
                taskList.add(course);
            }
        }
        return taskList;
    }

    public List<EnrollmentDTO> scoreView(EnrollmentDTO enroll) throws SQLException {
        String query = QueryUtil.getQuery("enroll.scoreCheck");
        List<EnrollmentDTO> scoreList = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, enroll.getStudentId());
            pstmt.setString(2, enroll.getClassNo());

            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                EnrollmentDTO score = new EnrollmentDTO();

                enroll.setScore(rset.getString("score"));
                enroll.setClassNo(rset.getString("class_name"));

                scoreList.add(enroll);
            }

        }
        return scoreList;
    }


    public CourseDTO timeEqual(String applyClassNo, String userId) throws SQLException {
        String query = QueryUtil.getQuery("course.timeCheck");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, userId); //로그인된 학번
            pstmt.setString(2, applyClassNo); //신청할 강의번호(요일 추출용)
            pstmt.setString(3, applyClassNo); //신청할 강의번호(종료시간 비교용)
            pstmt.setString(4, applyClassNo); //신청할 강의번호(시작시간 비교용)

            ResultSet rset = pstmt.executeQuery();
            if(rset.next()) { //겹치는 강의가 있다면
                CourseDTO timeEqual = new CourseDTO();
                timeEqual.setClassName(rset.getString("class_name"));
                timeEqual.setClassTime(rset.getString("class_time"));
                return timeEqual;
            } //결과가 존재(true)하면 시간이 겹친다.
        }
        return null;
    }

    public List<EnrollmentDTO> totalScoreView(String studentId) throws SQLException {
        List<EnrollmentDTO> list = new ArrayList<>();
        String query = QueryUtil.getQuery("enroll.totalScoreView");
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, studentId);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                EnrollmentDTO enroll = new EnrollmentDTO();
                enroll.setClassNo(rset.getString("class_name"));
                String scorePoint = ("학점: " + rset.getString("score") + " (" + rset.getString("class_point") + ")");
                enroll.setScore(scorePoint);
                list.add(enroll);
            }
        }
        return list;
    }
}