package com.lms.controller;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import com.lms.model.dto.StudentDTO;
import com.lms.model.service.AuthService;
import com.lms.view.MainView;
import com.lms.model.dto.ProfessorDTO;
import com.lms.model.service.AuthService;

public class AuthController {

    private final MainView mainView;
    private final AuthService authService;


//    public boolean registerProfessor(ProfessorDTO professor) {
//        return authService.insertProfessor(professor);
//    }


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


    public void registerProfessor() {
        mainView.displayMessage("교수 회원가입 기능은 현재 준비 중입니다.");
    }

    public void startAuthProcess(int menu) {
        if (menu == 3) {
            handleProfessorRegistration();
        } else if (menu == 2) {
            System.out.println("학생 가입 기능은 준비 중입니다.");
        }

    }

    public void handleProfessorRegistration() {
        try {
            ProfessorDTO newProfessor = mainView.inputProfessorInfo();
            boolean success = authService.insertProfessor(newProfessor);

            if (success) {
                mainView.displayMessage("✅ 교수 등록 성공");
            }
        } catch (RuntimeException e) {
            mainView.displayMessage("🚨 교수 등록 실패/n 영문, 숫자, 특수 기호를 포함해 8자 이상 작성해주세요. " + e.getMessage());
        }

    }

}

