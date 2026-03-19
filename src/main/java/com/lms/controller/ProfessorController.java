package com.lms.controller;

import com.lms.model.dto.CourseDTO;
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


    public List<CourseDTO> findCoursesByProfId(String profId) {
        return service.selectCoursesByProfId(profId);
    }
    public List<CourseDTO> findStudentsByCourseId(String courseId) {
        return service.selectStudentsByClassNo(courseId); // 만능 상자(CourseDTO)로 받음
    }

    public int createAssignment(String courseId, String profId, String classTask) {
        return service.updateClassTask(courseId, profId, classTask);
    }
    public int updateGrade(String courseId, String studentId, double score) {
        return service.updateStudentScore(courseId, studentId, score);
    }

}