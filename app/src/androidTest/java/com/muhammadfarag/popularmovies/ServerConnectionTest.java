package com.muhammadfarag.popularmovies;

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
        String url = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.connect();
        int responseCode;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            responseCode = httpURLConnection.getResponseCode();
        }
        assertEquals(HttpURLConnection.HTTP_UNAUTHORIZED, responseCode);
    }

    public void testRetrievingMoviesDataFromServerWithKeyWillResultInSuccessResponse() throws Exception {
        String url = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";
        String apikey = getContext().getString(R.string.server_api_key);
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url + "&api_key=" + apikey).openConnection();
        httpURLConnection.connect();
        int responseCode;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (IOException e) {
            responseCode = httpURLConnection.getResponseCode();
        }
        assertEquals("Successful connection test, please make sure that the api_key is properly set",HttpURLConnection.HTTP_OK, responseCode);
    }

    public void testRetrievingMoviesDataFromServerReturnsResult() throws Exception{
        String url = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc";
        String apikey = getContext().getString(R.string.server_api_key);
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url + "&api_key=" + apikey).openConnection();
        httpURLConnection.connect();
        InputStream inputStream = httpURLConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        assertNotNull(inputStream);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line + "\n");
        }
        assertNotSame("Data retrieved is not empty",0,buffer.length());
    }

}
