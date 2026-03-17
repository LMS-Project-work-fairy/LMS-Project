package com.lms.controller;

import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import com.lms.model.service.AuthService;
import com.lms.view.MainView;

public class AuthController {

    private final MainView mainView;
    private final AuthService authService;

    public AuthController(MainView mainView) {
        this.mainView = mainView;
        this.authService = new AuthService();
    }

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
        mainView.displayMessage("학생 회원가입 기능은 현재 준비 중입니다.");
    }

    public void registerProfessor() {
        mainView.displayMessage("교수 회원가입 기능은 현재 준비 중입니다.");
    }
}