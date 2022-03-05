package com.example.uocampus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button Forum_botton,Appoint_botton,Map_botton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Forum_botton = (Button) findViewById(R.id.forum);
        Appoint_botton = (Button) findViewById(R.id.appointment);
        Map_botton = (Button) findViewById(R.id.map);

        Forum_botton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Forum.class);
                startActivity(intent);
            }
        });
    }

}