package com.android.podcast.podcastplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PodCastPlaylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_playlist);

        Toolbar myToolbar = findViewById(R.id.tool_bar_include);
        setSupportActionBar(myToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        LinearLayout episodeLinearLayout = findViewById(R.id.episode);
        episodeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent episodeIntent = new Intent(v.getContext(), PodcastEpisodeActivity.class);
                startActivity(episodeIntent);
            }
        });

        ImageView buttonPlay = findViewById(R.id.button_play);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playerIntent = new Intent(v.getContext(), PlayerActivity.class);
                startActivity(playerIntent);
            }
        });
    }
}
