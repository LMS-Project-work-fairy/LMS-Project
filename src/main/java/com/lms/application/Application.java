package com.lms.application;

import com.lms.common.JDBCTemplate;
import com.lms.controller.AuthController;
import com.lms.model.dao.ProfessorDAO;
import com.lms.model.dto.ProfessorDTO;
import com.lms.model.service.AuthService;
import com.lms.view.MainView;

import java.sql.Connection;

public class Application {

    public static void main(String[] args) {

        Connection con = JDBCTemplate.getConnection();
        ProfessorDAO professorDAO = new ProfessorDAO(con);
        AuthService authService = new AuthService(professorDAO);
        AuthController authController = new AuthController(authService);
        MainView mainView = new MainView();
        AuthController authController = new AuthController(mainView);

        boolean running = true;

        while (running) {
            int menu = mainView.displayMainMenu();

            switch (menu) {
                case 1:
                    authController.login();
                    break;
                case 2:
                    authController.registerStudent();
                    break;
                case 3:
                    authController.registerProfessor();
                    break;
                case 0:
                    mainView.displayMessage("프로그램을 종료합니다.");
                    running = false;
                    break;
                default:
                    mainView.displayMessage("잘못된 메뉴 번호입니다.");
            }
        }
    }

int menu = MainView.displayMainMenu();
if (menu == 3) {
    ProfessorDTO newProfessor = MainView.inputProfessorInfo();
Boolean success = AuthController.registerProfessor(newProfessor);

    if (success) {
        MainView.displayMessage("✅교수 등록 성공");
        } else {
        MainView.displayMessage("🚨교수 등록 실패");
        }
    }
    
}
