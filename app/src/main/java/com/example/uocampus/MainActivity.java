package com.example.uocampus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uocampus.forum.Entry_page;

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
                Intent intent = new Intent(MainActivity.this, Entry_page.class);
                startActivity(intent);
            }

        });
    }
    private void initialization(){
        if(PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    16);
        }
    }

}