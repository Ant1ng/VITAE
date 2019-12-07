package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UploadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
    }

    public void click(View v) {
        VITAEVideo video = new VITAEVideo("Get Dunked On!", "/sdcard/DCIM/Camera/20190616_112213.mp4", "/sdcard/Pictures/Screenshots/Screenshot_2016-07-16-15-47-19.png");
        VITAEVideoStore.writeVideo(video, getBaseContext());

        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    
}
