package com.example.uocampus.activity.forum;

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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private String username;
    SharedPreferences sp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        init();
        sp = getSharedPreferences( "saved_ID", Context.MODE_PRIVATE);

    }
    private void init(){
        Button login = findViewById(R.id.login_btn);
        Button logout = findViewById(R.id.logout_btn);
        Button back = findViewById(R.id.login_back);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, EntryPageActivity.class);
            startActivity(intent);
        });
        //创建login button  关联user的 db， 设置全局Host ID
        login.setOnClickListener((view -> {
            username = ((EditText)findViewById(R.id.nickname)).getText().toString();
            if(username.isEmpty()){
                String str = "Please enter a valid nick name";
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "nick name is null");
            }else {
                String str = "Your nickname is: " + username;
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "account nickname set to " + username);
                saveData();
                Intent intent = new Intent(LoginActivity.this, EntryPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
                intent.putExtra("username", username);
                LoginActivity.this.startActivity(intent);
                finish();
            }
        }));

        logout.setOnClickListener((view -> {
            String str = "Your have logged out";
            Log.i(TAG,"account logged out, host ID set to null");
            Toast.makeText(this, str , Toast.LENGTH_SHORT).show();
            saveData();
            Intent intent = new Intent(LoginActivity.this, EntryPageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            intent.putExtra("username", "");
            LoginActivity.this.startActivity(intent);
            finish();
        }));

    }
    public void saveData(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.apply();
    }
}
