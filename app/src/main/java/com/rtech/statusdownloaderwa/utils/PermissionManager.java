package com.rtech.statusdownloaderwa.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rtech.statusdownloaderwa.AppRoot.RootApp;

public class PermissionManager {
    public static boolean isAllGranted(String [] permissions){
       for(String permission:permissions){
           if(ContextCompat.checkSelfPermission(RootApp.getContextGlobal(),permission)!= PackageManager.PERMISSION_GRANTED)return false;
       }
       return true;
    }

    public static void getPermissions(Activity activity,String [] permissions,int requestCode){
        ActivityCompat.requestPermissions(activity,permissions,requestCode);
    }

}
