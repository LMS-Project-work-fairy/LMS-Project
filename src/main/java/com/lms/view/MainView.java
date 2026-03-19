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
        System.out.println("\n========== 로그인 ==========");
        System.out.println("1. 학생");
        System.out.println("2. 교수");
        System.out.print("로그인 유형 선택: ");

        int roleMenu;

        //로그인 옵션 숫자로 입력받게 하기 위한 예외구문 작성
        try{
            roleMenu = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            displayMessage("로그인 유형은 숫자로 입력해주세요.");
            return null;
        }

        //코드 가독성을 위해서 if-else 구문으로 수정
        String role;
        if (roleMenu == 1){
            role = "STUDENT";
        } else if (roleMenu == 2){
            role = "PROFESSOR";
        } else{
            displayMessage("잘못된 로그인 유형입니다.");
            return null;
        }

        System.out.print("아이디 입력: ");
        String userId = sc.nextLine();

        System.out.println("비밀번호 입력: ");
        String password = sc.nextLine();

        return new LoginRequestDTO(role,userId,password);
    }

    public ProfessorDTO inputProfessorInfo() {
        System.out.println("\n========== 교수 회원가입 ========== \n(뒤로가기는 'b', 취소는 'q' 입니다.)");
        ProfessorDTO professorDTO = new ProfessorDTO();

        int step = 1;

        while (step <= 7) {
            switch (step) {
                case 1 :
                    System.out.print("교수 번호(P0000) \n");
                    String id = sc.nextLine().trim();
                    if ("q".equalsIgnoreCase(id)) {
                        return null;
                    }
                    if (id.matches("^P\\d{4}$")) {
                        professorDTO.setProfessorId(id);
                        System.out.println("✅ 교수 번호가 일치합니다.");
                        step++;
                    } else {
                        System.out.println("🚨 [입력 오류] 교수 번호는 'P'로 시작하는 숫자 4자리여야 합니다.");
                    }
                    break;

                case 2 :
                    System.out.print("이름을 입력해주세요 \n");
                    String name = sc.nextLine().trim();
                    if ("q".equalsIgnoreCase(name)) return null;
                    if ("b".equalsIgnoreCase(name)) {
                        step--;
                        continue;
                    }
                    professorDTO.setProfessorName(name);
                    step++;
                    break;

                case 3 :
                    System.out.print("주민등록번호(숫자만 입력)를 입력해주세요 \n ");
                    String inputNo = sc.nextLine().trim();

                    if ("q".equalsIgnoreCase(inputNo)) return null;
                    if ("b".equalsIgnoreCase(inputNo)) {
                        step--;
                        continue;
                    }

                    if (inputNo.matches("^\\d{13}$")) {
                        String formattedNo = inputNo.substring(0,6) + "-" + inputNo.substring(6);

                        professorDTO.setProfessorNo(formattedNo);
                        System.out.println("➡️ 입력 확인: " + formattedNo);
                        System.out.print("이 정보가 맞습니까? (y/n): ");
                        if (sc.nextLine().equalsIgnoreCase("y")) {
                            System.out.println("✅ 주민번호 형식이 일치합니다.");
                            step++;
                        } else {
                            System.out.println("🔄 다시 입력해 주세요.");
                        }
                    } else {
                        System.out.println("🚨 [입력 오류] 숫자 13자리를 정확히 입력해주세요.");
                    }
                    break;

                case 4:
                    System.out.print("주소를 다음과 같은 형식으로 입력해주세요" +
                            "\n ----------------------------- "+
                            "\n|   시·도/시·군·구 + 도로명 주소  | " +
                            "\n|  시·도/시·군·구/읍·면·동 + 지번 | " +
                            "\n|    시·도/시·군·구 + 건물명     | " +
                            "\n ------------------------------ \n"
                    );
                    String adress = sc.nextLine().trim();
                    if ("q".equalsIgnoreCase(adress)) return null;
                    if ("b".equalsIgnoreCase(adress)) {
                        step--;
                        continue;
                    }
                    professorDTO.setProfessorAddress(adress);
                    step++;
                    break;

                case 5:
                    System.out.print("이메일 주소(example@lms.com)를 입력해주세요 \n ");
                    String email = sc.nextLine().trim();
                    String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                    if ("q".equalsIgnoreCase(email)) return null;
                    if ("b".equalsIgnoreCase(email)) {
                        step--;
                        continue;
                    }

                    if (email.matches(emailRegex)) {
                        professorDTO.setProfessorEmail(email);
                        System.out.println("✅ 이메일 형식이 일치합니다.");
                        step++;
                    } else {
                        System.out.println("🚨 이메일 형식이 올바르지 않습니다. '@'를 포함한 정확한 주소를 입력해주세요.");
                    }
                    break;

                case 6:
                    System.out.print("전화번호(숫자만 입력)를 입력해주세요 \n");
                    String inputPhone = sc.nextLine().trim().replace("-", "");

                    if ("q".equalsIgnoreCase(inputPhone)) return null;
                    if ("b".equalsIgnoreCase(inputPhone)) {
                        step--;
                        continue;
                    }

                    if (inputPhone.matches("^010\\d{7,8}$")) {
                        String formattedPhone;
                        if (inputPhone.length() == 11) {
                            formattedPhone = inputPhone.substring(0, 3) + "-" + inputPhone.substring(3, 7) + "-" + inputPhone.substring(7);
                        } else {
                            formattedPhone = inputPhone.substring(0, 3) + "-" + inputPhone.substring(3, 6) + "-" + inputPhone.substring(6);
                        }
                        System.out.println("➡️ 변환된 형식: " + formattedPhone);
                        professorDTO.setProfessorPhone(formattedPhone);
                        System.out.print("이 정보가 맞습니까? (y/n): ");
                        if (sc.nextLine().equalsIgnoreCase("y")) {
                            System.out.println("✅ 전화번호 형식이 일치합니다.");
                            step++;
                        } else {
                            System.out.println("🔄 다시 입력해 주세요.");
                        }
                    } else {
                        System.out.println("🚨 [입력 오류] 올바른 전화번호 형식이 아닙니다.");
                    }
                    break;

                case 7:
                    System.out.print("비밀번호를 입력해주세요 \n");
                    String pw = sc.nextLine().trim();

                    if ("q".equalsIgnoreCase(pw)) return null;
                    if ("b".equalsIgnoreCase(pw)) {
                        step--;
                        continue;
                    }
                    String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$";
                    if (!pw.matches(regex)) {
                        System.out.println("🚨 [입력 오류] 비밀번호는 영문, 숫자, 특수문자 포함 8자 이상이어야 합니다.");
                        continue;
                    }

                    System.out.print("비밀번호를 다시 입력해주세요 \n");
                    String pwCheck = sc.nextLine().trim();

                    if ("q".equalsIgnoreCase(pwCheck)) return null;
                    if ("b".equalsIgnoreCase(pwCheck)) {
                        System.out.println("🔄 비밀번호 입력부터 다시 시작합니다.");
                        continue;
                    }

                    if (pw.equals(pwCheck)) {
                        professorDTO.setProfessorPw(pw);
                        System.out.println("✅ 비밀번호가 일치합니다.");
                        step++;
                    } else {
                        System.out.println("🚨 비밀번호가 일치하지 않습니다. 처음부터 다시 입력해주세요.");
                    }
                    break;
            }
        }

        return professorDTO;
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
//
//        while (true) {
//            System.out.print("이메일 주소(example@lms.com) \n ");
//            String email = sc.nextLine();
//            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
//
//            if ("q".equalsIgnoreCase(email)) {
//                return null;
//            }
//            if (email.matches(emailRegex)) {
//                professorDTO.setProfessorEmail(email); // 형식 통과 시 DTO에 저장
//                break;
//            }
//            System.out.println("🚨 이메일 형식이 올바르지 않습니다. '@'를 포함한 정확한 주소를 입력해주세요.");
//        }
//
//        while (true) {
//            System.out.print("전화번호(010-####-####) \n");
//            String phone = sc.nextLine();
//
//            if ("q".equalsIgnoreCase(phone)) {
//                return null;
//            }
//            if (phone.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
//                professorDTO.setProfessorPhone(phone);
//                break;
//            }
//            System.out.println("🚨 전화번호 형식이 맞지 않습니다. (예: 010-1234-5678)");
//        }
//
//
//
//        while (true) {
//            System.out.print("비밀번호 \n");
//            String pw = sc.nextLine();
//
//            String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$";
//
//            if ("q".equalsIgnoreCase(pw)) {
//                return null;
//            }
//            if (!pw.matches(regex)) {
//                System.out.println("🚨 [입력 오류] 비밀번호는 영문, 숫자, 특수문자 포함 8자 이상이어야 합니다.");
//                continue;
//            }
//
//            System.out.print("비밀번호 확인 \n");
//            String pwCheck = sc.nextLine();
//
//            if ("q".equalsIgnoreCase(pwCheck)) {
//                return null;
//            }
//            if (pw.equals(pwCheck)) {
//                professorDTO.setProfessorPw(pw);
//                System.out.println("✅ 비밀번호가 일치합니다.");
//                break;
//            } else {
//                System.out.println("🚨 비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
//            }
//        }
