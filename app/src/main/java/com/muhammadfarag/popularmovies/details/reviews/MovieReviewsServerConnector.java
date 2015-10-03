package com.muhammadfarag.popularmovies.details.reviews;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.muhammadfarag.popularmovies.R;
import com.muhammadfarag.popularmovies.network.UnauthorizedException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muhammadfarag on 10/3/15.
 */
public class MovieReviewsServerConnector {

    public static final String RESULTS_kEY = "results";
    private static final String TAG = "ReviewsServerConnector";
    private final String apikey;
    private Context context;
    private int mId;


    public MovieReviewsServerConnector(Context context, int id) {
        this.context = context;
        mId = id;
//        this.apikey = context.getString(R.string.server_api_key);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.apikey = sharedPreferences.getString("api-key", context.getString(R.string.server_api_key));
    }

    public String getData() throws IOException, UnauthorizedException {
        String baseUrl = this.context.getString(R.string.trailer_url);
        // TODO: store string constants in resource file(s)
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendPath(String.valueOf(mId))
                .appendPath("reviews")
                .appendQueryParameter("api_key", this.apikey).build();

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        httpURLConnection.connect();

        int responseCode;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            responseCode = httpURLConnection.getResponseCode();
        }

        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                return stringBuilder.toString();
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new UnauthorizedException();
            default:
                throw new IllegalStateException("Connection method is not equipped to handle this case");
        }
    }

    public Map<String, String> getReviews() throws JSONException, IOException, UnauthorizedException {
        Map<String, String> trailers = new HashMap<>();
        final JSONObject jsonObject = new JSONObject(getData());
        JSONArray results = jsonObject.getJSONArray(RESULTS_kEY);
        for (int i = 0; i < results.length(); i++) {
            JSONObject movieJsonObject = results.getJSONObject(i);
                trailers.put(movieJsonObject.getString("author"), movieJsonObject.getString("content"));
        }
        return trailers;
    }


}
