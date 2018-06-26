package com.android.booklist;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cristhian.dutra on 24/05/2018.
 */
public class BookUtils {

    private static final String LOG_TAG = BookUtils.class.getSimpleName();

    /**
     * Return a list of books from Google Book API
     *
     * @param googleBookAPIUrl
     * @return
     */
    public static List<Book> fetchBookData(String googleBookAPIUrl) {

        URL url = createURL(googleBookAPIUrl);

        String json = null;
        try {
            json = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing inputStream", e);
        }

        return extractBookFromJson(json);
    }

    /**
     * Extract book list from a json
     * @param json
     * @return
     */
    private static List<Book> extractBookFromJson(String json) {
        if (json == null) {
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray items = jsonObject.getJSONArray("items");

            for (int x = 0 ; x < items.length() ; x++) {
                JSONObject book = items.getJSONObject(x);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");

                List<String> authors = new ArrayList<>();
                JSONArray authorArray = volumeInfo.getJSONArray("authors");
                for (int y = 0 ; y < authorArray.length() ; y++) {
                    authors.add(authorArray.getString(y));
                }

                String thumbnail = null;
                if (!volumeInfo.isNull("imageLinks")) {
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                    thumbnail = imageLinks.getString("thumbnail");
                }

                String infoLink = null;
                if (!volumeInfo.isNull("infoLink")) {
                    infoLink = volumeInfo.getString("infoLink");
                }

                books.add(new Book(title, authors, thumbnail, infoLink));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the Book Json results");
        }

        return books;
    }

    /**
     * Make HttpRequest based on Google API url
     * @param url
     * @return
     * @throws IOException
     */
    private static String makeHttpRequest(URL url) throws IOException {
        if (url == null) {
            return null;
        }

        String json = null;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                json = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code" + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem to retrieve books");
        }finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return json;
    }

    /**
     * Transform InputStream to Json String
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }

    /**
     * Create url object
     * @param googleBookAPIUrl
     * @return
     */
    private static URL createURL(String googleBookAPIUrl) {
        URL url = null;
        try {
            url = new URL(googleBookAPIUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating url");
        }

        return url;
    }

}
