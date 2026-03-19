package com.lms.model.dao;


import com.lms.common.QueryUtil;
import com.lms.model.dto.CourseDTO;
import com.lms.model.dto.ProfessorDTO;
import com.mysql.cj.util.DnsSrv;
import com.lms.common.JDBCTemplate;
import com.lms.common.QueryUtil;
import com.lms.model.dto.LoginRequestDTO;
import com.lms.model.dto.LoginUserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    private final Connection connection;

    public ProfessorDAO(Connection connection) {
        this.connection = connection;
    }


    // 교수 회원가입 정보 저장
    public String save(ProfessorDTO newprofessor) throws SQLException {

        String query = QueryUtil.getQuery("professor.save");

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            ResultSet rset = pstmt.executeQuery();

            pstmt.setString(1, newprofessor.getProfessorId());
            pstmt.setString(2, newprofessor.getProfessorName());
            pstmt.setString(3, newprofessor.getProfessorNo());
            pstmt.setString(4, newprofessor.getProfessorAddress());
            pstmt.setString(5, newprofessor.getProfessorEmail());
            pstmt.setString(6, newprofessor.getProfessorPhone());
            pstmt.setString(7, newprofessor.getProfessorPw());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "SUCCESS";

            }
        }

        return null;
    }

    //교수 로그인 메소드
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
