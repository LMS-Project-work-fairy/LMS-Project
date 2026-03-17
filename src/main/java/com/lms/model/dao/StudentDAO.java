package com.lms.model.dao;

import com.lms.common.JDBCTemplate;
import com.lms.common.QueryUtil;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDAO {
    public LoginUserDTO loginStudent(Connection con, LoginRequestDTO request) {
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        LoginUserDTO loginUser = null;

        String query = QueryUtil.getQuery("loginStudent");

        try{
            pstmt = con.prepareStatement(query);
            pstmt.setString(1,request.getUserId());
            pstmt.setString(2, request.getPassword());

            rset = pstmt.executeQuery();

            if (rset.next()){
                loginUser = new LoginUserDTO();
                loginUser.setRole("STUDENT");
                loginUser.setUserId(rset.getString("student_id"));
                loginUser.setUserName(rset.getString("student_name"));
            }
        } catch (SQLException e){
            throw new RuntimeException("학생 로그인 조회 실패", e);
        }finally{
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }
        return loginUser;

    }
}
