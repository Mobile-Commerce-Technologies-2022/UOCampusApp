package com.example.uocampus.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.uocampus.model.PostModel;

import java.util.List;

public class DBControl extends SQLiteOpenHelper {

    private static final String TAG = DBControl.class.getSimpleName();
    private static Integer Version = 1;
    private String id,time,post_content,title;
    private int total = 0;

    public DBControl(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBControl(@Nullable Context context, String name, int version){
        this(context,name,null,version);
    }
    public DBControl(@Nullable Context context, String name){
        this(context,name,Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        System.out.println("Database Create");
        String st = "CREATE TABLE IF NOT EXISTS TEST_1(time STRING primary key,ID TEXT, title TEXT, content TEXT)";
        sqLiteDatabase.execSQL(st);
    }
    public String getID() {

        return this.id;
    }
    public String getTime(){

        return this.time;
    }
    public String getTitle(){
        return this.title;
    }
    public String getContent(){

        return this.post_content;
    }
    public int getTotal(){

        return this.total;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int x, int y) {
    }

    public boolean addData(PostModel sub) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", sub.getPost_content());
        values.put("ID",sub.getUsername());
        values.put("title", sub.getTitle());
        values.put("time",sub.getTime());

        long table = db.insert("TEST_1", null, values);
        db.close();
        Log.d(TAG,"new value inserted - ID: " + sub.getUsername() + ", time: " + sub.getTime() + ", title: "+ sub.getTitle() + ", content: " + sub.getPost_content() + ".");
        return table != -1;
    }

    @SuppressLint("Range")
    public void getData(String dbname, List<PostModel> sp){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(dbname,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            id = cursor.getString(cursor.getColumnIndex("ID"));
            time =  cursor.getString(cursor.getColumnIndex("time"));
            title = cursor.getString(cursor.getColumnIndex("title"));
            post_content = cursor.getString(cursor.getColumnIndex("content"));
            sp.add(new PostModel(id,time,title,post_content));
            Log.d(TAG,"id: "+id+ ", time: "+ time + ", content: "+ post_content);
            total ++;
        }
        cursor.close();
        db.close();
    }



}