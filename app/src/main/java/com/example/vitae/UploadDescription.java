package com.example.vitae;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UploadDescription extends AppCompatActivity {

    private Uri uri;
    private String imgPath;

    public final static int pick_photo_code = 235;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_description);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("uri")) {
            uri = Uri.parse(extras.getString("uri"));
        }
    }

    public void ImageSelect(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Photo"), pick_photo_code);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == pick_photo_code) {
            Uri selectedMediaUri = data.getData();
            imgPath = Upload.getPath(getBaseContext(), selectedMediaUri);
        }
    }

    public void Submit(View v) {
        TextView titleView = findViewById(R.id.titleView);
        String title;

        if (titleView == null) { title = "title"; }
        else { title = titleView.getText().toString(); }
        if (imgPath == null) {
            VITAEVideoStore.writeVideo(new VITAEVideo(
                    title,
                    uri
            ), getBaseContext());
        } else {
            VITAEVideoStore.writeVideo(new VITAEVideo(
                    title,
                    uri,
                    imgPath
            ), getBaseContext());
        }
        finish();
    }
}
