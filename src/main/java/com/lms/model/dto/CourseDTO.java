package com.lms.model.dto;

public class CourseDTO {
    private String classNo;
    private String className;
    private String classPoint;
    private String classTime;
    private String classRoom;
    private String classType;
    private String professorId;
    private String classTask;



    public CourseDTO() {
    }


    public CourseDTO(String classNo, String className, String classPoint,
                     String classTime, String classRoom,
                     String classType, String professorId,
                     String classTask) {
        this.classNo = classNo;
        this.className = className;
        this.classPoint = classPoint;
        this.classTime = classTime;
        this.classRoom = classRoom;
        this.classType = classType;
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

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }

    public String getClassTask() {
        return classTask;
    }

    public void setClassTask(String classTask) {
        this.classTask = classTask;
    }


    @Override
    public String toString() {
        return "CourseDTO{" +
                "classNo='" + classNo + '\'' +
                ", className='" + className + '\'' +
                ", classPoint=" + classPoint +
                ", classTime='" + classTime + '\'' +
                ", classRoom='" + classRoom + '\'' +
                ", classType='" + classType + '\'' +
                ", classTask='" + classTask + '\'' +
                ", professorId='" + professorId + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", enrollDate='" + enrollDate + '\'' +
                ", score=" + score +
                ", status=" + status +
                '}';
    }
}

