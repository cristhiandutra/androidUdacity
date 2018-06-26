package com.android.theguadiannews;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static String THE_GUARDIAN_API_URL = "https://content.guardianapis.com/search";
    private static final int NEWS_LOADER_ID = 0;

    private NewsAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        // Find a reference to TextView emptyView
        mEmptyView = findViewById(R.id.empty_view);

        // Find a reference to NewsList
        ListView newsList = findViewById(R.id.news_list);

        // Create an adapter
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set adapter and EmptyView
        newsList.setAdapter(mAdapter);
        newsList.setEmptyView(mEmptyView);

        // Configure to open news information on item click
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News news = mAdapter.getItem(position);

                if (news.getUrl() != null) {
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                    urlIntent.setData(Uri.parse(news.getUrl()));
                    startActivity(urlIntent);
                }
            }
        });

        // Find a reference to ProgressBar
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        if (isInternetConnected()) {
            getLoaderManager().initLoader(NEWS_LOADER_ID, null, this);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mEmptyView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Uri.parse(THE_GUARDIAN_API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String pageSize = sharedPreferences.getString(getString(R.string.settings_page_size_key), getString(R.string.settings_page_size_default));

        uriBuilder.appendQueryParameter(getString(R.string.query_api_key), getString(R.string.query_api_key_value));
        uriBuilder.appendQueryParameter(getString(R.string.query_show_fields), getString(R.string.query_show_fields_value));
        uriBuilder.appendQueryParameter(getString(R.string.query_page_size), pageSize);

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter.clear();

        mEmptyView.setText(R.string.no_news_found);

        if (news == null) {
            return;
        }

        mAdapter.addAll(news);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
