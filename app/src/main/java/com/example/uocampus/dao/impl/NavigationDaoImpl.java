package com.example.uocampus.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.uocampus.dao.NavigationDao;
import com.example.uocampus.model.FacilityModel;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class NavigationDaoImpl extends SQLiteOpenHelper implements NavigationDao {

    public NavigationDaoImpl(@Nullable Context context) {
        super(context, "Navigation.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String statement = "CREATE TABLE IF NOT EXISTS NavigationRecords" +
                " (FACILITY_NAME TEXT PRIMARY KEY, "
                + "LAT TEXT, "
                + "LNG TEXT)";

        sqLiteDatabase.execSQL(statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public List<FacilityModel> queryAll() {
        SQLiteDatabase db = this.getReadableDatabase();

        List<FacilityModel> list = new ArrayList<>();
        String query = "SELECT * FROM NavigationRecords;";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                String facilityName = cursor.getString(0);
                String lat = cursor.getString(1);
                String lng = cursor.getString(2);

                FacilityModel facility = new FacilityModel(facilityName,
                        new LatLng(Double.parseDouble(lat),
                                Double.parseDouble(lng)));
                list.add(facility);

            } while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    @Override
    public boolean addFacilityModel(FacilityModel facility) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("FACILITY_NAME", String.valueOf(facility.getNAME()));
        contentValues.put("LAT", String.valueOf(facility.getLatLng().latitude));
        contentValues.put("LNG", String.valueOf(facility.getLatLng().longitude));

        long result = db.insert("NavigationRecords", null, contentValues);
        db.close();
        return result != -1;
    }
}

