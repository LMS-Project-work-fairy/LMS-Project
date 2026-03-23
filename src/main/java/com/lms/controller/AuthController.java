package com.lms.controller;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import com.lms.model.service.AuthService;
import com.lms.view.MainView;
import com.lms.model.dto.ProfessorDTO;
import com.lms.model.service.AuthService;

import java.sql.SQLException;
import java.sql.SQLOutput;

public class AuthController {

    private final MainView mainView;
    private final AuthService authService;


    public AuthController(MainView mainView, AuthService authService) {
        this.mainView = mainView;
        this.authService = authService;
    }

  
    //로그인 기능 로직
    public void login() {

        LoginRequestDTO request = mainView.inputLoginInfo();

        if (request == null) {
            mainView.displayMessage("로그인 정보 입력이 올바르지 않습니다.");
            return;
        }

        LoginUserDTO loginUser = authService.login(request);

        if (loginUser == null) {
            mainView.displayMessage("로그인 실패: 아이디 또는 비밀번호를 확인해주세요.");
            return;
        }

        if ("STUDENT".equals(loginUser.getRole())) {
            System.out.println("학생 계정으로 로그인 성공했습니다.");
            // 나중에 학생 기능 연결
            // new StudentController().openStudentMain();
        } else if ("PROFESSOR".equals(loginUser.getRole())) {
            System.out.println("교수 계정으로 로그인 성공했습니다.");
            // 나중에 교수 기능 연결
            // new ProfessorController().openProfessorMain();
        }
    }

    public void registerStudent() {
//        mainView.displayMessage("학생 회원가입 기능은 현재 준비 중입니다.");
    }

    public void registerProfessor() {

        String secretKey = "LMS-ADMIN-777";
        while(true) {
            System.out.println("\n 🔐교수 가입 인증 코드를 입력하세요 (취소:q)");
            System.out.println("\n 인증코드 \n");
            String inputKey = new java.util.Scanner(System.in).nextLine().trim();

            if ("q".equalsIgnoreCase(inputKey)) {
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

        ProfessorDTO professorDTO = mainView.inputProfessorInfo();

        if (professorDTO == null) {
            System.out.println("🚫입력을 취소하셨습니다.");
            return;
        }

        try {
            boolean success = false;
            try {
                success = authService.insertProfessor(professorDTO);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if (success) {
                mainView.displayMessage("🌟 교수 회원가입 성공");
            }
        } catch (RuntimeException e) {
            mainView.displayMessage("🚨 교수 회원가입 실패" + e.getMessage());
        }

    }

}

