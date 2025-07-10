package com.rtech.statusdownloaderwa.filesProvider;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.documentfile.provider.DocumentFile;

import com.rtech.statusdownloaderwa.AppRoot.RootApp;
import com.rtech.statusdownloaderwa.models.MediaModel;
import com.rtech.statusdownloaderwa.utils.MediaTypeChecker;

import java.io.File;
import java.util.ArrayList;

public class Provider {
   public static SharedPreferences prefs=RootApp.getAppPrefs();
    public static ArrayList<MediaModel> getMediaAll() {
        ArrayList<MediaModel> tempArray=new ArrayList<>();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            File files=new File(Environment.getExternalStorageDirectory(),"Android/media/com.whatsapp/WhatsApp/Media/.Statuses/");

            if(!files.exists()||!files.isDirectory()) { Log.d("ErrorInApp", "getMediaAll: "+"something went wrong");}
            else{
                File[] list=files.listFiles();

                for(File currentFile:list){
                    if(currentFile.isFile()){
                        if(MediaTypeChecker.isVideo(currentFile)){
                            tempArray.add(new MediaModel(currentFile.getAbsolutePath(),true,"file"));
                        }
                        else if(MediaTypeChecker.isImage(currentFile)){
                            tempArray.add(new MediaModel(currentFile.getAbsolutePath(),false,"file"));

                        }

                    }

                }

            }
        }
        else{
            String uri=prefs.getString("status_uri","null");
            DocumentFile statusFolder = DocumentFile.fromTreeUri(RootApp.getContextGlobal(), Uri.parse(uri));
            if (statusFolder != null && statusFolder.isDirectory()) {
                for (DocumentFile file : statusFolder.listFiles()) {
                    if (file.isFile()) {
                        String name = file.getName();
                        String mime = RootApp.getContextGlobal().getContentResolver().getType(file.getUri());
                        if (mime != null && (mime.startsWith("image/") || mime.startsWith("video/"))) {


                            // Load with Glide, ExoPlayer, or copy to app folder
                        }
                        if(mime !=null&& mime.startsWith("video/")){
                            tempArray.add(new MediaModel(file.getUri().toString(),true,"uri"));
                        }
                        else if(mime!=null&&mime.startsWith("image/")){
                            tempArray.add(new MediaModel(file.getUri().toString(),false,"uri"));
                        }



                    }
                }
            }




        }
        return tempArray;
    }
    public static ArrayList<MediaModel> getMediaImages() {
        ArrayList<MediaModel> tempArray=new ArrayList<>();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            File files=new File(Environment.getExternalStorageDirectory(),"Android/media/com.whatsapp/WhatsApp/Media/.Statuses/");

            if(!files.exists()||!files.isDirectory()) { Log.d("ErrorInApp", "getMediaAll: "+"something went wrong");}
            else{
                File[] list=files.listFiles();

                for(File currentFile:list){
                    if(currentFile.isFile()){
                        if(!MediaTypeChecker.isVideo(currentFile)){
                            tempArray.add(new MediaModel(currentFile.getAbsolutePath(),false,"file"));
                        }


                    }

                }

            }
        }
        else{
            String uri=prefs.getString("status_uri","null");
            DocumentFile statusFolder = DocumentFile.fromTreeUri(RootApp.getContextGlobal(), Uri.parse(uri));
            if (statusFolder != null && statusFolder.isDirectory()) {
                for (DocumentFile file : statusFolder.listFiles()) {
                    if (file.isFile()) {
                        String name = file.getName();
                        String mime = RootApp.getContextGlobal().getContentResolver().getType(file.getUri());
                        if(mime!=null&&mime.startsWith("image/")){
                            tempArray.add(new MediaModel(file.getUri().toString(),false,"uri"));
                        }



                    }
                }
            }




        }
        return tempArray;
    }
    public static ArrayList<MediaModel> getMediaVideos() {
        ArrayList<MediaModel> tempArray=new ArrayList<>();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            File files=new File(Environment.getExternalStorageDirectory(),"Android/media/com.whatsapp/WhatsApp/Media/.Statuses/");

            if(!files.exists()||!files.isDirectory()) { Log.d("ErrorInApp", "getMediaAll: "+"something went wrong");}
            else{
                File[] list=files.listFiles();

                for(File currentFile:list){
                    if(currentFile.isFile()){
                        if(MediaTypeChecker.isVideo(currentFile)){
                            tempArray.add(new MediaModel(currentFile.getAbsolutePath(),true,"file"));
                        }


                    }

                }

            }
        }
        else{
            String uri=prefs.getString("status_uri","null");
            DocumentFile statusFolder = DocumentFile.fromTreeUri(RootApp.getContextGlobal(), Uri.parse(uri));
            if (statusFolder != null && statusFolder.isDirectory()) {
                for (DocumentFile file : statusFolder.listFiles()) {
                    if (file.isFile()) {
                        String name = file.getName();
                        String mime = RootApp.getContextGlobal().getContentResolver().getType(file.getUri());

                        if(mime !=null&& mime.startsWith("video/")){
                            tempArray.add(new MediaModel(file.getUri().toString(),true,"uri"));
                        }




                    }
                }
            }




        }
        return tempArray;
    }
}