package com.muhammadfarag.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.test.AndroidTestCase;

import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by muhammad on 25/06/15.
 */
public class ServerConnectionTest extends AndroidTestCase {

    public void testRetrievingMoviesDataFromServerWithoutKeyWillResultInUnauthorizedResponse() throws Exception {
        assertNull("Unauthorized response resulted in null value", getData(getContext(), ""));
    }


    public void testRetrievingMoviesDataFromServerReturnsResult() throws Exception {
        String apikey = getContext().getString(R.string.server_api_key);
        assertNotSame("Data retrieved is not empty", 0, getData(getContext(), apikey).length());
    }

    private static String getData(Context context, String apiKey) throws IOException {
        String baseUrl = context.getString(R.string.server_base_url);
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("api_key", apiKey).build();

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(uri.toString()).openConnection();
        httpURLConnection.connect();

        int responseCode = 0;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            responseCode = httpURLConnection.getResponseCode();
        }

        switch (responseCode) {
            case HttpURLConnection.HTTP_OK:
                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                return null;
            default:
                throw new IllegalStateException("Connection method is not equiped to handle this case");
        }
    }


}
