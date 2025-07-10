package com.rtech.statusdownloaderwa.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.rtech.statusdownloaderwa.AppRoot.RootApp;
import com.rtech.statusdownloaderwa.R;
import com.rtech.statusdownloaderwa.databinding.ActivityOpenMediaBinding;
import com.rtech.statusdownloaderwa.interfaces.MediaSaveInterface;
import com.rtech.statusdownloaderwa.utils.MediaSaver;

import java.io.File;

public class OpenMedia extends AppCompatActivity {
    ActivityOpenMediaBinding mainXml;
    Intent intentdata;
    String mediaType,path;
    boolean isVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mainXml=ActivityOpenMediaBinding.inflate(getLayoutInflater());
        setContentView(mainXml.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setSupportActionBar(mainXml.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Preview");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(0); // clear light status flag
            window.setStatusBarColor(Color.BLACK); // or any dark color you're using
        }
        MediaSaveInterface saverCallback=new MediaSaveInterface() {
            @Override
            public void onSave() {

                Toast.makeText(OpenMedia.this, "Status saved successfully", Toast.LENGTH_SHORT).show();
                mainXml.downloadBtn.setEnabled(true);

            }

            @Override
            public void onError(String error) {
                Toast.makeText(OpenMedia.this, "Error saving Status", Toast.LENGTH_SHORT).show();
                mainXml.downloadBtn.setEnabled(true);

            }
        };

intentdata=getIntent();
        if(intentdata!=null){
            mediaType=intentdata.getStringExtra("type");
            path=intentdata.getStringExtra("path");
            isVideo=intentdata.getBooleanExtra("isVideo",false);
            if(mediaType!=null && path!=null){
                if(mediaType.equals("file")){
                    mainXml.videoView.setVisibility(isVideo?android.view.View.VISIBLE:android.view.View.GONE);
                    mainXml.imageView.setVisibility(isVideo?android.view.View.GONE:android.view.View.VISIBLE);
                    mainXml.downloadBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mainXml.downloadBtn.setEnabled(false);
                            MediaSaver.saveFromFile(RootApp.getContextGlobal(),path,saverCallback);
                        }
                    });
                    if(isVideo){
                        mainXml.videoView.setVideoPath(path);
                        mainXml.videoView.start();
                    }else{
                        File file=new File(path);
                        Glide.with(this).load(file).into(mainXml.imageView);
                    }
                }else if(mediaType.equals("uri")){
                    mainXml.videoView.setVisibility(isVideo?android.view.View.VISIBLE:android.view.View.GONE);
                    mainXml.imageView.setVisibility(isVideo?android.view.View.GONE:android.view.View.VISIBLE);
                    mainXml.downloadBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mainXml.downloadBtn.setEnabled(false);
                            MediaSaver.saveFromUri(RootApp.getContextGlobal(),path,saverCallback);
                        }
                    });
                    if(isVideo){
                        mainXml.videoView.setVideoPath(path);
                        mainXml.videoView.start();
                    }else{

                        Glide.with(this).load(path).into(mainXml.imageView);
                    }


                }
            }else{
                Toast.makeText(this, "Invalid data received", Toast.LENGTH_SHORT).show();
                finish();
            }

        }else{
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            finish();
        }



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return true;
    }
}