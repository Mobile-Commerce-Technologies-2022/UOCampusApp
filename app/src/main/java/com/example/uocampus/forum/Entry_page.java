package com.example.uocampus.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uocampus.activity.MainActivity;
import com.example.uocampus.R;

public class Entry_page extends AppCompatActivity{
    private static final String TAG = Entry_page.class.getSimpleName();
    Button viewForum,login,post,back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_entry);
        viewForum = (Button) findViewById(R.id.viewForum);
        login = (Button) findViewById(R.id.login);
        post = (Button) findViewById(R.id.post);
        back = (Button) findViewById(R.id.menu);
        buttonInit();

    }

    private void buttonInit() {
        viewForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Entry_page.this, Forum_Page.class);
                startActivity(intent);
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Entry_page.this, Post_Page.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Entry_page.this, Login_Page.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Entry_page.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



}
