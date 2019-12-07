package com.example.vitae;

public class VITAEVideo {
    String path;
    String imgpath;
    String title;

    VITAEVideo(String title, String path) {
        this(title, path, "N/A");
    }

    VITAEVideo(String title, String path, String imgpath) {
        this.title = title;
        this.path = path;
        this.imgpath = imgpath;
    }
}