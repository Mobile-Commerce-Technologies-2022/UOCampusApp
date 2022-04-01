package com.example.uocampus.forum;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.List;

public class DBControl extends SQLiteOpenHelper {
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
        String st = "CREATE TABLE IF NOT EXISTS TEST(time STRING primary key,ID INTEGER, title TEXT, content TEXT)";
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

    public boolean addData(Submit_Post_Func sub) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", sub.getPost_content());
        values.put("ID",sub.getHostID());
        values.put("title", sub.getTitle());
        values.put("time",sub.getTime());

        long table = db.insert("TEST", null, values);
        db.close();
        System.out.println("new value inserted - ID: " + sub.getHostID() + ", time: " + sub.getTime() + ", title: "+ sub.getTitle() + ", content: " + sub.getPost_content() + ".");
        return table != -1;
    }

    @SuppressLint("Range")
    public void getData(String dbname, List<Submit_Post_Func> sp){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(dbname,new String[] {"ID","time","title","Content"},"id=?",new String[]{"1"},null,null,null);
        while(cursor.moveToNext()){

            id = cursor.getString(cursor.getColumnIndex("ID"));
            time =  cursor.getString(cursor.getColumnIndex("time"));
            title = cursor.getString(cursor.getColumnIndex("title"));
            post_content = cursor.getString(cursor.getColumnIndex("content"));
            sp.add(new Submit_Post_Func(id,time,title,post_content));
//            System.out.println("id: "+id+ ", time: "+ time + ", content: "+ post_content);
            total ++;
        }
        cursor.close();
        db.close();
    }



}