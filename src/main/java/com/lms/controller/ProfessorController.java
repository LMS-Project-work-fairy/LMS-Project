package com.lms.controller;


import com.lms.model.dto.EnrollmentCourseDTO;
import com.lms.model.service.ProfessorService;
import com.lms.view.ProfessorView;

import java.util.List;

public class ProfessorController {

    private final ProfessorView view;
    private final ProfessorService service;

    public ProfessorController() {
        this.view = new ProfessorView(this);
        this.service = new ProfessorService();
    }

    // 로그인 담당이 호출할 메소드
    public void startProfessorMenu(String profId) {
        view.displayMainMenu(profId);
    }


    public List<EnrollmentCourseDTO> findCoursesByProfId(String profId) {
        return service.selectCoursesByProfId(profId);
    }
    public List<EnrollmentCourseDTO> findStudentsByCourseId(String courseId) {
        return service.selectStudentsByClassNo(courseId);
    }

    public int createAssignment(String courseId, String profId, String classTask) {
        return service.updateClassTask(courseId, profId, classTask);
    }
    public int updateGrade(String courseId, String studentId, double score) {
        return service.updateStudentScore(courseId, studentId, score);
    }

}