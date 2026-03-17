package com.lms.model.service;

import com.lms.model.dao.ProfessorDAO;
import com.lms.model.dto.ProfessorDTO;

import java.sql.SQLException;

public class AuthService {

    private final ProfessorDAO professorDAO;


    public AuthService(ProfessorDAO professorDAO) {

        this.professorDAO = professorDAO;
    }


    public boolean insertProfessor(ProfessorDTO newprofessor) {
        try {
            String result =  professorDAO.save(newprofessor) ;
            return result != null && !result.isEmpty();
        } catch (SQLException e) {
            throw new RuntimeException("교수 데이터 입력 중 Error 발생 🚨" + e);
        }
    }
}
