package com.lms.application;

import com.lms.common.JDBCTemplate;
import com.lms.controller.StudentController;
import com.lms.model.dao.StudentDAO;
import com.lms.model.service.StudentService;
import com.lms.view.StudentView;

import java.sql.Connection;

public class ApplicationTest {
    public static void main(String[] args) {
        Connection con = null;
        try {
            con = JDBCTemplate.getConnection();
            System.out.println("✅ DB 연결 성공!");

            // 1. 조립 (인수 에러 방지용)
            StudentDAO dao = new StudentDAO(con);
            StudentService service = new StudentService(con); // 님 말씀대로 con만 넣음
            StudentController controller = new StudentController(service);
            StudentView view = new StudentView(controller);

            // 2. 메인 메뉴 무시하고 바로 님의 학생 메뉴 실행!
            System.out.println("🚀 테스트 모드: 학생 메뉴로 즉시 진입합니다.");
            view.displayStudentMenu();

        } catch (Exception e) {
            System.out.println("🚨 에러 발생: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // JDBCTemplate.close(con); // 일단 테스트를 위해 주석 처리하거나 닫기
        }
    }
}
