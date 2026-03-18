//package com.lms.application;
//
//import com.lms.common.JDBCTemplate;
//import com.lms.controller.AuthController;
//import com.lms.view.MainView;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class Application {
//
//    public static void main(String[] args) {
//
//        try (Connection testCon = JDBCTemplate.getConnection()) {
//            if (testCon != null) {
//                System.out.println("✅ 데이터베이스 연결 성공! 시스템을 시작합니다.");
//            }
//        } catch (SQLException e) {
//                System.err.println("🚨 데이터베이스 연결 실패...");
//                return;
//        }
//        MainView mainView = new MainView();
//        AuthController authController = new AuthController();
//
//        boolean running = true;
//
//        while (running) {
//            int menu = mainView.displayMainMenu();
//
//            switch (menu) {
//                case 1:
//                    System.out.println("테스트: 로그인 없이 학생 메뉴 진입");
//                    authController.login();
//                    break;
//                case 2:
//                    authController.registerStudent();
//                    break;
//                case 3:
//                    authController.registerProfessor();
//                    break;
//                case 0:
//                    mainView.displayMessage("프로그램을 종료합니다.");
//                    running = false;
//                    break;
//                default:
//                    mainView.displayMessage("잘못된 메뉴 번호입니다.");
//            }
//        }
//    }
//}