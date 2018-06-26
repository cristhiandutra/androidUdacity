package com.android.podcast.podcastplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.tool_bar_include);
        setSupportActionBar(myToolbar);

        View podcastPlayNow = findViewById(R.id.podcast_play_bar);
        podcastPlayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playerIntent = new Intent(v.getContext(), PlayerActivity.class);
                startActivity(playerIntent);
            }
        });

        ImageView podcastImageView = findViewById(R.id.podcast);
        podcastImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent podcastPlayListIntent = new Intent(v.getContext(), PodCastPlaylistActivity.class);
                startActivity(podcastPlayListIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_podcast:

                Intent addPodCastIntent = new Intent(this, AddPodcastActivity.class);
                startActivity(addPodCastIntent);

                return true;
            case R.id.action_donate:

                Intent donateIntent = new Intent(this, DonateActivity.class);
                startActivity(donateIntent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
