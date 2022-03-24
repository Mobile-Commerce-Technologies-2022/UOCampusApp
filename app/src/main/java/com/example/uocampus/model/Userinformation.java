package com.example.uocampus.model;

public class Userinformation {

    public String name;
    public int phonenumer;
    public int studentnumber;
    public boolean priority = false;

    public Userinformation(){

    }

    public Userinformation(String name, int phonenumer, int studentnumber, boolean priority) {
        this.name = name;
        this.phonenumer = phonenumer;
        this.studentnumber = studentnumber;
        this.priority = priority;
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

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

}
