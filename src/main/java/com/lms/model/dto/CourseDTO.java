package com.lms.model.dto;

public class CourseDTO {
    private String classNo;
    private String className;
    private String classTime;
    private String classRoom;
    private String classType;
    private String professorId;

    public CourseDTO() {
    }

    public CourseDTO(String classNo, String className, String classTime, String classRoom, String classType, String professorId) {
        this.classNo = classNo;
        this.className = className;
        this.classTime = classTime;
        this.classRoom = classRoom;
        this.classType = classType;
        this.professorId = professorId;
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

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
                "classNo='" + classNo + '\'' +
                ", className='" + className + '\'' +
                ", classTime='" + classTime + '\'' +
                ", classRoom='" + classRoom + '\'' +
                ", classType='" + classType + '\'' +
                ", professorId='" + professorId + '\'' +

                '}';
    }

    public void setProfessorName(String professorName) {

    }
}
