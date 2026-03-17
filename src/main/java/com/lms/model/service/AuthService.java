package com.lms.model.service;

import com.lms.common.JDBCTemplate;
import com.lms.model.dao.ProfessorDAO;
import com.lms.model.dao.StudentDAO;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;

import java.sql.Connection;

public class AuthService {

    private final StudentDAO studentDAO = new StudentDAO();
    private final ProfessorDAO professorDAO = new ProfessorDAO();

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
