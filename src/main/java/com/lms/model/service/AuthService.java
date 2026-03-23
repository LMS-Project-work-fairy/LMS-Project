package com.lms.model.service;
import com.lms.model.dao.ProfessorDAO;
import com.lms.model.dto.ProfessorDTO;
import java.sql.SQLException;
import com.lms.common.JDBCTemplate;
import com.lms.model.dao.StudentDAO;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import java.sql.Connection;
import java.util.List;

public class AuthService {

    private final ProfessorDAO professorDAO;
    private final StudentDAO studentDAO;

    public AuthService(StudentDAO studentDAO,ProfessorDAO professorDAO) {
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
            throw new RuntimeException("\n비밀번호는 최소 8자 이상 이여야 합니다.");
        }

        Connection con = JDBCTemplate.getConnection();

        try {
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
                throw new RuntimeException("교수 데이터 입력 중 Error 발생 🚨" + e);
            } finally {
                JDBCTemplate.close(con);
            }


    }

    

    public LoginUserDTO login(LoginRequestDTO request) {

        if(request == null){
            return null;
        }

        Connection con = JDBCTemplate.getConnection();

        try{
            if ("STUDENT".equalsIgnoreCase(request.getRole())){
                return studentDAO.loginStudent(con,request);
            }else if ("PROFESSOR".equalsIgnoreCase(request.getRole())){
                return professorDAO.loginProfessor(con,request);
            }
            return null;
        }finally{
            JDBCTemplate.close(con);
        }
    }
}
