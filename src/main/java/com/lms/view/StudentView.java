package com.lms.view;

import com.lms.controller.StudentController;
import com.lms.model.dto.*;

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
        while (true) {
            System.out.println();
            System.out.println("======= 학생 옵션 =======");
            System.out.println("1. 수강신청");
            System.out.println("2. 수강 취소");
            System.out.println("3. 수강 신청한 강의 조회");
            System.out.println("4. 메시지함");
            System.out.println("5. 내 정보 수정");
            System.out.println("0. 로그아웃");
            System.out.print("번호를 입력해주세요: ");

            String menu = sc.nextLine();

            switch (menu) {
                case "1":
                    subjectApply();
                    break;
                case "2":
                    subjectDelete(); //수강 내역 기능 먼저
                    break;
                case "3":
                    subjectView();
                    break;
                case "4":
                    messageBox();
                    break;
                case "5":
                    editMyInfo();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("⚠️ 잘못된 번호입니다. 1~4번을 입력해주세요.");
                    ;
            }
        }
    }


    public void courseDetailView(CourseDTO course) {
        System.out.println("강의명: " + course.getClassName() + " (" + course.getClassNo() + ")");
        System.out.println("강의실: " + course.getClassRoom() + " (" + course.getClassTime() + ")");
        System.out.println("강의 종류: " + course.getClassType());
        System.out.println("학점: " + course.getClassPoint());
        System.out.println("교수: " + course.getProfessorId());
        System.out.println("수강인원: " + course.getClassTask() + "/" + (int) course.getClassCapacity());
        System.out.println("-------------------------------------------");
    }

    public void subjectApply() {
        List<CourseDTO> allClass = controller.findClass();


        while (true) {
            System.out.println("======= 수강 가능 강의 목록 ======");
            if (allClass == null || allClass.isEmpty()) {
                System.out.println("현재 개설된 강의가 없습니다.");
            } else {
                for (CourseDTO course : allClass) {
                    courseDetailView(course); // 여기서 님의 \n이 들어간 toString이 호출됨!
                }
            }
            double[] gpaPoint = totalScoreView(false);
            maxClassApply(gpaPoint, 0);

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

                List<EnrollmentDTO> myEnrollList = controller.enrollView(loginUser.getUserId());

                // [추가 2] 입력한 강의 번호(applyClassNo)가 이미 내 목록에 있는지 확인
                boolean isAlreadyEnrolled = false;
                if (myEnrollList != null) {
                    for (EnrollmentDTO e : myEnrollList) {
                        if (e.getClassNo().equals(applyClassNo)) {
                            isAlreadyEnrolled = true;
                            break;
                        }
                    }
                }

                // [추가 3] 이미 신청한 과목이면 컷!
                if (isAlreadyEnrolled) {
                    System.out.println("❌ 이미 수강 신청한 과목입니다! (중복 신청 불가)");
                    System.out.print("엔터로 뒤로가기");
                    sc.nextLine();
                    continue; // 다시 강의 선택 화면으로 점프
                }

                // [추가] 리스트에서 내가 입력한 번호(applyClassNo)와 똑같은 강의 객체 하나를 찾음
                CourseDTO applyCourse = null;
                for (CourseDTO c : allClass) {
                    if (c.getClassNo().equals(applyClassNo)) {
                        applyCourse = c;
                        break;
                    }
                }

                if (applyCourse == null) {
                    System.out.println("❌ 잘못된 강의 번호입니다. 다시 입력해주세요.");
                    continue; // 다시 위로 올라가서 번호 입력받게 만듦
                }

                double newCoursePoint = Double.parseDouble(applyCourse.getClassPoint());
                //신청 시 총 평점에 따른 수강 가능 학점 확인
                if (!maxClassApply(gpaPoint, newCoursePoint)) {
                    System.out.println("학점 관리가 필요합니다.");
                    System.out.print("엔터로 뒤로가기");
                    sc.nextLine();
                    continue;
                }

                int currentCount = Integer.parseInt(applyCourse.getClassTask()); // 님의 출력 로직 참고
                int maxCapacity = (int) applyCourse.getClassCapacity();

                if (currentCount >= maxCapacity) {
                    System.out.println("🚨 [신청 불가] 수강 정원이 초과되었습니다!");
                    System.out.println("현재 인원: " + currentCount + "명 / 정원: " + maxCapacity + "명");
                    System.out.print("엔터로 뒤로가기");
                    sc.nextLine();
                    continue; // ❌ 더 이상 진행하지 않고 위로 보냄
                }

                CourseDTO timeEqualClass = controller.timeEqual(applyClassNo, loginUser.getUserId());
                if (timeEqualClass != null) {
                    System.out.println("🚨시간표가 중복됩니다.");
                    System.out.println("신청하신 강의가 기존 수강 목록과 겹칩니다.");
                    System.out.println("기존 강의: " + timeEqualClass.getClassName());
                    System.out.println("강의 시간: " + timeEqualClass.getClassTime());
                    System.out.println("---------------------------------------------");
                    System.out.println("신청 시도한 강의: " + applyCourse.getClassName());
                    System.out.println("신청 시도한 강의 시간: " + applyCourse.getClassTime());

                    System.out.println("엔터로 뒤로가기");
                    sc.nextLine();
                } else {
                    int result = controller.addClass(applyClassNo, loginUser.getUserId());
                    if (result > 0) {
                        //addClass(추가) 성공 시
                        System.out.println("✅ " + applyClassNo + " 강의가 신청됐습니다.");
                        System.out.print("엔터로 뒤로가기");
                        sc.nextLine();
                    } else {
                        //addClass 실패 시(이 기능 발전 시켜서 인원 초과 상황도 가능?)
                        System.out.println("🚨 실패했습니다. 다시 시도해주세요.");
                        System.out.print("엔터로 뒤로가기");
                        sc.nextLine();
                    }
                }

            } else {
                System.out.println("취소합니다.");
            }

        }
    }

    public boolean maxClassApply(double[] gpaPoint, double newPoint) {

        double gpa = gpaPoint[0];
        int points = (int)gpaPoint[1];

        int limit = 20; //기본 학점
        if (gpa < 2.0) {
            limit = 10;
        } else if (gpa >= 4.0) {
            limit = 25;
        }

        if (newPoint == 0) {
            // 목록 조회 시 안내용
            if (limit == 10) System.out.println("📢 성적 경고로 이번 학기 10학점까지만 신청 가능합니다.");
            else if (limit == 25) System.out.println("📢 우수 성적자로 이번 학기 25학점 신청 가능합니다.");
            else System.out.println("📢 이번 학기 20학점 신청 가능합니다.");
        }

        // 3. 실제 학점 초과 체크
        if (points + newPoint > limit) {
            System.out.println("❌ 학점 초과로 신청 불가능합니다.");
            System.out.println("현재: " + points + " / 신청 과목: " + newPoint
                    + " \n합계: " + (points + newPoint) + "/" + limit);
            return false;
        }
        return true;

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
            System.out.println("강의 번호: " + e.getClassNo());
            System.out.println(e.getEnrollDate());
            System.out.println("--------------------------------------");
        }
    }

    private void subjectView() {
        while (true) {
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
                System.out.println("1. 전체 성적 조회, 2. 상세 과목 성적 조회");
                System.out.print("확인할 옵션을 선택해주세요(돌아가기는 0): ");
                String scoreMenu = sc.nextLine();
                if (scoreMenu.equals("0")) {
                    return;
                }
                if (scoreMenu.equals("1")) {
                    totalScoreView(true);
                    return;
                } else if (scoreMenu.equals("2")) {
                    scoreView(myEnrollList);
                    return;
                } else {
                    System.out.println("옵션 번호를 확인해주세요.");
                }
            } else {
                System.out.println("옵션 번호를 확인해주세요.");
                System.out.print("엔터로 뒤로가기");
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
            System.out.print("엔터로 뒤로가기");
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
        while (true) {
            displayMyEnrollList(myEnrollList);
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
                System.out.print("(엔터로 뒤로가기"); // 이 한 줄이 중요!
                sc.nextLine();
                return;
            }

            List<EnrollmentDTO> scoreList = controller.scoreView(scoreClassNo, loginUser.getUserId());
            System.out.println("====== 강의별 성적 ======");
            if (scoreList == null || scoreList.isEmpty()) {
                System.out.println("조회된 과제 내용이 없습니다.");
            } else {
                for (EnrollmentDTO score : scoreList) {
                    System.out.println("강의명: " + score.getClassNo());
                    System.out.println("성적: " + score.getScore());
                    System.out.println("------------------------------");
                }
            }
            System.out.print("엔터로 뒤로가기");
            sc.nextLine();
        }
    }

    public double[] totalScoreView(boolean showFlag) {
        String studentId = loginUser.getUserId();
        List<EnrollmentDTO> totalScoreList = controller.totalScoreView(studentId);

        if (showFlag) {
            System.out.println("======= 전체 성적 조회 =======");
        }

        double totalScoreSum = 0; //총 학점
        int totalPoints = 0;
        double resultGpa = 0.0;//이수 학점

        for (EnrollmentDTO e : totalScoreList) {

            double point = Double.parseDouble(e.getScore().substring(10, 13));
            totalPoints += (int) point;

            if (e.getScore() == null || e.getScore().toLowerCase().contains("null")) {
                if (showFlag) {
                    System.out.println("강의명: " + e.getClassNo() + " | 성적 미입력");
                }
                continue; // 다음 과목으로 넘어감!
            }

            if (showFlag) {
                System.out.println("강의명: " + e.getClassNo() + " | " + e.getScore());
            }
            double score = Double.parseDouble(e.getScore().substring(4, 8));

            totalScoreSum += (score * point);
        }

        System.out.println("---------------------------");
        if (totalPoints > 0) {
            double gpa = totalScoreSum / totalPoints;
            resultGpa = Math.round(gpa * 100) / 100.0;
            System.out.println("총 이수 학점: " + totalPoints);
            System.out.println("전체 평균 평점: " + resultGpa + "/4.5");
        }
        if (showFlag) {
            System.out.println("============================");
            System.out.println("\n엔터로 뒤로가기");
            sc.nextLine();
        }

        double[] gpaPoint = {resultGpa, totalPoints};

        return gpaPoint;
    }

    private void subjectDelete() {
        System.out.println("======= 수강 취소 모드 =======");
        String studentId = loginUser.getUserId();
        List<EnrollmentDTO> myEnrollList = controller.enrollView(studentId);
        displayMyEnrollList(myEnrollList);

        System.out.println("위 목록에서 취소할 강의 번호를 입력해주세요.");
        System.out.print("수강 취소할 강의 번호(돌아가기는 0): ");
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
        }
        System.out.println("===============================");
        System.out.print("엔터로 뒤로 가기");
        sc.nextLine();
    }

    public void messageBox() {
        while (true) {
            System.out.println("1. 메시지함 확인, 2. 메시지 보내기");
            System.out.print("옵션을 선택해주세요(돌아가기는 0): ");
            String messageMenu = sc.nextLine();

            if (messageMenu.trim().isEmpty()) {
                continue;
            }

            if (messageMenu.equals("0")) {
                return;
            } else if (messageMenu.equals("1")) {
                messageCheck();
            } else if (messageMenu.equals("2")) {
                messageSend();
            } else {
                System.out.println("옵션 번호를 확인해주세요.");
                System.out.print("엔터로 뒤로가기");
                sc.nextLine();
            }
        }
    }

    public void messageCheck() {
        System.out.println("======= 내 메시지함 =======");
        String myId = loginUser.getUserId();

        List<UserDTO> myMessages = controller.messageCheck(myId);

        // 2. 개수 출력
        System.out.println("📢 전체 메시지: " + myMessages.size() + "건");
        System.out.println("-----------------------------");

        if (myMessages.isEmpty()) {
            System.out.println("도착한 메시지가 없습니다.");
        } else {
            // 3. 메시지 내용들 출력
            for (UserDTO m : myMessages) {
                System.out.println("발신자: " + m.getUserName());
                System.out.println("내용: " + m.getContent());
                System.out.println("-----------------------------");
            }
        }
        System.out.print("엔터로 뒤로가기");
        sc.nextLine();
    }

    public void messageSend() {
        System.out.println("======= 메시지 전송 ========");
        System.out.println("------- 보낼 대상 목록 -------");
        messageMember();
        System.out.print("받는 사람 ID: ");
        String acceptSend = sc.nextLine();
        System.out.print("내용: ");
        String messageContent = sc.nextLine();

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd (E) HH:mm:ss");
        String now = sdf.format(new java.util.Date());

        UserDTO newMsg = new UserDTO();
        newMsg.setUserId(loginUser.getUserId());
        newMsg.setStudentId(loginUser.getUserId());
        newMsg.setReceiverId(acceptSend);
        newMsg.setContent(messageContent + " \n(발신일: " + now + ")");
        newMsg.setUserName(loginUser.getUserName());

        int result = controller.messageSend(newMsg);

        if (result > 0) {
            System.out.println("✅ 메시지가 성공적으로 전송되었습니다.");
            System.out.print("엔터로 뒤로가기");
            sc.nextLine();
        } else {
            System.out.println("메시지 전송에 실패했습니다.");
            System.out.println("엔터로 뒤로가기");
            sc.nextLine();
        }
    }

    public void messageMember() {
        List<StudentDTO> memberList = controller.messageMember();
        System.out.println("ID | 이름(구분)");
        System.out.println("-----------------------------------");

        for (StudentDTO s : memberList) {
            System.out.println(s.getStudentId() + " | " + s.getStudentName());
        }
        System.out.println("------------------------------------");
    }

    private void editMyInfo() {
        while (true) {
            StudentDTO myInfo = myInfoView();

            System.out.println("무엇을 수정하시겠습니까?");
            System.out.println("1. 이름, 2. 주소, 3. 이메일, 4. 전화번호, 5. 비밀번호");
            System.out.print("옵션을 선택해주세요(돌아가기는 0): ");
            String infoMenu = sc.nextLine();

            if (infoMenu.equals("0")) {
                return;
            }

            if (infoMenu.equals("1")) {
                System.out.print("수정할 이름 입력: ");
                myInfo.setStudentName(sc.nextLine());
            } else if (infoMenu.equals("2")) {
                System.out.print("수정할 주소 입력: ");
                myInfo.setStudentAddress(sc.nextLine());
            } else if (infoMenu.equals("3")) {
                System.out.print("수정할 이메일 입력: ");
                String newEmail = sc.nextLine();
                if (!newEmail.contains("@")) {
                    System.out.println("올바른 이메일 형식이 아닙니다.");
                    System.out.print("엔터로 뒤로가기");
                    sc.nextLine();
                    continue;
                } else {
                    myInfo.setStudentEmail(newEmail);
                }
            } else if (infoMenu.equals("4")) {
                System.out.print("수정할 전화번호 입력(-제외): ");
                String phone = sc.nextLine();
                if (phone.length() != 11) {
                    System.out.println("올바른 전화번호를 입력해주세요.");
                    System.out.print("엔터로 뒤로가기");
                    sc.nextLine();
                    continue;
                } else {
                    String phonPull = (phone.substring(0, 3) + "-" +
                            phone.substring(3, 7) + "-" +
                            phone.substring(7, 11));
                    myInfo.setStudentPhone(phonPull);
                }
            } else if (infoMenu.equals("5")) {
                System.out.print("수정할 비밀번호 입력: ");
                String newPw = sc.nextLine();
                String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$";
                if (!newPw.matches(regex)) {
                    System.out.println("비밀번호는 최소 8자 이상 이여야 합니다.");
                    System.out.print("엔터로 뒤로가기");
                    sc.nextLine();
                    continue;
                } else {
                    myInfo.setStudentPw(newPw);
                }
            } else {
                System.out.println("옵션 번호를 확인해주세요.");
                System.out.print("엔터로 뒤로가기");
                sc.nextLine();
            }
            int result = controller.editMyInfo(myInfo);

            if (result > 0) {
                System.out.println("성공적으로 수정되었습니다.");
                System.out.print("엔터로 돌아가기");
                sc.nextLine();
            } else {
                System.out.println("수정에 실패했습니다.");
                System.out.print("엔터로 돌아가기");
                sc.nextLine();
            }
        }
    }

    public StudentDTO myInfoView() {
        StudentDTO myInfoView = controller.myInfoView(loginUser.getUserId());
        System.out.println("======= 내 정보 조회 =======");
        if (myInfoView != null) {
            System.out.println("학번: " + myInfoView.getStudentId());
            System.out.println("이름: " + myInfoView.getStudentName());
            System.out.println("주민번호: " + myInfoView.getStudentNo().substring(0, 8) + "******");
            System.out.println("주소: " + myInfoView.getStudentAddress());
            System.out.println("이메일: " + myInfoView.getStudentEmail());
            System.out.println("전화번호: " + myInfoView.getStudentPhone());
            System.out.println("비밀번호: " + myInfoView.getStudentPw());
            System.out.println("지도교수번호: " + myInfoView.getProfessorId());
        } else {
            System.out.println("학생 정보를 찾을 수 없습니다.");
        }
        System.out.println("---------------------------------------");
        return myInfoView;
    }
}

//    private int inputInt() {
//        while (true) {
//            try {
//                int value = Integer.parseInt(sc.nextLine());
//                return value;
//            } catch (NumberFormatException e) {
//                System.out.print("숫자만 입력해주세요 : ");
//            }
//        }
//    }
//
//    private long inputLong() {
//        while (true) {
//            try {
//                // nextLine으로 받아서 long으로 파싱!
//                return Long.parseLong(sc.nextLine());
//            } catch (NumberFormatException e) {
//                System.out.print("숫자(ID)만 입력해주세요 : ");
//            }
//        }
//    }
//

