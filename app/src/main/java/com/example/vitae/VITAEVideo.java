package com.example.vitae;

import android.graphics.Bitmap;
import android.net.Uri;

public class VITAEVideo {
    String imgpath;
    String title;
    Uri uri;

    VITAEVideo(String title, Uri uri) {
        this(title, uri, "N/A");
    }

    VITAEVideo(String title, Uri uri, String imgpath) {
        this.title = title;
        this.uri = uri;
        this.imgpath = imgpath;
    }
}