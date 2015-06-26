package com.muhammadfarag.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.test.AndroidTestCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Project: Popular Movies
 * Created by muhammad on 25/06/15.
 */
public class ServerConnectionTest extends AndroidTestCase {


    public void testRetrievingMoviesDataWithoutProperKeyWillResultInUnauthorizedException() {
        try {
            String incorrectApiKey = "";
            getData(getContext(), incorrectApiKey);
            fail("Expected an Exception to be thrown");
        } catch (UnauthorizedException ex) {
            // Nothing to be done... test passed
        } catch (IOException e) {
            fail("Expected Unauthorized Exception but found IOException");
        }
    }

    public void testRetrievingMoviesDataFromServerReturnsResult() throws Exception {
        String apikey = getContext().getString(R.string.server_api_key);
        assertNotSame("Data retrieved is not empty", 0, getData(getContext(), apikey).length());
    }

    private static String getData(Context context, String apiKey) throws IOException, UnauthorizedException {
        String baseUrl = context.getString(R.string.server_base_url);
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("api_key", apiKey).build();

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


}
