package com.lms.model.service;

import com.lms.controller.StudentController;
import com.lms.model.dao.StudentDAO;
import com.lms.model.dto.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentService {



    public class StudentView {
        private final StudentController controller;
        private final LoginUserDTO loginUser; // 로그인 정보 저장용
        private final Scanner sc = new Scanner(System.in);

        // 생성자에서 로그인 정보를 받도록 수정
        public StudentView(StudentController controller, LoginUserDTO loginUser) {
            this.controller = controller;
            this.loginUser = loginUser;
        }

        private void subjectView() {
            while(true) {
                // 이제 고정된 학번이 아니라 로그인한 유저의 ID를 사용합니다!
                String studentId = loginUser.getUserId();
                List<EnrollmentDTO> myEnrollList = controller.enrollView(studentId);
                // ... 이하 동일
            }
        }
    }

    private final StudentDAO studentDAO;

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
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

    public List<CourseDTO> taskView(EnrollmentDTO enroll) {
        try {
            return studentDAO.taskView(enroll);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EnrollmentDTO> scoreView(EnrollmentDTO enroll) {
        try {
            return studentDAO.scoreView(enroll);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public CourseDTO timeEqual(String applyClassNo, String studentId) {
        try {
            return studentDAO.timeEqual(applyClassNo, studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EnrollmentDTO> totalScoreView(String studentId) {
        try {
            return studentDAO.totalScoreView(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StudentDTO> messageMember() {
        try {
            return studentDAO.messageMember();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StudentDTO myInfoView(String studentId) {
        try {
            return studentDAO.myInfoView(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int editMyInfo(StudentDTO myInfo) {
        try {
            return studentDAO.editMyInfo(myInfo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    public int messageSend(UserDTO msg) {
//        try {
//            return studentDAO.sendMessage(msg);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public List<UserDTO> messageCheck(String myId) {
//        try {
//            return studentDAO.messageCheck(myId);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }


