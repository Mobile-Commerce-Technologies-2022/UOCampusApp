package com.example.uocampus.forum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uocampus.activity.MainActivity;
import com.example.uocampus.R;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post_Page extends AppCompatActivity {

    private static final String TAG = Post_Page.class.getSimpleName();
    private Submit_Post_Func sub = new Submit_Post_Func();
    private Button submit_button, back_button;
    private EditText content,title;
    private String hostID;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);
        submit_button = findViewById(R.id.send_btn);
        content = findViewById(R.id.content_et);
        back_button = findViewById(R.id.back_btn);
        title = findViewById(R.id.post_title);
        hostID = getIntent().getStringExtra("hostID");
        sp = getApplication().getSharedPreferences("saved_ID", Context.MODE_PRIVATE);
        hostID = sp.getString("hostID","");
        Log.i(TAG,"Host ID is: " + hostID);
        if (hostID.isEmpty()){
            Log.i(TAG,"Host ID is null");
            String str = "Please login first";
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Login_Page.class);
            startActivity(intent);
            }
        else {
            buttonFunc();
        }
    }

    public void buttonFunc(){
        submit_button.setOnClickListener((view -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String DateTime = simpleDateFormat.format(date);
            sub.setHostID(hostID);
            sub.setTime(DateTime);
            sub.setPost_content(content.getText().toString());
            sub.setTitle(title.getText().toString());
            DBControl loader = new DBControl(this, "TEST_1");
            loader.addData(sub);
            Log.d(TAG,"Post been sent");
            String str = "Your post has been sent";
            Intent intent = new Intent(this, Entry_page.class);
            startActivity(intent);
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }));

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Post_Page.this, Entry_page.class);
                startActivity(intent);
            }
        });
    }




}
