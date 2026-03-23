package com.lms.model.service;

import com.lms.common.JDBCTemplate;
import com.lms.model.dao.ProfessorDAO;
import com.lms.model.dao.StudentDAO;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import com.lms.model.dto.ProfessorDTO;
import com.lms.model.dto.StudentDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private final ProfessorDAO professorDAO;
    private final StudentDAO studentDAO;   // 현재는 거의 안 쓰지만 생성자 호환 때문에 유지
    private int deviceFailCount = 0;
    private long deviceLockUntil = 0L;
    private int deviceLockLevel = 0;

    public AuthService(StudentDAO studentDAO, ProfessorDAO professorDAO) {
        this.studentDAO = studentDAO;
        this.professorDAO = professorDAO;
    }

    public boolean insertProfessor(ProfessorDTO professorDTO) throws SQLException {

        if (!professorDTO.getProfessorId().matches("^P\\d{4}$")) {
            throw new RuntimeException("\n교수 번호는 'P' 로 시작하는 숫자 4자리여야 합니다.");
        }

        if (!professorDTO.getProfessorNo().matches("^\\d{6}-\\d{7}$")) {
            throw new RuntimeException("\n주민번호 형식이 올바르지 않습니다. (######-#######)");
        }

        if (!professorDTO.getProfessorPhone().matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
            throw new RuntimeException("\n전화번호 형식이 올바르지 않습니다. (010-####-####)");
        }

        String pw = professorDTO.getProfessorPw();
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$";

        if (!pw.matches(regex)) {
            throw new RuntimeException("\n비밀번호는 영문, 숫자, 특수문자를 포함하여 최소 8자 이상이어야 합니다.");
        }

        Connection con = JDBCTemplate.getConnection();

        try {
            if (professorDAO.existById(con, professorDTO.getProfessorId())) {
                throw new RuntimeException("\n이미 사용 중인 교수 번호입니다.");
            }

            if (professorDAO.existByEmail(con, professorDTO.getProfessorEmail())) {
                throw new RuntimeException("\n이미 등록된 이메일 주소 입니다.");
            }

            String result = professorDAO.save(con, professorDTO);

            if ("SUCCESS".equals(result)) {
                JDBCTemplate.commit(con);
                return true;
            } else {
                JDBCTemplate.rollback(con);
                return false;
            }

        } catch (SQLException e) {
            JDBCTemplate.rollback(con);
            throw new RuntimeException("교수 데이터 입력 중 Error 발생 🚨 " + e.getMessage(), e);
        } finally {
            JDBCTemplate.close(con);
        }
    }

    private boolean isProfessorRole(String role) {
        return "PROFESSOR".equalsIgnoreCase(role);
    }

    public int getCaptchaThreshold(String role) {
        return isProfessorRole(role) ? 2 : 3;
    }

    public int getLockThreshold(String role) {
        return 5;
    }

    private int getLockMinutes(String role, int lockLevel) {
        if (isProfessorRole(role)) {
            if (lockLevel == 1) return 5;
            if (lockLevel == 2) return 10;
            return 30;
        }

        if (lockLevel == 1) return 3;
        if (lockLevel == 2) return 5;
        if (lockLevel == 3) return 10;
        return 30;
    }

    public int getDeviceFailCount() {
        return deviceFailCount;
    }

    public boolean needHumanCheck(String role) {
        return deviceFailCount >= getCaptchaThreshold(role);
    }

    public boolean isDeviceLocked() {
        if (deviceLockUntil == 0L) {
            return false;
        }

        if (System.currentTimeMillis() >= deviceLockUntil) {
            deviceLockUntil = 0L;
            return false;
        }

        return true;
    }

    public long getRemainingDeviceLockSeconds() {
        if (deviceLockUntil == 0L) {
            return 0;
        }

        long remain = (deviceLockUntil - System.currentTimeMillis()) / 1000;
        return Math.max(remain, 0);
    }

    public int getCurrentDeviceLockLevel() {
        return deviceLockLevel;
    }

    public int recordLoginFailure(String role) {
        deviceFailCount++;

        int lockThreshold = getLockThreshold(role);

        if (deviceFailCount % lockThreshold == 0) {
            deviceLockLevel++;

            int lockMinutes = getLockMinutes(role, deviceLockLevel);
            deviceLockUntil = System.currentTimeMillis() + lockMinutes * 60_000L;
        }

        return deviceFailCount;
    }

    public void recordLoginSuccess() {
        deviceFailCount = 0;
        deviceLockUntil = 0L;
        deviceLockLevel = 0;
    }


    public LoginUserDTO login(LoginRequestDTO request) {

        if (request == null) {
            return null;
        }

        Connection con = JDBCTemplate.getConnection();
        StudentDAO loginStudentDAO = new StudentDAO(con);

        try {
            if ("STUDENT".equalsIgnoreCase(request.getRole())) {
                return loginStudentDAO.loginStudent(request);
            } else if ("PROFESSOR".equalsIgnoreCase(request.getRole())) {
                return professorDAO.loginProfessor(con, request);
            }
            return null;
        } finally {
            JDBCTemplate.close(con);
        }
    }



    public int registerStudent(StudentDTO student) {

        String pw = student.getStudentPw();
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$";

        if (!pw.matches(regex)) {
            throw new RuntimeException("비밀번호는 영문, 숫자, 특수문자를 포함해 8자 이상이어야 합니다.");
        }

        Connection connection = JDBCTemplate.getConnection();

        try {
            StudentDAO registerStudentDAO = new StudentDAO(connection);

            if (registerStudentDAO.existsByStudentId(student.getStudentId())) {
                throw new RuntimeException("이미 사용 중인 학번입니다.");
            }

            int result = registerStudentDAO.save(student);

            if (result > 0) {
                connection.commit();
                return result;
            } else {
                connection.rollback();
                throw new RuntimeException("회원가입 저장에 실패했습니다.");
            }

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException("롤백 중 오류 발생", ex);
            }
            throw new RuntimeException("학생 회원가입 중 DB 오류 발생: " + e.getMessage(), e);

        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Connection 종료 중 오류 발생", e);
            }
        }
    }
}