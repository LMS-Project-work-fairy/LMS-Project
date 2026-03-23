package com.lms.application;

import com.lms.controller.AuthController;
import com.lms.view.MainView;

public class Application {

    public static void main(String[] args) {

        MainView mainView = new MainView();
        AuthController authController = new AuthController();

        boolean running = true;

        while (running) {
            int menu = mainView.displayMainMenu();

            switch (menu) {
//                case 1:
//                    authController.login();
//                    break;
                case 2:
                    authController.registerStudent();
                    break;
//                case 3:
//                    authController.registerProfessor();
//                    break;
                case 0:
                    mainView.displayMessage("프로그램을 종료합니다.");
                    running = false;
                    break;
                default:
                    mainView.displayMessage("잘못된 메뉴 번호입니다.");
            }
        }
    }
}