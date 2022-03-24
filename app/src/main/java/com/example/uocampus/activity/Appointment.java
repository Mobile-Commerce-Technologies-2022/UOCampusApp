package com.example.uocampus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uocampus.R;

public class Appointment extends AppCompatActivity {
    private Button queue,menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

          queue = findViewById(R.id.queue);
        queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        UserInfo_setup userInfo_setup = new UserInfo_setup();
        userInfo_setup.show(getSupportFragmentManager(),"userInfo_setup");
    }
}