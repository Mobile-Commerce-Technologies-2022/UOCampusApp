package com.example.uocampus.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.uocampus.dao.ForumDao;
import com.example.uocampus.model.PostModel;

import java.util.ArrayList;
import java.util.List;

public class ForumDaoImpl extends SQLiteOpenHelper implements ForumDao {

    private static final String TAG = ForumDaoImpl.class.getSimpleName();
    private static final Integer VERSION = 1;
    private static final String dbname = "ForumPosts.db";

    public ForumDaoImpl(@Nullable Context context) {
        super(context, dbname, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String st = "CREATE TABLE IF NOT EXISTS Posts (time STRING primary key, " +
                " username TEXT," +
                " title TEXT," +
                " content TEXT)";
        sqLiteDatabase.execSQL(st);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    @Override
    public boolean addPost(PostModel post) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time",post.getTime());
        values.put("username",post.getUsername());
        values.put("title", post.getTitle());
        values.put("content", post.getPost_content());

        long table = db.insert("Posts", null, values);
        db.close();
        Log.d(TAG,post.toString());
        return table != -1;
    }

    @Override
    public List<PostModel> getPosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<PostModel> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM Posts", null);
        while(cursor.moveToNext()){
            String time =  cursor.getString(0);
            String username = cursor.getString(1);
            String title = cursor.getString(2);
            String post_content = cursor.getString(3);
            list.add(new PostModel(username,time,title,post_content));
            Log.d(TAG,"username: "+username+ ", time: "+ time + ", content: "+ post_content);
        }
        cursor.close();
        db.close();

        return list;
    }


}
