package com.example.uocampus.model.Q_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uocampus.R;
import com.example.uocampus.model.DBHelper;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Q_Userlist extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<String> name,sid;
    DBHelper DB;
    MyAdaptor adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quserlist);
        DB = new DBHelper(this);
        name = new ArrayList<>();
        sid = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new MyAdaptor(this,name,sid);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
    }

    private void displaydata()
    {
        Cursor cursor = DB.getdata();
        if(cursor.getCount()==0)
        {
            Toast.makeText(Q_Userlist.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            while(cursor.moveToNext())
            {
                name.add(cursor.getString(1));
                sid.add(cursor.getString(0));
            }
        }
    }
}