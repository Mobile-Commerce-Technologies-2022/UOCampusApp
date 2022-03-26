package com.example.uocampus.activity.Q_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uocampus.R;
import com.example.uocampus.model.DBHelper;

public class Q_Appointment extends AppCompatActivity implements Q_UserInfo_setup.DialogListener {
    private static final String TAG = "" ;
    private Button queue,menu,viewbtn;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        DB = new DBHelper(this);
          queue = findViewById(R.id.queue);
        viewbtn = findViewById(R.id.view);
        queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


    }

    private void openDialog() {
        Q_UserInfo_setup userInfo_setup = new Q_UserInfo_setup();
        userInfo_setup.show(getSupportFragmentManager(),"userInfo_setup");
    }

    @Override
    public void applyTexts(String name, String phone, String sid) {
        if(name!=null&phone!=null&sid!=null) {
            Boolean checkinsertdata = DB.insertuserdata(name, phone, sid);
            if (checkinsertdata == true) {
                Toast.makeText(Q_Appointment.this,"You are in Queue",Toast.LENGTH_SHORT).show();
                Log.d(TAG, " get data");
            } else {
                Log.d(TAG, "didnt get data");
                Toast.makeText(Q_Appointment.this,"You are not in Queue",Toast.LENGTH_SHORT).show();

            }
        }
    }
}