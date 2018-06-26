package com.android.booklist;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    // The Google Book API URL
    private static String GOOGLE_BOOK_API_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=20&q=";

    // ID to identify the loader
    private static final int BOOK_LOADER_ID = 1;

    // Variables globals
    private BookAdapter mAdapter;
    private ProgressBar mProgressBar;
    private TextView mInfoTextView;
    private EditText mSearchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Find a reference to progressBar in layout and set visibility gone
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);

        // Find a reference to infoTextView
        mInfoTextView = findViewById(R.id.info_text_view);

        // Create an adapter
        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        // Find a reference to resultListView and set the adapter
        ListView resultListView = findViewById(R.id.result_list_view);
        resultListView.setAdapter(mAdapter);

        // Configure to open book information on item click
        resultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Book book = mAdapter.getItem(position);

                // Open a book information if there is
                if (book.getmInfoLinkUrl() != null) {
                    Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                    urlIntent.setData(Uri.parse(book.getmInfoLinkUrl()));
                    startActivity(urlIntent);
                }

            }
        });

        // Find a reference to searchEditText
        mSearchEditText = findViewById(R.id.search_edit_text);

        // Verify if there is internet to continue
        // If internet exists so fill infoTextView with type word instruction
        if(!isInternetConnected()) {
            mInfoTextView.setText(R.string.no_internet_connection);
        } else if (mAdapter.isEmpty()) {
            mInfoTextView.setText(R.string.type_word_search);
        }

        // Find a reference to searchButton and configure onClickListener
        final Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Verify again if there is internet to continue when user click on search
                    if (!isInternetConnected()) {
                        mAdapter.clear();
                        mInfoTextView.setText(R.string.no_internet_connection);
                        return;
                    }

                    // clean the info text
                    mInfoTextView.setText(null);

                    // If the user type a word
                    if (mSearchEditText.getText().length() > 0) {
                        // Configure a bundle with google book url + typed word
                        Bundle bundle = new Bundle();
                        bundle.putString("url", GOOGLE_BOOK_API_URL + mSearchEditText.getText());

                        // Clean adapter and show progressBar
                        mAdapter.clear();
                        mProgressBar.setVisibility(View.VISIBLE);

                        // Restart loarder to make a request again with typed word
                        getLoaderManager().restartLoader(BOOK_LOADER_ID, bundle, BookActivity.this);
                    }
                }
        });

        // Start loader always the activity is created, so show data if there are information on adapter when rotate screen.
        getLoaderManager().initLoader(BOOK_LOADER_ID, null, BookActivity.this);
    }

    /**
     * Verify there is internet connection
     * @return
     */
    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        String url = null;

        // Verify if bundle have the request url
        if (args != null && args.containsKey("url")) {
            url = args.getString("url");
        }
        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        mProgressBar.setVisibility(View.GONE);

        // Verify internet connection when load finish
        if (!isInternetConnected()) {
            mAdapter.clear();
            mInfoTextView.setText(R.string.no_internet_connection);
            return;
        }

        // The first time that load is created the book list is null
        // so return
        if (books == null) {
            return;
        }

        // Set the book list found
        if (books.isEmpty()) {
            mInfoTextView.setText(R.string.no_books_found);
        } else {
            mInfoTextView.setText(null);
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        mAdapter.clear();
    }
}
