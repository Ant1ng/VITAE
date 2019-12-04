package com.example.vitae;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Display extends Entries {

    final String TAG = "DISPLAY";
    String videoID;
    int attempts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Intent intent = getIntent();
        videoID = intent.getStringExtra("videoID");
        Log.d(TAG, "onCreate: " + "called with " + videoID);

        attempts = 3;
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                Log.d(TAG, "onReady: " + "called with " + videoID);
                youTubePlayer.loadVideo(videoID, 0);
            }
            @Override
            public void onVideoId(@NonNull YouTubePlayer youTubePlayer, String videoId) {
                Log.d(TAG, "onVideoId: " + "called with " + videoID + " on attempt " + attempts);
                if (attempts > 0) {
                    youTubePlayer.loadVideo(videoID, 0);
                    attempts -= 1;
                }
            }
        });
        init(videoID, (ListView) findViewById(R.id.FAQ), getBaseContext());
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: " + "called with " + videoID);
        finish();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: " + "called with " + videoID);
        super.onResume();
    }
}
