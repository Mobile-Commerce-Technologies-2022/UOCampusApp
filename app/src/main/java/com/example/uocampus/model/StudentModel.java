package com.example.uocampus.model;

public class StudentModel {
    private String username;
    private String studentNum;
    private String phoneNum;

    public StudentModel(String username, String studentNum, String phoneNum) {
        this.username = username;
        this.studentNum = studentNum;
        this.phoneNum = phoneNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
