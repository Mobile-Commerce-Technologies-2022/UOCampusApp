package com.example.uocampus.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uocampus.dao.AppointmentDao;
import com.example.uocampus.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDaoImpl extends SQLiteOpenHelper implements AppointmentDao {

    public AppointmentDaoImpl(Context context) {
        super(context, "Appointment.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table UserDetails(studentNum TEXT primary key," +
                                            " username TEXT," +
                                            " phoneNum TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists UserDetails");
    }

    @Override
    public boolean insertAppointment(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("studentNum", student.getStudentNum());
        contentValues.put("username", student.getUsername());
        contentValues.put("phoneNum", student.getPhoneNum());
        long result = db.insert("UserDetails",null, contentValues);
        db.close();

        return result != -1;
    }

    @Override
    public boolean removeAppointment(String studentNum) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from Userdetails where studentNum = ?",new String[]{studentNum});
        if (cursor.getCount() > 0){
            long result = db.delete("UserDetails","studentNum = ?", new String[]{studentNum});
            db.close();
            return result != -1;
        }
        db.close();
        return false;
    }

    @Override
    public List<StudentModel> getAppointments() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<StudentModel> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("Select * from UserDetails",null);

        while(cursor.moveToNext()) {
            String username = cursor.getString(0);
            String studentNum = cursor.getString(1);
            String phoneNum = cursor.getString(2);
            list.add(new StudentModel(username, studentNum, phoneNum));
        }
        cursor.close();
        db.close();
        return list;
    }

    @Override
    public Integer count() {
        String countQuery = "SELECT  * FROM UserDetails";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    @Override
    public Integer getWaitingTime() {
        Integer count = count();
        if (count == 0 || count == 1) {
            return 1;
        } else if (count > 1 && count <= 3) {
            return 10;
        } else {
            return 20;
        }
    }
}
