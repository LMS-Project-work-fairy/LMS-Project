package com.lms.controller;

import com.lms.model.dto.StudentDTO;
import com.lms.model.service.AuthService;
import com.lms.view.MainView;

public class AuthController {

    private final MainView mainView;
    private final AuthService service;

    public AuthController() {
        this.mainView = new MainView();
        this.service = new AuthService();
    }

    public void registerStudent() {

        StudentDTO newStudent = mainView.inputStudentInfo();

        int result = service.registerStudent(newStudent);

        if (result > 0) {
            mainView.displayMessage("회원가입에 성공하였습니다!");
        } else {
            mainView.displayMessage("회원가입에 실패하였습니다!");
        }
    }
}