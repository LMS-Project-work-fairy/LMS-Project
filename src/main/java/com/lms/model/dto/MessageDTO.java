package com.lms.model.dto;

public class MessageDTO {
    private String userId;
    private String userName;
    private String studentId;
    private String professorId;
    private String receiverId;
    private String content;


    public MessageDTO() {
    }

    public MessageDTO(String userId, String userName, String studentId, String professorId, String receiverId, String content) {
        this.userId = userId;
        this.userName = userName;
        this.studentId = studentId;
        this.professorId = professorId;
        this.receiverId = receiverId;
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "MessageDTO{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", studentId='" + studentId + '\'' +
                ", professorId='" + professorId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
