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

public class Login_Page extends AppCompatActivity {

    private static final String TAG = Login_Page.class.getSimpleName();
    private Button login,logout,back;
    private EditText nickname;
    private Submit_Post_Func sub = new Submit_Post_Func();
    SharedPreferences sp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        init();
        sp = getSharedPreferences( "saved_ID", Context.MODE_PRIVATE);

    }
    private void init(){
        login = findViewById(R.id.login_btn);
        logout = findViewById(R.id.logout_btn);
        back = findViewById(R.id.login_back);
        nickname = findViewById(R.id.nickname);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Page.this, Entry_page.class);
                startActivity(intent);
            }
        });
        //创建login button  关联user的 db， 设置全局Host ID
        login.setOnClickListener((view -> {
            if(nickname.getText().toString().isEmpty()){
                String str = "Please enter a valid nick name";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "nick name is null");
            }else {
                String str = "Your nickname is: " + nickname.getText().toString();
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "account nickname set to " + nickname.getText().toString());
                saveData();
                Intent intent = new Intent(Login_Page.this, Entry_page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                intent.putExtra("hostID", nickname.getText().toString());
                Login_Page.this.startActivity(intent);
                finish();
            }
        }));

        logout.setOnClickListener((view -> {
            String str = "Your have logged out";
            Log.i(TAG,"account logged out, host ID set to null");
            Toast.makeText(this, str , Toast.LENGTH_SHORT).show();
            saveData();
            Intent intent = new Intent(Login_Page.this, Entry_page.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            intent.putExtra("hostID", "");
            Login_Page.this.startActivity(intent);
            finish();
        }));

    }
    public void saveData(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("hostID",nickname.getText().toString());
        editor.commit();
    }
}
