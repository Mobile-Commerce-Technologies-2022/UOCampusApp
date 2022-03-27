package com.example.uocampus.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uocampus.R;
import com.example.uocampus.model.DBHelper;
import com.example.uocampus.model.Q_view.Q_CancelQueue;
import com.example.uocampus.model.Q_view.Q_UserInfo_setup;
import com.example.uocampus.model.Q_view.Q_Userlist;

public class Q_Appointment extends AppCompatActivity implements Q_UserInfo_setup.DialogListener, Q_CancelQueue.DialogListener {
    private static final String TAG = "" ;
    private Button queue,cancelbtn,viewbtn;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        DB = new DBHelper(this);
          queue = findViewById(R.id.queue);
        viewbtn = findViewById(R.id.view);
        cancelbtn = findViewById(R.id.cancelQ);

        viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Q_Appointment.this, Q_Userlist.class));
            }
        });
        queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCancelDialog();
            }
        });


    }

    private void openCancelDialog(){
        Q_CancelQueue cancelQueue = new Q_CancelQueue();
        cancelQueue.show(getSupportFragmentManager(),"cancelQueue");
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

    @Override
    public void applycancelTexts(String studentTXT) {
        Boolean deletedata = DB.deletedata(studentTXT);
        if (deletedata == true) {
            Toast.makeText(Q_Appointment.this,"Queue deleted",Toast.LENGTH_SHORT).show();
            Log.d(TAG, " get data");
        } else {
            Log.d(TAG, "didnt get data");
            Toast.makeText(Q_Appointment.this,"Queue not deleted",Toast.LENGTH_SHORT).show();

        }
    }
}