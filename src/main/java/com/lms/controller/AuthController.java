package com.lms.controller;

import com.lms.model.dto.StudentDTO;
import com.lms.model.service.AuthService;
import com.lms.view.StudentView;

public class AuthController {

    private final StudentView studentView;
    private final AuthService service;

    public AuthController() {
        this.studentView = new StudentView();
        this.service = new AuthService();
    }

    public void registerStudent() {

        StudentDTO newStudent = studentView.inputStudentInfo();

        int result = service.registerStudent(newStudent);

        if (result > 0) {
            studentView.displayMessage("회원가입에 성공하였습니다!");
        } else {
            studentView.displayMessage("회원가입에 실패하였습니다!");
        }
    }
}