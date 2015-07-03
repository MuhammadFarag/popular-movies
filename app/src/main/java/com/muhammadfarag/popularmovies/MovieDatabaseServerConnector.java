package com.muhammadfarag.popularmovies;

import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Project: Popular Movies
 * Created by muhammad on 26/06/15.
 */
class MovieDatabaseServerConnector {

    private Context context;
    private final String apikey;

    public MovieDatabaseServerConnector(Context context) {
        this.context = context;
        this.apikey = context.getString(R.string.server_api_key);

    }

    public String getData() throws IOException, UnauthorizedException {
        String baseUrl = this.context.getString(R.string.server_base_url);
        // TODO: store string constants in resource file(s)
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
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

    public String getPage(int page) throws IOException, UnauthorizedException{
        String baseUrl = this.context.getString(R.string.server_base_url);
        // TODO: store string constants in resource file(s)
        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("sort_by", "popularity.desc")
                .appendQueryParameter("api_key", this.apikey)
                .appendQueryParameter("page", String.valueOf(page))
                .build();

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
