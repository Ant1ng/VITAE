package com.example.vitae;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VITAEDisplay extends Entries {

    private VideoView videoView;
    private MediaController mediaController;
    private String path;
    private String name = "20190616_112213";
    private String TAG = "LOCAL_VIDEO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent = getIntent();
        this.path = intent.getStringExtra("path");

        videoView = findViewById(R.id.videoView);

        if (mediaController == null) {
            mediaController = new MediaController(VITAEDisplay.this);
            mediaController.setAnchorView(findViewById(R.id.linearLayout));
        }

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission is granted");
            startMovie();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        init(name, (ListView) findViewById(R.id.FAQ), getBaseContext());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            startMovie();
        }
    }

    public void startMovie() {
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
    }
}