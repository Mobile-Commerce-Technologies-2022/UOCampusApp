package com.example.uocampus.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Userdetails(studentnum TEXT primary key, name TEXT, phonenum TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int ii) {
        db.execSQL("drop Table if exists Userdetails");
    }

    public Boolean insertuserdata(String name, String phonenum, String studentnum){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("studentnum", studentnum);
        contentValues.put("phonenum", phonenum);
        long result = DB.insert("Userdetails",null,contentValues);
        if (result == -1){
            return false;
        }else
        {
            return true;
        }
    }

    public Boolean deletedata(String studentnum){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails where studentnum = ?",new String[]{studentnum});
        if (cursor.getCount() > 0){
            long result = DB.delete("Userdetails","studentnum = ?", new String[]{studentnum});
            if (result == -1) {
                return false;
            }else{
                return true;
            }
        }else
        {
            return false;
        }
    }


    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Userdetails",null);
        return cursor;
    }

    public Integer getProfilesCount() {
        String countQuery = "SELECT  * FROM Userdetails";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public Integer getapproxtime(){
       Integer numcount = getProfilesCount();
       if (numcount >=0 || numcount <=1){
           return 1;
       }else if (numcount >1 ||numcount <=3){
           return 10;
       }else{
           return 20;
        }
    }

    public Boolean checkStatus(){
        Integer numcount = getProfilesCount();
        if(numcount ==0) {
            return true;
        }
        else{
            return false;
        }
    }


}
