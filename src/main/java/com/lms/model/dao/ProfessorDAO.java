package com.lms.model.dao;

import com.lms.common.JDBCTemplate;
import com.lms.common.QueryUtil;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorDAO {

    public LoginUserDTO loginProfessor(Connection con, LoginRequestDTO request) {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        LoginUserDTO loginUser = null;

        String qeury = QueryUtil.getQuery("loginProfessor");

        try {
            pstmt = con.prepareStatement(qeury);
            pstmt.setString(1, request.getUserId());
            pstmt.setString(2, request.getPassword());

            rset = pstmt.executeQuery();

            if (rset.next()) {
                loginUser = new LoginUserDTO();
                loginUser.setRole("PROFESSOR");
                loginUser.setUserId(rset.getString("professor_id"));
                loginUser.setUserName(rset.getString("professor_name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException("교수 로그인 조회 실패", e);
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }

        return loginUser;
    }
}