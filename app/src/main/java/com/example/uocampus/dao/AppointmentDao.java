package com.example.uocampus.dao;

import com.example.uocampus.model.StudentModel;

import java.util.List;

public interface AppointmentDao {

    boolean insertAppointment(StudentModel user);

    boolean removeAppointment(String studentNum);

    List<StudentModel> getAppointments();

    Integer count();

    Integer getWaitingTime();

}
