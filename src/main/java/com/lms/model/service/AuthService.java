package com.lms.model.service;
import com.lms.model.dao.ProfessorDAO;
import com.lms.model.dto.ProfessorDTO;
import java.sql.SQLException;
import com.lms.common.JDBCTemplate;
import com.lms.model.dao.StudentDAO;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import java.sql.Connection;

public class AuthService {

    private final ProfessorDAO professorDAO;
    private final StudentDAO studentDAO;

    public AuthService(StudentDAO studentDAO,ProfessorDAO professorDAO) {
        this.studentDAO = studentDAO;
        this.professorDAO = professorDAO;
    }


    public boolean insertProfessor(ProfessorDTO newprofessor) {
        String pw = newprofessor.getProfessorPw();

        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()]).{8,}$";
        if (!pw.matches(regex)) {
            throw new RuntimeException("비밀번호는 최소 8자 이상 이여야 합니다.");
        }

            try {
                String result = professorDAO.save(newprofessor);
                return result != null && !result.isEmpty();
            } catch (SQLException e) {
                throw new RuntimeException("교수 데이터 입력 중 Error 발생 🚨" + e);
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
