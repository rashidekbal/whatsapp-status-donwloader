package com.rtech.statusdownloaderwa.AppRoot;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.ads.MobileAds;

public class RootApp extends Application {
    public static RootApp instance;
    public static SharedPreferences appPrefs;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        appPrefs=getSharedPreferences("appPrefs",MODE_PRIVATE);

    }
    public static Context getContextGlobal(){
        return instance.getApplicationContext();

    }
    public static SharedPreferences getAppPrefs(){
        return appPrefs;
    }
}
