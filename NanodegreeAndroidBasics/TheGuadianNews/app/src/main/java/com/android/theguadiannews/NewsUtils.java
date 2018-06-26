package com.android.theguadiannews;

import android.content.Context;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsUtils {

    private static final String LOG_TAG = NewsUtils.class.getSimpleName();

    public static List<News> fetchNewsData(String guardianAPIUrl, Context context) {
        // Create an url object
        URL uri = createUrl(guardianAPIUrl);

        String json = null;
        try {
            // Make a http request and return json string response
            json = makeHttpRequest(uri, context);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing inputStream", e);
        }

        // Extract list of news from json string
        return extractNewsFromJson(json);
    }

    private static List<News> extractNewsFromJson(String json) {
        if (json == null) {
            return null;
        }

        List<News> news = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json).getJSONObject("response");
            JSONArray results = jsonObject.getJSONArray("results");

            for (int x = 0 ; x < results.length() ; x++) {
                JSONObject newsJsonObject = results.getJSONObject(x);

                String section = null;
                if (!newsJsonObject.isNull("sectionName")) {
                    section = newsJsonObject.getString("sectionName");
                }

                String title = null;
                if (!newsJsonObject.isNull("webTitle")) {
                    title = newsJsonObject.getString("webTitle");
                }

                String url = null;
                if (!newsJsonObject.isNull("webUrl")) {
                    url = newsJsonObject.getString("webUrl");
                }

                Date publication = null;
                String datePublication = null;
                if (!newsJsonObject.isNull("webPublicationDate")) {
                    try {
                        datePublication = newsJsonObject.getString("webPublicationDate");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        publication = sdf.parse(datePublication);
                    } catch (ParseException e) {
                        Log.e(LOG_TAG, "Error conventing webPublication date " + datePublication);
                    }
                }

                String thumbnail = null;
                if (!newsJsonObject.isNull("fields")) {
                    JSONObject fields = newsJsonObject.getJSONObject("fields");

                    if (!fields.isNull("thumbnail")) {
                        thumbnail = fields.getString("thumbnail");
                    }
                }

                news.add(new News(title, section, url, thumbnail, publication));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the News Json results", e);
        }

        return news;
    }

    private static String makeHttpRequest(URL url, Context context) throws IOException {
        if (url == null) {
            return null;
        }

        String json = null;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(context.getString(R.string.http_request_method));
            httpURLConnection.setReadTimeout(context.getResources().getInteger(R.integer.http_read_time_out));
            httpURLConnection.setConnectTimeout(context.getResources().getInteger(R.integer.http_connection_time_out));

            if (httpURLConnection.getResponseCode() == context.getResources().getInteger(R.integer.http_response_ok)) {
                inputStream = httpURLConnection.getInputStream();
                json = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code "+ httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem to retrieve news", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return json;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
        }

        return sb.toString();
    }

    /**
     * Create url object
     *
     * @param guardianAPIUrl
     * @return
     */
    private static URL createUrl(String guardianAPIUrl) {
        URL url = null;

        try {
            url = new URL(guardianAPIUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating url");
        }

        return url;
    }
}
