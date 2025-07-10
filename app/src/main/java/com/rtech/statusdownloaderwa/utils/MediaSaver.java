package com.rtech.statusdownloaderwa.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.rtech.statusdownloaderwa.interfaces.MediaSaveInterface;

import java.io.*;

public class MediaSaver {

    public static void saveFromUri(Context context, String uriString, MediaSaveInterface callback) {
        try {
            Uri uri = Uri.parse(uriString);
            ContentResolver resolver = context.getContentResolver();
            String mime = resolver.getType(uri);

            String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mime);
            if (extension == null) extension = "mp4"; // fallback

            String fileName = "status_" + System.currentTimeMillis() + "." + extension;

            File destDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "StatusDownloaderWA");

            if (!destDir.exists()) destDir.mkdirs();

            File destFile = new File(destDir, fileName);
            InputStream in = resolver.openInputStream(uri);
            OutputStream out = new FileOutputStream(destFile);

            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            in.close();
            out.close();

            MediaScannerConnection.scanFile(context, new String[]{destFile.getAbsolutePath()}, null, null);
            callback.onSave();

        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }

    public static void saveFromFile(Context context, String path, MediaSaveInterface callback) {
        try {
            File source = new File(path);
            if (!source.exists()) {
                callback.onError("Source file not found");
                return;
            }

            String extension = path.substring(path.lastIndexOf('.') + 1);
            String fileName = "status_" + System.currentTimeMillis() + "." + extension;

            File destDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "StatusDownloaderWA");

            if (!destDir.exists()) destDir.mkdirs();

            File destFile = new File(destDir, fileName);

            FileInputStream in = new FileInputStream(source);
            FileOutputStream out = new FileOutputStream(destFile);

            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            in.close();
            out.close();

            MediaScannerConnection.scanFile(context, new String[]{destFile.getAbsolutePath()}, null, null);
            callback.onSave();

        } catch (Exception e) {
            callback.onError(e.getMessage());
        }
    }
}