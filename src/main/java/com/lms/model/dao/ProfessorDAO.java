package com.lms.model.dao;

import com.lms.common.QueryUtil;
import com.lms.model.dto.ProfessorDTO;
import com.mysql.cj.util.DnsSrv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDAO {

    private final Connection connection;

    public ProfessorDAO(Connection connection) {
        this.connection = connection;
    }

    public String save(ProfessorDTO newprofessor) throws SQLException {

        String query = QueryUtil.getQuery("lms.save");

        try (PreparedStatement pstmt = connection.prepareStatement(query)){

            ResultSet rset = pstmt.executeQuery();

            pstmt.setString(1,newprofessor.getProfessorId());
            pstmt.setString(2,newprofessor.getProfessorName());
            pstmt.setString(3,newprofessor.getProfessorNo());
            pstmt.setString(4,newprofessor.getProfessorAddress());
            pstmt.setString(5,newprofessor.getProfessorEmail());
            pstmt.setString(6,newprofessor.getProfessorPhone());
            pstmt.setString(7,newprofessor.getProfessorPw());

            int affectedRows = pstmt.executeUpdate();

            if(affectedRows > 0) {
                return "SUCCESS";

            }
        }

        return null;
    }
}
