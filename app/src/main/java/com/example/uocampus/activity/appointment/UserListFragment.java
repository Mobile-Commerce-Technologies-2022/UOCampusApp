package com.example.uocampus.activity.appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uocampus.R;
import com.example.uocampus.adapter.AppointmentAdapter;
import com.example.uocampus.dao.AppointmentDao;
import com.example.uocampus.dao.impl.AppointmentDaoImpl;
import com.example.uocampus.model.StudentModel;

import android.os.Bundle;

import java.util.List;

public class UserListFragment extends AppCompatActivity {
    RecyclerView recyclerView;
    private final AppointmentDao appointmentDao = new AppointmentDaoImpl(UserListFragment.this);
    AppointmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quserlist);
        List<StudentModel> studentModelList = appointmentDao.getAppointments();

        recyclerView = findViewById(R.id.recyclerview);
        adapter = new AppointmentAdapter(this, studentModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}