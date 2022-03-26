package com.example.uocampus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uocampus.Forum;
import com.example.uocampus.R;
import com.example.uocampus.activity.Q_view.Q_Appointment;

public class MainActivity extends AppCompatActivity {
    Button btnForum, btnAppointment,btnMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnForum = (Button) findViewById(R.id.btn_forum);
        btnAppointment = (Button) findViewById(R.id.btn_appointment);
        btnMap = (Button) findViewById(R.id.btn_map);

        btnForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Forum.class);
                startActivity(intent);
            }
        });

        btnAppointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Q_Appointment.class);
                startActivity(intent);
            }
        });
    }

}