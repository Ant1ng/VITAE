package com.example.vitae;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class VITAEVideoStore {

    private final static String filename = "VideoList";

    public static void writeVideo(VITAEVideo video, Context context) {
        try {
            FileOutputStream outputStream =
                    context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(video.title.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(video.uri.toString().getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(video.imgpath.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();
        } catch (Exception e) {
            Log.d("VITAEVideoStore", e.getMessage());
        }
    }

    public static ArrayList<VITAEVideo> readVideo(Context context) {
        ArrayList<VITAEVideo> videos = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                String title = line;
                String path = reader.readLine();
                Uri uri = Uri.parse(path);
                String imgpath = reader.readLine();
                VITAEVideo video = new VITAEVideo(title, uri, imgpath);
                videos.add(video);
            }
        }
        catch (Exception e) {
            return videos;
        }
        return videos;
    }
}
