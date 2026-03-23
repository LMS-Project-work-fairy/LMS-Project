package com.lms.view;

import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.ProfessorDTO;
import com.lms.model.dto.StudentDTO;
import com.lms.model.service.AuthService;
import java.util.function.Predicate;

import java.util.Scanner;

public class MainView {

    private final Scanner sc = new Scanner(System.in);
    private AuthService authService;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public int displayMainMenu() {
        while (true) {
            System.out.println("\n========== LMS 메인 ==========");
            System.out.println("1. 로그인");
            System.out.println("2. 학생 회원가입");
            System.out.println("3. 교수 회원가입");
            System.out.println("0. 종료");
            System.out.print("메뉴 선택: ");

            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ 메뉴는 숫자로 입력해주세요.\n");
            }
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public LoginRequestDTO inputLoginInfo() {
        System.out.println("\n========== 로그인 ==========");
        System.out.println("1. 학생");
        System.out.println("2. 교수");
        System.out.print("로그인 유형 선택: ");

        int roleMenu;

        try {
            roleMenu = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            displayMessage("로그인 유형은 숫자로 입력해주세요.");
            return null;
        }

        String role;
        if (roleMenu == 1) {
            role = "STUDENT";
        } else if (roleMenu == 2) {
            role = "PROFESSOR";
        } else {
            displayMessage("잘못된 로그인 유형입니다.");
            return null;
        }

        System.out.print("아이디 입력: ");
        String userId = sc.nextLine();

        System.out.print("비밀번호 입력: ");
        String password = sc.nextLine();

        return new LoginRequestDTO(role, userId, password);
    }

    public StudentDTO inputStudentInfo(Predicate<String> isDuplicateStudentId) {
        System.out.println("========== 학생 회원가입 ==========\n");

        StudentDTO student = new StudentDTO();
        int step = 0;

        while (true) {
            switch (step) {

                case 0: { // 학번
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 메인으로 돌아가기: 0 *");
                    System.out.print("학번 (* 숫자 8자리를 입력해주세요 *) : ");
                    String input = sc.nextLine();

                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (!input.matches("\\d{8}")) {
                        System.out.println("❌ 학번은 숫자 8자리입니다. 다시 입력해주세요.\n");
                        break;
                    }

                    if (isDuplicateStudentId.test(input)) {
                        System.out.println("❌ 이미 존재하는 아이디입니다. 다시 입력해주세요.\n");
                        break;
                    }

                    student.setStudentId(input);
                    step++;
                }

                case 1: { // 이름
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("이름 : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (input.matches("^[가-힣a-zA-Z]+$")) {
                        student.setStudentName(input);
                        step++;
                    } else {
                        System.out.println("❌ 이름은 한글 또는 영문만 입력 가능합니다.\n");
                    }
                    break;
                }

                case 2: { // 주민등록번호
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("주민등록번호 (* 123456-1234567 형식으로 입력해주세요 *) : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (!input.matches("\\d{6}-\\d{7}")) {
                        System.out.println("❌ 주민등록번호 형식이 올바르지 않습니다.\n");
                        break;
                    }

                    if (authService.existsStudentNo(input)) {
                        System.out.println("❌ 이미 등록된 주민등록번호입니다.\n");
                        break;
                    }

                    student.setStudentNo(input);
                    step++;
                }

                case 3: { // 주소
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("주소 : ");
                    System.out.println("* 정확한 주소를 모르시는 경우, 시/군/구 까지만 입력해주세요 *");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (input.matches(".*[가-힣a-zA-Z]+.*")) {
                        student.setStudentAddress(input);
                        step++;
                    } else {
                        System.out.println("❌ 형식에 맞춰 주소를 입력해주세요.\n");
                    }
                    break;
                }

                case 4: { // 이메일
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("이메일 (* 이메일 형식을 준수해 주세요 *) : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (!input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                        System.out.println("❌ 올바른 이메일 형식이 아닙니다.\n");
                        break;
                    }

                    if (isDuplicateStudentId != null && authService.existsStudentEmail(input)) {
                        System.out.println("❌ 이미 사용 중인 이메일입니다.\n");
                        break;
                    }

                    student.setStudentEmail(input);
                    step++;
                }

                case 5: { // 전화번호
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("전화번호 (* 번호 중간에 '-'도 입력해주세요 *) : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (input.matches("^010-\\d{4}-\\d{4}$")) {
                        student.setStudentPhone(input);
                        step++;
                    } else {
                        System.out.println("❌ 전화번호는 010-1234-5678 형식으로 입력해주세요.\n");
                    }
                    break;
                }

                case 6: { // 비밀번호
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("비밀번호 (* 비밀번호는 영문, 숫자, 특수문자를 포함해 8자 이상이어야 합니다 *) : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (input.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$")) {
                        student.setStudentPw(input);
                        step++;
                    } else {
                        System.out.println("❌ 비밀번호 형식이 올바르지 않습니다.\n");
                    }
                    break;
                }

                case 7: { // 비밀번호 확인
                    System.out.println("[학생 회원가입]");
                    System.out.println("* 이전 단계: 1 | 메인으로: 0 *");
                    System.out.print("확인을 위해 비밀번호를 다시 입력해주세요 : ");
                    String input = sc.nextLine();

                    if (input.equals("1")) {
                        step--;
                        continue;
                    }
                    if (input.equals("0")) {
                        System.out.println("메인 화면으로 돌아갑니다.\n");
                        return null;
                    }

                    if (input.equals(student.getStudentPw())) {
                        return student;
                    } else {
                        System.out.println("❌ 비밀번호가 일치하지 않습니다.\n");
                    }
                    break;
                }
            }
        }
    }

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