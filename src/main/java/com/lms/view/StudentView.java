package com.lms.view;

import com.lms.controller.CourseController;
import com.lms.controller.StudentController;
import com.lms.model.dto.CourseDTO;
import com.lms.model.dto.EnrollmentDTO;
import com.lms.model.dto.LoginUserDTO;
import com.lms.model.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class StudentView {
    private final StudentController controller;
    private final LoginUserDTO loginUser;
    private final Scanner sc = new Scanner(System.in);

    public StudentView(StudentController controller, LoginUserDTO loginUser) {
        this.controller = controller;
        this.loginUser = loginUser;
    }

    public void displayStudentMenu() {
        while(true) {
            System.out.println();
            System.out.println("======= 학생 옵션 =======");
            System.out.println("1. 수강신청");
            System.out.println("2. 수강 취소");
            System.out.println("3. 수강 신청한 강의 조회");
            System.out.println("4. 뒤로 가기");
            System.out.print("번호를 입력해주세요: ");

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
            if (allClass == null || allClass.isEmpty()) {
                System.out.println("현재 개설된 강의가 없습니다.");
            } else {
                for (CourseDTO course : allClass) {
                    System.out.println(course); // 여기서 님의 \n이 들어간 toString이 호출됨!
                    System.out.println("------------------------------------"); // 구분선
                }
            }
            System.out.print("신청할 강의 번호를 입력해주세요(돌아가기는 0): ");
            String applyClassNo = sc.nextLine();

            if (applyClassNo.equals("0")) {
                System.out.println("이전 화면으로 돌아갑니다.");
                return;
            }

            System.out.println("입력하신 강의 번호는 " + applyClassNo + " 입니다.");
            System.out.print("신청하시겠습니까? (1. 신청/ 2. 취소): ");
            String confirm = sc.nextLine();

            if (confirm.equals("1")) {
                // [추가] 리스트에서 내가 입력한 번호(applyClassNo)와 똑같은 강의 객체 하나를 찾음
                CourseDTO applyCourse = null;
                for (CourseDTO c : allClass) {
                    if (c.getClassNo().equals(applyClassNo)) {
                        applyCourse = c;
                        break;
                    }
                }
                CourseDTO timeEqualClass = controller.timeEqual(applyClassNo, loginUser.getUserId());
                if (timeEqualClass != null) {
                    System.out.println("🚨시간표가 중복됩니다.");
                    System.out.println("신청하신 강의가 기존 수강 목록과 겹칩니다.");
                    System.out.println("기존 강의: " + timeEqualClass.getClassName());
                    System.out.println("강의 시간: " + timeEqualClass.getClassTime());
                    System.out.println("신청 시도한 강의: " + applyCourse.getClassName());
                    System.out.println("신청 시도한 강의 시간: " + applyCourse.getClassTime());
                } else{
                    int result = controller.addClass(applyClassNo, loginUser.getUserId());
                    if (result > 0) {
                    //addClass(추가) 성공 시
                    System.out.println("✅ " + applyClassNo + " 강의가 신청됐습니다.");
                } else {
                    //addClass 실패 시(이 기능 발전 시켜서 인원 초과 상황도 가능?)
                    System.out.println("🚨 실패했습니다. 다시 시도해주세요.");
                    }
                }
                System.out.print("엔터로 뒤로가기");
                sc.nextLine();

            } else {
                System.out.println("취소합니다.");
            }

        }
    }

    private CourseDTO timeEqual(String applyClassNo) {
        CourseDTO timeEqual = controller.timeEqual(applyClassNo, loginUser.getUserId());
        return timeEqual;
    }

    private void displayMyEnrollList(List<EnrollmentDTO> list) {
        System.out.println("======= 내 수강 신청 내역 ========");
        if (list == null || list.isEmpty()) {
            System.out.println("신청한 강의가 없습니다.");
            return;
        }
        for (EnrollmentDTO e : list) {
            System.out.println(e);
        }
    }

    private void subjectView() {
        while(true) {
            String studentId = loginUser.getUserId(); //현재 로그인한 학생의 ID
            List<EnrollmentDTO> myEnrollList = controller.enrollView(studentId);

            displayMyEnrollList(myEnrollList);

            System.out.println("1. 과제 확인, 2. 성적 확인");
            System.out.print("확인할 옵션을 선택해주세요(돌아가기는 0): ");
            String subMenu = sc.nextLine();

            if (subMenu.equals("0")) {
                return;
            }
            if (subMenu.equals("1")) {
                taskView(myEnrollList);
            } else if (subMenu.equals("2")) {
                scoreView(myEnrollList);
            } else {
                System.out.println("옵션 번호를 확인해주세요.");
                System.out.println("엔터로 뒤로가기");
                sc.nextLine();
            }

        }

    }

    public void taskView(List<EnrollmentDTO> myEnrollList) {
        System.out.print("상세 과제 확인을 원하는 강의 번호를 입력해주세요(돌아가기는 0): ");
        String taskClassNo = sc.nextLine();

        if (taskClassNo.equals("0")) {
            return;
        }

        // [검증 로직] 입력한 번호가 내 리스트에 있는지 확인
        boolean isExist = false;
        for (EnrollmentDTO enroll : myEnrollList) {
            if (enroll.getClassNo().equals(taskClassNo)) {
                isExist = true;
                break;
            }
        }

        // 리스트에 없는 번호면 컷!
        if (!isExist) {
            System.out.println("⚠️ 수강 중인 강의 번호가 아닙니다. 번호를 확인해주세요.");
            System.out.println("(엔터를 누르면 목록으로 돌아갑니다)"); // 이 한 줄이 중요!
            sc.nextLine();
            return;
        }

        List<CourseDTO> myTaskList = controller.taskView(taskClassNo, loginUser.getUserId());

        System.out.println("======= 상세 과제 내용 =======");
        if (myTaskList == null || myTaskList.isEmpty()) {
            System.out.println("조회된 과제 내용이 없습니다.");
        } else {
            for (CourseDTO task : myTaskList) {
                System.out.println("강의명: " + task.getClassName());
                System.out.println("과제 내용: " + task.getClassTask());
                System.out.println("------------------------------");
            }
        }
        System.out.print("엔터로 뒤로가기");
        sc.nextLine();
    }

    public void scoreView(List<EnrollmentDTO> myEnrollList) {
        System.out.print("성적 확인할 강의 번호를 입력해주세요(돌아가기는 0): ");
        String scoreClassNo = sc.nextLine();

        if (scoreClassNo.equals("0")) {
            return;
        }

        // [검증 로직] 입력한 번호가 내 리스트에 있는지 확인
        boolean isExist = false;
        for (EnrollmentDTO enroll : myEnrollList) {
            if (enroll.getClassNo().equals(scoreClassNo)) {
                isExist = true;
                break;
            }
        }

        // 리스트에 없는 번호면 컷!
        if (!isExist) {
            System.out.println("⚠️ 수강 중인 강의 번호가 아닙니다. 번호를 확인해주세요.");
            System.out.println("(엔터를 누르면 목록으로 돌아갑니다)"); // 이 한 줄이 중요!
            sc.nextLine();
            return;
        }

        List<EnrollmentDTO> scoreList = controller.scoreView(scoreClassNo, loginUser.getUserId());
        System.out.println("====== 강의별 성적 ======");
        if (scoreList == null || scoreList.isEmpty()) {
            System.out.println("조회된 과제 내용이 없습니다.");
        } else {
            for (EnrollmentDTO score : scoreList) {
                System.out.println("강의명: " + score.getClassName());
                System.out.println("성적: " + score.getScore());
                System.out.println("------------------------------");
            }
        }
        System.out.print("엔터로 뒤로가기");
        sc.nextLine();


    }

    private void subjectDelete() {
        System.out.println("======= 수강 취소 모드 =======");
        String studentId = loginUser.getUserId();
        List<EnrollmentDTO> myEnrollList = controller.enrollView(studentId);
        displayMyEnrollList(myEnrollList);

        System.out.println("위 목록에서 취소할 강의 번호를 입력해주세요.");
        System.out.print("수강 취소할 강의 번호: ");
        String deleteClassNo = sc.nextLine();

        if (deleteClassNo.equals("0")) return; //취소 방지용

        System.out.print("정말 " + deleteClassNo + " 강의을 취소하시겠습니까? (1. 예/ 2. 아니오): ");
        String confirm = sc.nextLine();

        if (confirm.equals("1")) {
            int result = controller.deleteClass(deleteClassNo, loginUser.getUserId());
            if (result > 0) {
                System.out.println("✅🚮 성공적으로 취소되었습니다.");
            } else {
                System.out.println("❌ 취소에 실패했습니다.");
            }
        } else {
            System.out.println("취소가 중단되었습니다.");
        } System.out.println("===============================");
        System.out.print("엔터로 뒤로 가기");
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

