package com.example.uocampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uocampus.R;
import com.example.uocampus.activity.appointment.AppointmentActivity;
import com.example.uocampus.activity.forum.EntryPageActivity;
import com.example.uocampus.singleton.UtilLoader;

public class MainActivity extends AppCompatActivity {
    Button btnForum, btnAppointment,btnMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnForum = (Button) findViewById(R.id.btn_forum);
        btnAppointment = (Button) findViewById(R.id.btn_appointment);
        btnMap = (Button) findViewById(R.id.btn_map);

        btnForum.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EntryPageActivity.class);
            startActivity(intent);
        });

        btnMap.setOnClickListener((view) -> UtilLoader.getInstance().go2Activity(this, NavigatorActivity.class));

        btnAppointment.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AppointmentActivity.class);
            startActivity(intent);
        });
    }

}