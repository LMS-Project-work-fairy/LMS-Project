package com.lms.model.dto;

public class StudentDTO {
    private String studentId;
    private String studentName;
    private String studentNo;
    private String studentAddress;
    private String studentEmail;
    private String studentPhone;
    private String studentPw;
    private String professorId;

    public StudentDTO() {
    }

    public StudentDTO(String student_id, String student_name, String student_no, String student_address, String student_email, String student_phone, String student_pw, String professorId) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentNo = studentNo;
        this.studentAddress = studentAddress;
        this.studentEmail = studentEmail;
        this.studentPhone = studentPhone;
        this.studentPw = studentPw;
        this.professorId = professorId;
    }

    public StudentDTO(String classNo, String className, String classTime, String professorName, String professorId) {
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
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

    public String getStudentPw() {
        return studentPw;
    }

    public void setStudentPw(String studentPw) {
        this.studentPw = studentPw;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "student_id=" + studentId +
                ", student_name='" + studentName + '\'' +
                ", student_no=" + studentNo +
                ", student_address='" + studentAddress + '\'' +
                ", student_email='" + studentEmail + '\'' +
                ", student_phone=" + studentPhone +
                ", student_pw=" + studentPw +
                ", professor_id=" + professorId +
                '}';
    }
}

