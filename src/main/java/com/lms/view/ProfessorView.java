package com.lms.view;
import com.lms.controller.ProfessorController;

import com.lms.model.dto.EnrollmentCourseDTO;
import com.lms.model.dto.MessageDTO;
import com.lms.model.dto.ProfessorDTO;
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
            System.out.println("2. 강좌 등록");
            System.out.println("3. 강좌 삭제");
            System.out.println("4. 개인정보 수정");
            System.out.println("5. 메시지함 (쪽지)");
            System.out.println("6. 로그아웃");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu) {
                case 1: displayMyCourses(profId); break;
                case 2: registerNewCourse(profId); break;
                case 3: deleteCourseMenu(profId); break;
                case 4: updateMyInfo(profId); break;
                case 5: manageMessages(profId); break;
                case 6:
                    printMessage("== 로그아웃 합니다. ==");
                    return;
                default: printError("다시 선택해주세요.");
            }
        }
    }
    // 담당 과목 조회
    public void displayMyCourses(String profId) {
        while(true) {
            printMessage("\n--- [담당 과목 조회] ---");

            List<EnrollmentCourseDTO> courseList = controller.findCoursesByProfId(profId);
            printCourses(courseList);

            if (courseList == null || courseList.isEmpty()) return;


            System.out.println("=================================");
            System.out.print("상세 옵션을 확인할 [과목명]을 입력해주세요 (이전 메뉴로: 0) : ");
            String selectedCourseName = inputString();

            if (selectedCourseName.equals("0")) return;


            String targetCourseId = getCourseIdByName(courseList, selectedCourseName);

            if (targetCourseId == null) {
                printError("일치하는 과목명이 없습니다. 띄어쓰기를 확인해주세요.");
            } else {

                showCourseDetailMenu(profId, targetCourseId, selectedCourseName);
            }
        }
    }

    // 개인정보 수정
    private void updateMyInfo(String profId) {
        while(true) {
            System.out.println("\n=== [개인정보 수정 메뉴] ===");
            System.out.println("1. 비밀번호 수정");
            System.out.println("2. 이름 수정");
            System.out.println("3. 전화번호 수정");
            System.out.println("4. 이메일 수정");
            System.out.println("5. 주소 수정");
            System.out.println("0. 수정 완료 (이전 메뉴로 돌아가기)");
            System.out.print("수정할 항목을 선택해주세요 : ");

            int menu = inputInt();

            if (menu == 0) {
                printMessage("개인정보 수정을 종료합니다.");
                return;
            }

            String columnName = "";
            String newValue = "";
            String itemName = "";

            switch(menu) {
                case 1:
                    System.out.print("새로운 비밀번호를 입력하세요: ");
                    newValue = inputString();
                    columnName = "professor_pw";
                    itemName = "비밀번호";
                    break;
                case 2:
                    System.out.print("새로운 이름을 입력하세요: ");
                    newValue = inputString();
                    columnName = "professor_name";
                    itemName = "이름";
                    break;
                case 3:
                    System.out.print("새로운 전화번호(예: 010-1111-1111)를 입력하세요: ");
                    newValue = inputString();
                    columnName = "professor_phone";
                    itemName = "전화번호";
                    break;
                case 4:
                    System.out.print("새로운 이메일을 입력하세요: ");
                    newValue = inputString();
                    columnName = "professor_email";
                    itemName = "이메일";
                    break;
                case 5:
                    System.out.print("새로운 주소를 입력하세요: ");
                    newValue = inputString();
                    columnName = "professor_address";
                    itemName = "주소";
                    break;
                default:
                    printError("잘못된 선택입니다. 다시 번호를 확인해주세요.");
                    continue;
            }


            int result = controller.updateSingleInfo(profId, columnName, newValue);

            if(result > 0) {
                printSuccess("[" + itemName + "] 정보가 성공적으로 수정되었습니다!");
            } else {
                printError("[" + itemName + "] 수정에 실패했습니다.");
            }
        }
    }



    private String getCourseIdByName(List<EnrollmentCourseDTO> list, String name) {
        for(EnrollmentCourseDTO c : list) {
            if(c.getClassName().equals(name)) return c.getClassNo();
        }
        return null;
    }

    // 신규 강좌 등록 화면
    private void registerNewCourse(String profId) {
        System.out.println("\n=== [신규 강좌 등록] ===");

        System.out.print("1. 강의명: ");
        String className = inputString();
        System.out.print("2. 학점(예: 3.0): ");
        double classPoint = inputDouble();
        System.out.print("3. 시간표(예: 월1-3): ");
        String classTime = inputString();
        System.out.print("4. 강의실(예: A101): ");
        String classRoom = inputString();

        System.out.print("5. 분류 선택 (1. 전공 / 2. 교양): ");
        int typeChoice = inputInt();
        String classType = (typeChoice == 1) ? "전공" : "교양";

        System.out.print("6. 수용 인원: ");
        float classCapacity = (float) inputDouble();


        EnrollmentCourseDTO newCourse = new EnrollmentCourseDTO();
        newCourse.setClassName(className);
        newCourse.setClassPoint(classPoint);
        newCourse.setClassTime(classTime);
        newCourse.setClassRoom(classRoom);
        newCourse.setClassType(classType);
        newCourse.setClassCapacity(classCapacity);
        newCourse.setProfessorId(profId);


        int result = controller.registerCourse(newCourse);

        if(result == -1) {
            printError("등록 실패: 해당 시간과 강의실에 이미 다른 강의가 존재합니다!");
        } else if(result > 0) {
            printSuccess("신규 강좌 등록이 성공적으로 완료되었습니다!");
        } else {
            printError("시스템 오류로 강좌 등록에 실패했습니다.");
        }
    }

    // 메시지함 관리
    private void manageMessages(String profId) {
        while (true) {
            System.out.println("\n=== [메시지함] ===");
            System.out.println("1. 받은 메시지 확인");
            System.out.println("2. 새 메시지 보내기");
            System.out.println("0. 이전 메뉴로 돌아가기");
            System.out.print("선택: ");

            int menu = inputInt();

            if (menu == 0) return;

            if (menu == 1) {
                // 받은 메시지 조회
                List<MessageDTO> msgList = controller.checkMessages(profId);
                printMessage("\n--- [받은 메시지 목록] ---");
                if (msgList.isEmpty()) {
                    System.out.println("도착한 메시지가 없습니다.");
                } else {
                    for (MessageDTO msg : msgList) {
                        System.out.println("💌 보낸사람: " + msg.getUserName() + " (" + msg.getUserId() + ")");
                        System.out.println("   내용: " + msg.getContent());
                        System.out.println("-------------------------");
                    }
                }
            } else if (menu == 2) {
                System.out.println("\n[새 메시지 작성 📝]");


                List<StudentDTO> memberList = controller.getAllMembers();
                System.out.println("--- [수신 가능 사용자 주소록] ---");
                for (StudentDTO member : memberList) {

                    System.out.println("▶ ID: " + member.getStudentId() + " | 이름: " + member.getStudentName());
                }
                System.out.println("---------------------------------");

                System.out.print("받는 사람의 ID를 정확히 입력하세요: ");
                String receiverId = inputString();
                System.out.print("보낼 내용을 입력하세요: ");
                String content = inputString();

                MessageDTO newMsg = new MessageDTO();
                newMsg.setUserId(profId);
                newMsg.setProfessorId(profId);
                newMsg.setReceiverId(receiverId);
                newMsg.setContent(content);

                int result = controller.sendMessage(newMsg);
                if (result > 0) printSuccess("메시지 전송 성공!");
                else printError("메시지 전송 실패. 수신자 ID를 확인해주세요.");
            } else {
                printError("잘못된 입력입니다.");
            }
        }
    }




    // 전용 상세 메뉴창
    private void showCourseDetailMenu(String profId, String courseId, String courseName) {
        while(true){
            System.out.println("\n=== [" + courseName + "] 과목 상세 옵션 ===");
            System.out.println("1. 수강 학생 확인 및 성적 관리");
            System.out.println("2. 과제 등록");
            System.out.println("0. 과목 목록으로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");

            int menu = inputInt();

            switch (menu){
                case 1: findEnrolledStudents(courseId); break;
                case 2: createAssignment(profId, courseId); break;
                case 0: return;
                default: printError("다시 선택해주세요.");
            }
        }
    }

    // 수강 학생 조회 + 성적 관리 통합
    private void findEnrolledStudents(String courseId) {
        while(true) {
            printMessage("\n--- 수강 학생 명단 ---");
            List<EnrollmentCourseDTO> studentList = controller.findStudentsByCourseId(courseId);
            printStudents(studentList);

            if (studentList == null || studentList.isEmpty()) return; // 학생이 없으면 튕겨나가기


            System.out.println("\n---------------------------------");
            System.out.println("1. 학생 성적 입력 및 수정");
            System.out.println("0. 이전 메뉴로 돌아가기");
            System.out.print("번호를 입력해주세요 : ");
            int choice = inputInt();

            if (choice == 1) {
                manageGrades(courseId);
            } else if (choice == 0) {
                return;
            } else {
                printError("잘못된 입력입니다.");
            }
        }
    }



    // 과제 등록
    private void createAssignment(String profId, String courseId) {
        System.out.println("\n[과제 등록] 🚨 과제 내용은 2000자 이내로 작성해주세요.");
        System.out.print("과제 제목을 입력해주세요 : ");
        String title = inputString();

        System.out.print("과제 내용을 입력해주세요 : ");
        String description = inputString();

        System.out.print("마감일(예: 2026-04-30)을 입력해주세요 : ");
        String deadline = inputString();


        String fullTask = "과제 제목: " + title + "\n과제 내용: " + description + "\n마감일: " + deadline;

        int result = controller.createAssignment(courseId, profId, fullTask);
        if (result > 0) printSuccess("과제 등록 성공!");
        else printError("과제 등록 실패.");
    }

    // 성적 관리
    private void manageGrades(String courseId) {
        System.out.print("\n성적을 입력할 학생의 학번을 입력해주세요 : ");
        String studentId = inputString();

        System.out.print("부여할 성적을 입력해주세요 : ");
        double grade = inputDouble();

        int result = controller.updateGrade(courseId, studentId, grade);
        if (result > 0) printSuccess("해당 학생의 성적 입력(수정)이 완료되었습니다!");
        else printError("성적 처리 실패. 학번을 다시 확인해주세요.");
    }

    //  강좌 삭제
    private boolean deleteCourse(String courseId, String courseName) {
        System.out.println("\n🚨 [경고] 정말로 [" + courseName + "] 강좌를 삭제하시겠습니까?");
        System.out.println("수강 중인 학생이 있다면 데이터가 함께 날아갈 수 있습니다!");
        System.out.print("삭제를 원하시면 '삭제' 라고 정확히 타이핑해주세요 (취소: 0) : ");
        String confirm = inputString();

        if (confirm.equals("삭제")) {
            int result = controller.deleteCourse(courseId);
            if (result > 0) {
                printSuccess("강좌가 성공적으로 삭제되었습니다.");
                return true;
            } else {
                printError("강좌 삭제에 실패했습니다.");
            }
        } else {
            printMessage("강좌 삭제가 취소되었습니다.");
        }
        return false;
    }


    private void deleteCourseMenu(String profId) {
        printMessage("\n--- [강좌 삭제] ---");


        List<EnrollmentCourseDTO> courseList = controller.findCoursesByProfId(profId);
        printCourses(courseList);

        if (courseList == null || courseList.isEmpty()) return;


        System.out.println("=================================");
        System.out.print("삭제할 [과목명]을 정확히 입력해주세요 (취소: 0) : ");
        String selectedCourseName = inputString();

        if (selectedCourseName.equals("0")) return;


        String targetCourseId = getCourseIdByName(courseList, selectedCourseName);

        if (targetCourseId == null) {
            printError("일치하는 과목명이 없습니다. 띄어쓰기를 확인해주세요.");
            return;
        }


        deleteCourse(targetCourseId, selectedCourseName);
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

    public void printCourses(List<EnrollmentCourseDTO> courseList) {
        if (courseList == null || courseList.isEmpty()) {
            System.out.println("조회 된 담당 과목이 없습니다!!");
            return;
        }
        System.out.println("===============담당 과목 조회 결과==================");
        for (EnrollmentCourseDTO course : courseList) {

            System.out.println("▶ " + course.getClassName() +
                    " (강의실: " + course.getClassRoom() + ", 시간: " + course.getClassTime() +
                    ", 수용인원: " + (int)course.getClassCapacity() + "명)");
        }
    }

    public void printStudents(List<EnrollmentCourseDTO> studentList) {
        if (studentList == null || studentList.isEmpty()) {
            System.out.println("해당 과목에 수강 중인 학생이 없거나 과목 번호가 틀렸습니다.");
            return;
        }
        System.out.println("===============수강 학생 명단==================");
        for (EnrollmentCourseDTO student : studentList) {
            String scoreDisplay = (student.getScore() > 0.0) ? String.valueOf(student.getScore()) : "미입력";
            System.out.println("[학번: " + student.getStudentId() + "] " + student.getStudentName() +
                    " | 성적: " + scoreDisplay);
        }
    }
}
