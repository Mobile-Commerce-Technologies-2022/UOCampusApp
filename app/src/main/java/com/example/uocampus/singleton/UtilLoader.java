package com.example.uocampus.singleton;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class UtilLoader {
    private UtilLoader(){}

    private enum Singleton {
        INSTANCE;

        private final UtilLoader instance;


        Singleton() {
            instance = new UtilLoader();
        }

        private UtilLoader getInstance() {
            return instance;
        }
    }

    public static UtilLoader getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    /**
     * Go to a given activity page
     * @param context Activity Context
     * @param cls Activity class
     */
    public void go2Activity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(context, intent, Bundle.EMPTY);
    }

    public void go2Activity(Context context, Intent intent) {
        startActivity(context, intent, Bundle.EMPTY);
    }

    public boolean checkPermissions(Context context, String permission) {
        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context,
                permission);
    }

    public void requestPermissions(Context context, String permission) {
        if(!checkPermissions(context, permission)) {
            Log.i(context.getClass().getSimpleName(), String.format("%s is requested", permission));
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{permission},
                    41);
        } else {
            Log.i(context.getClass().getSimpleName(), String.format("%s is granted", permission));
        }
    }
}
