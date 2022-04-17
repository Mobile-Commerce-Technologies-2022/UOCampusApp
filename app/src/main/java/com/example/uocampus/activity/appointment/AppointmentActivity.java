package com.example.uocampus.activity.appointment;

import static com.example.uocampus.service.AppointmentService.notification.CHANNEL_ID;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.uocampus.R;
import com.example.uocampus.dao.AppointmentDao;
import com.example.uocampus.dao.DBHelper;
import com.example.uocampus.dao.impl.AppointmentDaoImpl;
import com.example.uocampus.model.StudentModel;

public class AppointmentActivity extends AppCompatActivity implements UserInfoFragment.DialogListener,
                                                                CancelQueueFragment.DialogListener {
    private static final String TAG = AppointmentActivity.class.getSimpleName();
    private Button btnAdd2Queue, btnCancelQueue, btnViewQueue;
    private TextView time, line;
    private NotificationManagerCompat notificationManager;
    private RelativeLayout relative;
    private AppointmentDao appointmentDao = new AppointmentDaoImpl(AppointmentActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        notificationManager = NotificationManagerCompat.from(this);
        btnAdd2Queue = findViewById(R.id.queue);
        btnViewQueue = findViewById(R.id.view);
        btnCancelQueue = findViewById(R.id.cancelQ);
        time = findViewById(R.id.wait_text);
        line = findViewById(R.id.line_text);
        relative = findViewById(R.id.background);
        updateStatus();

        btnViewQueue.setOnClickListener(view -> startActivity(new Intent(AppointmentActivity.this,
                                                                                UserListFragment.class)));
        btnAdd2Queue.setOnClickListener(view -> openDialog());

        btnCancelQueue.setOnClickListener(view -> openCancelDialog());

    }

    private void openCancelDialog(){
        CancelQueueFragment cancelQueue = new CancelQueueFragment();
        cancelQueue.show(getSupportFragmentManager(),"cancelQueue");
    }

    private void openDialog() {
        UserInfoFragment userInfo_setup = new UserInfoFragment();
        userInfo_setup.show(getSupportFragmentManager(),"userInfo_setup");
    }

    @Override
    public void addAppointment(String name, String phone, String sid) {
        if(!name.isEmpty() && !phone.isEmpty() && !sid.isEmpty()) {
            boolean isInserted = appointmentDao.insertAppointment(new StudentModel(name, sid, phone));
            Log.d(TAG, String.valueOf(appointmentDao.count()));
            if (isInserted) {
                Toast.makeText(AppointmentActivity.this,"You are in Queue",
                                                            Toast.LENGTH_SHORT).show();
                sendChannel();
                Log.d(TAG, " get data");
            } else {
                Log.d(TAG, "didn't get data");
                Toast.makeText(AppointmentActivity.this,"You are not in Queue",
                                                                Toast.LENGTH_SHORT).show();
            }
        }
        updateStatus();
    }

    @Override
    public void cancelAppointment(String studentNum) {
        Boolean isDeleted = appointmentDao.removeAppointment(studentNum);
        if (isDeleted) {
            Toast.makeText(AppointmentActivity.this,"Queue deleted",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AppointmentActivity.this,"Queue not deleted",Toast.LENGTH_SHORT).show();
        }
        updateStatus();
    }

    @SuppressLint("ResourceAsColor")
    public void updateStatus(){
        int count = appointmentDao.count();
        line.setText(String.format("%s", count));
        time.setText(String.format("%s mins", appointmentDao.getWaitingTime()));

        //context awareness by changing color
        if (count > 3 && count < 6){
            relative.setBackgroundColor(R.color.grey);
        } else if(count >= 6){
            relative.setBackgroundColor(R.color.pink);
        }
    }

    public void sendChannel(){
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_line)
                .setContentTitle("You are in Queue")
                .setContentText("Your approximate wait time is " + appointmentDao.getWaitingTime() + " mins")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(1,notification);
    }
}
