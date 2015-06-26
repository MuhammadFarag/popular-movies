package com.muhammadfarag.popularmovies;

import android.test.AndroidTestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Project: Popular Movies
 * Created by muhammad on 26/06/15.
 */
public class DataParsingTest extends AndroidTestCase{


    public void testCanRetrieveUpperLevelFields() throws JSONException {
        String testData = getContext().getString(R.string.server_example_payload);
        JSONObject jsonTestData = new JSONObject(testData);
        jsonTestData.getJSONArray("results");
        jsonTestData.getInt("page");
        jsonTestData.getInt("total_pages");
        jsonTestData.getInt("total_results");
    }

    public void testCanRetrievePageNumber() throws JSONException {
        String testData = getContext().getString(R.string.server_example_payload);
        JSONObject jsonTestData = new JSONObject(testData);
        int pageNumber = jsonTestData.getInt("page");
        assertEquals("Page number", 1, pageNumber);
    }

    public void testCanRetrieveTotalPages() throws JSONException {
        String testData = getContext().getString(R.string.server_example_payload);
        JSONObject jsonTestData = new JSONObject(testData);
        int pageNumber = jsonTestData.getInt("total_pages");
        assertEquals("Total number of pages", 11584, pageNumber);
    }

    public void testCanRetrieveTotalResults() throws JSONException {
        String testData = getContext().getString(R.string.server_example_payload);
        JSONObject jsonTestData = new JSONObject(testData);
        int pageNumber = jsonTestData.getInt("total_results");
        assertEquals("total number of results",231676,pageNumber);
    }

    public void testCheckSizeOfRetrievedResults() throws JSONException {
        String testData = getContext().getString(R.string.server_example_payload);
        JSONObject jsonTestData = new JSONObject(testData);
        JSONArray results = jsonTestData.getJSONArray("results");
        int pageNumber = results.length();
        assertEquals("Number of results per page",20,pageNumber);
    }

}
