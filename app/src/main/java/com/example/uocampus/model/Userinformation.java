package com.example.uocampus.model;

public class Userinformation {

    public String name;
    public int phonenumer;
    public int studentnumber;

    public Userinformation(){

    }

    public Userinformation(String name, int phonenumer, int studentnumber) {
        this.name = name;
        this.phonenumer = phonenumer;
        this.studentnumber = studentnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhonenumer() {
        return phonenumer;
    }

    public void setPhonenumer(int phonenumer) {
        this.phonenumer = phonenumer;
    }

    public int getStudentnumber() {
        return studentnumber;
    }

    public void setStudentnumber(int studentnumber) {
        this.studentnumber = studentnumber;
    }



}
