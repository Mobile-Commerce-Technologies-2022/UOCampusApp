package com.example.uocampus.forum;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.uocampus.MainActivity;
import com.example.uocampus.R;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post_Page extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Submit_Post_Func sub = new Submit_Post_Func();
    private Button submit_button, back_button;
    private EditText content,title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);
        submit_button = findViewById(R.id.send_btn);
        content = findViewById(R.id.content_et);
        back_button = findViewById(R.id.back_btn);
        title = findViewById(R.id.post_title);

        submit_button.setOnClickListener((view -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String DateTime = simpleDateFormat.format(date);
                sub.setTime(DateTime);
                sub.setPost_content(content.getText().toString());
                sub.setHostID(Integer.toString(1));
                sub.setTitle(title.getText().toString());
                DBControl loader = new DBControl(this,"TEST");
                loader.addData(sub);
                String str = "Your post has been sent";
                Intent intent = new Intent(this, Entry_page.class);
                startActivity(intent);

                Toast.makeText(this, str, Toast.LENGTH_LONG).show();
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
