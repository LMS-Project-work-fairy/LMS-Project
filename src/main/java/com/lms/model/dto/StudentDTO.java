package com.lms.model.dto;

public class StudentDTO {

    private String studentNo;
    private String studentId;
    private String studentPw;
    private String studentName;
    private String studentAddress;
    private String studentEmail;
    private String studentPhone;

    public StudentDTO() {}

    public StudentDTO(String studentId, String studentPw, String studentNo, String studentName,
                      String studentAddress, String studentEmail, String studentPhone) {
        this.studentId = studentId;
        this.studentPw = studentPw;
        this.studentPw = studentNo;
        this.studentName = studentName;
        this.studentAddress = studentAddress;
        this.studentEmail = studentEmail;
        this.studentPhone = studentPhone;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentPw() {
        return studentPw;
    }

    public void setStudentPw(String studentPw) {
        this.studentPw = studentPw;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentAddress() {
        return studentAddress;
    }

    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }
}