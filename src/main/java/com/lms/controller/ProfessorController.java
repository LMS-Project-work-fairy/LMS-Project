package com.lms.controller;

import com.lms.view.ProfessorView;

public class ProfessorController {

    private final ProfessorView view;

    public ProfessorController() {
        this.view = new ProfessorView(this);
    }

    // 로그인 담당이 호출할 메소드
    public void startProfessorMenu(String profId) {
        view.displayMainMenu(profId);
    }

    /*
    public List<CourseDTO> findCoursesByProfId(String profId) {
        return null; // 나중에 Service 연결
    }

    public List<StudentDTO> findStudentsByCourseId(String courseId) {
        return null; // 나중에 Service 연결
    }

    public int createAssignment(String courseId, String title, String description, String deadline) {
        return 1;
    }
    */
}