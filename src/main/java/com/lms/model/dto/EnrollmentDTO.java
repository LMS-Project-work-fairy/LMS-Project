package com.lms.model.dto;

public class EnrollmentDTO {
    private String studentId;
    private String classNo;
    private String enrollDate;
    private String score;
    private String status;

    private String studentName;
    private String className;
    private String professorName;

    public EnrollmentDTO() {
    }

    public EnrollmentDTO(String studentId, String classNo, String enrollDate, String score, String status) {
        this.studentId = studentId;
        this.classNo = classNo;
        this.enrollDate = enrollDate;
        this.score = score;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(String enrollDate) {
        this.enrollDate = enrollDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "수강 신청 내역 =" +
                "\n학생명: " + studentName + " (" + studentId + ")" +
                ",\n과목명: " + className + " (" + classNo + ")" +
                "\n교수명: " + professorName +
                "\n신청일: " + enrollDate;
    }

    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public String getProfessorName() {
        return professorName;
    }
    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }
}
