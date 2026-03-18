package com.lms.controller;

import com.lms.model.dto.CourseDTO;
import com.lms.model.dto.EnrollmentDTO;
import com.lms.model.service.StudentService;

import java.util.List;

public class StudentController {
    private final StudentService service;


    public StudentController(StudentService service) {
        this.service = service;
    }

    public List<CourseDTO> findClass() {
        return service.findClass();
    }

    public int addClass(String applyClassNo) {
        EnrollmentDTO enroll = new EnrollmentDTO();

        //로그인 정보
        enroll.setStudentId("20230001"); //사용자에게 입력받은 학번
        enroll.setClassNo(applyClassNo);
        return service.addClass(enroll);
        //addClass()괄호 안에 applyClassNo대신 enroll를 넣는 이유는 누가 수강신청했는지 알기 위함
    }

    public List<EnrollmentDTO> enrollView(String studentId) {
        return service.enrollView(studentId);
    }

    public int deleteClass(String deleteClassNo) {
        EnrollmentDTO enroll = new EnrollmentDTO();

        //로그인 정보
        enroll.setStudentId("20230001"); //사용자에게 입력받은 학번
        enroll.setClassNo(deleteClassNo);
        return service.deleteClass(enroll);
    }
}
