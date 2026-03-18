package com.lms.view;

import com.lms.controller.StudentController;
import com.lms.model.dto.CourseDTO;
import com.lms.model.dto.EnrollmentDTO;
import com.lms.model.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class StudentView {
    private final StudentController controller;
    private final Scanner sc = new Scanner(System.in);

    public StudentView(StudentController controller) {
        this.controller = controller;
    }

    public void displayStudentMenu() {
        while(true) {
            System.out.println();
            System.out.println("======= 학생 옵션 =======");
            System.out.println("1. 수강신청");
            System.out.println("2. 수강 취소");
            System.out.println("3. 수강 신청한 과목 조회");
            System.out.println("4. 뒤로 가기");
            System.out.println("번호를 입력해주세요: ");

            int menu = inputInt();

            switch (menu) {
                case 1:
                    subjectApply();
                    break;
                case 2:
                    subjectDelete(); //수강 내역 기능 먼저
                    break;
                case 3:
                    subjectView();
                    System.out.println("엔터로 뒤로 가기");
                    sc.nextLine();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("⚠️ 잘못된 번호입니다. 1~4번을 입력해주세요.");;
            }
        }
    }



    public void subjectApply() {
        List<CourseDTO> allClass = controller.findClass();
        while(true) {
            System.out.println("======= 수강 가능 강의 목록 ======");
            System.out.println(allClass);
            System.out.println("신청할 과목 번호를 입력해주세요(돌아가기는 0): ");
            String applyClassNo = sc.nextLine();

            if (applyClassNo.equals("0")) {
                System.out.println("이전 화면으로 돌아갑니다.");
                return;
            }

            System.out.println("입력하신 과목 번호는 " + applyClassNo + " 입니다.");
            System.out.println("신청하시겠습니까? (1. 신청/ 2. 취소)");
            String confirm = sc.nextLine();

            if (confirm.equals("1")) {
                int result = controller.addClass(applyClassNo);
                if (result > 0) {
                    //addClass(추가) 성공 시
                    System.out.println("✅ " + applyClassNo + " 과목이 신청됐습니다.");
                } else {
                    //addClass 실패 시(이 기능 발전 시켜서 인원 초과 상황도 가능?)
                    System.out.println("🚨 실패했습니다. 다시 시도해주세요.");
                }

            } else {
                System.out.println("취소합니다.");
            }

        }
    }

    private void subjectView() {
            System.out.println("======= 내 수강 신청 내역 ========");

            String studentId = "20230001"; //현재 로그인한 학생의 ID
            List<EnrollmentDTO> myEnrollList = controller.enrollView(studentId);

            if (myEnrollList == null || myEnrollList.isEmpty()) {
                System.out.println("신청한 과목이 없습니다.");
            } else {
                for (EnrollmentDTO e: myEnrollList)
                System.out.println(e);
            }
    }

    private void subjectDelete() {
        System.out.println("======= 수강 취소 모드 =======");

        subjectView();

        System.out.println("위 목록에서 취소할 과목 번호를 입력해주세요.");
        System.out.print("수강 취소할 과목 번호: ");
        String deleteClassNo = sc.nextLine();

        if (deleteClassNo.equals("0")) return; //취소 방지용

        System.out.print("정말 " + deleteClassNo + " 과목을 취소하시겠습니까? (1. 예/ 2. 아니오): ");
        String confirm = sc.nextLine();

        if (confirm.equals("1")) {
            int result = controller.deleteClass(deleteClassNo);
            if (result > 0) {
                System.out.println("✅🚮 성공적으로 취소되었습니다.");
            } else {
                System.out.println("❌ 취소에 실패했습니다.");
            }
        } else {
            System.out.println("취소가 중단되었습니다.");
        } System.out.println("===============================");
        System.out.println("엔터로 뒤로 가기");
        sc.nextLine();
    }

    private int inputInt() {
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("숫자만 입력해주세요 : ");
            }
        }
    }

    private long inputLong() {
        while (true) {
            try {
                // nextLine으로 받아서 long으로 파싱!
                return Long.parseLong(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("숫자(ID)만 입력해주세요 : ");
            }
        }
    }
}
