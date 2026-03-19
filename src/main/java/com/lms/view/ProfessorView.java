package com.lms.view;


import com.lms.controller.ProfessorController;
import com.lms.model.dto.CourseDTO;
import com.lms.model.dto.StudentDTO;

import java.util.Scanner;
import java.util.List;

public class ProfessorView {
    private final ProfessorController controller;
    private final Scanner sc = new Scanner(System.in);


    public ProfessorView(ProfessorController controller) {
        this.controller = controller;
    }

    // 교수 메인 메뉴
    public void displayMainMenu(String profId) {
        while (true) {
            System.out.println();
            System.out.println("=================================");
            System.out.println("         [교수] 메인 메뉴");
            System.out.println("=================================");
            System.out.println("1. 담당 과목 조회");
            System.out.println("2. 로그아웃");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    displayMyCourses(profId);
                    break;
                case 2:
                    printMessage("== 로그아웃 합니다. ==");
                    return; // 메서드 종료 (이전 화면으로 돌아감)
                default:
                    printError("다시 선택해주세요.");

            }
        }
    }
    // 담당 과목 조회
    public void displayMyCourses(String profId) {
        printMessage("\n--- [담당 과목 조회] ---");


        List<CourseDTO> courseList = controller.findCoursesByProfId(profId);
        printCourses(courseList);

        while(true){
            System.out.println();
            System.out.println("=================================");
            System.out.println("과목 상세 옵션");
            System.out.println("=================================");
            System.out.println("1. 수강 학생 확인");
            System.out.println("2. 과제 등록");
            System.out.println("3. 성적 관리");
            System.out.println("4. 이전으로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu){
                case 1:
                    findEnrolledStudents();
                    break;
                case 2:
                    createAssignment(profId);
                    break;
                case 3:
                    manageGrades();
                    break;
                case 4:
                    return;

                default:
                    printError("다시 선택해주세요.");
            }
        }
    }

    // 수강 학생 조회
    private void findEnrolledStudents() {
        System.out.print("\n조회할 과목 번호(또는 과목 코드)를 입력해주세요 : ");
        String courseId = inputString();

        printMessage("\n--- [" + courseId + "] 수강 학생 명단 ---");


        List<CourseDTO> studentList = controller.findStudentsByCourseId(courseId);
        printStudents(studentList);
    }

    // 과제 등록
    private void createAssignment(String profId) {
        System.out.print("\n과제를 등록할 과목 번호(또는 과목 코드)를 입력해주세요 : ");
        String courseId = inputString();

        System.out.print("과제 제목을 입력해주세요 : ");
        String title = inputString();

        System.out.print("과제 내용을 입력해주세요 : ");
        String description = inputString();

        System.out.print("마감일(예: 2026-04-30)을 입력해주세요 : ");
        String deadline = inputString();

        String fullTask = "[" + title + "] " + description + " (마감: " + deadline + ")";


        int result = controller.createAssignment(courseId, profId, fullTask);
        if (result > 0) {
            printSuccess("과제 등록 성공!");
        } else {
            printError("과제 등록 실패. 과목 코드를 다시 확인해주세요.");
        }
    }

    // 성적 관리
    private void manageGrades(){
        System.out.print("\n성적을 입력할 과목 번호(또는 과목 코드)를 입력해주세요 : ");
        String courseId = inputString();

        System.out.print("성적을 입력할 학생의 학번을 입력해주세요 : ");
        String studentId = inputString();

        System.out.print("부여할 성적을 입력해주세요 : ");
        double grade = inputDouble();

        int result = controller.updateGrade(courseId, studentId, grade);

        if (result > 0) {
            printSuccess("해당 학생의 성적 입력(수정)이 완료되었습니다!");
        } else {
            printError("성적 처리 실패. 학번이나 과목 코드를 다시 확인해주세요.");
        }
    }

    private double inputDouble() {while (true) {
        try {
            return Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.print("숫자(소수점 포함)만 입력해주세요 : ");
        }
    }
    }

    private int inputInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }
    private String inputString() {
        return sc.nextLine();
    }
    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.out.println("🚨🚨 " + message);
    }

    public void printSuccess(String message) {
        System.out.println("✅ " + message);
    }

    public void printCourses(List<CourseDTO> courseList) {
        if (courseList == null || courseList.isEmpty()) {
            System.out.println("조회 된 담당 과목이 없습니다!!");
            return;
        }
        System.out.println("===============담당 과목 조회 결과==================");
        for (CourseDTO courseDTO : courseList) {
            System.out.println(courseDTO);
        }
    }

    public void printStudents(List<CourseDTO> studentList) { // StudentDTO -> CourseDTO 변경
        if (studentList == null || studentList.isEmpty()) {
            System.out.println("해당 과목에 수강 중인 학생이 없습니다.");
            return;
        }
        System.out.println("===============수강 학생 명단==================");
        for (CourseDTO courseDTO : studentList) { // StudentDTO -> CourseDTO 변경
            System.out.println(courseDTO);
        }
    }

}


