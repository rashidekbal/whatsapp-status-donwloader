package com.rtech.statusdownloaderwa.utils;

import java.io.File;

public class MediaTypeChecker {
    public static boolean isVideo(File file){
        String name=file.getName().toLowerCase();
        return name.endsWith(".mp4")||name.endsWith(".mkv")||name.endsWith(".3gp")||name.endsWith("avi");
    }
    public static boolean isImage(File file){
        String name=file.getName().toLowerCase();
        return name.endsWith(".jpg")||name.endsWith(".jpeg")||name.endsWith(".png")||name.endsWith("webp");
    }

}
