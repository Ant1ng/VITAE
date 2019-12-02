package com.example.vitae;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class Display extends Entries {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        init("lol", (ListView) findViewById(R.id.FAQ));
    }
}
