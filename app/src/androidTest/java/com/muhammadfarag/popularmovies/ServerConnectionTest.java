package com.muhammadfarag.popularmovies;

import junit.framework.TestCase;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by muhammad on 25/06/15.
 */
public class ServerConnectionTest extends TestCase {

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
}
