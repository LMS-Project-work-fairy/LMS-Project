package com.lms.view;

import java.util.Scanner;
import com.lms.model.dto.StudentDTO;

public class StudentView {

    private Scanner sc = new Scanner(System.in);

    public StudentDTO inputStudentInfo() {
        StudentDTO student = new StudentDTO();

        System.out.println("\n===== 학생 회원가입 =====");

        System.out.print("학번: ");
        student.setStudentId(sc.nextLine());

        System.out.print("비밀번호: ");
        student.setStudentPw(sc.nextLine());

        System.out.println("주민번호: ");
        student.setStudentNo(sc.nextLine());

        System.out.print("이름: ");
        student.setStudentName(sc.nextLine());

        System.out.print("주소: ");
        student.setStudentAddress(sc.nextLine());

        System.out.print("이메일: ");
        student.setStudentEmail(sc.nextLine());

        System.out.print("전화번호: ");
        student.setStudentPhone(sc.nextLine());

        return student;
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}