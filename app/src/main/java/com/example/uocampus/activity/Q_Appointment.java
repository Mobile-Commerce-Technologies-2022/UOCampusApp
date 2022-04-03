package com.example.uocampus.activity;

import static com.example.uocampus.service.AppointmentService.notification.CHANNEL_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uocampus.R;
import com.example.uocampus.model.DBHelper;
import com.example.uocampus.model.Appointment_Model.Q_CancelQueue;
import com.example.uocampus.model.Appointment_Model.Q_UserInfo_setup;
import com.example.uocampus.model.Appointment_Model.Q_Userlist;

public class Q_Appointment extends AppCompatActivity implements Q_UserInfo_setup.DialogListener, Q_CancelQueue.DialogListener {
    private static final String TAG = "" ;
    private Button queue,cancelbtn,viewbtn;
    DBHelper DB;
    private TextView time, line;
    private NotificationManagerCompat notificationManager;
    private RelativeLayout relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        DB = new DBHelper(this);
        notificationManager = NotificationManagerCompat.from(this);
          queue = findViewById(R.id.queue);
        viewbtn = findViewById(R.id.view);
        cancelbtn = findViewById(R.id.cancelQ);
        time = findViewById(R.id.wait_text);
        line = findViewById(R.id.line_text);
        relative = findViewById(R.id.background);
        updatestatus();

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
            Log.d(TAG, String.valueOf(DB.getProfilesCount()));
            if (checkinsertdata == true) {
                Toast.makeText(Q_Appointment.this,"You are in Queue",Toast.LENGTH_SHORT).show();
                sendchannel();
                Log.d(TAG, " get data");
            } else {
                Log.d(TAG, "didnt get data");
                Toast.makeText(Q_Appointment.this,"You are not in Queue",Toast.LENGTH_SHORT).show();

            }
        }
        updatestatus();
    }

    @Override
    public void applycancelTexts(String studentTXT) {
        Boolean deletedata = DB.deletedata(studentTXT);
        if (deletedata == true) {
            Toast.makeText(Q_Appointment.this,"Queue deleted",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Q_Appointment.this,"Queue not deleted",Toast.LENGTH_SHORT).show();

        }
        updatestatus();
    }

    @SuppressLint("ResourceAsColor")
    public void updatestatus(){
        line.setText((DB.getProfilesCount()).toString());
        time.setText((DB.getapproxtime()).toString() + " mins");

        //context awareness by chaning color
        if (DB.getProfilesCount() > 3){
            relative.setBackgroundColor(R.color.grey);
        }else if(DB.getProfilesCount() > 5){
            relative.setBackgroundColor(R.color.pink);
        }
    }

    public void sendchannel(){
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_line)
                .setContentTitle("You are in Queue")
                .setContentText("Your approximate wait time is " + (DB.getapproxtime()).toString() + " mins")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(1,notification);
    }
}
