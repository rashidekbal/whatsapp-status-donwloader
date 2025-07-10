package com.rtech.statusdownloaderwa.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.rtech.statusdownloaderwa.AppRoot.RootApp;
import com.rtech.statusdownloaderwa.R;
import com.rtech.statusdownloaderwa.adapters.ViewPagerAdapter;
import com.rtech.statusdownloaderwa.databinding.ActivityMainBinding;
import com.rtech.statusdownloaderwa.utils.PermissionManager;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainXml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mainXml=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainXml.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(mainXml.toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);




        checkPermissionAndStart();
        MobileAds.initialize(this);
        AdRequest adRequest=new AdRequest.Builder().build();
        //ad loaded
        mainXml.adView.loadAd(adRequest);



    }

    private void checkPermissionAndStart(){
        if (Build.VERSION.SDK_INT<=Build.VERSION_CODES.Q){
            if(!PermissionManager.isAllGranted(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})){
                PermissionManager.getPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
            }
            else {
                //start mainFunction
                mainFunction();
            }

        }else{
            if (!hasStatusFolderPermission()) {
                new AlertDialog.Builder(this).setTitle("Permission Required").setMessage("StoragePermission Required for accessing status")
                        .setPositiveButton("Provide", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showDialogForSAF();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();

            }else
            {
                mainFunction();
            }



        }

    }

    private void showDialogForSAF() {
        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.storage_access_dialog);
        dialog.setCancelable(false);
        AppCompatButton okBtn=dialog.findViewById(R.id.getPermissionBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = "primary:Android/media/com.whatsapp/WhatsApp/Media/.Statuses";
                Uri initialUri = Uri.parse("content://com.android.externalstorage.documents/tree/" + Uri.encode(path));

                Intent accessFolderIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                accessFolderIntent.putExtra("android.provider.extra.INITIAL_URI", initialUri);
                accessFolderIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivityForResult(accessFolderIntent, 201);
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void mainFunction(){
        ViewPagerAdapter viewpagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        mainXml.viewPager.setAdapter(viewpagerAdapter);
        mainXml.tabLayout.setupWithViewPager(mainXml.viewPager);




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==101){
            if(!PermissionManager.isAllGranted(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})){
                new AlertDialog.Builder(this).setTitle("Permission Required").setMessage("StoragePermission Required for accessing status")
                        .setPositiveButton("Go to setting", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri path=Uri.fromParts("package",getPackageName(),null);
                                startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(path));
                            }
                        }).setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
            }
            else {
                //start mainFunction
                mainFunction();
            }

        }
    }

    private boolean hasStatusFolderPermission(){
        SharedPreferences prefs= RootApp.getAppPrefs();
        String uriStr=prefs.getString("status_uri",null);

        if(uriStr==null)return false;
        Uri uri=Uri.parse(uriStr);
        for(UriPermission permission: getContentResolver().getPersistedUriPermissions()){
            if(permission.getUri().equals(uri)&&permission.isReadPermission()){
                return true;
            }
        }
        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 201 && resultCode == RESULT_OK && data != null) {
            Uri treeUri = data.getData();
            String docId = DocumentsContract.getTreeDocumentId(treeUri);
            if (!docId.endsWith(".Statuses")) {
                new AlertDialog.Builder(this).setTitle("Wrong Directory Selected").setMessage("Please follow the give Instruction")
                        .setPositiveButton("Provide", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showDialogForSAF();
                                dialog.dismiss();
                            }
                        }).setNegativeButton("No thanks", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).show();
                return;
            }

            getContentResolver().takePersistableUriPermission(
                    treeUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            );

            RootApp.getAppPrefs().edit().putString("status_uri", treeUri.toString()).apply();

            // Start mainFunction now
            mainFunction();
        }
    }

}