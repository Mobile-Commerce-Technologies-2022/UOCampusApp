package com.example.uocampus.activity.forum;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uocampus.R;
import com.example.uocampus.dao.ForumDao;
import com.example.uocampus.dao.impl.ForumDaoImpl;
import com.example.uocampus.model.PostModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostPageActivity extends AppCompatActivity {

    private static final String TAG = PostPageActivity.class.getSimpleName();
    private Button submit_button, back_button;
    private EditText content,title;
    private String username;
    private final ForumDao forumDao = new ForumDaoImpl(PostPageActivity.this);
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);
        submit_button = findViewById(R.id.send_btn);
        content = findViewById(R.id.content_et);
        back_button = findViewById(R.id.back_btn);
        title = findViewById(R.id.post_title);
        username = getIntent().getStringExtra("username");
        sp = getApplication().getSharedPreferences("saved_ID", Context.MODE_PRIVATE);
        username = sp.getString("username","");
        Log.i(TAG,"Host ID is: " + username);
        if (username.isEmpty()){
            Log.i(TAG,"username is null");
            String str = "Please login first";
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            initButtonListeners();
        }
    }

    @SuppressLint("SimpleDateFormat")
    public void initButtonListeners(){
        submit_button.setOnClickListener((view -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            String dateTime = simpleDateFormat.format(date);
            PostModel post = new PostModel(username,
                                            dateTime,
                                            title.getText().toString(),
                                            content.getText().toString());
            forumDao.addPost(post);

            Log.d(TAG,"Post been sent");
            String str = "Your post has been sent";
            Intent intent = new Intent(this, EntryPageActivity.class);
            startActivity(intent);
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }));

        back_button.setOnClickListener(v -> {
            Intent intent = new Intent(PostPageActivity.this, EntryPageActivity.class);
            startActivity(intent);
        });
    }




}
