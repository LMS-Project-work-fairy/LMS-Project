package com.lms.view;

import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.ProfessorDTO;
import com.lms.model.dto.StudentDTO;

import java.util.Scanner;

public class MainView {

    private final Scanner sc = new Scanner(System.in);

    public int displayMainMenu() {
        System.out.println("\n========== LMS 메인 ==========");
        System.out.println("1. 로그인");
        System.out.println("2. 학생 회원가입");
        System.out.println("3. 교수 회원가입");
        System.out.println("0. 종료");
        System.out.print("메뉴 선택: ");
        return Integer.parseInt(sc.nextLine());
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public LoginRequestDTO inputLoginInfo() {

        while (true) {
            System.out.println("\n========== 로그인 ==========");
            System.out.println("1. 학생");
            System.out.println("2. 교수");
            System.out.println("0. 뒤로가기");
            System.out.print("로그인 유형 선택: ");

            int roleMenu;

            //로그인 옵션 숫자로 입력받게 하기 위한 예외구문 작성
            try {
                roleMenu = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                displayMessage("로그인 유형은 숫자로 입력해주세요.");
                continue;
            }

            if(roleMenu == 0) {
                return new LoginRequestDTO("BACK","","");
            }

            //코드 가독성을 위해서 if-else 구문으로 수정
            String role;
            if (roleMenu == 1) {
                role = "STUDENT";
            } else if (roleMenu == 2) {
                role = "PROFESSOR";
            } else {
                displayMessage("잘못된 로그인 유형입니다.");
                continue;
            }

            System.out.print("아이디 입력: ");
            String userId = sc.nextLine();

            System.out.print("비밀번호 입력: ");
            String password = sc.nextLine();

            return new LoginRequestDTO(role, userId, password);
        }
    }

//    public StudentDTO inputStudentInfo() {
//        System.out.println("\n========== 학생 회원가입 ==========");
//        StudentDTO student = new StudentDTO();
//
//        System.out.print("학번: ");
//        student.setStudentId(Long.parseLong(sc.nextLine()));
//
//        System.out.print("이름: ");
//        student.setStudentName(sc.nextLine());
//
//        System.out.print("주민등록번호: ");
//        student.setStudentNo(sc.nextLine());
//
//        System.out.print("주소: ");
//        student.setStudentAddress(sc.nextLine());
//
//        System.out.print("이메일: ");
//        student.setStudentEmail(sc.nextLine());
//
//        System.out.print("전화번호: ");
//        student.setStudentPhone(sc.nextLine());
//
//        System.out.print("비밀번호: ");
//        student.setStudentPw(sc.nextLine());
//
//
//        return student;
//    }

    public ProfessorDTO inputProfessorInfo() {
        System.out.println("\n========== 교수 회원가입 ==========");
        ProfessorDTO professor = new ProfessorDTO();

        System.out.print("교수번호: ");
        professor.setProfessorId(sc.nextLine());

        System.out.print("이름: ");
        professor.setProfessorName(sc.nextLine());

        System.out.print("주민등록번호: ");
        professor.setProfessorNo(sc.nextLine());

        System.out.print("주소: ");
        professor.setProfessorAddress(sc.nextLine());

        System.out.print("이메일: ");
        professor.setProfessorEmail(sc.nextLine());

        System.out.print("전화번호: ");
        professor.setProfessorPhone(sc.nextLine());

        System.out.print("비밀번호: ");
        professor.setProfessorPw(sc.nextLine());

        return professor;
    }
}