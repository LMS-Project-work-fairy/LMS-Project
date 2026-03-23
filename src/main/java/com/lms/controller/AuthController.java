package com.lms.controller;

import com.lms.common.JDBCTemplate;
import com.lms.model.dao.StudentDAO;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import com.lms.model.dto.StudentDTO;
import com.lms.model.dto.ProfessorDTO;
import com.lms.model.service.AuthService;
import com.lms.model.service.StudentService;
import com.lms.view.MainView;
import com.lms.view.StudentView;

import java.sql.Connection;

import java.sql.SQLException;
import java.sql.SQLOutput;

public class AuthController {

    private final MainView mainView;
    private final AuthService authService;


    public AuthController(MainView mainView, AuthService authService) {
        this.mainView = mainView;
        this.authService = authService;
    }

    // 로그인 기능 로직

    public void login() {
        while (true) {
            LoginRequestDTO request = mainView.inputLoginInfo();

            if (request == null) {
                mainView.displayMessage("로그인 정보 입력이 올바르지 않습니다.");
                return;
            }
            if ("BACK".equals(request.getRole())) {
                mainView.displayMessage("메인 화면으로 돌아갑니다.");
                return;
            }

            LoginUserDTO loginUser = authService.login(request);

            if (loginUser == null) {
                mainView.displayMessage("로그인 실패: 아이디 또는 비밀번호를 확인해주세요.");
                continue;
            }

            if ("STUDENT".equals(loginUser.getRole())) {
                System.out.println("학생 계정으로 로그인 성공했습니다.");
                // 나중에 학생 기능 연결
                // new StudentController().openStudentMain();

                Connection con = JDBCTemplate.getConnection();
                StudentService studentService = new StudentService(new StudentDAO(con));
                StudentController studentController = new StudentController(studentService);
                StudentView studentView = new StudentView(studentController, loginUser);
                studentView.displayStudentMenu();
                break;

                //여기에 학생 기능

            } else if ("PROFESSOR".equals(loginUser.getRole())) {
                System.out.println("교수 계정으로 로그인 성공했습니다.");
                new ProfessorController().startProfessorMenu(loginUser.getUserId());
                break;
            } else {
                mainView.displayMessage("알 수 없는 사용자 권한입니다.");
            }
        }
    }

        public void registerStudent () {
            try {
                StudentDTO newStudent = mainView.inputStudentInfo();

                int result = authService.registerStudent(newStudent);

                if (result > 0) {
                    mainView.displayMessage("회원가입에 성공하였습니다!");
                } else {
                    mainView.displayMessage("회원가입에 실패하였습니다!");
                }
            } catch (RuntimeException e) {
                mainView.displayMessage("학생 회원가입 중 오류가 발생했습니다: " + e.getMessage());
            }
        }


        public void registerProfessor () {

            String secretKey = "LMS-ADMIN-777";
            String inputId = null;

            while (true) {
                System.out.println("\n 🔐교수 가입 인증 코드를 입력하세요 (취소:0)");
                System.out.println("\n 인증코드 \n");
                String inputKey = new java.util.Scanner(System.in).nextLine().trim();

                if ("0".equalsIgnoreCase(inputKey)) {
                    System.out.println("🚫 가입 절차를 중단합니다.");
                    return;
                }

                if (secretKey.equalsIgnoreCase(inputKey)) {
                    System.out.println("✅ 인증 성공! 가입 창으로 이동합니다.");
                    break;
                } else {
                    System.out.println("🚨 인증 코드가 일치하지 않습니다. 다시 입력해주세요.");
                }
            }



            while(true) {
                inputId = mainView.inputProfessorId();

                if (inputId == null) {
                    System.out.println("🚫 아이디 입력을 취소하셨습니다. 메인으로 돌아갑니다.");
                    return;
                }

                if (authService.isDuplicateId(inputId)) {
                    mainView.displayMessage("🚨 [중복] 이미 가입된 교수 번호입니다. 가입이 불가능합니다.");
                    continue;
                }
                break;
            }

            ProfessorDTO professorDTO = mainView.inputRestOfProfessorInfo(inputId, authService);


            if (professorDTO == null) {
                System.out.println("🚫입력을 취소하셨습니다.");
                return;
            }

//            if (authService.isDuplicateId(professorDTO.getProfessorId())) {
//                mainView.displayMessage("🚨 [중복 오류] 입력하신 '" + professorDTO.getProfessorId() + "'은(는) 이미 사용 중입니다.");
//                return;
//            }
//
//            if (authService.isDuplicateNo(professorDTO.getProfessorNo())) {
//                mainView.displayMessage("🚨 [중복 오류] 입력하신 '" + professorDTO.getProfessorNo() + "'은(는) 이미 사용 중입니다.");
//                return;
//            }
//
//            if (authService.isDuplicatePhone(professorDTO.getProfessorPhone())) {
//                mainView.displayMessage("🚨 [중복 오류] 입력하신 '" + professorDTO.getProfessorPhone() + "'은(는) 이미 사용 중입니다.");
//                return;
//            }
//
//            if (authService.isDuplicateEmail(professorDTO.getProfessorEmail())) {
//                mainView.displayMessage("🚨 [중복 오류] 입력하신 '" + professorDTO.getProfessorEmail() + "'은(는) 이미 사용 중입니다.");
//                return;
//            }

            try {
                if (authService.insertProfessor(professorDTO)) {
                    mainView.displayMessage("🌟 교수 회원가입 성공");
                }
            } catch (RuntimeException e) {
                mainView.displayMessage("🚨 가입 실패: " + e.getMessage());
            } catch (SQLException e) {
                mainView.displayMessage("🚨 DB 오류 발생: " + e.getMessage());
            }
        }
    }

