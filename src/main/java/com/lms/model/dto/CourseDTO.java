package com.lms.model.dto;

public class CourseDTO {
    // 강의 정보
    private String classNo;      // 강의번호
    private String className;    // 강의명
    private double classPoint;   // 학점
    private String classTime;    // 시간표
    private String classRoom;    // 강의실
    private String classType;    // 분류
    private String classTask;    // 과제
    private String professorId;  // 교수번호

    // 수강 학생 정보
    private String studentId;    // 학번
    private String studentName;  // 학생 이름
    private String enrollDate;   // 수강신청일
    private double score;        // 성적
    private boolean status;      // 수강상태

    public CourseDTO(){}

    public CourseDTO(String classNo, String className, double classPoint, String classTime, String classRoom, String classType, String classTask, String professorId, String studentId, String studentName, String enrollDate, double score, boolean status) {
    private String classNo;
    private String className;
    private String classPoint;
    private String classTime;
    private String classRoom;
    private String classType;
    private String professorId;
    private String classTask;

    private String professorName;

    public CourseDTO() {
    }

    public CourseDTO(String classNo, String className, String classPoint, String classTime, String classRoom, String classType, String professorId, String classTask) {
        this.classNo = classNo;
        this.className = className;
        this.classPoint = classPoint;
        this.classTime = classTime;
        this.classRoom = classRoom;
        this.classType = classType;
        this.classTask = classTask;
        this.professorId = professorId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.enrollDate = enrollDate;
        this.score = score;
        this.status = status;
        this.professorId = professorId;
        this.classTask = classTask;
    }

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    public double getClassPoint() {
        return classPoint;
    }

    public void setClassPoint(double classPoint) {
    public String getClassPoint() {
        return classPoint;
    }

    public void setClassPoint(String classPoint) {
        this.classPoint = classPoint;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getClassTask() {
        return classTask;
    }

    public void setClassTask(String classTask) {
        this.classTask = classTask;
    }
    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
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

    public String getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(String enrollDate) {
        this.enrollDate = enrollDate;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public String toString() {
        if (studentId != null) {
            // 학생 정보가 들어있을 때 (수강생 명단 출력용)
            String scoreDisplay = (score > 0.0) ? String.valueOf(score) : "미입력";
            return "[학번: " + studentId + "] " + studentName + " | 성적: " + scoreDisplay;
        } else {
            // 강의 정보만 들어있을 때 (담당 과목 조회용)
            return "[" + classNo + "] " + className + " (강의실: " + classRoom + ", 시간: " + classTime + ")";
        }
    }

    public String getClassTask() {
        return classTask;
    }

    public void setClassTask(String classTask) {
        this.classTask = classTask;
    }

    @Override
    public String toString() {
        return "\n강의명: " + className + " (" + classNo + ") " +
                "\n강의실: " + classRoom + " (" + classTime + ") " +
                "\n강의 종류: " + classType +
                "\n학점: " + classPoint +
                "\n교수: " + professorName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

}

