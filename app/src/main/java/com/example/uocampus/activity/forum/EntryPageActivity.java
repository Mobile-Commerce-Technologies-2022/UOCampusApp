package com.example.uocampus.activity.forum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.uocampus.R;
import com.example.uocampus.activity.MainActivity;

public class EntryPageActivity extends AppCompatActivity{
    private static final String TAG = EntryPageActivity.class.getSimpleName();
    Button viewForum,login,post,back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_entry);
        viewForum = findViewById(R.id.viewForum);
        login = findViewById(R.id.login);
        post = findViewById(R.id.post);
        back = findViewById(R.id.menu);
        buttonInit();

    }

    private void buttonInit() {
        viewForum.setOnClickListener(v -> {
            Intent intent = new Intent(EntryPageActivity.this, ForumPageActivity.class);
            startActivity(intent);
        });
        post.setOnClickListener(v -> {
            Intent intent = new Intent(EntryPageActivity.this, PostPageActivity.class);
            startActivity(intent);
        });
        login.setOnClickListener(v -> {
            Intent intent = new Intent(EntryPageActivity.this, LoginActivity.class);
            startActivity(intent);
        });
        back.setOnClickListener(v -> {
            Intent intent = new Intent(EntryPageActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}
